package mtf.project.repository;

import mtf.project.model.HalamanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface HalamanDb extends JpaRepository<HalamanModel, Long>{
    List<HalamanModel> findAll();
    Optional<HalamanModel> findById(Long id);
    List<HalamanModel> findByStatusPosting(Integer id);
}
