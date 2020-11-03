package mtf.project.repository;

import mtf.project.model.PengajuanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PengajuanDb extends JpaRepository<PengajuanModel, Long> {
}
