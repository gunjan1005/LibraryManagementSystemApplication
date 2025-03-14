package com.capgemini.controller;

import com.capgemini.config.JwtGeneratorValidator;
import com.capgemini.model.User;
import com.capgemini.model.UserDTO;
import com.capgemini.repository.UserRepository;
import com.capgemini.service.DefaultUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "User Authentication Service")
//@RequestMapping("/auth")
public class RestAppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtGeneratorValidator jwtGenVal;

    @Autowired
    BCryptPasswordEncoder bcCryptPasswordEncoder;

    @Autowired
    private DefaultUserService userService;

    @Operation(summary = "User registration by giving all the details.")
    @PostMapping("/registration")
    public ResponseEntity<Object> registerUser(@RequestBody UserDTO userDTO) {
        User users =  userService.save(userDTO);
        if (users.equals(null))
            return generateRespose("Not able to save user ", HttpStatus.BAD_REQUEST, userDTO);
        else
            return generateRespose("User saved successfully : " + users.getId(), HttpStatus.OK, users);
    }

    @Operation(summary = "Token Generation using Credentials.")
    @GetMapping("/genToken")
    public String generateJwtToken(@RequestBody UserDTO userDto) throws Exception {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtGenVal.generateToken(authentication);
    }

    @Operation(summary = "Accessing Welcome Admin page using admin token.")
    @GetMapping("/welcomeAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String welcome() {
        return "WelcomeAdmin";
    }

    @Operation(summary = "Accessing Welcome User page using admin token.")
    @GetMapping("/welcomeUser")
    @PreAuthorize("hasAuthority('USER')")
    public String welcomeUser() {
        return "WelcomeUSER";
    }



    public ResponseEntity<Object> generateRespose(String message, HttpStatus st, Object responseobj) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("meaasge", message);
        map.put("Status", st.value());
        map.put("data", responseobj);

        return new ResponseEntity<Object>(map, st);
    }
}
