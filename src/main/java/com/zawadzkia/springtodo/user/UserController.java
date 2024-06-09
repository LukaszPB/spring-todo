package com.zawadzkia.springtodo.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        //Delets user with all his tasks
        userService.deleteUser(id);
        return "admin/controll";
    }

//    @GetMapping("/users")
//    public List<UserDTO> getUserList(){
//        return userService.getAllUsers();
//    }

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new UserDTO());  // Bind empty UserDTO for the form
        return "admin/controll";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("newUser") UserDTO newUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers());  // Repopulate user list if there are errors
            return "admin/controll";
        }
        userService.addUser(newUser);
        return "redirect:/admin/users";
    }
}
