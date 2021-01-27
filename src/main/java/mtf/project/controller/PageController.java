package mtf.project.controller;

import mtf.project.model.FaqModel;
import mtf.project.model.HalamanModel;
import mtf.project.model.PromoModel;
import mtf.project.model.TestimoniModel;
import mtf.project.model.YoutubeModel;
import mtf.project.service.FaqService;
import mtf.project.service.HalamanService;
import mtf.project.service.PromoService;
import mtf.project.service.TestimoniService;
import mtf.project.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


@Controller
public class PageController{

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
    
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority: auth.getAuthorities()){
            model.addAttribute("role", authority.getAuthority());
        }
        return "index";
    }

    @RequestMapping(path = "/faq", method = RequestMethod.GET)
    public String faqPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority: auth.getAuthorities()){
            model.addAttribute("role", authority.getAuthority());
        }
        return "faq";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(path = "/promo-bumn", method = RequestMethod.GET)
    public String bumn(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority: auth.getAuthorities()){
            model.addAttribute("role", authority.getAuthority());
        }
        return "bumn-promo-detail";
    }

    @RequestMapping(path = "/promo-pns", method = RequestMethod.GET)
    public String pns(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority: auth.getAuthorities()){
            model.addAttribute("role", authority.getAuthority());
        }
        return "pns-promo-detail";
    }

    @RequestMapping(path = "/promo-wiraswasta", method = RequestMethod.GET)
    public String wiraswasta(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority: auth.getAuthorities()){
            model.addAttribute("role", authority.getAuthority());
        }
        return "wiraswasta-promo-detail";
    }

    @RequestMapping(path = "/faq-cms", method = RequestMethod.GET)
    public String faqCms(Model model){
        List<FaqModel> listFaq = faqService.getAllFaqByStatusPosting(1);
        model.addAttribute("listFaq", listFaq);
        return "faq-cms";
    }

    @RequestMapping(path = "/home-cms", method = RequestMethod.GET)
    public String homeCms(Model model){
        List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoniByStatusPosting(1);
        YoutubeModel youtubeModel = youtubeService.getYoutubeById((long)1);
        String youtubeLink = youtubeModel.getLink().split("v=")[1];

        List<String> testimoniImage = new ArrayList<>();
        for(TestimoniModel testimoni : listTestimoni){
            String dataImage = Base64.getEncoder().encodeToString(testimoni.getFile().getData());
            testimoniImage.add(dataImage);
        }

        List<PromoModel> listPromo = promoService.getAllPromoByStatusPosting(1);

        List<String> promoImage = new ArrayList<>();
        for(PromoModel promo : listPromo){
            String dataImage = Base64.getEncoder().encodeToString(promo.getBanner().getData());
            promoImage.add(dataImage);
        }

        List<HalamanModel> listHalaman = halamanService.getAllHalamanByStatusPosting(1);

        model.addAttribute("listHalaman", listHalaman);
        model.addAttribute("promoImage", promoImage);
        model.addAttribute("listPromo", listPromo);
        model.addAttribute("testimoniImage", testimoniImage);
        model.addAttribute("listTestimoni", listTestimoni);
        model.addAttribute("youtubeLink", youtubeLink);
        return "home-cms";
    }

    @RequestMapping(value = "/promo/{id}", method = RequestMethod.GET)
    public String detailPromo(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException{

        PromoModel promo = promoService.getPromoById(id);
        model.addAttribute("promo", promo);
        if(promo.getBannerFull() != null){
            String dataBannerFullImage = Base64.getEncoder().encodeToString(promo.getBannerFull().getData());
            model.addAttribute("dataBannerFullImage", dataBannerFullImage);
            model.addAttribute("hasBannerFullImage", true);
        }
        return "promo-detail";
    }

    @RequestMapping(value = "/page/{id}", method = RequestMethod.GET)
    public String updateHalamanForm(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException{
        HalamanModel halaman = halamanService.getHalamanById(id);
        model.addAttribute("halaman", halaman);
        return "pages-template";
    }
    
}

