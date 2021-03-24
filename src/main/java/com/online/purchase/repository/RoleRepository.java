package com.online.purchase.repository;


import com.online.purchase.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findByName(String name);
}
