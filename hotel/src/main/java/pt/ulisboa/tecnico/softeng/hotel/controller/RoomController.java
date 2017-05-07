package pt.ulisboa.tecnico.softeng.hotel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.hotel.services.local.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.HotelData;
import pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.HotelRoomData;
import pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.RoomBookingData;

@Controller
@RequestMapping(value = "/hotels/{hotelCode}/rooms/{roomNumber}")
public class RoomController {
    private static Logger logger = LoggerFactory.getLogger(HotelsController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String clientForm(Model model, @PathVariable String hotelCode, @PathVariable String roomNumber) {
        logger.info("bookingForm code:{} num:{}", hotelCode, roomNumber);

        HotelData hotelData = HotelInterface.getHotelDataByCode(hotelCode);
        if (hotelData == null) {
            model.addAttribute("error", "Error: there is no hotel with code " + hotelCode);
            model.addAttribute("hotel", new HotelData());
            model.addAttribute("booking", new RoomBookingData());
            model.addAttribute("room", new HotelRoomData());
            return "room";
        }

        HotelRoomData roomData = HotelInterface.getRoomDataByRoomNumber(hotelData, roomNumber);
        if (roomData == null) {
            model.addAttribute("error", "Error: there is no room with number " + roomNumber);
            model.addAttribute("hotel", hotelData);
            model.addAttribute("room", new HotelRoomData());
            model.addAttribute("rooms", hotelData.getRooms());
            return "room";
        }

        model.addAttribute("hotel", hotelData);
        model.addAttribute("room", roomData);
        model.addAttribute("rooms", hotelData.getRooms());
        model.addAttribute("booking", new RoomBookingData());
        model.addAttribute("bookings", roomData.getBookings());
        return "room";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String accountSubmit(Model model, @PathVariable String hotelCode, @PathVariable String roomNumber,
                                @ModelAttribute RoomBookingData bookData) {
        logger.info("bookingSubmit hotelCode:{} roomNumber:{} | ", hotelCode, roomNumber);
        logger.info("bookingSubmit arrival:{} departure:{} | ", bookData.getArrival(), bookData.getDeparture());

        try {
            Room.Type type = Room.Type.valueOf(bookData.getRoomType());
            HotelInterface.reserveRoom(type, bookData.getArrival(), bookData.getDeparture());
        } catch (HotelException he) {
            model.addAttribute("error", "Error: it was not possible to create the booking");
            he.printStackTrace();
            model.addAttribute("booking", bookData.getReference());
            model.addAttribute("room", bookData.getRoomType());
            model.addAttribute("hotel", bookData.getHotelName());
            return "hotel";
        }

        return "redirect:/hotels/" + hotelCode + "/rooms/" + roomNumber;
    }
}
