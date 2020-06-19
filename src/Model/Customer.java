package Model;

public class Customer {

    private int customerID;
    private String customerName;
    private int addressID;
    private Boolean active;

    public Customer(int customerID, String customerName, int addressID, Boolean active) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.addressID = addressID;
        this.active = active;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
