package mtf.project.controller;

import mtf.project.helpers.ClickConnectionHelper;
import mtf.project.helpers.PengajuanExporter;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PengajuanController {

    @Autowired
    PengajuanService pengajuanService;

    @PostMapping(value = "/pengajuan/add")
    public ResponseEntity<Object> submitPengajuan(@ModelAttribute PengajuanModel pengajuan) throws IOException {
        try {
            ClickConnectionHelper.addClickCounter("Kirim Pengajuan");
            pengajuanService.addPengajuan(pengajuan);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception handlerException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerException);
        }
    }

    @GetMapping(path = "/admin/pengajuan")
    public String pengajuanHome(Model model) {
        List<PengajuanModel> listPengajuan = pengajuanService.getAllPengajuan();
        model.addAttribute("listPengajuan", listPengajuan);
        return "cms/pengajuan/pengajuan-dashboard";
    }

    @PostMapping(value = "/admin/pengajuan/delete")
    public RedirectView deletePengajuan(PengajuanModel pengajuan, RedirectAttributes redirectAttributes) {
        PengajuanModel pengajuanDeleted = pengajuanService.getPengajuanById(pengajuan.getId());
        pengajuanService.deletePengajuan(pengajuanDeleted);
        redirectAttributes.addFlashAttribute("deleteSuccess", true);
        return new RedirectView("/admin/pengajuan", true);
    }

    @GetMapping("/admin/pengajuan/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pengajuan " + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<PengajuanModel> listPengajuan = pengajuanService.getAllPengajuan();
        PengajuanExporter excelExporter = new PengajuanExporter(listPengajuan);
        excelExporter.export(response);
    }
}
