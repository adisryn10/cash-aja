package mtf.project.service;

import mtf.project.model.ClickModel;

import java.util.Date;
import java.util.List;

public interface ClickService {

    ClickModel createClick(ClickModel click);

    List<ClickModel> getAllClick();

    List<ClickModel> getAllClickByCategory(String category);

    List<ClickModel> getAllClickByDate(Date date);

    ClickModel getClickById(Long id);

    void updateClick(ClickModel click);
}