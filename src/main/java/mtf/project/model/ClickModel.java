package mtf.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "click")
public class ClickModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Date date;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @NotNull
    @Column(name = "totalClick", nullable = false)
    private Integer totalClick;

    public void setId(Long id) {
        this.id = id;
    }

    public void setClickDate(Date date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTotalClick(Integer totalClick) {
        this.totalClick = totalClick;
    }

    public Long getId() {
        return id;
    }

    public Date getClickDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public Integer getTotalClick() {
        return totalClick;
    }
}