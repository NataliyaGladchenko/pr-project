package com.example.prproject.controllers.auth;

import com.example.prproject.security.AuthToken;
import com.example.prproject.security.AuthUser;
import com.example.prproject.security.JwtTokenProvider;
import com.example.prproject.security.PlatformUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.prproject.controllers.BaseController.AUTH_URL;
import static com.example.prproject.controllers.BaseController.JWT_URL;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@Tag(name = "Authorization Controller")
public class AuthorizationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(JWT_URL + AUTH_URL)
    @ResponseBody
    @Operation(method = "Returns auth token")
    public AuthToken authorize(@RequestBody AuthUser authUser) {
        String username = authUser.getUsername();
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, authUser.getPassword()));

        List<String> roles = auth.getAuthorities().stream().map(Objects::toString).collect(Collectors.toList());
        return new AuthToken()
                .setUsername(username)
                .setToken(jwtTokenProvider.createToken(username, roles));
    }

}
