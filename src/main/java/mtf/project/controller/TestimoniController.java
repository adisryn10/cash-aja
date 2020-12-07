package mtf.project.controller;

import mtf.project.model.FileModel;
import mtf.project.model.RoleModel;
import mtf.project.model.TestimoniModel;
import mtf.project.model.UserRoleModel;
import mtf.project.service.FileService;
import mtf.project.service.TestimoniService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/admin/testimoni")
public class TestimoniController {

    @Autowired
    TestimoniService testimoniService;

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/tambah", method = RequestMethod.POST)
    public String addTestimoniSubmit(TestimoniModel testimoni, @RequestParam("files") MultipartFile file, Model model){
        try {
            FileModel fileSaved = fileService.store(file);
            testimoni.setFile(fileSaved);
            TestimoniModel testimoniSaved = testimoniService.createTestimoni(testimoni);
            List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();
            model.addAttribute("listTestimoni", listTestimoni);
            model.addAttribute("addSuccess", true);
            return "testimoni";
        }
        catch (Exception e){
            return "form-tambah-testimoni";
        }
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.GET)
    public String addTestimoniForm(Model model){
        TestimoniModel testimoni = new TestimoniModel();
        model.addAttribute("testimoni", testimoni);
        return "form-tambah-testimoni";
    }

    @RequestMapping(path = "")
    public String testimoniHome(Model model){
        List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();
        model.addAttribute("listTestimoni", listTestimoni);
        return "testimoni";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteTestimoni(TestimoniModel testimoni){
        TestimoniModel testimoniDeleted = testimoniService.getTestimoniById(testimoni.getId());
        testimoniService.deleteTestimoni(testimoniDeleted);
        return "redirect:/testimoni";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String updateUserForm(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException{
        TestimoniModel testimoni = testimoniService.getTestimoniById(id);
        String dataImage = Base64.getEncoder().encodeToString(testimoni.getFile().getData());

        model.addAttribute("testimoni", testimoni);
        model.addAttribute("dataImage", dataImage);
        return "form-update-testimoni";
    }
}
