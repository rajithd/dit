package com.dit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "restaurants")
public class Restaurant {

    private String id;
    private String name;
    private String description;
    private String address;
    private List<Zone> zones;
    private String url;
    private String regNo;
    private String slogan;
    @DBRef
    private List<Menu> menus;

    private List<Menu> specialOffers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Zone> getZones() {
        if (zones == null) {
            zones = new LinkedList<Zone>();
        }
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public List<Menu> getMenus() {
        if (menus == null) {
            menus = new LinkedList<Menu>();
        }
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Menu> getSpecialOffers() {
        if(this.specialOffers == null){
            return specialOffers = new LinkedList<>();
        }
        return specialOffers;
    }

    public void setSpecialOffers(List<Menu> specialOffers) {
        this.specialOffers = specialOffers;
    }
}
