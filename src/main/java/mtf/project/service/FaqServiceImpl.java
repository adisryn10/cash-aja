package mtf.project.service;

import mtf.project.model.FaqModel;
import mtf.project.repository.FaqDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqServiceImpl implements FaqService{

    @Autowired
    FaqDb faqDb;

    @Override
    public FaqModel createFaq(FaqModel faq) {
        return faqDb.save(faq);
    }

    @Override
    public List<FaqModel> getAllFaq() {
        return faqDb.findAll();
    }

    @Override
    public List<FaqModel> getAllFaqByStatusPosting(Integer statusPosting) {
        return faqDb.findByStatusPosting(statusPosting);
    }

    @Override
    public FaqModel getFaqById(Long id) {
        return faqDb.findById(id).get();
    }

    @Override
    public void updateFaq(FaqModel faq) {
        faqDb.save(faq);
    }

    @Override
    public void deleteFaq(FaqModel faq) {
        faqDb.delete(faq);
    }
}
