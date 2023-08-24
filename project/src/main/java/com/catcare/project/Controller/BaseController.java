package com.catcare.project.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("")
public class BaseController {
    
    // Redirect from http://localhost:8090/ to http://localhost:8090/catcare/landing
    @RequestMapping("")
    public RedirectView redirectToLanding() {
        return new RedirectView("/catcare/landing");
    }

}