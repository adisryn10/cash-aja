package mtf.project.controller;

import mtf.project.model.FaqModel;
import mtf.project.model.TestimoniModel;
import mtf.project.model.YoutubeModel;
import mtf.project.service.FaqService;
import mtf.project.service.TestimoniService;
import mtf.project.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Controller
public class PageController{

    @Autowired
    FaqService faqService;

    @Autowired
    TestimoniService testimoniService;

    @Autowired
    YoutubeService youtubeService;

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

        model.addAttribute("testimoniImage", testimoniImage);
        model.addAttribute("listTestimoni", listTestimoni);
        model.addAttribute("youtubeLink", youtubeLink);
        return "home-cms";
    }
}
