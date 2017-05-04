package pt.ulisboa.tecnico.softeng.hotel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.hotel.services.local.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.services.local.dataobjects.HotelData;

@Controller
@RequestMapping(value = "/hotels")
public class HotelsController {
    private static Logger logger = LoggerFactory.getLogger(HotelsController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String hotelForm(Model model) {
        logger.info("hotelForm");
        model.addAttribute("hotel", new HotelData());
        model.addAttribute("hotels", HotelInterface.getHotels());
        return "hotels";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String hotelSubmit(Model model, @ModelAttribute HotelData hotelData) {
        logger.info("hotelSubmit name:{} code:{}", hotelData.getCode(), hotelData.getName());

        try {
        	HotelInterface.createHotel(hotelData);
        } catch (HotelException be) {
            model.addAttribute("error", "Error: it was not possible to create the Hotel");
            be.printStackTrace();
            model.addAttribute("hotel", hotelData);
            model.addAttribute("hotels", HotelInterface.getHotels());
            return "hotels";
        }

        return "redirect:/hotels";
    }
}
