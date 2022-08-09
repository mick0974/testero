package it.univr.di.testero.controller;


import it.univr.di.testero.config.AuthService;
import it.univr.di.testero.model.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;


@Controller
public class MainController implements ErrorController {

    @Autowired
    AuthService authService;

    @GetMapping("/")
    public String getIndex() {
        User authUser = authService.userGet();

        if (authUser == null) {
            return "redirect:/login";
        }else{
            return "redirect:/home";
        }
    }

    @GetMapping("/home")
    public String getHome(Model model) {
        User authUser = authService.userGet();
        model.addAttribute("user", authUser);
        return "home";
    }

    @GetMapping("/login")
    public String getLogin() {
        User userAuth = authService.userGet();
        if (userAuth!=null){
            return "redirect:/home";
        }
        return "login";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        String error_message = "Unknown Error";
        int statusCode=-1;

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            statusCode = Integer.parseInt(status.toString());
            error_message = "Error "+statusCode;
        }

        if(statusCode == HttpStatus.FORBIDDEN.value()) {
            error_message += ": Access forbidden.\nYou need higher powers to access this resource.\nThis incident will be reported.";
        }
        else if(statusCode == HttpStatus.NOT_FOUND.value()) {
            error_message += ": Not found.\nThe resource you are looking for has never existed.";
        }
        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            error_message += ": Internal error.\nServer is on fire.";
        }

        model.addAttribute("error_message",error_message);
        return "error";
    }



}