package mtf.project.repository;

import mtf.project.model.PromoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PromoDb extends JpaRepository<PromoModel, Long>{
    
}
