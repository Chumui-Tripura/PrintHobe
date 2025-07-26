package com.PrintHobe.PrintingManagement.Service;

import com.PrintHobe.PrintingManagement.DTOs.LoginResponse;
import com.PrintHobe.PrintingManagement.DTOs.OperatorResponseDTO;
import com.PrintHobe.PrintingManagement.DTOs.PrinterSummaryDTO;
import com.PrintHobe.PrintingManagement.Entity.Operator;
import com.PrintHobe.PrintingManagement.Entity.Printer;
import com.PrintHobe.PrintingManagement.Entity.User;
import com.PrintHobe.PrintingManagement.Repository.OperatorRepository;
import com.PrintHobe.PrintingManagement.Repository.PrinterRepository;
import com.PrintHobe.PrintingManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PrinterRepository printerRepository;

    @Autowired
    private OperatorRepository operatorRepository;


    public User registerUser(User user){
        // hashing the pass before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean login(String email, String rawPassword){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    public Object loginAndFetchDetails(String email, String rawPassword){
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())){
            User user = userOpt.get();

            // User Related Data
            Long userId = user.getUserId();
            String firstName = user.getFirstName();
            int availablePackagePages = user.getPackagePage();
            String role = "USER";

            //Printer Documents
            // As we are thinking there is only one printer
            Optional<Printer> optionalPrinter = printerRepository.findById(1L);
            Printer printer =optionalPrinter.get();

            boolean printerAvailable = printer.getStatus() == Printer.PrinterStatus.AVAILABLE;
            BigDecimal costPerPageBW = printer.getCostBw();
            BigDecimal getCostPerPageColor = printer.getCostColor();

            return new LoginResponse(userId, role, "Login Successful", firstName, printerAvailable, availablePackagePages, costPerPageBW, getCostPerPageColor);
        }
        // I need to work here on Operator Logic.

        Optional<Operator> operatorOpt = operatorRepository.findByEmail(email);
        if (operatorOpt.isPresent() && passwordEncoder.matches(rawPassword, operatorOpt.get().getPassword())) {
            Operator operator = operatorOpt.get();

            Long operatorId = operator.getOperatorId();
            String firstName = operator.getFirstName();
            String lastName = operator.getLastName();
            String operatorEmail = operator.getEmail();
            String phoneNumber = operator.getPhoneNumber();


            return new OperatorResponseDTO(operatorId, firstName, lastName, operatorEmail, phoneNumber, "OPERATOR");
        }

        throw new RuntimeException("Invalid Email or Password");
    }


    // This is for User Front Page Left and Top Section
    public PrinterSummaryDTO getPrinterSummaryById(Long printerId) {
        Printer printer = printerRepository.findById(printerId)
                .orElseThrow(() -> new RuntimeException("Printer not found with id: " + printerId));

        PrinterSummaryDTO dto = new PrinterSummaryDTO();
        dto.setPrinterName(printer.getName());
        dto.setColorCost(printer.getCostColor());
        dto.setBlackWhiteCost(printer.getCostBw());
        dto.setPackagePrice(printer.getPackagePrice());
        dto.setPackagePages(printer.getPackagePage());

        boolean isAvailable = printer.getStatus() == Printer.PrinterStatus.AVAILABLE &&
                (printer.getAvailableTill() == null || printer.getAvailableTill().isAfter(LocalDateTime.now()));
        dto.setAvailable(isAvailable);

        // Set operator phone number
        if (printer.getOperator() != null) {
            dto.setOperatorPhoneNumber(printer.getOperator().getPhoneNumber());
        }

        return dto;
    }

}
