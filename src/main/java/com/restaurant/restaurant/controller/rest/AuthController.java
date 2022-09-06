package com.restaurant.restaurant.controller.rest;

import com.restaurant.restaurant.entity.Role;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.jwtsecurity.CustomUserDetails;
import com.restaurant.restaurant.jwtsecurity.JwtTokenProvider;
import com.restaurant.restaurant.payload.ApiResponse;
import com.restaurant.restaurant.payload.request.SignInRequest;
import com.restaurant.restaurant.payload.request.SignupRequest;
import com.restaurant.restaurant.payload.response.JwtResponse;
import com.restaurant.restaurant.service.JwtUserDetailsService;
import com.restaurant.restaurant.service.RoleService;
import com.restaurant.restaurant.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.SQLOutput;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) throws Exception {
        System.out.println(signInRequest.getUsername());
        authenticate(signInRequest.getUsername(), signInRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(signInRequest.getUsername());

        final String token = jwtTokenProvider.generateToken(userDetails);
        Map<String,Object> maps= new HashMap<>();
        maps.put("user", userService.findByEmail(signInRequest.getUsername()));
        maps.put("token", token);
        return ResponseEntity.ok(maps);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        Role role = roleService.findById(Long.valueOf(2));
        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setIsEnabled(true);
        user.setCreatedAt(Instant.now());
        userService.save(user);
        System.out.println(user);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "User registered successfully"), HttpStatus.OK);
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("User is curently disabled", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("User id or password is wrong", e);
        }catch (Exception ex){
            System.out.println("Wrong");
        }
    }
}
