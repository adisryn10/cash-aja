package mtf.project.controller;

import mtf.project.model.FileModel;
import mtf.project.model.TestimoniModel;
import mtf.project.model.UserRoleModel;
import mtf.project.model.YoutubeModel;
import mtf.project.service.FileService;
import mtf.project.service.TestimoniService;
import mtf.project.service.UserService;
import mtf.project.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/testimoni")
public class TestimoniController {

    @Autowired
    UserService userService;

    @Autowired
    TestimoniService testimoniService;

    @Autowired
    FileService fileService;

    @Autowired
    YoutubeService youtubeService;

    @RequestMapping(path = "")
    public String testimoniHome(Model model) {
        List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();
        model.addAttribute("listTestimoni", listTestimoni);
        return "cms/testimoni/testimoni-dashboard";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addTestimoniForm(Model model) {
        TestimoniModel testimoni = new TestimoniModel();
        model.addAttribute("testimoni", testimoni);
        return "cms/testimoni/form-tambah-testimoni";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, params = {"draft"})
    public RedirectView addTestimoniDraft(TestimoniModel testimoni,
                                          Authentication auth,
                                          @RequestParam("files") MultipartFile file,
                                          Model model, RedirectAttributes redirectAttributes) {
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            testimoni.setLatestAuthor(latestAuthor);

            FileModel fileSaved = fileService.store(file);
            testimoni.setFile(fileSaved);

            testimoni.setStatusPosting(0);

            Date date = new Date(System.currentTimeMillis());
            testimoni.setLatestEdit(date);

            TestimoniModel testimoniSaved = testimoniService.createTestimoni(testimoni);
            List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();
            redirectAttributes.addFlashAttribute("listTestimoni", listTestimoni);
            redirectAttributes.addFlashAttribute("addSuccess", true);
            return new RedirectView("/admin/testimoni", true);
        } catch (Exception e) {
            return new RedirectView("/admin/add", true);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, params = {"publish"})
    public RedirectView addTestimoniPublish(TestimoniModel testimoni,
                                            Authentication auth,
                                            @RequestParam("files") MultipartFile file,
                                            Model model,
                                            RedirectAttributes redirectAttributes) {
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            testimoni.setLatestAuthor(latestAuthor);

            if (file.isEmpty()) {
                File defaultFile = new File("src/main/resources/static/cust/images/user-avatar.png");
                FileInputStream input = new FileInputStream(defaultFile);
                file = new MockMultipartFile("defaultFile",
                        defaultFile.getName(), "image/png", input.readAllBytes());
            }

            FileModel fileSaved = fileService.store(file);
            testimoni.setFile(fileSaved);


            testimoni.setStatusPosting(1);

            Date date = new Date(System.currentTimeMillis());
            testimoni.setLatestEdit(date);

            TestimoniModel testimoniSaved = testimoniService.createTestimoni(testimoni);
            List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();

            redirectAttributes.addFlashAttribute("listTestimoni", listTestimoni);
            redirectAttributes.addFlashAttribute("addSuccess", true);
            return new RedirectView("/admin/testimoni", true);
        } catch (Exception e) {
            return new RedirectView("/admin/add", true);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RedirectView deleteTestimoni(TestimoniModel testimoni, RedirectAttributes redirectAttributes) {
        TestimoniModel testimoniDeleted = testimoniService.getTestimoniById(testimoni.getId());
        testimoniService.deleteTestimoni(testimoniDeleted);
        redirectAttributes.addFlashAttribute("deleteSuccess", true);
        return new RedirectView("/admin/testimoni", true);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String updateUserForm(@PathVariable Long id, Model model, HttpServletResponse response) throws IOException {
        TestimoniModel testimoni = testimoniService.getTestimoniById(id);
        model.addAttribute("testimoni", testimoni);
        if (testimoni.getFile() != null) {
            String dataImage = Base64.getEncoder().encodeToString(testimoni.getFile().getData());
            model.addAttribute("dataImage", dataImage);
            model.addAttribute("hasImage", true);
        }
        return "cms/testimoni/form-update-testimoni";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params = {"draft"})
    public String updateTestimoniDraft(TestimoniModel testimoni,
                                       Authentication auth,
                                       @RequestParam("files") MultipartFile file,
                                       Model model) {
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            testimoni.setLatestAuthor(latestAuthor);

            FileModel currentFile = testimoniService.getTestimoniById(testimoni.getId()).getFile();

            if (!file.isEmpty()) {
                if (currentFile != null) {
                    fileService.deleteFile(currentFile);
                }

                FileModel fileSaved = fileService.store(file);
                testimoni.setFile(fileSaved);
            } else {
                testimoni.setFile(currentFile);
            }
            testimoni.setStatusPosting(0);

            Date date = new Date(System.currentTimeMillis());
            testimoni.setLatestEdit(date);

            testimoniService.updateTestimoni(testimoni);
            List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();

            String dataImage = Base64.getEncoder().encodeToString(testimoni.getFile().getData());
            model.addAttribute("dataImage", dataImage);
            model.addAttribute("hasImage", true);
            model.addAttribute("testimoni", testimoni);
            model.addAttribute("listTestimoni", listTestimoni);
            model.addAttribute("updateSuccess", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "cms/testimoni/form-update-testimoni";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, params = {"publish"})
    public String updateTestimoniPublish(TestimoniModel testimoni,
                                         Authentication auth,
                                         @RequestParam("files") MultipartFile file,
                                         Model model) {
        try {
            UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
            testimoni.setLatestAuthor(latestAuthor);

            FileModel currentFile = testimoniService.getTestimoniById(testimoni.getId()).getFile();

            if (!file.isEmpty()) {
                if (currentFile != null) {
                    fileService.deleteFile(currentFile);
                }

                FileModel fileSaved = fileService.store(file);
                testimoni.setFile(fileSaved);
            } else {
                testimoni.setFile(currentFile);
            }

            testimoni.setStatusPosting(1);

            Date date = new Date(System.currentTimeMillis());
            testimoni.setLatestEdit(date);

            testimoniService.updateTestimoni(testimoni);
            List<TestimoniModel> listTestimoni = testimoniService.getAllTestimoni();

            String dataImage = Base64.getEncoder().encodeToString(testimoni.getFile().getData());
            model.addAttribute("dataImage", dataImage);
            model.addAttribute("hasImage", true);

            model.addAttribute("testimoni", testimoni);
            model.addAttribute("listTestimoni", listTestimoni);
            model.addAttribute("updateSuccess", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "cms/testimoni/form-update-testimoni";
    }

    @RequestMapping(value = "/youtube", method = RequestMethod.GET)
    public String addYoutubeForm(Model model) {
        YoutubeModel currentYoutube = youtubeService.getYoutubeById((long) 1);
        YoutubeModel newYoutube = new YoutubeModel();
        if (currentYoutube == null) {
            newYoutube.setId((long) 1);
        } else {
            newYoutube = currentYoutube;
        }

        model.addAttribute("youtube", newYoutube);
        return "cms/testimoni/form-update-youtube";
    }

    @RequestMapping(value = "/youtube", method = RequestMethod.POST)
    public String updateYoutubePublish(YoutubeModel youtube, Model model) {
        YoutubeModel youtubeUpdated = youtubeService.updateYoutube(youtube);
        model.addAttribute("updateSuccess", true);
        model.addAttribute("youtube", youtubeUpdated);
        return "cms/testimoni/form-update-youtube";
    }
}
