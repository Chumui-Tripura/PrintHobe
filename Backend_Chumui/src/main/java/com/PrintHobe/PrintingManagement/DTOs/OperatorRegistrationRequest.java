package com.PrintHobe.PrintingManagement.DTOs;

import java.math.BigDecimal;

public class OperatorRegistrationRequest {

    public static class PrinterInfo{
        private String name;
        private String location;
        private String status;
        private double timePerPageBw;
        private double timePerPageColor;
        private BigDecimal costBw;
        private BigDecimal costColor;
        private String hasPackage;
        private BigDecimal packagePrice;
        private Integer packagePage;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getTimePerPageBw() {
            return timePerPageBw;
        }

        public void setTimePerPageBw(double timePerPageBw) {
            this.timePerPageBw = timePerPageBw;
        }

        public double getTimePerPageColor() {
            return timePerPageColor;
        }

        public void setTimePerPageColor(double timePerPageColor) {
            this.timePerPageColor = timePerPageColor;
        }

        public BigDecimal getCostBw() {
            return costBw;
        }

        public void setCostBw(BigDecimal costBw) {
            this.costBw = costBw;
        }

        public BigDecimal getCostColor() {
            return costColor;
        }

        public void setCostColor(BigDecimal costColor) {
            this.costColor = costColor;
        }

        public String getHasPackage() {
            return hasPackage;
        }

        public void setHasPackage(String hasPackage) {
            this.hasPackage = hasPackage;
        }

        public BigDecimal getPackagePrice() {
            return packagePrice;
        }

        public void setPackagePrice(BigDecimal packagePrice) {
            this.packagePrice = packagePrice;
        }

        public Integer getPackagePage() {
            return packagePage;
        }

        public void setPackagePage(Integer packagePage) {
            this.packagePage = packagePage;
        }
    }

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private PrinterInfo printer;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PrinterInfo getPrinter() {
        return printer;
    }

    public void setPrinter(PrinterInfo printer) {
        this.printer = printer;
    }
}
