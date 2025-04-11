package lis.shop.billion.service;


import lis.shop.billion.entity.User;
import lis.shop.billion.entity.UserDetails;
import lis.shop.billion.repository.UserDetailsCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsCustomService {

    private final UserDetailsCustomRepository userDetailsRepository;


    public UserDetails findDetailsByEmail(String email) {
        Optional<UserDetails> userDetails = userDetailsRepository.findByEmail(email);

        if (userDetails.isEmpty()) {
            throw new RuntimeException("Інформація відсутня.");
        }
        return userDetails.get();
    }
}
