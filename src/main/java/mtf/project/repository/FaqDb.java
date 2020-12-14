package mtf.project.repository;

import mtf.project.model.FaqModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FaqDb extends JpaRepository<FaqModel, Long> {
    List<FaqModel> findAll();
    Optional<FaqModel> findById(Long id);
}
