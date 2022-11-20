package com.digitalmedia.users.service;

import com.digitalmedia.users.model.User;
import com.digitalmedia.users.model.dto.UserAdminDTO;
import com.digitalmedia.users.repository.UserRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    public static final String REALM = "DigitalMedia";
    private final UserRepository userRepository;
    private final Keycloak keycloakClient;

    public UserServiceImpl(UserRepository userRepository, Keycloak keycloakClient) {
        this.userRepository = userRepository;
        this.keycloakClient = keycloakClient;
    }

    @Override
    public User validateAndGetUserExtra(String username) {
        return userRepository.validateAndGetUser(username);
    }

    @Override
    public Optional<User> getUserExtra(String username) {
        return userRepository.getUserExtra(username);
    }

    @Override
    public User saveUserExtra(User user) {
        return userRepository.saveUserExtra(user);
    }

    public List<User> getUsers() {
        return keycloakClient
                .realm(REALM)
                .users()
                .list()
                .stream()
                .map(User::fromResource)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAdminDTO> getUsersNoAdmin() {
        List<UserRepresentation> usersFromKeycloak = keycloakClient
                .realm(REALM)
                .users()
                .list();
        List<UserRepresentation> usersEnabled = usersFromKeycloak
                .stream()
                .filter(ur -> ur.getGroups()
                        .stream()
                        .noneMatch(s -> Objects.equals(s, "admin")))
                .collect(Collectors.toList());

        return usersEnabled
                .stream()
                .map(u -> new UserAdminDTO(u.getUsername(), u.getEmail())).collect(Collectors.toList());
    }

}
