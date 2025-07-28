package kz.slamkulgroup.kyndebackendapp.service;

import kz.slamkulgroup.kyndebackendapp.dto.JwtResponse;
import kz.slamkulgroup.kyndebackendapp.dto.LoginRequest;
import kz.slamkulgroup.kyndebackendapp.dto.RegisterRequest;
import kz.slamkulgroup.kyndebackendapp.entity.Streak;
import kz.slamkulgroup.kyndebackendapp.entity.User;
import kz.slamkulgroup.kyndebackendapp.mapper.UserMapper;
import kz.slamkulgroup.kyndebackendapp.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    public void registerUser(RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }
        
        if (registerRequest.getPhone() != null && userService.existsByPhone(registerRequest.getPhone())) {
            throw new RuntimeException("Phone number is already taken!");
        }
        
        User user = userMapper.toEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        
        User savedUser = userService.save(user);
        
        Streak streak = new Streak(savedUser);
        savedUser.setStreak(streak);
        userService.save(savedUser);
        
        logger.info("User registered successfully with email: {}", registerRequest.getEmail());
    }
    
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        
        String jwt = jwtUtils.generateJwtToken(loginRequest.getEmail());
        
        logger.info("User authenticated successfully: {}", loginRequest.getEmail());
        
        return new JwtResponse(jwt, loginRequest.getEmail());
    }
}