package mtf.project.service;

import mtf.project.model.HalamanModel;

import java.util.List;

public interface HalamanService {
    List<HalamanModel> getAllHalaman();
    HalamanModel getHalamanById(Long id);
    HalamanModel createHalaman(HalamanModel halaman);
    void deleteHalaman(HalamanModel halaman);
    void updateHalaman(HalamanModel halaman);
}
