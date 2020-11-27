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

    @RequestMapping(value = "/tambah", method = RequestMethod.POST)
    public String addUserSubmit(UserRoleModel user,
        @RequestParam("konfirmasi") String konfirmasi, Model model){
        if(!user.getPassword().equals(konfirmasi)){
            model.addAttribute("isNotEqual", true);
            return "form-tambah-admin";
        }
        userService.addUser(user);
        model.addAttribute("user", user);
        return "redirect:/admin/users";
    }
}