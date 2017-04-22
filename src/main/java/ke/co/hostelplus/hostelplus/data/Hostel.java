package ke.co.hostelplus.hostelplus.data;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by Badru on 19/04/2017.
 */

public class Hostel {
    int id;
    String name;
    String cost;
    String location;
    String amenities;
    List<AppusersHostels> appusers_hostels;
    String photo;
    String photo_dir;
    String phone;
    String email;
    String contact_name;
    String persons;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto_dir() {
        return photo_dir;
    }

    public void setPhoto_dir(String photo_dir) {
        this.photo_dir = photo_dir;
    }

    public Hostel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public List<AppusersHostels> getAppusersHostels() {
        return appusers_hostels;
    }

    public void setAppusersHostels(List<AppusersHostels> appusersHostels) {
        this.appusers_hostels = appusersHostels;
    }
}
