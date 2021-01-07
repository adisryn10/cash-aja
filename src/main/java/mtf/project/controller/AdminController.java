package mtf.project.controller;

import mtf.project.model.RoleModel;
import mtf.project.model.UserRoleModel;
import mtf.project.service.RoleService;
import mtf.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController{

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping(path = "")
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority: auth.getAuthorities()){
            model.addAttribute("role", authority.getAuthority());
        }
        List<UserRoleModel> listUser = userService.getUserByRoleNama("CUSTOMER");

        model.addAttribute("listUser", listUser);
        return "admin-home";
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String user(Model model){

        List<UserRoleModel> listUser = userService.getAllUser();

        model.addAttribute("listUser", listUser);
        return "users";
    }

    @RequestMapping(path = "/user/detail/{idUser}", method = RequestMethod.GET)
    public String userDetail(@PathVariable String idUser, Model model){
        UserRoleModel user = userService.getUserById(idUser);
        model.addAttribute("user", user);
        return "user-detail";
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.GET)
    public String addUserForm(Model model){
        UserRoleModel user = new UserRoleModel();
        List<RoleModel> listRole = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);
        return "form-tambah-admin";
    }

    @RequestMapping(value = "/detail/{username}", method = RequestMethod.GET)
    public String updateUserForm(@PathVariable String username, Model model){
        UserRoleModel user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "form-update-admin";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RedirectView updateUserSubmit(UserRoleModel user, Model model, Authentication auth, RedirectAttributes redirectAttributes){
        UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
        user.setLatestAuthor(latestAuthor);

        Date date = new Date(System.currentTimeMillis());
        user.setLatestEdit(date);

        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("updateSuccess", true);
        return new RedirectView("/admin/detail/"+user.getUsername(),true);
    }

    @RequestMapping(value = "/tambah", method = RequestMethod.POST)
    public RedirectView addUserSubmit(UserRoleModel user, Authentication auth,
        @RequestParam("konfirmasi") String konfirmasi, Model model,RedirectAttributes redirectAttributes){
        if(!user.getPassword().equals(konfirmasi)){
            redirectAttributes.addFlashAttribute("isNotEqual", true);
            return new RedirectView("/admin/tambah",true);
        }
        UserRoleModel latestAuthor = userService.getUserByUsername(auth.getName());
        user.setLatestAuthor(latestAuthor);

        Date date = new Date(System.currentTimeMillis());
        user.setLatestEdit(date);

        user.setStatus(1);

        userService.addUser(user);
        redirectAttributes.addFlashAttribute("addSuccess", true);
        return new RedirectView("/admin/users");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RedirectView deleteUserSubmit(UserRoleModel user, Model model, RedirectAttributes redirectAttributes){
        userService.deleteUser(user);
        redirectAttributes.addFlashAttribute("user", user);
        redirectAttributes.addFlashAttribute("deleteSuccess",true);
        return new RedirectView("/admin/users", true);
    }
}