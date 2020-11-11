package mtf.project.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="promo")
public class PromoModel implements Serializable {
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
    private byte[] detail;

    public void setId(Long id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    
    public void setBanner(byte[] banner) {
        this.banner = banner;
    }

    public void setDetail(byte[] detail) {
        this.detail = detail;
    }

    
}
    