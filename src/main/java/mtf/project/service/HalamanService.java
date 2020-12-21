package mtf.project.service;

import mtf.project.model.HalamanModel;

import java.util.List;

public interface HalamanService {
    List<HalamanModel> getAllHalaman();
    HalamanModel getHalamanById(Long id);
    HalamanModel createHalaman(HalamanModel testimoni);
    void deleteHalaman(HalamanModel testimoni);
    void updateHalaman(HalamanModel testimoni);
}
