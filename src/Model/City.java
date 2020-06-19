package Model;

public class City {

    private int cityID;
    private String city;
    private int countryID;

    public City(int cityID, String city, int countryID) {
        this.cityID = cityID;
        this.city = city;
        this.countryID = countryID;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
