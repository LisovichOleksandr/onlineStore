package lis.shop.billion.service;

import lis.shop.billion.exception.UnauthorizedException;
import lis.shop.billion.securityConfiguration.jwt.JwtAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getAuthenticatedUserEmail() {
        JwtAuthentication auth = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new UnauthorizedException();
        }
        return (String) auth.getPrincipal();
    }
}
