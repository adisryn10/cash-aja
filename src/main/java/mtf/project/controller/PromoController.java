package mtf.project.controller;

import mtf.project.model.*;
import mtf.project.repository.YoutubeDb;
import mtf.project.service.FileService;
import mtf.project.service.PromoService;
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
@RequestMapping("/admin/promo")
public class PromoController {

    @Autowired
    UserService userService;

    @Autowired
    PromoService promoService;

    @Autowired
    FileService fileService;

    @RequestMapping(path = "")
    public String promoHome(Model model){
        List<PromoModel> listPromo = promoService.getAllPromo();
        model.addAttribute("listPromo", listPromo);
        return "cms/promo/promo-dashboard";
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.GET)
    public String addPromoForm(Model model){
        PromoModel promo = new PromoModel();
        model.addAttribute("promo", promo);
        return "cms/promo/form-tambah-promo";
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.POST, params={"draft"})
    public RedirectView addPromoDraft(PromoModel promo,
                                    Authentication auth,
                                    @RequestParam("files") MultipartFile file,
                                    Model model, RedirectAttributes redirectAttributes){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            promo.setLatestAuthor(latestAuthor);

            FileModel fileSaved = fileService.store(file);
            promo.setBanner(fileSaved);

            promo.setStatusPosting(0);

            Date date = new Date(System.currentTimeMillis());
            promo.setLatestEdit(date);

            PromoModel promoSaved = promoService.createPromo(promo);
            List<PromoModel> listPromo = promoService.getAllPromo();
            redirectAttributes.addFlashAttribute("listPromo", listPromo);
            redirectAttributes.addFlashAttribute("addSuccess",true);
            return new RedirectView("/admin/promo", true);
        }
        catch (Exception e){
            return new RedirectView("/admin/promo/tambah", true);
        }
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.POST, params={"publish"})
    public RedirectView addPromoPublish(PromoModel promo,
                                      Authentication auth,
                                      @RequestParam("files") MultipartFile file,
                                      Model model,
                                      RedirectAttributes redirectAttributes){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            promo.setLatestAuthor(latestAuthor);

            FileModel fileSaved = fileService.store(file);
            promo.setBanner(fileSaved);

            promo.setStatusPosting(1);

            Date date = new Date(System.currentTimeMillis());
            promo.setLatestEdit(date);

            PromoModel promoSaved = promoService.createPromo(promo);
            List<PromoModel> listPromo = promoService.getAllPromo();

            redirectAttributes.addFlashAttribute("listPromo", listPromo);
            redirectAttributes.addFlashAttribute("addSuccess",true);
            return new RedirectView("/admin/promo", true);
        }
        catch (Exception e){
            return new RedirectView("/admin/promo/tambah", true);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RedirectView deletePromo(PromoModel promo, RedirectAttributes redirectAttributes){
        PromoModel promoDeleted = promoService.getPromoById(promo.getId());
        promoService.deletePromo(promoDeleted);
        redirectAttributes.addFlashAttribute("deleteSuccess",true);
        return new RedirectView("/admin/promo", true);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String updateUserForm(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException{
        PromoModel promo = promoService.getPromoById(id);
        model.addAttribute("promo", promo);
        if(promo.getBanner() != null){
            String dataImage = Base64.getEncoder().encodeToString(promo.getBanner().getData());
            model.addAttribute("dataImage", dataImage);
            model.addAttribute("hasImage", true);
        }
        return "cms/promo/form-update-promo";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params={"draft"})
    public String updatePromoDraft(PromoModel promo,
                                    Authentication auth,
                                    @RequestParam("files") MultipartFile file,
                                    Model model){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            promo.setLatestAuthor(latestAuthor);

            FileModel currentFile = promoService.getPromoById(promo.getId()).getBanner();

            if(!file.isEmpty()){
                if(currentFile!=null){
                    fileService.deleteFile(currentFile);
                }

                FileModel fileSaved = fileService.store(file);
                promo.setBanner(fileSaved);
            }
            else{
                promo.setBanner(currentFile);
            }
            promo.setStatusPosting(0);

            Date date = new Date(System.currentTimeMillis());
            promo.setLatestEdit(date);

            promoService.updatePromo(promo);
            List<PromoModel> listPromo = promoService.getAllPromo();
            
            String dataImage = Base64.getEncoder().encodeToString(promo.getBanner().getData());
            model.addAttribute("dataImage", dataImage);
            model.addAttribute("hasImage", true);

            model.addAttribute("promo", promo);
            model.addAttribute("listPromo", listPromo);
            model.addAttribute("updateSuccess", true);
            return "cms/promo/form-update-promo";
        }
        catch (Exception e){
            e.printStackTrace();
            return "cms/promo/form-update-promo";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params={"publish"})
    public String updatePromoPublish(PromoModel promo,
                                      Authentication auth,
                                      @RequestParam("files") MultipartFile file,
                                      Model model){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            promo.setLatestAuthor(latestAuthor);

            FileModel currentFile = promoService.getPromoById(promo.getId()).getBanner();

            if(!file.isEmpty()){
                if(currentFile!=null){
                    fileService.deleteFile(currentFile);
                }

                FileModel fileSaved = fileService.store(file);
                promo.setBanner(fileSaved);
            }
            else{
                promo.setBanner(currentFile);
            }

            promo.setStatusPosting(1);

            Date date = new Date(System.currentTimeMillis());
            promo.setLatestEdit(date);

            promoService.updatePromo(promo);
            List<PromoModel> listPromo = promoService.getAllPromo();
            
            String dataImage = Base64.getEncoder().encodeToString(promo.getBanner().getData());
            model.addAttribute("dataImage", dataImage);
            model.addAttribute("hasImage", true);

            model.addAttribute("promo", promo);
            model.addAttribute("listPromo", listPromo);
            model.addAttribute("updateSuccess", true);
            return "cms/promo/form-update-promo";
        }
        catch (Exception e){
            e.printStackTrace();
            return "cms/promo/form-update-promo";
        }
    }

}
