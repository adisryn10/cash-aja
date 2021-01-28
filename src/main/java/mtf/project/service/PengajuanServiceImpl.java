package mtf.project.service;

import mtf.project.model.PengajuanModel;
import mtf.project.repository.PengajuanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PengajuanServiceImpl implements PengajuanService {
    @Autowired
    private PengajuanDb pengajuanDb;

    @Override
    public void addPengajuan(PengajuanModel pengajuan) {
        pengajuanDb.save(pengajuan);
    }

    @Override
    public List<PengajuanModel> getAllPengajuan() { return pengajuanDb.findAll(); }

    @Override
    public PengajuanModel getPengajuanById(Long id) { return pengajuanDb.findById(id).get(); }

    @Override
    public void deletePengajuan(PengajuanModel pengajuan) { pengajuanDb.delete(pengajuan); }
}
