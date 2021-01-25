package com.example.demo.controllers;

import com.example.demo.dto.NewUserFormDto;
import com.example.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        final NewUserFormDto newUserFormDto = new NewUserFormDto();
        model.addAttribute("newUserFormDto", newUserFormDto);
        return "registrationForm";
    }

    @PostMapping("/register")
    public String submitRegisterForm(@ModelAttribute @Valid NewUserFormDto newUserFormDto,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            return "registrationForm";
        }
        if (!userService.findUser(newUserFormDto).isEmpty()){
            redirectAttributes.addFlashAttribute("message", "User with that email already exist!");
            return "redirect:/register/";
        }
        userService.registerUser(newUserFormDto);
        return "thankYou";
    }
}
