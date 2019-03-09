package application.controller.web;

import application.data.model.User;
import application.data.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/detail")
    private String userDetail(Model model){
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);
        model.addAttribute("user",user);
        return "/user/user-detail";
    }

    @RequestMapping(path = "/detail", method = RequestMethod.POST)
    public String updateUser(@Valid @ModelAttribute("user")User user) {
        String  username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntity = userService.findUserByUsername(username);
        userEntity.setAddress(user.getAddress());
        userEntity.setEmail(user.getEmail());
        userEntity.setAvatar(user.getAvatar());
        userEntity.setName(user.getName());
        userEntity.setGender(user.getGender());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userService.updateUser(userEntity);
        return "redirect:/user/user-detail?update-success";

    }
//
//    @GetMapping("/change-password")
//    public String changePassWord(Model model) {
//
//    }

}
