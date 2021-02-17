package com.infoshare.controller;

import com.infoshare.domain.Volunteer;
import com.infoshare.formobjects.SearchVolunteerForm;
import com.infoshare.formobjects.VolunteerForm;
import com.infoshare.formobjects.VolunteerSearchForm;
import com.infoshare.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("volunteer")
public class VolunteerController {

    public static final String TYPES = "types";
    private final VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("/create")
    public String createVolunteer(Model model) {
        model.addAttribute(new VolunteerForm());
        model.addAttribute(TYPES, volunteerService.getTypesOfHelp());
        return "volunteer-register-form";
    }

    @PostMapping("/form-details")
    public String createVolunteerFormDetails(@Valid @ModelAttribute("volunteerForm") VolunteerForm volunteerFrom, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute(TYPES, volunteerService.getTypesOfHelp());
            return "/volunteer-register-form";
        } else {
            volunteerService.registerNewVolunteer(volunteerFrom.getName(), volunteerFrom.getLocation(), volunteerFrom.getEmail(),
                    volunteerFrom.getPhone(), volunteerFrom.getTypeOfHelp(), volunteerFrom.isAvailable());
            return "redirect:/volunteer/all";
        }
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Volunteer> printAllVolunteers() {
        return volunteerService.getAllVolunteers();
    }

    @GetMapping("/search")
    public String searchForAvailableVolunteers(Model model) {
        model.addAttribute("VolunteerSearchForm", new VolunteerSearchForm());
        return "volunteer-search-form";
    }

    @PostMapping("/search/result")
    public String resultOfSearchForVolunteer(@Valid @ModelAttribute("VolunteerSearchForm") VolunteerSearchForm volunteerSearchForm,
                                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "volunteer-search-form";
        }
        model.addAttribute("volunteers", volunteerService.getVolunteerFilteredList(
                volunteerSearchForm.getCity(),
                volunteerSearchForm.getTypeOfHelp()));
        return "volunteer-search-results";
    }

    @GetMapping("/search-to-edit")
    public String searchForVolunteerToEdit(Model model) {
        model.addAttribute("searchVolunteerForm", new SearchVolunteerForm());
        return "search-volunteer-for-edit-form";
    }

    @PostMapping("/check-search-to-edit")
    public String checkIfVolunteerToEditExists(@Valid @ModelAttribute("searchVolunteerForm") SearchVolunteerForm searchVolunteerForm, BindingResult br) {
        if (br.hasErrors()) {
            return "search-volunteer-for-edit-form";
        } else {
            return "redirect:/volunteer/edit?email=" + searchVolunteerForm.getEmail();
        }
    }

    @GetMapping("/edit")
    public String editVolunteer(Model model, @RequestParam(value = "email") String email) {
        Volunteer volunteerForEdit = volunteerService.searchForVolunteer(email);
        model.addAttribute(new VolunteerForm(volunteerForEdit));
        model.addAttribute(TYPES, volunteerService.getTypesOfHelp());
        return "edited-volunteer-form";
    }

    @PostMapping("/submit-edited-form")
    public String submitEditedVolunteerForm(@Valid @ModelAttribute("volunteerForm") VolunteerForm volunteerForm, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute(TYPES, volunteerService.getTypesOfHelp());
            return "edited-volunteer-form";
        } else {
            volunteerService.editVolunteerData(volunteerForm.getName(), volunteerForm.getLocation(), volunteerForm.getEmail(), volunteerForm.getPhone(), volunteerForm.getTypeOfHelp(), volunteerForm.isAvailable(), volunteerForm.getUuid());
            return "successfully-edited-form";
        }
    }

    @GetMapping("/change-status")
    public String changeVolunteerStatus(Model model) {
        return getTestViewWithPageUnderConstructionMessage(model);
    }

    private String getTestViewWithPageUnderConstructionMessage(Model model) {
        model.addAttribute("message", "This page is under construction...");
        return "test-view";
    }
}
