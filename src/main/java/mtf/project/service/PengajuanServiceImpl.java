package mtf.project.service;

import mtf.project.model.PengajuanModel;
import mtf.project.repository.PengajuanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PengajuanServiceImpl implements PengajuanService {
    @Autowired
    private PengajuanDb pengajuanDb;

    @Override
    public void addPengajuan(PengajuanModel pengajuan) {
        pengajuanDb.save(pengajuan);
    }
}
