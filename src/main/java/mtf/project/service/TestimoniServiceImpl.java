package mtf.project.service;

import mtf.project.model.TestimoniModel;
import mtf.project.repository.TestimoniDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestimoniServiceImpl implements TestimoniService{

    @Autowired
    TestimoniDb testimoniDb;

    @Override
    public List<TestimoniModel> getAllTestimoni() {
        return testimoniDb.findAll();
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
}
