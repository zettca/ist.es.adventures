package pt.ulisboa.tecnico.softeng.hotel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String clientForm(Model model, @PathVariable String hotelCode) {
        logger.info("bookingForm account:{}", hotelCode);

        HotelData hotelData = HotelInterface.getHotelDataByCode(hotelCode, HotelData.CopyDepth.ROOMS);

        if (hotelData != null) {
            model.addAttribute("hotel", hotelData);
            model.addAttribute("room", new HotelRoomData());
            model.addAttribute("rooms", hotelData.getRooms());
            return "hotel";
        } else {
            model.addAttribute("error", "Error: there is no hotel with code " + hotelCode);
            model.addAttribute("hotel", new HotelData());
            model.addAttribute("booking", new RoomBookingData());
            model.addAttribute("room", new HotelRoomData());
            return "hotels";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String accountSubmit(Model model, @PathVariable String hotelCode, @ModelAttribute RoomBookingData bookingData) {
        logger.info("bookingSubmit hotelCode:{} roomNumber:{}", hotelCode, bookingData.getRoomNumber());

        try {
            HotelInterface.createBooking(hotelCode, bookingData);
        } catch (HotelException he) {
            model.addAttribute("error", "Error: it was not possible to create the booking");
            he.printStackTrace();
            model.addAttribute("booking", bookingData.getReference());
            model.addAttribute("room", bookingData.getRoomType());
            model.addAttribute("hotel", bookingData.getHotelName());
            return "hotel";
        }

        return "redirect:/hotels/" + hotelCode + "/rooms/" + bookingData.getRoomNumber();
    }
}
