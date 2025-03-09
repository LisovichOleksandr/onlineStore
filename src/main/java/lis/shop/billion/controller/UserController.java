package lis.shop.billion.controller;
import lis.shop.billion.controller.dto.UserDto;
import lis.shop.billion.entity.User;
import lis.shop.billion.securityConfiguration.jwt.JwtAuthentication;
import lis.shop.billion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    private ResponseEntity<UserDto> getUsers(){
        // Получимо користувача  що знаходиться в системі і по емайлу знайдем в БД
        JwtAuthentication auth = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = (String) auth.getPrincipal();
        UserDto userDto =  userService.getUserDtoByEmail(email);
        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/ud")
    public ResponseEntity<UserDetails> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userDetails);
    }

//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        return ResponseEntity.ok(userService.saveUser(user));
//    }
}
