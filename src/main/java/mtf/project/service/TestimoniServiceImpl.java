    package mtf.project.service;

import mtf.project.model.TestimoniModel;
import mtf.project.repository.TestimoniDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TestimoniServiceImpl implements TestimoniService {

    @Autowired
    TestimoniDb testimoniDb;

    @Override
    public List<TestimoniModel> getAllTestimoni() {
        return testimoniDb.findAll();
    }

    @Override
    public List<TestimoniModel> getAllTestimoniByStatusPosting(Integer statusPosting) {
        return testimoniDb.findByStatusPosting(statusPosting);
    }

    @Override
    public TestimoniModel getTestimoniById(Long id) {
        return testimoniDb.findById(id).get();
    }

    @Override
    public TestimoniModel createTestimoni(TestimoniModel testimoni) {
        return testimoniDb.save(testimoni);
    }

    @Override
    public void deleteTestimoni(TestimoniModel testimoni) {
        testimoniDb.delete(testimoni);
    }

    @Override
    public void updateTestimoni(TestimoniModel testimoni) {
        testimoniDb.save(testimoni);
    }
}
