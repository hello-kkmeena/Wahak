package com.wahak.controller;

import com.wahak.dto.ResetPasswordRequest;
import com.wahak.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author krishna.meena
 */


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/reset-password")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String resetPassword(@RequestParam @NotBlank @Valid String token) {
        if(authService.isValidToken(token)) {
            return "resetPassword";
        }
        return "invalidToken";
    }

    @PostMapping("/reset-password")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> resetPassword(@RequestParam @NotBlank @Valid String token, @RequestBody @Valid ResetPasswordRequest request) {
        if (authService.isValidToken(token)) {
            return ResponseEntity.ok(authService.createPassword(token,request));
        }
        return ResponseEntity.badRequest().body("invalidToken");
    }



//post controller check rate limiting
//
    @PostMapping("/login")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> login(@RequestParam @NotBlank @Valid String mobile) {
        Map<String,Object> response=authService.login(mobile);
        if( (boolean)response.get("status")) {
            return ResponseEntity.ok().header("otp_token", (String) response.get("token")).body(response);
        }
        return ResponseEntity.internalServerError().body(response);
    }

    @PostMapping("/verify-otp")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> verifyOtp(@RequestHeader("otp_token") String otpToken,
                                            @RequestParam @NotBlank @Valid String otp) {
        Map<String,Object> response=authService.verifyOtp(otpToken,otp);
        if( (boolean)response.get("status")) {
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.internalServerError().body(response);
    }





}
