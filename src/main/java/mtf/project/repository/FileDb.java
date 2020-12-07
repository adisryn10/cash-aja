package mtf.project.repository;

import mtf.project.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDb extends JpaRepository<FileModel,Long> {
    Optional<FileModel> findById(String id);
}
