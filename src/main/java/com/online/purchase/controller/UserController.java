package com.online.purchase.controller;

import com.online.purchase.model.Product;
import com.online.purchase.model.Role;
import com.online.purchase.model.User;
import com.online.purchase.model.UserRole;
import com.online.purchase.repository.RoleRepository;
import com.online.purchase.repository.UserRepository;
import com.online.purchase.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    EntityManager entityManager;


    @RequestMapping("/signup")
    public String getSigUpPage(){
        return "register";
    }

    @RequestMapping("/user/{userId}/save")
    public String  userForm(ModelMap model, @PathVariable("userId") Long userId){

        if(userId==0){

            model.addAttribute("user",new User());
        }

        else if(userId>0){
            User user=userRepository.getOne(userId);

            model.addAttribute("user",user);
        }

        return "register";
    }

    @PostMapping("/user/save")
    public String  save(ModelMap model, User user){


        if(user.getId() == null){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Role role=roleRepository.getOne(Long.valueOf(2));
            UserRole userRole=new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            userRepository.save(user);
            userRoleRepository.save(userRole);

        }

        else if(user.getId()>0){

            User user1=userRepository.getOne(user.getId());
            user1.setEmail(user.getEmail());
            user1.setfName(user.getfName());
            user1.setTelephone(user.getTelephone());
            user1.setlName(user.getlName());

            userRepository.save(user1);
        }

        return "redirect:/login";
    }
    @RequestMapping("user/list")
    public String getUserPage(){
        return "users";
    }


    @RequestMapping("user/{id}/profile")
    public String getUserProfilePage(ModelMap model, @PathVariable("id") long id){
        User user = userRepository.getOne(id);
        String baseQuery = "select * from product where user_id="+id;
        Query query=entityManager.createNativeQuery(baseQuery, Product.class);
        List<Product> products = query.getResultList();
        model.addAttribute("user", user);
        model.addAttribute("products", products);
        return "blog-details";
    }
}
