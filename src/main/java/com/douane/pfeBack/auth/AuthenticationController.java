package com.douane.pfeBack.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// Marks this class as a REST controller, meaning it will handle web requests.
@RestController
// Sets the base URL for all methods in this controller.
@RequestMapping("/api/v1/auth/")
// Lombok annotation to automatically generate a constructor with 1 parameter for each field that requires special handling.
// In this case, it's used for dependency injection of AuthenticationService.
//@RequiredArgsConstructor
public class AuthenticationController {
    // Injection of the AuthenticationService to handle the business logic.
    @Autowired
      AuthenticationService service;

    // Method to handle POST requests for user registration. It consumes and produces JSON.
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody RegisterRequest request // @RequestBody annotation binds the HTTP request body to the parameter
    ){
        // Calls the register method of the service and wraps the response in a ResponseEntity with an OK status.
        return ResponseEntity.ok(service.register(request));
    }

    // Method to handle POST requests for user authentication. It consumes and produces JSON.
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request // @RequestBody annotation binds the HTTP request body to the parameter
    ){
        // Calls the authenticate method of the service and wraps the response in a ResponseEntity with an OK status.
        return ResponseEntity.ok(service.authenticate(request));
    }
}
