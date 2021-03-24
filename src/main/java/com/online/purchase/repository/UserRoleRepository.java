package com.online.purchase.repository;

import com.online.purchase.model.User;
import com.online.purchase.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    public List<UserRole> findAllByUser(User user);
}
