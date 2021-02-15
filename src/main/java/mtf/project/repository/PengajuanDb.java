package mtf.project.repository;

import mtf.project.model.PengajuanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PengajuanDb extends JpaRepository<PengajuanModel, Long> {
    List<PengajuanModel> findAll();
    Optional<PengajuanModel> findById(Long id);
}
