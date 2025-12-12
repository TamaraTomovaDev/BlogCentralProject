package org.intecbrussel.dto;

public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;
    private String street;
    private String houseNr;
    private String city;
    private String zip;

    public UserUpdateRequest() {}

    // getters / setters...
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getHouseNr() { return houseNr; }
    public void setHouseNr(String houseNr) { this.houseNr = houseNr; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }
}
