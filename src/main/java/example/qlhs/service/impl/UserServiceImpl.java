package example.qlhs.service.impl;

import example.qlhs.dto.response.ErrorsData;
import example.qlhs.dto.response.JwtResponse;
import example.qlhs.dto.response.ResponseSuccess;
import example.qlhs.entity.Role;
import example.qlhs.entity.User;
import example.qlhs.exception.CustomValidationException;
import example.qlhs.repository.RoleRepository;
import example.qlhs.repository.UserRepository;
import example.qlhs.security.jwt.JWTProvider;
import example.qlhs.security.principals.CustomUserDetails;
import example.qlhs.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    @Override
    public JwtResponse login(User user) {
       userRepository.findByUsername(user.getUsername()).orElseThrow(()-> new RuntimeException("Username not found"));

        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        }catch (AuthenticationException ex){
            log.error("Sai username hoac password");
            ErrorsData errorsData = ErrorsData.builder()
                    .message("Sai username hoac password")
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            throw new CustomValidationException(errorsData);
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(userDetails);
        return JwtResponse.builder()
                .username(userDetails.getUsername())
                .authorities(userDetails.getAuthorities())
                .accessToken(accessToken)
                .build();
    }



    @Override
    public ResponseSuccess register(User user) {
        User newUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        Set<Role> roles = new HashSet<>();

        roles.add(roleRepository.findByRoleName("ROLE_USER").orElseThrow(()->new NoSuchElementException("Khong ton tai role user")));

        newUser.setRoles(roles);
        userRepository.save(newUser);
        ResponseSuccess responseSuccess = ResponseSuccess.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("Dang ky tai khoan thanh cong")
                .build();
        return responseSuccess;
    }


}

