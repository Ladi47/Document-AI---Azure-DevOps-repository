package com.example.validation;

public class UserRegistrationValidator {

    public ValidationReport validate(UserAccount user) {
        ValidationReport report = new ValidationReport();

        if (user == null) {
            report.addIssue("Obiekt uzytkownika jest pusty.");
            return report;
        }

        validateUsername(user.getUsername(), report);
        validateEmail(user.getEmail(), report);
        validatePassword(user.getPassword(), report);
        validateAge(user.getAge(), report);

        return report;
    }

    private void validateUsername(String username, ValidationReport report) {
        if (username == null || username.isBlank()) {
            report.addIssue("Nazwa uzytkownika jest wymagana.");
            return;
        }

        if (username.length() < 3 || username.length() > 20) {
            report.addIssue("Nazwa uzytkownika musi miec od 3 do 20 znakow.");
        }

        if (!username.matches("[a-zA-Z0-9._-]+")) {
            report.addIssue("Nazwa uzytkownika moze zawierac tylko litery, cyfry oraz znaki: . _ -");
        }
    }

    private void validateEmail(String email, ValidationReport report) {
        if (email == null || email.isBlank()) {
            report.addIssue("Adres e-mail jest wymagany.");
            return;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            report.addIssue("Adres e-mail ma niepoprawny format.");
        }
    }

    private void validatePassword(String password, ValidationReport report) {
        if (password == null || password.isBlank()) {
            report.addIssue("Haslo jest wymagane.");
            return;
        }

        if (password.length() < 8) {
            report.addIssue("Haslo musi miec co najmniej 8 znakow.");
        }

        if (!password.matches(".*[A-Z].*")) {
            report.addIssue("Haslo musi zawierac co najmniej jedna wielka litere.");
        }

        if (!password.matches(".*[a-z].*")) {
            report.addIssue("Haslo musi zawierac co najmniej jedna mala litere.");
        }

        if (!password.matches(".*[0-9].*")) {
            report.addIssue("Haslo musi zawierac co najmniej jedna cyfre.");
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>/?].*")) {
            report.addIssue("Haslo musi zawierac co najmniej jeden znak specjalny.");
        }
    }

    private void validateAge(int age, ValidationReport report) {
        if (age < 18) {
            report.addIssue("Uzytkownik musi miec co najmniej 18 lat.");
        }

        if (age > 120) {
            report.addIssue("Wiek uzytkownika jest poza realistycznym zakresem.");
        }
    }
}
