package mtf.project.service;

import mtf.project.model.FaqModel;

import java.util.List;

public interface FaqService {
    FaqModel createFaq(FaqModel faq);
    List<FaqModel> getAllFaq();
    FaqModel getFaqById(Long id);

    void updateFaq(FaqModel faq);

    void deleteFaq(FaqModel faq);
}
