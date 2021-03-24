package com.online.purchase.service;

import java.util.ArrayList;
import java.util.List;


import com.online.purchase.model.UserRole;
import com.online.purchase.repository.UserRepository;
import com.online.purchase.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

//    @Autowired
//    private UserDao userDao;

//    @Autowired
//    private RoleDao roleDao;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.online.purchase.model.User user = this.userRepository.findUserByUsername(userName);

        if (user == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + user);

        // [ROLE_USER, ROLE_ADMIN,..]
//        List<String> roleNames = this.roleDao.getRoleNames(((com.app.pharmacy.apteka.model.User) user).getId());

        List<UserRole> userRoles = userRoleRepository.findAllByUser(((com.online.purchase.model.User) user));

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (userRoles != null) {
            for (UserRole userRole : userRoles) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole().getName());
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(user.getUsername(), //
                ((com.online.purchase.model.User) user).getPassword(), grantList);

        return userDetails;
    }
}