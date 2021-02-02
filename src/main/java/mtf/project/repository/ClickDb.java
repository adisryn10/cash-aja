package mtf.project.repository;

import mtf.project.model.ClickModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClickDb extends JpaRepository<ClickModel, Long> {

    List<ClickModel> findAll();

    Optional<ClickModel> findById(Long id);

    List<ClickModel> findClickByCategory(String category);

    List<ClickModel> findByDate(Date date);

}