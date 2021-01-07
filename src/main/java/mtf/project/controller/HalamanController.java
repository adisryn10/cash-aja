package mtf.project.controller;

import mtf.project.model.*;
import mtf.project.service.HalamanService;
import mtf.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/admin/halaman")
public class HalamanController {

    @Autowired
    UserService userService;

    @Autowired
    HalamanService halamanService;


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
                                    Model model, RedirectAttributes redirectAttributes){
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            halaman.setLatestAuthor(latestAuthor);

            halaman.setStatusPosting(0);

            Date date = new Date(System.currentTimeMillis());
            halaman.setLatestEdit(date);

            halamanService.createHalaman(halaman);
            List<HalamanModel> listHalaman = halamanService.getAllHalaman();
            redirectAttributes.addFlashAttribute("listHalaman", listHalaman);
            redirectAttributes.addFlashAttribute("addSuccess",true);
            redirectAttributes.addFlashAttribute("listHalaman", listHalaman);
            redirectAttributes.addFlashAttribute("addSuccess",true);
            return new RedirectView("/admin/halaman", true);
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.POST, params={"publish"})
    public RedirectView addHalamanPublish(HalamanModel halaman,
                                      Authentication auth,
                                      Model model,
                                      RedirectAttributes redirectAttributes){

            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            halaman.setLatestAuthor(latestAuthor);

            halaman.setStatusPosting(1);

            Date date = new Date(System.currentTimeMillis());
            halaman.setLatestEdit(date);

            halamanService.createHalaman(halaman);
            List<HalamanModel> listHalaman = halamanService.getAllHalaman();
            redirectAttributes.addFlashAttribute("addSuccess",true);
            return new RedirectView("/admin/halaman-dashboard", true);
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
        return "form-update-halaman";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params={"draft"})
    public String updateHalamanDraft(HalamanModel halaman,
                                    Authentication auth,
                                    Model model){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            halaman.setLatestAuthor(latestAuthor);
            
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
                                      Model model){
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            halaman.setLatestAuthor(latestAuthor);

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
