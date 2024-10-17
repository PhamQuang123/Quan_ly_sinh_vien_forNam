package example.qlhs.controller.AuthController;

import example.qlhs.dto.response.ErrorsData;
import example.qlhs.dto.response.JwtResponse;
import example.qlhs.dto.response.ResponseSuccess;
import example.qlhs.entity.User;
import example.qlhs.exception.CustomValidationException;
import example.qlhs.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseSuccess> register(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            ErrorsData errorsData = ErrorsData.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            throw new CustomValidationException(errors,errorsData);
        }


        ResponseSuccess response =  userService.register(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseSuccess> login(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            ErrorsData errorsData = ErrorsData.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            throw new CustomValidationException(errors,errorsData);
        }
       JwtResponse jwtResponse = userService.login(user);
        ResponseSuccess response = ResponseSuccess.builder()
                .timestamp(LocalDateTime.now())
                .message("successfully login")
                .status(HttpStatus.CREATED.value())
                .jwtResponse(jwtResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
