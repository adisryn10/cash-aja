package mtf.project.repository;

import mtf.project.model.PromoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromoDb extends JpaRepository<PromoModel, Long>{
    Optional<PromoModel> findById(Long id);
    List<PromoModel> findByStatusPosting(Integer id);
}
