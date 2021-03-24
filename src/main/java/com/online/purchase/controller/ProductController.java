package com.online.purchase.controller;

import com.online.purchase.model.Category;
import com.online.purchase.model.Product;
import com.online.purchase.model.User;
import com.online.purchase.repository.CategoryRepository;
import com.online.purchase.repository.ProductRepository;
import com.online.purchase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping("/product/list")
    public String getProductListPage(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        return "product-list";
    }
    @RequestMapping("/product/{id}/save")
    public String productSavePage(ModelMap model, @PathVariable("id") long id){

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        if (id == 0){

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user=userRepository.findUserByUsername(auth.getName());

            Product product = new Product();
            product.setOwner(user);
            model.addAttribute("product", product);

        }
        else{
            model.addAttribute("product", productRepository.getOne(id));
        }

        return "product-edit";
    }

    @PostMapping("/product/save")
    public String save(ModelMap model, Product product){
        if(product.getId() == null){
            productRepository.save(product);
        }
        else if(product.getId()>0){
            Product product1 = productRepository.getOne(product.getId());

            product1.setCategory(product.getCategory());
            product1.setDescription(product.getDescription());
            product1.setOwner(product.getOwner());
            product1.setPrice(product.getPrice());
            product1.setTitle(product.getTitle());

            productRepository.save(product1);
        }

        return "redirect:/";
    }
    @RequestMapping("product/{id}/details")
    public String getProductDetailPage(ModelMap model, @PathVariable("id") long id){
        Product product = productRepository.getOne(id);
        model.addAttribute("product", product);
        return "product-detail";
    }
}
