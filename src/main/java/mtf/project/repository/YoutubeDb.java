package mtf.project.repository;

import mtf.project.model.YoutubeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YoutubeDb extends JpaRepository<YoutubeModel, Long> {
}
