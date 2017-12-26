package shm.dim.lab_16;

public class Contact {

    private String name;
    private String phoneNumber;
    private String ID;

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getID(){
        return ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}