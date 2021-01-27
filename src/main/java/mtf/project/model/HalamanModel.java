package mtf.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="halaman")
public class HalamanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "judul", nullable = false)
    private String judul;

    @NotNull
    @Column(name = "konten", nullable = false,  length = 10485760)
    private String konten;

    @NotNull
    @Column(name = "statusPosting", nullable = false)
    private Integer statusPosting;

    @NotNull
    @Column(name = "latest_edit", nullable = false)
    private Date latestEdit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "latest_author", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserRoleModel latestAuthor;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJudul(){
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKonten(){
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }

    public UserRoleModel getLatestAuthor() {
        return latestAuthor;
    }

    public void setLatestAuthor(UserRoleModel latestAuthor) {
        this.latestAuthor = latestAuthor;
    }

    public Date getLatestEdit() {
        return latestEdit;
    }

    // public String getLatestEdit() {
    //     DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
    //     String strDate = dateFormat.format(latestEdit);
    //     return strDate;
    // }

    public void setLatestEdit(Date latestEdit) {
        this.latestEdit = latestEdit;
    }

    public Integer getStatusPosting() {
        return statusPosting;
    }

    public void setStatusPosting(Integer statusPosting) {
        this.statusPosting = statusPosting;
    }
    
}
    