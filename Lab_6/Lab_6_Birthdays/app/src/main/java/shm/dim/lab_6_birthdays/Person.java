package shm.dim.lab_6_birthdays;

public class Person {

    String secondName, firstName, phoneNumber, birthDate;

    public Person(String secondName, String firstName, String phoneNumber, String birthDate) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public void setSecondName( String secondName) {
        this.secondName = secondName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setFirstName( String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setPhoneNumber( String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setBirthDate( String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        return secondName + " "  + firstName + " " + phoneNumber + " " + birthDate;
    }
}