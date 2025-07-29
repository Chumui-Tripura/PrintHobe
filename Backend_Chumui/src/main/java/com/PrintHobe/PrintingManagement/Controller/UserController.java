package com.PrintHobe.PrintingManagement.Controller;

import com.PrintHobe.PrintingManagement.DTOs.LoginResponse;
import com.PrintHobe.PrintingManagement.DTOs.PrinterSummaryDTO;
import com.PrintHobe.PrintingManagement.Entity.User;
import com.PrintHobe.PrintingManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            Object signupResponse = userService.registerAndFetchDetails(user);
            return ResponseEntity.ok(signupResponse);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signup failed: " + ex.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest){
        try {
            Object loginResponse = userService.loginAndFetchDetails(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(loginResponse);
        } catch (RuntimeException ex) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    // 9 is used for OS lab Printer
    @GetMapping("/printer/9/summary")
    public ResponseEntity<PrinterSummaryDTO> getPrinterSummary() {
        PrinterSummaryDTO summary = userService.getPrinterSummaryById(9L);
        return ResponseEntity.ok(summary);
    }
}