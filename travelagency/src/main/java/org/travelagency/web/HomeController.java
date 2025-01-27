package org.travelagency.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView index() {

        return new ModelAndView("index");
    }

    @GetMapping("/about-us")
    public ModelAndView about() {

        return new ModelAndView("about-us");
    }

    @GetMapping("/contacts")
    public ModelAndView contacts() {

        return new ModelAndView("contacts");
    }
}
