package mtf.project.service;

import mtf.project.model.ClickModel;
import mtf.project.repository.ClickDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ClickServiceImpl implements ClickService {

    @Autowired
    ClickDb clickDb;

    @Override
    public ClickModel createClick(ClickModel click) {
        return clickDb.save(click);
    }

    @Override
    public List<ClickModel> getAllClick() {
        return clickDb.findAll();
    }

    @Override
    public List<ClickModel> getAllClickByCategory(String category) {
        return clickDb.findClickByCategory(category);
    }

    @Override
    public ClickModel getClickById(Long id) {
        return clickDb.findById(id).get();
    }

    @Override
    public void updateClick(ClickModel click) {
        clickDb.save(click);
    }

    @Override
    public List<ClickModel> getAllClickByDate(Date date) {
        return clickDb.findByDate(date);
    }
}
