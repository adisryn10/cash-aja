package mtf.project.service;

import mtf.project.model.PromoModel;
import mtf.project.repository.PromoDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PromoServiceImpl implements PromoService {

    @Autowired
    PromoDb promoDb;

    @Override
    public List<PromoModel> getAllPromo() {
        return promoDb.findAll();
    }

    @Override
    public List<PromoModel> getAllPromoByStatusPosting(Integer statusPosting) {
        return promoDb.findByStatusPosting(statusPosting);
    }


    @Override
    public PromoModel getPromoById(Long id) {
        return promoDb.findById(id).get();
    }

    @Override
    public PromoModel createPromo(PromoModel promo) {
        return promoDb.save(promo);
    }

    @Override
    public void deletePromo(PromoModel promo) {
        promoDb.delete(promo);
    }

    @Override
    public void updatePromo(PromoModel promo) {
        promoDb.save(promo);
    }
}
