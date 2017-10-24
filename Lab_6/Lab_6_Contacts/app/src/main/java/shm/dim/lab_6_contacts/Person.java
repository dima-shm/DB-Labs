package shm.dim.lab_6_contacts;

public class Person {

    String secondName, firstName, phoneNumber, birthday;

    public Person(String secondName, String firstName, String phoneNumber, String birthday) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
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

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return secondName + " "  + firstName + " " + phoneNumber + " " + birthday;
    }
}