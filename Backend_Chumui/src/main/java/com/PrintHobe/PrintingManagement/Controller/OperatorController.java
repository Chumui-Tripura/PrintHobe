package com.PrintHobe.PrintingManagement.Controller;

import com.PrintHobe.PrintingManagement.DTOs.OperatorDashboardResponse;
import com.PrintHobe.PrintingManagement.DTOs.OperatorRegistrationRequest;
import com.PrintHobe.PrintingManagement.DTOs.OperatorResponseDTO;
import com.PrintHobe.PrintingManagement.DTOs.UpdateAvailableTillRequest;
import com.PrintHobe.PrintingManagement.Entity.Operator;
import com.PrintHobe.PrintingManagement.Repository.OperatorRepository;
import com.PrintHobe.PrintingManagement.Service.OperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/operators")
@CrossOrigin
public class OperatorController {

    private final OperatorService operatorService;
    private final OperatorRepository operatorRepository;

    public OperatorController(OperatorService operatorService, OperatorRepository operatorRepository){
        this.operatorService = operatorService;
        this.operatorRepository = operatorRepository;
    }



    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerOperator(@RequestBody OperatorRegistrationRequest dto) {
        try {
            operatorService.registerOperator(dto);
            return ResponseEntity.ok(Map.of("message", "Operator and Printer Registered Successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Registration failed. Please try again"));
        }
    }

    // This is for fetching the data of the operator after first sign up
    @GetMapping("/email/{email}")
    public ResponseEntity<OperatorResponseDTO> getOperatorByEmail(@PathVariable String email){
        return operatorRepository.findByEmail(email)
                .map(op -> new OperatorResponseDTO(
                        op.getOperatorId(),
                        op.getFirstName(),
                        op.getLastName(),
                        op.getEmail(),
                        op.getPhoneNumber(),
                        "OPERATOR"
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dashboard/{operatorId}")
    public ResponseEntity<OperatorDashboardResponse> getDashboardData(@PathVariable Long operatorId){
        OperatorDashboardResponse data = operatorService.getOperatorDashboardData(operatorId);
        return ResponseEntity.ok(data);
    }

    // For Setting the printer status
    @PutMapping("/printer/{operatorId}/status")
    public ResponseEntity<String> updatePrinterStatus(@PathVariable Long operatorId, @RequestParam String status) {
        try {
            operatorService.updatePrinterStatus(operatorId, status.toUpperCase());
            return ResponseEntity.ok("Printer status updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    // This is for updating the time
    @PutMapping("/printer/{operatorId}/available-till")
    public ResponseEntity<String> updateAvailableTillTime(
            @PathVariable Long operatorId,
            @RequestBody UpdateAvailableTillRequest request) {

        operatorService.updateAvailableTill(operatorId, request.getTime());
        return ResponseEntity.ok("Available till time updated successfully");
    }
}

// If your frontend (HTML/JS) runs on, say, http://localhost:3000 and your backend API runs on http://localhost:8080, this is considered a cross-origin request.

// Without @CrossOrigin (or proper CORS configuration), the browser will block the request and you'll get a CORS error in the browser console.

//Adding @CrossOrigin on your controller or method allows the backend to tell the browser: "I accept requests from other origins," so your frontend can successfully call your API.

// What is meant by validation errors?
//These are errors caused by invalid input from the client.
//
//For example:
//
//Trying to register with an email that already exists.
//
//Missing required fields.
//
//Password too short or invalid format.

//What is a generic fallback error?
//This is an unexpected error that you didn’t explicitly check for.
//
//For example:
//
//Database connection issues.
//
//Null pointer exceptions.
//
//Any other internal server problems.
//
//You catch all other exceptions in the catch (Exception e) block.
//
//You respond with HTTP 500 (Internal Server Error) because the problem is on the server side, not due to the client’s input.