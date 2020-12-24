package mtf.project.controller;

import mtf.project.model.PengajuanModel;
import mtf.project.service.PengajuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class PengajuanController {

    @Autowired
    PengajuanService pengajuanService;

    @PostMapping(value = "/pengajuan/add")
    public ResponseEntity<Object> submitPengajuan(@ModelAttribute PengajuanModel pengajuan) {
        try {
            pengajuanService.addPengajuan(pengajuan);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception handlerException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerException);
        }
    }

    @GetMapping(path = "/admin/pengajuan")
    public String pengajuanHome(Model model){
        List<PengajuanModel> listPengajuan = pengajuanService.getAllPengajuan();
        model.addAttribute("listPengajuan", listPengajuan);
        return "cms-pengajuan";
    }

    @PostMapping(value = "/admin/pengajuan/delete")
    public RedirectView deletePengajuan(PengajuanModel pengajuan, RedirectAttributes redirectAttributes) {
        PengajuanModel pengajuanDeleted = pengajuanService.getPengajuanById(pengajuan.getId());
        pengajuanService.deletePengajuan(pengajuanDeleted);
        redirectAttributes.addFlashAttribute("deleteSuccess", true);
        return new RedirectView("/admin/pengajuan", true);
    }
}