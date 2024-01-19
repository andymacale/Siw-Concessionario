package it.uniroma3.siw.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String nonTrovata(HttpServletRequest richiesta) throws Exception {
        Object stato = richiesta.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (stato != null && Integer.valueOf(stato.toString()) == HttpStatus.NOT_FOUND.value()) {
            return "404.html";
        }
        return "error.html";
    }
}
