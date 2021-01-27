package mtf.project.service;

import mtf.project.model.YoutubeModel;

public interface YoutubeService {
    YoutubeModel updateYoutube(YoutubeModel youtube);
    YoutubeModel getYoutubeById(Long id);
}
