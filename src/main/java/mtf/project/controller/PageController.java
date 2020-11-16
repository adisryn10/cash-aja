package mtf.project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class PageController{

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
    
}
