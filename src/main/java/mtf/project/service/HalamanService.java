package mtf.project.service;

import mtf.project.model.HalamanModel;

import java.util.List;

public interface HalamanService {
    HalamanModel createHalaman(HalamanModel halaman);
    List<HalamanModel> getAllHalaman();
    List<HalamanModel> getAllHalamanByStatusPosting(Integer statusPosting);
    HalamanModel getHalamanById(Long id);
    void deleteHalaman(HalamanModel halaman);
    void updateHalaman(HalamanModel halaman);
}
