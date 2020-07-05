package Model;

public class Customer extends Address{

    private Integer customerId;
    private String customerName, customerAddress, customerCity, customerCountry, customerPhone, postalCode;;
    private Integer addressId;
    private Integer active;

    public Customer() {}


    public Customer(Integer customerId, String customerName, Integer addressId, String customerAddress, String postalCode, String customerCity, String customerCountry, String customerPhone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.customerAddress = customerAddress;
        this.customerCity = customerCity;
        this.customerCountry = customerCountry;
        this.customerPhone = customerPhone;
        this.postalCode = postalCode;
    }



    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPostalCode() {
        return postalCode;
    }

    public void setCustomerPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
