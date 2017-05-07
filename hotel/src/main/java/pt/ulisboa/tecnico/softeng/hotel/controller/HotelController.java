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

@Controller
@RequestMapping(value = "/hotels/{hotelCode}")
public class HotelController {
    private static Logger logger = LoggerFactory.getLogger(HotelController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String roomForm(Model model, @PathVariable String hotelCode) {
        logger.info("roomForm");

        HotelData hotelData = HotelInterface.getHotelDataByCode(hotelCode);
        if (hotelData == null) {
            model.addAttribute("error", "Error: there is no hotel with code " + hotelCode);
            model.addAttribute("hotel", new HotelData());
            model.addAttribute("room", new HotelRoomData());
            return "hotel";
        }

        model.addAttribute("room", new HotelRoomData());
        model.addAttribute("rooms", hotelData.getRooms());
        model.addAttribute("hotel", HotelInterface.getHotelDataByCode(hotelCode));
        return "hotel";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String roomSubmit(Model model, @PathVariable String hotelCode, @ModelAttribute HotelRoomData roomData) {
        logger.info("roomSubmit number:{} type:{}", roomData.getNumber(), roomData.getType());

        HotelData hotelData = HotelInterface.getHotelDataByCode(hotelCode);

        try {
            HotelInterface.createRoom(hotelCode, roomData);
        } catch (HotelException be) {
            model.addAttribute("error", "Error: it was not possible to create the rom");
            be.printStackTrace();
            model.addAttribute("room", roomData);
            model.addAttribute("rooms", hotelData.getRooms());
            model.addAttribute("hotel", hotelData);
            return "hotel";
        }

        return "redirect:/hotels/" + hotelCode;
    }
}
