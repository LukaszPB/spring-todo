package com.zawadzkia.springtodo.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    @PostMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        //Delets user with all his tasks
        userService.deleteUser(id);

    }

    @GetMapping("/users")
    public List<UserDTO> getUserList(){
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public void addUser(@ModelAttribute("newUser") UserDTO newUser){
        userService.addUser(newUser);
    }
}
