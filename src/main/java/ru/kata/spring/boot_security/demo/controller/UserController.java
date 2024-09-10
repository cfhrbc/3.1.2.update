package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userHome(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/home";
    }
}
/*
@Controller
@RequestMapping("/user")
public class UserController {
    UserRepository userDao;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }


    @GetMapping()
    public String dashboardPageList(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userDao.getUserByName(currentUser.getUsername()).orElseThrow();
        model.addAttribute("currentUser", user);

        return "infoUserOneNewStyle";
    }

}

 */
