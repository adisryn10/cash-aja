package mtf.project.controller;

import mtf.project.helpers.ClickConnectionHelper;
import mtf.project.model.*;
import mtf.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
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
    public String index() throws IOException {
        try {
            ClickConnectionHelper.addClickCounter("Halaman Utama");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "home";
    }

    @RequestMapping(path = "/home-cms", method = RequestMethod.GET)
    public String homeCms(Model model) {

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
        if (listHalaman.size() != 0) {
            model.addAttribute("listHalaman", listHalaman);
        }
        return "home-cms";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() throws IOException {
        try {
            ClickConnectionHelper.addClickCounter("CMS Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "cms-login";
    }

    @RequestMapping(path = "/promo-bumn", method = RequestMethod.GET)
    public String bumn() {
        return "bumn-promo-detail";
    }

    @RequestMapping(path = "/promo-pns", method = RequestMethod.GET)
    public String pns() {
        return "pns-promo-detail";
    }

    @RequestMapping(path = "/promo-wiraswasta", method = RequestMethod.GET)
    public String wiraswasta() {
        return "wiraswasta-promo-detail";
    }

    @RequestMapping(path = "/faq", method = RequestMethod.GET)
    public String faq() throws IOException {
        try {
            ClickConnectionHelper.addClickCounter("FAQ Layanan");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "faq";
    }

    @RequestMapping(path = "/faq-cms", method = RequestMethod.GET)
    public String faqCms(Model model) {
        List<FaqModel> listFaq = faqService.getAllFaqByStatusPosting(1);
        model.addAttribute("listFaq", listFaq);
        return "faq-cms";
    }

    @RequestMapping(value = "/promo/{id}", method = RequestMethod.GET)
    public String detailPromo(@PathVariable Long id, Model model) throws IOException {
        try {
            ClickConnectionHelper.addClickCounter("Web Banner & Promo");
            PromoModel promo = promoService.getPromoById(id);
            model.addAttribute("promo", promo);
            if (promo.getBannerFull() != null) {
                String dataBannerFullImage = Base64.getEncoder().encodeToString(promo.getBannerFull().getData());
                model.addAttribute("dataBannerFullImage", dataBannerFullImage);
                model.addAttribute("hasBannerFullImage", true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "promo-detail";
    }

    @RequestMapping(value = "/page/{id}", method = RequestMethod.GET)
    public String updateHalamanForm(@PathVariable Long id, Model model) throws IOException {
        try {
            ClickConnectionHelper.addClickCounter("Pages on Navigation Bar");
            HalamanModel halaman = halamanService.getHalamanById(id);
            model.addAttribute("halaman", halaman);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "pages-template";
    }
}

