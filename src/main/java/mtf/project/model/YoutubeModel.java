package mtf.project.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="youtube")
public class YoutubeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "link", nullable = false)
    private String link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
