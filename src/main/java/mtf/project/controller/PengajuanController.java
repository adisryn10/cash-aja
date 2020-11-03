package mtf.project.controller;

import mtf.project.model.PengajuanModel;
import mtf.project.service.PengajuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PengajuanController {
    @Autowired
    PengajuanService pengajuanService;

    @PostMapping(value = "/pengajuan/add/")
    public ResponseEntity<Object> submitPengajuan(@ModelAttribute PengajuanModel pengajuan) {
        try {
            pengajuanService.addPengajuan(pengajuan);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception handlerException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerException);
        }
    }
}