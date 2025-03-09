package lis.shop.billion.controller;

import jakarta.security.auth.message.AuthException;
import lis.shop.billion.controller.registerDto.RegisterResponse;
import lis.shop.billion.controller.registerDto.RegisterUser;
import lis.shop.billion.controller.jwtDto.JwtRequest;
import lis.shop.billion.controller.jwtDto.JwtResponse;
import lis.shop.billion.controller.jwtDto.RefreshJwtRequest;
import lis.shop.billion.entity.User;
import lis.shop.billion.service.AuthService;
import lis.shop.billion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterUser registerUser) {

        if (registerUser.isSimilarPassword()){
            User user = userService.saveUser(registerUser);
            RegisterResponse registerResponse = new RegisterResponse(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(registerResponse);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest jwtRequest) {
        System.out.println("JWT_REQUEST >>>>>>>>>>>>>>" + jwtRequest.password());
        final JwtResponse token;
        try {
            System.out.println("JWT_REQUEST >>>>>>>>>>>>>>" + jwtRequest.email());
            token = authService.login(jwtRequest);
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(token);
        ////******************
//        try {
//            Authentication authenticate = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));
//        } catch (BadCredentialsException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверные учетные данные");
//        }
//        Optional<User> byUsername = userService.findByUsername(request.username());
//        UserDetails userDetails = byUsername
//                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
//        String token = jwtUtil.generateToken(userDetails);
//        System.out.println("TOKEN " + token);
//        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
