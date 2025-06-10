package ru.tarasov.techservice.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tarasov.techservice.entity.ApplicationUser;
import ru.tarasov.techservice.entity.Booking;
import ru.tarasov.techservice.service.OperatorService;

import javax.management.relation.RoleNotFoundException;
import javax.security.auth.login.AccountException;

@Controller
@RequestMapping("/operator")
public class OperatorController {

    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @GetMapping("/create")
    public String create(ApplicationUser applicationUser) {
        return "operator/create";
    }

    @GetMapping("/edit")
    public String edit(ApplicationUser applicationUser) {
        return "operator/edit";
    }

    @Secured("ROLE_OPERATOR")
    @GetMapping("/booking")
    public String edit(Booking booking) {
        return "operator/booking";
    }

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @PostMapping("/create")
    public String createOperator(ApplicationUser user, Model model) {
        try {
            operatorService.createOperator(user);
            return "redirect:/account/login";
        } catch (AccountException e) {
            model.addAttribute("error", e.getMessage());
            return "operator/create";
        }
    }

    @PostMapping("/edit")
    public String editOperator(ApplicationUser user, Model model) {
        try {
            operatorService.editOperator(user);
            return "redirect:/account/login";
        } catch (AccountException | RoleNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "operator/edit";
        }
    }

    @Secured("ROLE_OPERATOR")
    @PostMapping("/booking")
    public String editBooking(Booking booking) {
        operatorService.updateBooking(booking);
        return "operator/booking";
    }
}
