package mtf.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mtf.project.model.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="userRole")
public class UserRoleModel implements Serializable{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @Size(max = 200)
    @Column(name="username", nullable = false)
    private String username;

    @NotNull
    @Size(max = 200)
    @Column(name="password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RoleModel role;

    @OneToOne(mappedBy = "user")
    private KreditModel kredit;

    @OneToOne(mappedBy = "user")
    private AngsuranModel angsuran;

    @OneToOne(mappedBy = "user")
    private AsuransiModel asuransi;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_integrity")
    private UserIntegrityModel userIntegrity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_identity")
    private UserIdentityModel userIdentity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }
    public KreditModel getKredit() {
        return this.kredit;
    }
    public void setKredit(KreditModel kredit) {
        this.kredit = kredit;
    }
    public AngsuranModel getAngsuran() {
        return this.angsuran;
    }
    public void setAngsuran(AngsuranModel angsuran) {
        this.angsuran = angsuran;
    }
    public AsuransiModel getAsuransi() {
        return this.asuransi;
    }

    public void setAsuransi(AsuransiModel asuransi) {
        this.asuransi = asuransi;
    }
    public UserIntegrityModel getUserIntegrity() {
        return this.userIntegrity;
    }
    public void setUserIntegrity(UserIntegrityModel userIntegrity) {
        this.userIntegrity = userIntegrity;
    }
    public UserIdentityModel getUserIdentity() {
        return this.userIdentity;
    }
    public void setUserIdentity(UserIdentityModel userIdentity) {
        this.userIdentity = userIdentity;
    }
}