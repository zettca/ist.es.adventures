package pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.hotel.domain.*;


import java.util.ArrayList;
import java.util.List;

public class HotelData {
    public enum CopyDepth {
        SHALLOW, ACCOUNTS, ROOMS, OPERATIONS
    }

    private String name;
    private String code;

    public HotelData() {
    }

    public HotelData(Hotel hotel, CopyDepth depth) {
    	this.code = hotel.getCode();
    	this.name = hotel.getName();
        
        switch (depth) {
     
            case SHALLOW:
                break;
            default:
                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
