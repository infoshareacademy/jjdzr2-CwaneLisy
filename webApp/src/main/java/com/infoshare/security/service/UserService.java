package com.infoshare.security.service;

import com.infoshare.domain.Provider;
import com.infoshare.domain.SocialProviderUserData;
import com.infoshare.domain.User;
import com.infoshare.formobjects.RegisterUserForm;
import com.infoshare.security.CustomOAuth2User;
import com.infoshare.security.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UsersRepository userRepository;

    public void processOAuthPostLogin(CustomOAuth2User user) {
        Optional<User> existUser = userRepository.findByUserName(user.getName());

        if (existUser.isEmpty()) {
            User newUser = new User();
            newUser.setUserName(user.getName());
            newUser.setUserEmail(user.getEmail());
            SocialProviderUserData socialProviderUserData =
                    new SocialProviderUserData((String) user.getAttributes().get("id"),
                            Provider.valueOf(user.getAuthorizedClientRegistrationId().toUpperCase()), newUser);
            newUser.setSocialProviderUserData(Set.of(socialProviderUserData));
            userRepository.save(newUser);
        } else {
            User currentUser = existUser.get();
            if (currentUser.getSocialProviderUserData().stream()
                    .noneMatch(socialProviderUserData -> checkExistenceOfSocialData(user, socialProviderUserData))) {
                currentUser.getSocialProviderUserData().add(
                        new SocialProviderUserData((String) user.getAttributes().get("id"),
                                Provider.valueOf(user.getAuthorizedClientRegistrationId().toUpperCase()), currentUser));
                userRepository.save(currentUser);
            }
        }

    }

    public void registerNewUser(RegisterUserForm form) {
        // TO DO: write method for user register.
    }

    private boolean checkExistenceOfSocialData(CustomOAuth2User user, SocialProviderUserData socialProviderUserData) {
        return socialProviderUserData.getProvider().name()
                .equalsIgnoreCase(user.getAuthorizedClientRegistrationId())
                && socialProviderUserData.getProviderUserId().equals(user.getAttributes().get("id"));
    }
}
