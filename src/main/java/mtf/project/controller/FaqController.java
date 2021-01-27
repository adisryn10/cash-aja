package mtf.project.controller;

import mtf.project.model.FaqModel;
import mtf.project.model.UserRoleModel;
import mtf.project.service.FaqService;
import mtf.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/faq")
public class FaqController {

    @Autowired
    FaqService faqService;

    @Autowired
    UserService userService;

    @RequestMapping(path = "")
    public String faqHome(Model model) {
        List<FaqModel> listFaq = faqService.getAllFaq();
        model.addAttribute("listFaq", listFaq);
        return "cms/faq/faq-dashboard";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addFaq(Model model) {
        FaqModel faq = new FaqModel();
        model.addAttribute("faq", faq);
        return "cms/faq/form-tambah-faq";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, params = {"draft"})
    public RedirectView addFaqDraft(FaqModel faq,
                                    Authentication auth,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
        faq.setLatestAuthor(latestAuthor);

        faq.setStatusPosting(0);

        Date date = new Date(System.currentTimeMillis());
        faq.setLatestEdit(date);

        faqService.createFaq(faq);
        List<FaqModel> listFaq = faqService.getAllFaq();
        model.addAttribute("listFaq", listFaq);
        model.addAttribute("addSuccess", true);
        redirectAttributes.addFlashAttribute("listFaq", listFaq);
        redirectAttributes.addFlashAttribute("addSuccess", true);
        return new RedirectView("/admin/faq", true);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, params = {"publish"})
    public RedirectView addFaqPublish(FaqModel faq,
                                      Authentication auth,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
        faq.setLatestAuthor(latestAuthor);

        faq.setStatusPosting(1);

        Date date = new Date(System.currentTimeMillis());
        faq.setLatestEdit(date);

        faqService.createFaq(faq);
        List<FaqModel> listFaq = faqService.getAllFaq();
        redirectAttributes.addFlashAttribute("addSuccess", true);
        return new RedirectView("/admin/faq", true);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String updateFaqForm(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException {
        FaqModel faq = faqService.getFaqById(id);
        model.addAttribute("faq", faq);
        return "cms/faq/form-update-faq";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params = {"draft"})
    public RedirectView updateFaqDraft(FaqModel faq,
                                       Authentication auth,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
        faq.setLatestAuthor(latestAuthor);

        faq.setStatusPosting(0);

        Date date = new Date(System.currentTimeMillis());
        faq.setLatestEdit(date);

        faqService.updateFaq(faq);

        redirectAttributes.addFlashAttribute("updateSuccess", true);
        return new RedirectView("/admin/faq/detail/" + faq.getId(), true);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params = {"publish"})
    public RedirectView updateFaqPublish(FaqModel faq,
                                         Authentication auth,
                                         Model model,
                                         RedirectAttributes redirectAttributes) {
        UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
        faq.setLatestAuthor(latestAuthor);

        faq.setStatusPosting(1);

        Date date = new Date(System.currentTimeMillis());
        faq.setLatestEdit(date);

        faqService.updateFaq(faq);
        redirectAttributes.addFlashAttribute("updateSuccess", true);
        return new RedirectView("/admin/faq/detail/" + faq.getId(), true);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RedirectView deleteFaq(FaqModel faq, RedirectAttributes redirectAttributes) {
        FaqModel faqDeleted = faqService.getFaqById(faq.getId());
        faqService.deleteFaq(faqDeleted);
        redirectAttributes.addFlashAttribute("deleteSuccess", true);
        return new RedirectView("/admin/faq", true);
    }
}
