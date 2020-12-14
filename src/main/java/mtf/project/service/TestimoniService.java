package mtf.project.service;

import mtf.project.model.TestimoniModel;

import java.util.List;

public interface TestimoniService {
    List<TestimoniModel> getAllTestimoni();
    TestimoniModel getTestimoniById(Long id);
    TestimoniModel createTestimoni(TestimoniModel testimoni);
    void deleteTestimoni(TestimoniModel testimoni);
    void updateTestimoni(TestimoniModel testimoni);
}
