package mtf.project.service;

import mtf.project.model.PengajuanModel;

import java.util.List;

public interface PengajuanService {
    void addPengajuan(PengajuanModel pengajuan);

    List<PengajuanModel> getAllPengajuan();

    PengajuanModel getPengajuanById(Long id);

    void deletePengajuan(PengajuanModel pengajuan);
}