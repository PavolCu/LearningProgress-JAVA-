package tracker;

// Třída Student reprezentuje informace o studentovi, včetně jména, příjmení a e-mailové adresy.
public class Student {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

}