package com.example.mysql;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Getter;
import lombok.Setter;



@Controller
@RequestMapping(path = "/user")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @GetMapping(path = "/hello")
  public @ResponseBody String hello(@RequestParam(defaultValue = "world") String name){
    return "hello" + name;
  }
  
  @PostMapping(path = "/add")
  public  @ResponseBody User add(@RequestParam("name") String name, @RequestParam("email") String email) {
    User user = new User();
    user.setName(name);
    user.setEmail(email);
    userRepository.save(user);
    return user;
  }

  @Getter
  @Setter
  public static class UserBody {
    private String name;
    private String email;
    // public UserBody( String name,   String email){
    //   this.name = name;
    //   this.email = email;
    // }

  }
  @PostMapping(path = "/addnew")
  public @ResponseBody UserBody addnew(@RequestBody UserBody userBody) {
  System.out.println(userBody);
    User user = new User();
    user.setEmail(userBody.email);
    user.setName(userBody.name);
    userRepository.save(user);
    return userBody;
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<User> all(){
    return userRepository.findAll();
  }
  @DeleteMapping(path = "/delete")
  public @ResponseBody String delete(@RequestParam Integer id) {
    userRepository.deleteById(id);
    return "delete";
  }

  @GetMapping(path = "/")
  public @ResponseBody User getOneUser(@RequestParam Integer id) {
      Optional<User> optUser = userRepository.findById(id);
      User user = optUser.get();
      return user;
  }
  @PatchMapping(path = "/update")
  public @ResponseBody User updateUser(@RequestBody UserBody userBody, @RequestParam Integer id) {
    Optional<User> optUser = userRepository.findById(id);
    User user = optUser.get();
    user.setEmail(userBody.email);
    user.setName(userBody.name);
    userRepository.save(user);
    return user;
  }
}
