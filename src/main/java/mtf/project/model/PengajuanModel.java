package mtf.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pengajuan")
public class PengajuanModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "noktp", nullable = false)
    private String noktp;

    @NotNull
    @Column(name = "lokasi", nullable = false)
    private String lokasi;

    @NotNull
    @Column(name = "hubungi", nullable = false)
    private String hubungi;

    @Column(name = "waktu_permintaan")
    private String waktuPermintaan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNoktp() {
        return noktp;
    }

    public void setNoktp(String noktp) {
        this.noktp = noktp;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getHubungi() {
        return hubungi;
    }

    public void setHubungi(String hubungi) {
        this.hubungi = hubungi;
    }

    public String getWaktuPermintaan() {
        return waktuPermintaan;
    }

    public void setWaktuPermintaan(String waktuPermintaan) {
        this.waktuPermintaan = waktuPermintaan;
    }
}
