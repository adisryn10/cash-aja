package mtf.project.controller;

import mtf.project.model.FileModel;
import mtf.project.model.TestimoniModel;
import mtf.project.model.UserRoleModel;
import mtf.project.service.FileService;
import mtf.project.service.TestimoniService;
import mtf.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/testimoni")
public class TestimoniController {

    @Autowired
    UserService userService;

    @Autowired
    TestimoniService testimoniService;

    @Autowired
    FileService fileService;

    @RequestMapping(path = "")
    public String testimoniHome(Model model){
        List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();
        model.addAttribute("listTestimoni", listTestimoni);
        return "cms-testimoni";
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.GET)
    public String addTestimoniForm(Model model){
        TestimoniModel testimoni = new TestimoniModel();
        model.addAttribute("testimoni", testimoni);
        return "cms-testimoni-form-tambah";
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.POST, params={"draft"})
    public RedirectView addTestimoniDraft(TestimoniModel testimoni,
                                    Authentication auth,
                                    @RequestParam("files") MultipartFile file,
                                    Model model, RedirectAttributes redirectAttributes){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            testimoni.setLatestAuthor(latestAuthor);

            FileModel fileSaved = fileService.store(file);
            testimoni.setFile(fileSaved);

            testimoni.setStatusPosting(0);

            Date date = new Date(System.currentTimeMillis());
            testimoni.setLatestEdit(date);

            TestimoniModel testimoniSaved = testimoniService.createTestimoni(testimoni);
            List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();
            redirectAttributes.addFlashAttribute("listTestimoni", listTestimoni);
            redirectAttributes.addFlashAttribute("addSuccess",true);
            return new RedirectView("/admin/testimoni", true);
        }
        catch (Exception e){
            return new RedirectView("/admin/tambah", true);
        }
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.POST, params={"publish"})
    public RedirectView addTestimoniPublish(TestimoniModel testimoni,
                                      Authentication auth,
                                      @RequestParam("files") MultipartFile file,
                                      Model model,
                                      RedirectAttributes redirectAttributes){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            testimoni.setLatestAuthor(latestAuthor);

            FileModel fileSaved = fileService.store(file);
            testimoni.setFile(fileSaved);

            testimoni.setStatusPosting(1);

            Date date = new Date(System.currentTimeMillis());
            testimoni.setLatestEdit(date);

            TestimoniModel testimoniSaved = testimoniService.createTestimoni(testimoni);
            List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();

            redirectAttributes.addFlashAttribute("listTestimoni", listTestimoni);
            redirectAttributes.addFlashAttribute("addSuccess",true);
            return new RedirectView("/admin/testimoni", true);
        }
        catch (Exception e){
            return new RedirectView("/admin/tambah", true);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RedirectView deleteTestimoni(TestimoniModel testimoni, RedirectAttributes redirectAttributes){
        TestimoniModel testimoniDeleted = testimoniService.getTestimoniById(testimoni.getId());
        testimoniService.deleteTestimoni(testimoniDeleted);
        redirectAttributes.addFlashAttribute("deleteSuccess",true);
        return new RedirectView("/admin/testimoni", true);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String updateUserForm(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException{
        TestimoniModel testimoni = testimoniService.getTestimoniById(id);
        model.addAttribute("testimoni", testimoni);
//        String dataImage = Base64.getEncoder().encodeToString(testimoni.getFile().getData());
//        model.addAttribute("dataImage", dataImage);
        return "cms-testimoni-form-update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params={"draft"})
    public String updateTestimoniDraft(TestimoniModel testimoni,
                                    Authentication auth,
                                    @RequestParam("files") MultipartFile file,
                                    Model model){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            testimoni.setLatestAuthor(latestAuthor);

            FileModel currentFile = testimoniService.getTestimoniById(testimoni.getId()).getFile();

            if(!file.isEmpty()){
                if(currentFile!=null){
                    fileService.deleteFile(currentFile);
                }

                FileModel fileSaved = fileService.store(file);
                testimoni.setFile(fileSaved);
            }
            else{
                testimoni.setFile(currentFile);
            }
            testimoni.setStatusPosting(0);

            Date date = new Date(System.currentTimeMillis());
            testimoni.setLatestEdit(date);

            testimoniService.updateTestimoni(testimoni);
            List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();
            model.addAttribute("testimoni", testimoni);
            model.addAttribute("listTestimoni", listTestimoni);
            model.addAttribute("updateSuccess", true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "cms-testimoni-form-update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params={"publish"})
    public String updateTestimoniPublish(TestimoniModel testimoni,
                                      Authentication auth,
                                      @RequestParam("files") MultipartFile file,
                                      Model model){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            testimoni.setLatestAuthor(latestAuthor);

            FileModel currentFile = testimoniService.getTestimoniById(testimoni.getId()).getFile();

            if(!file.isEmpty()){
                if(currentFile!=null){
                    fileService.deleteFile(currentFile);
                }

                FileModel fileSaved = fileService.store(file);
                testimoni.setFile(fileSaved);
            }
            else{
                testimoni.setFile(currentFile);
            }

            testimoni.setStatusPosting(1);

            Date date = new Date(System.currentTimeMillis());
            testimoni.setLatestEdit(date);

            testimoniService.updateTestimoni(testimoni);
            List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();
            model.addAttribute("testimoni", testimoni);
            model.addAttribute("listTestimoni", listTestimoni);
            model.addAttribute("updateSuccess", true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "cms-testimoni-form-update";
    }
}
