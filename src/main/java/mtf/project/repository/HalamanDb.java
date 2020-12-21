package mtf.project.repository;

import mtf.project.model.HalamanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface HalamanDb extends JpaRepository<HalamanModel, Long>{
    
}
