package example.qlhs.service;

import example.qlhs.dto.response.JwtResponse;
import example.qlhs.dto.response.ResponseSuccess;
import example.qlhs.entity.User;

public interface UserService {
    JwtResponse login(User user);
    ResponseSuccess register(User user);
}
