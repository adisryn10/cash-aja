package mtf.project.controller;

import mtf.project.model.FaqModel;
import mtf.project.model.TestimoniModel;
import mtf.project.model.YoutubeModel;
import mtf.project.service.FaqService;
import mtf.project.service.TestimoniService;
import mtf.project.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index() {
        return "home";
    }

    @RequestMapping(path = "/home-cms", method = RequestMethod.GET)
    public String homeCms(Model model) {
        List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoniByStatusPosting(1);
        YoutubeModel youtubeModel = youtubeService.getYoutubeById((long) 1);

        if (youtubeModel != null) {
            String youtubeLink = youtubeModel.getLink().split("v=")[1];
            model.addAttribute("youtubeLink", youtubeLink);
        }
        if (listTestimoni.size() != 0) {
            List<String> testimoniImage = new ArrayList<>();
            for (TestimoniModel testimoni : listTestimoni) {
                String dataImage = Base64.getEncoder().encodeToString(testimoni.getFile().getData());
                testimoniImage.add(dataImage);
            }
            System.out.println("========");
            System.out.println(testimoniImage);

            model.addAttribute("testimoniImage", testimoniImage);
            model.addAttribute("listTestimoni", listTestimoni);
        }
        return "home-cms";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
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
    public String faq() {
        return "faq";
    }

    @RequestMapping(path = "/faq-cms", method = RequestMethod.GET)
    public String faqCms(Model model) {
        List<FaqModel> listFaq = faqService.getAllFaqByStatusPosting(1);
        model.addAttribute("listFaq", listFaq);
        return "faq-cms";
    }
}
