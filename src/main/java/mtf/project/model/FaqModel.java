package mtf.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="faq")
public class FaqModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "pertanyaan", nullable = false)
    private String pertanyaan;

    @NotNull
    @Column(name = "jawaban", nullable = false, length = 10485760)
    private String jawaban;

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

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public Integer getStatusPosting() {
        return statusPosting;
    }

    public void setStatusPosting(Integer statusPosting) {
        this.statusPosting = statusPosting;
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
}
