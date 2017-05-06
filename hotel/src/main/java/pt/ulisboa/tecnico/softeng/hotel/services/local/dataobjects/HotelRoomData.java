package pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class HotelRoomData {
	
    private String number;
    private pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type type;

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
}