package com.online.purchase.repository;


import com.online.purchase.model.Product;
import com.online.purchase.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository("roleRepository")
public interface ProductRepository extends JpaRepository<Product,Long> {
    public Product findByTitle(String title);
}
