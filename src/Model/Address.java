package Model;

public class Address {

    private int addressID;
    private String address;
    private String address2;
    private int cityID;
    private String postalCode;
    private String phone;

    public Address(int addressID, String address, String address2, int cityID, String postalCode, String phone) {
        this.addressID = addressID;
        this.address = address;
        this.address2 = address2;
        this.cityID = cityID;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
