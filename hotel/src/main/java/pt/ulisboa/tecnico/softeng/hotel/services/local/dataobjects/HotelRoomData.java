package pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class HotelRoomData {
	
    private String number;
    private Type type;
    private List<RoomBookingData> bookings = new ArrayList<>();

    public HotelRoomData() {
    }

    public HotelRoomData(Room room) {
        this.number = room.getNumber();
        this.type = room.getType();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

	public List<RoomBookingData> getBookings() {
		return bookings;
	}
}