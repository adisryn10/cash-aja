package mtf.project.controller;

import mtf.project.model.*;
import mtf.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Controller
public class PageController {

    @Autowired
    FaqService faqService;

    @Autowired
    TestimoniService testimoniService;

    @Autowired
    YoutubeService youtubeService;

    @Autowired
    PromoService promoService;

    @Autowired
    HalamanService halamanService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Model model) {
        YoutubeModel youtubeModel = youtubeService.getYoutubeById((long) 1);
        if (youtubeModel != null) {
            String youtubeLink = youtubeModel.getLink().split("v=")[1];
            model.addAttribute("youtubeLink", youtubeLink);
        }
        List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoniByStatusPosting(1);
        if (listTestimoni.size() != 0) {
            List<String> testimoniImage = new ArrayList<>();
            for (TestimoniModel testimoni : listTestimoni) {
                String dataImage = Base64.getEncoder().encodeToString(testimoni.getFile().getData());
                testimoniImage.add(dataImage);
            }
            model.addAttribute("testimoniImage", testimoniImage);
            model.addAttribute("listTestimoni", listTestimoni);
        }
        List<PromoModel> listPromo = promoService.getAllPromoByStatusPosting(1);
        if (listPromo.size() != 0) {
            List<String> promoImage = new ArrayList<>();
            for (PromoModel promo : listPromo) {
                String dataImage = Base64.getEncoder().encodeToString(promo.getBanner().getData());
                promoImage.add(dataImage);
            }
            model.addAttribute("promoImage", promoImage);
            model.addAttribute("listPromo", listPromo);
        }
        List<HalamanModel> listHalaman = halamanService.getAllHalamanByStatusPosting(1);
        model.addAttribute("listHalaman", listHalaman);
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "cms-login";
    }

    @RequestMapping(path = "/faq", method = RequestMethod.GET)
    public String faq(Model model) {
        List<FaqModel> listFaq = faqService.getAllFaqByStatusPosting(1);
        model.addAttribute("listFaq", listFaq);
        List<HalamanModel> listHalaman = halamanService.getAllHalamanByStatusPosting(1);
        model.addAttribute("listHalaman", listHalaman);
        return "faq";
    }

    @RequestMapping(value = "/promo/{id}", method = RequestMethod.GET)
    public String detailPromo(@PathVariable Long id, Model model) {
        PromoModel promo = promoService.getPromoById(id);
        model.addAttribute("promo", promo);
        if (promo.getBannerFull() != null) {
            String dataBannerFullImage = Base64.getEncoder().encodeToString(promo.getBannerFull().getData());
            model.addAttribute("dataBannerFullImage", dataBannerFullImage);
            model.addAttribute("hasBannerFullImage", true);
        }
        List<HalamanModel> listHalaman = halamanService.getAllHalamanByStatusPosting(1);
        model.addAttribute("listHalaman", listHalaman);
        return "promo-detail";
    }

    @RequestMapping(value = "/page/{id}", method = RequestMethod.GET)
    public String updateHalamanForm(@PathVariable Long id, Model model) {
        HalamanModel halaman = halamanService.getHalamanById(id);
        model.addAttribute("halaman", halaman);
        List<HalamanModel> listHalaman = halamanService.getAllHalamanByStatusPosting(1);
        model.addAttribute("listHalaman", listHalaman);
        return "pages-template";
    }
}
