public class Example {
    private final String name;
    private final UserRegistrationValidator validator;

    public Example(String name) {
        this.name = name;
        this.validator = new UserRegistrationValidator();
    }

    public String getName() {
        return name;
    }

    public void display() {
        System.out.println("Example name: " + name);
    }

    public ValidationReport validateUser(UserAccount user) {
        return validator.validate(user);
    }

    public void runValidationDemo() {
        UserAccount validUser = new UserAccount("anna.kowalska", "anna.kowalska@example.com", "Haslo#2026", 28);
        UserAccount invalidUser = new UserAccount("a", "zly-mail", "123", 14);

        printResult(validUser);
        printResult(invalidUser);
    }

    private void printResult(UserAccount user) {
        ValidationReport report = validateUser(user);
        System.out.println("Sprawdzanie uzytkownika: " + user.getUsername());

        if (report.isValid()) {
            System.out.println("Wynik: OK - dane sa poprawne.");
        } else {
            System.out.println("Wynik: BLAD - znaleziono problemy:");
            for (String issue : report.getIssues()) {
                System.out.println(" - " + issue);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Example example = new Example("UserValidationModule");
        example.display();
        example.runValidationDemo();
    }
}
