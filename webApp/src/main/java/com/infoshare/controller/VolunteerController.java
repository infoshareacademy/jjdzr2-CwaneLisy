package com.infoshare.controller;

import com.infoshare.domain.Volunteer;
import com.infoshare.dto.FilterForm;
import com.infoshare.formobjects.SearchVolunteerForm;
import com.infoshare.formobjects.VolunteerForm;
import com.infoshare.formobjects.VolunteerListObject;
import com.infoshare.formobjects.VolunteerSearchForm;
import com.infoshare.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("volunteer")
public class VolunteerController {

    public static final String ACTION_URL = "actionUrl";
    public static final String TYPES = "types";
    private final VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("/create")
    public String createVolunteer(Model model) {
        model.addAttribute(ACTION_URL, "form-details");
        model.addAttribute("request",new VolunteerForm());
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
    public String printAllVolunteers(Model model) {
        model.addAttribute(ACTION_URL, "submit-edited-form");
        model.addAttribute(TYPES, volunteerService.getTypesOfHelp());
        model.addAttribute("request", new VolunteerForm());
        model.addAttribute("volunteersList", getAllVolunteers());
        model.addAttribute("filterForm", new FilterForm());
        return "volunteer-list";
    }

    public List<VolunteerListObject> getAllVolunteers() {
        return volunteerService.getAllVolunteers().stream()
                .map(this::convertToVolunteerListForm)
                .collect(Collectors.toList());
    }

    public VolunteerListObject convertToVolunteerListForm(Volunteer volunteer) {
        return VolunteerListObject.VolunteerListObjectBuilder.aVolunteerListObject()
                .withUuid(volunteer.getUuid())
                .withName(volunteer.getName())
                .withPhone(volunteer.getPhone())
                .withLocation(volunteer.getLocation())
                .withTypeOfHelp(volunteer.getTypeOfHelp())
                .withIsAvailable(volunteer.isAvailable())
                .withEmail(volunteer.getEmail())
                .build();

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
    public String submitEditedVolunteerForm(@Valid @ModelAttribute("request") VolunteerForm volunteerForm,
                                            BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute(TYPES, volunteerService.getTypesOfHelp());
            model.addAttribute(ACTION_URL, "submit-edited-form");
            model.addAttribute("hasErrors", true);
            model.addAttribute("volunteersList",getAllVolunteers());
            return "volunteer-list";
        } else {
            volunteerService.editVolunteerData(volunteerForm.getName(), volunteerForm.getLocation(), volunteerForm.getEmail(), volunteerForm.getPhone(), volunteerForm.getTypeOfHelp(), volunteerForm.isAvailable(), volunteerForm.getUuid());
            return "redirect:/volunteer/all";
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
