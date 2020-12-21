package mtf.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="promo")
public class HalamanModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "judul", nullable = false)
    private String judul;

    @NotNull
    @Column(name = "banner", length = Integer.MAX_VALUE, nullable = false)
    private byte[] banner;

    @NotNull
    @Column(name = "detail", length = Integer.MAX_VALUE, nullable = false)
    private String detail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "latest_author", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserRoleModel latestAuthor;

    @NotNull
    @Column(name = "latest_edit", nullable = false)
    private Date latestEdit;

    @NotNull
    @Column(name = "statusPosting", nullable = false)
    private Integer statusPosting;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file", referencedColumnName = "id")
    private FileModel file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    
    public void setBanner(byte[] banner) {
        this.banner = banner;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public void setLatestEdit(Date latestEdit) {
        this.latestEdit = latestEdit;
    }

    public FileModel getFile() {
        return file;
    }

    public void setFile(FileModel file) {
        this.file = file;
    }

    public Integer getStatusPosting() {
        return statusPosting;
    }

    public void setStatusPosting(Integer statusPosting) {
        this.statusPosting = statusPosting;
    }
    
}
    