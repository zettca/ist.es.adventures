package pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.hotel.domain.Booking;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

import java.util.ArrayList;
import java.util.List;

public class HotelRoomData {
    private String number;
    private Type type;
    private List<RoomBookingData> bookings = new ArrayList<>();

    public HotelRoomData() {
    }

    public HotelRoomData(Room room) {
        this.number = room.getNumber();
        this.type = room.getType();

        for (Booking booking : room.getBookingSet()) {
            bookings.add(new RoomBookingData(room, booking));
        }
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