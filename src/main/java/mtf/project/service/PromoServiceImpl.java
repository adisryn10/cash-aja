package mtf.project.service;

import mtf.project.model.PromoModel;
import mtf.project.repository.PromoDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoServiceImpl implements PromoService{

    @Autowired
    PromoDb testimoniDb;

    @Override
    public List<PromoModel> getAllPromo() {
        return testimoniDb.findAll();
    }

    @Override
    public PromoModel getPromoById(Long id) {
        return testimoniDb.findById(id).get();
    }

    @Override
    public PromoModel createPromo(PromoModel testimoni) {
        return testimoniDb.save(testimoni);
    }

    @Override
    public void deletePromo(PromoModel testimoni) {
        testimoniDb.delete(testimoni);
    }

    @Override
    public void updatePromo(PromoModel testimoni) {
        testimoniDb.save(testimoni);
    }
}
