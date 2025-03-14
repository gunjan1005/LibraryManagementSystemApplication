package com.capgemini.service;

import com.capgemini.model.User;
import com.capgemini.model.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface DefaultUserService extends UserDetailsService {
    User save(UserDTO userRegisteredDTO);
}
