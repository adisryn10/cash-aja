package mtf.project.repository;

import mtf.project.model.TestimoniModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestimoniDb extends JpaRepository<TestimoniModel, Long> {
    Optional<TestimoniModel> findById(Long id);
    List<TestimoniModel> findByStatusPosting(Integer id);
}
