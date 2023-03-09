package com.sundev.mission4us.web.rest;

import com.sundev.mission4us.service.UserService;
import com.sundev.mission4us.service.dto.AdminUserDTO;

import java.net.URI;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sundev.mission4us.web.rest.vm.ManagedUserVM;
import org.keycloak.admin.client.Keycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {
    private final Keycloak keycloak;

    private static class AccountResourceException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserService userService;

    public AccountResource(UserService userService, Keycloak keycloak) {
        this.userService = userService;
        this.keycloak = keycloak;
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @param principal the current user; resolves to {@code null} if not authenticated.
     * @return the current user.
     * @throws AccountResourceException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    @SuppressWarnings("unchecked")
    public AdminUserDTO getAccount(Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            return userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
        } else {
            throw new AccountResourceException("User could not be found");
        }
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * creating user in keycloak server and in postgres database
     * @param managedUserVM
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<AdminUserDTO> createUser(@Valid @RequestBody ManagedUserVM managedUserVM) {
        log.debug("Rest request to create user: {}", managedUserVM);
        AdminUserDTO adminUserDTO = userService.createUser(managedUserVM);
        return ResponseEntity.created(URI.create("/users/"+adminUserDTO.getId())).build();
    }

    @GetMapping("/count")
    public Integer getUsersTest() {
        return keycloak.realm("local_tests").users().count();
    }
}
