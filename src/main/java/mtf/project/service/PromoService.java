package mtf.project.service;

import mtf.project.model.PromoModel;

import java.util.List;

public interface PromoService {
    List<PromoModel> getAllPromo();
    List<PromoModel> getAllPromoByStatusPosting(Integer statusPosting);
    PromoModel getPromoById(Long id);
    PromoModel createPromo(PromoModel testimoni);
    void deletePromo(PromoModel testimoni);
    void updatePromo(PromoModel testimoni);
}
