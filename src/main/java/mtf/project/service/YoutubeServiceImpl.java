package mtf.project.service;

import mtf.project.model.YoutubeModel;
import mtf.project.repository.YoutubeDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoutubeServiceImpl implements YoutubeService{

    @Autowired
    YoutubeDb youtubeDb;

    @Override
    public YoutubeModel updateYoutube(YoutubeModel youtube) {
        youtube.setLink(youtube.getLink());
        return youtubeDb.save(youtube);
    }

    @Override
    public YoutubeModel getYoutubeById(Long id) {
        return youtubeDb.findById(id).orElse(null);
    }
}
