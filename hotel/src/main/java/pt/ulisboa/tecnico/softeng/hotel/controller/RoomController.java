package pt.ulisboa.tecnico.softeng.hotel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import pt.ulisboa.tecnico.softeng.hotel.controller.HotelsController;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.hotel.services.local.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.HotelData;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.hotel.services.local.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.HotelRoomData;
import pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.HotelData;

@Controller
@RequestMapping(value = "/Hotels/{roomCode}")
public class RoomController {
    private static Logger logger = LoggerFactory.getLogger(RoomController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String roomForm(Model model) {
        logger.info("roomForm");
        model.addAttribute("room", new HotelRoomData());
        model.addAttribute("hotel", HotelInterface.getHotels());
        return "hotel";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String bankSubmit(Model model, @PathVariable String hotelCode, @ModelAttribute HotelRoomData roomData) {
        logger.info("roomSubmit number:{} type:{}", roomData.getNumber(), roomData.getType());

        try {
           HotelInterface.createRoom(hotelCode,roomData);
        } catch (HotelException be) {
            model.addAttribute("error", "Error: it was not possible to create the rom");
            be.printStackTrace();
            model.addAttribute("room", roomData);
            model.addAttribute("hotel", HotelInterface.getHotels());
            return "banks";
        }

        return "redirect:/banks";
    }
}
