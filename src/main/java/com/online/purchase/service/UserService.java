package com.online.purchase.service;



import com.online.purchase.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User findById(Long id);
    public User findUserByUsername(String username);
    public User findUserByEmail(String email);
    public void save(User user);

}