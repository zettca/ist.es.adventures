package pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects;

import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;

import java.util.ArrayList;
import java.util.List;

public class HotelData {
    private String name;
    private String code;
    private List<HotelRoomData> rooms = new ArrayList<>();

    public HotelData() {
    }

    public HotelData(Hotel hotel) {
        this.code = hotel.getCode();
        this.name = hotel.getName();

        for (Room room : hotel.getRoomSet()) {
            this.rooms.add(new HotelRoomData(room));
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
