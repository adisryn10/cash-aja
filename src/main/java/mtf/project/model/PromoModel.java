package mtf.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name="promo")
public class PromoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "judul", nullable = false)
    private String judul;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banner", referencedColumnName = "id")
    private FileModel banner;

    @NotNull
    @Column(name = "detail", length = Integer.MAX_VALUE, nullable = false)
    private String detail;

    @NotNull
    @Column(name = "statusPosting", nullable = false)
    private Integer statusPosting;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "latest_author", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserRoleModel latestAuthor;

    @NotNull
    @Column(name = "latest_edit", nullable = false)
    private Date latestEdit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getJudul() {
        return judul;
    }
    
    public String getDetail() {
        return detail;
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

    public FileModel getBanner() {
        return banner;
    }

    public void setBanner(FileModel banner) {
        this.banner = banner;
    }

    public Integer getStatusPosting() {
        return statusPosting;
    }

    public void setStatusPosting(Integer statusPosting) {
        this.statusPosting = statusPosting;
    }
    
}
    