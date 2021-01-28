package mtf.project.controller;

import mtf.project.model.FileModel;
import mtf.project.model.PromoModel;
import mtf.project.model.UserRoleModel;
import mtf.project.service.FileService;
import mtf.project.service.PromoService;
import mtf.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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
    public String promoHome(Model model) {
        List<PromoModel> listPromo = promoService.getAllPromo();
        model.addAttribute("listPromo", listPromo);
        return "cms/promo/promo-dashboard";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPromoForm(Model model) {
        PromoModel promo = new PromoModel();
        model.addAttribute("promo", promo);
        return "cms/promo/form-tambah-promo";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, params = {"draft"})
    public RedirectView addPromoDraft(PromoModel promo,
                                      Authentication auth,
                                      @RequestParam("file1") MultipartFile file1,
                                      @RequestParam("file2") MultipartFile file2,
                                      Model model, RedirectAttributes redirectAttributes) {
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            promo.setLatestAuthor(latestAuthor);

            FileModel file1Saved = fileService.store(file1);
            promo.setBanner(file1Saved);

            FileModel file2Saved = fileService.store(file2);
            promo.setBannerFull(file2Saved);

            promo.setStatusPosting(0);

            Date date = new Date(System.currentTimeMillis());
            promo.setLatestEdit(date);

            PromoModel promoSaved = promoService.createPromo(promo);
            List<PromoModel> listPromo = promoService.getAllPromo();
            redirectAttributes.addFlashAttribute("listPromo", listPromo);
            redirectAttributes.addFlashAttribute("addSuccess", true);
            return new RedirectView("/admin/promo", true);
        } catch (Exception e) {
            return new RedirectView("/admin/promo/add", true);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, params = {"publish"})
    public RedirectView addPromoPublish(PromoModel promo,
                                        Authentication auth,
                                        @RequestParam("file1") MultipartFile file1,
                                        @RequestParam("file2") MultipartFile file2,
                                        Model model,
                                        RedirectAttributes redirectAttributes) {
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            promo.setLatestAuthor(latestAuthor);

            FileModel file1Saved = fileService.store(file1);
            promo.setBanner(file1Saved);

            FileModel file2Saved = fileService.store(file2);
            promo.setBannerFull(file2Saved);

            promo.setStatusPosting(1);

            Date date = new Date(System.currentTimeMillis());
            promo.setLatestEdit(date);

            PromoModel promoSaved = promoService.createPromo(promo);
            List<PromoModel> listPromo = promoService.getAllPromo();

            redirectAttributes.addFlashAttribute("listPromo", listPromo);
            redirectAttributes.addFlashAttribute("addSuccess", true);
            return new RedirectView("/admin/promo", true);
        } catch (Exception e) {
            return new RedirectView("/admin/promo/add", true);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RedirectView deletePromo(PromoModel promo, RedirectAttributes redirectAttributes) {
        PromoModel promoDeleted = promoService.getPromoById(promo.getId());
        promoService.deletePromo(promoDeleted);
        redirectAttributes.addFlashAttribute("deleteSuccess", true);
        return new RedirectView("/admin/promo", true);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String updateUserForm(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException {
        PromoModel promo = promoService.getPromoById(id);
        model.addAttribute("promo", promo);

        if (promo.getBanner() != null) {
            String dataBannerImage = Base64.getEncoder().encodeToString(promo.getBanner().getData());
            model.addAttribute("dataBannerImage", dataBannerImage);
            model.addAttribute("hasBannerImage", true);
        }

        if (promo.getBannerFull() != null) {
            String dataBannerFullImage = Base64.getEncoder().encodeToString(promo.getBannerFull().getData());
            model.addAttribute("dataBannerFullImage", dataBannerFullImage);
            model.addAttribute("hasBannerFullImage", true);
        }

        return "cms/promo/form-update-promo";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params = {"draft"})
    public String updatePromoDraft(PromoModel promo,
                                   Authentication auth,
                                   @RequestParam("files") MultipartFile file,
                                   Model model) {
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            promo.setLatestAuthor(latestAuthor);

            FileModel currentFile = promoService.getPromoById(promo.getId()).getBanner();

            if (!file.isEmpty()) {
                if (currentFile != null) {
                    fileService.deleteFile(currentFile);
                }

                FileModel fileSaved = fileService.store(file);
                promo.setBanner(fileSaved);
            } else {
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
        } catch (Exception e) {
            e.printStackTrace();
            return "cms/promo/form-update-promo";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params = {"publish"})
    public String updatePromoPublish(PromoModel promo,
                                     Authentication auth,
                                     @RequestParam("files") MultipartFile file,
                                     Model model) {
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            promo.setLatestAuthor(latestAuthor);

            FileModel currentFile = promoService.getPromoById(promo.getId()).getBanner();

            if (!file.isEmpty()) {
                if (currentFile != null) {
                    fileService.deleteFile(currentFile);
                }

                FileModel fileSaved = fileService.store(file);
                promo.setBanner(fileSaved);
            } else {
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
        } catch (Exception e) {
            e.printStackTrace();
            return "cms/promo/form-update-promo";
        }
    }
}
