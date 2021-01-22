package mtf.project.service;

import mtf.project.model.HalamanModel;
import mtf.project.repository.HalamanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HalamanServiceImpl implements HalamanService{

    @Autowired
    HalamanDb halamanDb;

    @Override
    public HalamanModel createHalaman(HalamanModel halaman) {
        return halamanDb.save(halaman);
    }

    @Override
    public List<HalamanModel> getAllHalaman() {
        return halamanDb.findAll();
    }

    @Override
    public List<HalamanModel> getAllHalamanByStatusPosting(Integer statusPosting) {
        return halamanDb.findByStatusPosting(statusPosting);
    }

    @Override
    public HalamanModel getHalamanById(Long id) {
        return halamanDb.findById(id).get();
    }

    @Override
    public void updateHalaman(HalamanModel halaman) {
        halamanDb.save(halaman);
    }

    @Override
    public void deleteHalaman(HalamanModel halaman) {
        halamanDb.delete(halaman);
    }

}
