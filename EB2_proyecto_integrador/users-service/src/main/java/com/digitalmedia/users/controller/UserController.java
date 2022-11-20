package com.digitalmedia.users.controller;

import com.digitalmedia.users.model.User;
import com.digitalmedia.users.model.dto.UserAdminDTO;
import com.digitalmedia.users.model.dto.UserRequest;
import com.digitalmedia.users.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    //TODO  estos dos endpoints funcionaran cuando este configurada la seguridad en el proyecto

    @GetMapping("/me") // obtenemos el usuario (en este caso se usa Mongo DB para los usuarios)
    public User getUserExtra(Principal principal) {
        return userService.validateAndGetUserExtra(principal.getName());
    }

    @PostMapping("/me") //
    public User saveUserExtra(@Valid @RequestBody UserRequest updateUserRequest, Principal principal) {
        Optional<User> userOptional = userService.getUserExtra(principal.getName()); // Busca el usuario
        User userExtra = userOptional.orElseGet(() -> new User(principal.getName())); // si no hay nada devuelve un usuario nuevo
        userExtra.setAvatar(updateUserRequest.getAvatar()); // le setea el avatar a ese usuario
        return userService.saveUserExtra(userExtra);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_administrador')")
    public List<UserAdminDTO> getAllUsersNoAdmin() {
        return userService.getUsersNoAdmin();
    }

}
