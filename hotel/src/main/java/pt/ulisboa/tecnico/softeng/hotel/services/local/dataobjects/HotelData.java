package pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.hotel.domain.*;

import java.util.ArrayList;
import java.util.List;

public class HotelData {
    public enum CopyDepth {
        HOTELS, ROOMS, BOOKINGS, SHALLOW
    }

    private String name;
    private String code;
    private List<HotelRoomData> rooms = new ArrayList<>();
    private List<RoomBookingData> bookings = new ArrayList<>();

    public HotelData() {
    }

    public HotelData(Hotel hotel, CopyDepth depth) {
    	this.code = hotel.getCode();
    	this.name = hotel.getName();
        
        switch (depth) {
        	case ROOMS:
        		for (Room room : hotel.getRoomSet()) {
                    this.rooms.add(new HotelRoomData(room));
                }
        	case BOOKINGS:
        		for(Room room : hotel.getRoomSet()){
        			for (Booking booking : room.getBookingSet()) {
        				this.bookings.add(new RoomBookingData(room, booking));
        			}
                }
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

	public List<HotelRoomData> getRooms() {
		return rooms;
	}
}
