package com.rutu.tataconnect.admin.POJOClass;

public class POJOViewAllCustomerDetails {

    String id,images,name,mobileno,emailid,address,username;
    Double latitude,longitude;


    public POJOViewAllCustomerDetails(String id, String images, String name, String mobileno,
                                      String emailid, Double latitude, Double longitude,
                                      String address, String username) {
        this.id = id;
        this.images = images;
        this.name = name;
        this.mobileno = mobileno;
        this.emailid = emailid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
