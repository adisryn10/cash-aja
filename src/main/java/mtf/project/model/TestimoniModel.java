package mtf.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="testimoni")
public class TestimoniModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "testimoni", nullable = false)
    private String testimoni;

    @NotNull
    @Column(name = "statusPosting", nullable = false)
    private Integer statusPosting;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file", referencedColumnName = "id")
    private FileModel file;

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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTestimoni() {
        return testimoni;
    }

    public void setTestimoni(String testimoni) {
        this.testimoni = testimoni;
    }

    public FileModel getFile() {
        return file;
    }

    public void setFile(FileModel file) {
        this.file = file;
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

    public Integer getStatusPosting() {
        return statusPosting;
    }

    public void setStatusPosting(Integer statusPosting) {
        this.statusPosting = statusPosting;
    }
}
