package mtf.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file", referencedColumnName = "id")
    private FileModel file;

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
}
