package mtf.project.controller;

import mtf.project.model.*;
import mtf.project.repository.YoutubeDb;
import mtf.project.service.FileService;
import mtf.project.service.HalamanService;
import mtf.project.service.UserService;
import mtf.project.service.YoutubeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/admin/halaman")
public class HalamanController {

    @Autowired
    UserService userService;

    @Autowired
    HalamanService halamanService;

    @Autowired
    FileService fileService;

    @RequestMapping(path = "")
    public String halamanHome(Model model){
        List<HalamanModel> listHalaman = halamanService.getAllHalaman();
        model.addAttribute("listHalaman", listHalaman);
        return "cms/halaman/halaman-dashboard";
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.GET)
    public String addHalamanForm(Model model){
        HalamanModel halaman = new HalamanModel();
        model.addAttribute("halaman", halaman);
        return "cms/halaman/form-tambah-halaman";
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.POST, params={"draft"})
    public RedirectView addHalamanDraft(HalamanModel halaman,
                                    Authentication auth,
                                    @RequestParam("files") MultipartFile file,
                                    Model model, RedirectAttributes redirectAttributes){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            halaman.setLatestAuthor(latestAuthor);

            FileModel fileSaved = fileService.store(file);
            halaman.setFile(fileSaved);

            halaman.setStatusPosting(0);

            Date date = new Date(System.currentTimeMillis());
            halaman.setLatestEdit(date);

            HalamanModel halamanSaved = halamanService.createHalaman(halaman);
            List<HalamanModel> listHalaman = halamanService.getAllHalaman();
            redirectAttributes.addFlashAttribute("listHalaman", listHalaman);
            redirectAttributes.addFlashAttribute("addSuccess",true);
            return new RedirectView("/admin/halaman", true);
        }
        catch (Exception e){
            return new RedirectView("/admin/tambah", true);
        }
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.POST, params={"publish"})
    public RedirectView addHalamanPublish(HalamanModel halaman,
                                      Authentication auth,
                                      @RequestParam("files") MultipartFile file,
                                      Model model,
                                      RedirectAttributes redirectAttributes){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            halaman.setLatestAuthor(latestAuthor);

            FileModel fileSaved = fileService.store(file);
            halaman.setFile(fileSaved);

            halaman.setStatusPosting(1);

            Date date = new Date(System.currentTimeMillis());
            halaman.setLatestEdit(date);

            HalamanModel halamanSaved = halamanService.createHalaman(halaman);
            List<HalamanModel> listHalaman = halamanService.getAllHalaman();

            redirectAttributes.addFlashAttribute("listHalaman", listHalaman);
            redirectAttributes.addFlashAttribute("addSuccess",true);
            return new RedirectView("/admin/halaman", true);
        }
        catch (Exception e){
            return new RedirectView("/admin/tambah", true);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RedirectView deleteHalaman(HalamanModel halaman, RedirectAttributes redirectAttributes){
        HalamanModel halamanDeleted = halamanService.getHalamanById(halaman.getId());
        halamanService.deleteHalaman(halamanDeleted);
        redirectAttributes.addFlashAttribute("deleteSuccess",true);
        return new RedirectView("/admin/halaman", true);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String updateUserForm(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException{
        HalamanModel halaman = halamanService.getHalamanById(id);
        model.addAttribute("halaman", halaman);
//        String dataImage = Base64.getEncoder().encodeToString(halaman.getFile().getData());
//        model.addAttribute("dataImage", dataImage);
        return "form-update-halaman";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params={"draft"})
    public String updateHalamanDraft(HalamanModel halaman,
                                    Authentication auth,
                                    @RequestParam("files") MultipartFile file,
                                    Model model){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            halaman.setLatestAuthor(latestAuthor);

            FileModel currentFile = halamanService.getHalamanById(halaman.getId()).getFile();

            if(!file.isEmpty()){
                if(currentFile!=null){
                    fileService.deleteFile(currentFile);
                }

                FileModel fileSaved = fileService.store(file);
                halaman.setFile(fileSaved);
            }
            else{
                halaman.setFile(currentFile);
            }
            halaman.setStatusPosting(0);

            Date date = new Date(System.currentTimeMillis());
            halaman.setLatestEdit(date);

            halamanService.updateHalaman(halaman);
            List<HalamanModel> listHalaman = halamanService.getAllHalaman();
            model.addAttribute("halaman", halaman);
            model.addAttribute("listHalaman", listHalaman);
            model.addAttribute("updateSuccess", true);
            return "form-update-halaman";
        }
        catch (Exception e){
            e.printStackTrace();
            return "form-update-halaman";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params={"publish"})
    public String updateHalamanPublish(HalamanModel halaman,
                                      Authentication auth,
                                      @RequestParam("files") MultipartFile file,
                                      Model model){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            halaman.setLatestAuthor(latestAuthor);

            FileModel currentFile = halamanService.getHalamanById(halaman.getId()).getFile();

            if(!file.isEmpty()){
                if(currentFile!=null){
                    fileService.deleteFile(currentFile);
                }

                FileModel fileSaved = fileService.store(file);
                halaman.setFile(fileSaved);
            }
            else{
                halaman.setFile(currentFile);
            }

            halaman.setStatusPosting(1);

            Date date = new Date(System.currentTimeMillis());
            halaman.setLatestEdit(date);

            halamanService.updateHalaman(halaman);
            List<HalamanModel> listHalaman = halamanService.getAllHalaman();
            model.addAttribute("halaman", halaman);
            model.addAttribute("listHalaman", listHalaman);
            model.addAttribute("updateSuccess", true);
            return "form-update-halaman";
        }
        catch (Exception e){
            e.printStackTrace();
            return "form-update-halaman";
        }
    }

}
