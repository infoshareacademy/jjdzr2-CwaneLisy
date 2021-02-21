package com.infoshare.controller;

import com.infoshare.domain.Volunteer;
import com.infoshare.dto.VolunteerFilterForm;
import com.infoshare.dto.VolunteerListObject;
import com.infoshare.formobjects.*;
import com.infoshare.service.VolunteerService;
import com.infoshare.util.NeedRequestHelper;
import com.infoshare.util.VolunteerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Controller
@RequestMapping("volunteer")
public class VolunteerController {
    public static final String EDIT_FORM_ACTION_URL = "actionUrl";
    public static final String NEW_FORM_ACTION_URL = "newActionUrl";

    public static final String EDIT_VOLUNTEER_URL = "submit-edited-form";
    public static final String SUBMIT_NEW_VOLUNTEER_URL = "submit-new-form";
    public static final String VOLUNTEER_LIST_VIEW = "volunteer-list";
    public static final String REDIRECT_VOLUNTEER_ALL = "redirect:/volunteer/all";

    public static final String FILTER_FORM_ATTR = "filterForm";
    public static final String NEW_VOLUNTEER_ATTR = "newVolunteer";
    public static final String EDIT_VOLUNTEER_ATTR = "editVolunteer";
    public static final String VOLUNTEER_LIST_ATTR = "volunteerList";

    public static final String TYPES_ATTR = "types";

    public static final String HAS_ERRORS_ATTR = "hasErrors";
    public static final String NEW_HAS_ERRORS_ATTR = "newHasErrors";

    private final VolunteerService volunteerService;
    BiConsumer<VolunteerService, VolunteerForm> createNewVolunteerConsumer =
            (service, volunteerForm) -> service.registerNewVolunteer(volunteerForm.getName(), volunteerForm.getLocation(), volunteerForm.getEmail(),
                    volunteerForm.getPhone(), volunteerForm.getTypeOfHelp(), volunteerForm.isAvailable());

    BiConsumer<VolunteerService, VolunteerForm> updateVolunteerConsumer =
            (service, volunteerForm) -> service.editVolunteerData(volunteerForm.getName(), volunteerForm.getLocation(), volunteerForm.getEmail(), volunteerForm.getPhone(), volunteerForm.getTypeOfHelp(), volunteerForm.isAvailable(), volunteerForm.getUuid());

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }


    @PostMapping("/filtering")
    public String filtering(@ModelAttribute("volunteerFilterForm") VolunteerFilterForm form,
                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addAllAttributes(VolunteerHelper.createFilteringRedirectAttributes(form));
        return "redirect:/volunteer/all";
    }

    @PostMapping("/submit-new-form")
    public String createVolunteerFormDetails(@Valid @ModelAttribute(NEW_VOLUNTEER_ATTR) VolunteerForm volunteerForm,
                                             BindingResult br,
                                             @RequestParam Map<String, String> requestValues,
                                             RedirectAttributes redirectAttributes) {
        if (br.hasErrors()) {
            return processFormWithErrors(redirectAttributes, requestValues,
                    Map.of(NEW_HAS_ERRORS_ATTR, true, NEW_VOLUNTEER_ATTR, volunteerForm,
                            BindingResult.MODEL_KEY_PREFIX + NEW_VOLUNTEER_ATTR, br));
        } else {
            return processFormWithoutErrors(volunteerForm, redirectAttributes, requestValues, createNewVolunteerConsumer);
        }
    }

    @PostMapping("/submit-edited-form")
    public String submitEditedVolunteerForm(@Valid @ModelAttribute(EDIT_VOLUNTEER_ATTR) VolunteerForm volunteerForm,
                                            BindingResult br,
                                            @RequestParam Map<String, String> requestValues,
                                            RedirectAttributes redirectAttributes) {
        if (br.hasErrors()) {
            return processFormWithErrors(redirectAttributes, requestValues,
                    Map.of(HAS_ERRORS_ATTR, true, EDIT_VOLUNTEER_ATTR, volunteerForm,
                            BindingResult.MODEL_KEY_PREFIX + EDIT_VOLUNTEER_ATTR, br));
        } else {
            return processFormWithoutErrors(volunteerForm, redirectAttributes, requestValues, updateVolunteerConsumer);
        }
    }

    private String processFormWithoutErrors(VolunteerForm volunteerForm,
                                            RedirectAttributes redirectAttributes,
                                            Map<String, String> requestValues,
                                            BiConsumer<VolunteerService, VolunteerForm> consumer) {

        consumer.accept(volunteerService, volunteerForm);
        redirectAttributes.addAllAttributes(NeedRequestHelper.filterAttributes(requestValues));
        return REDIRECT_VOLUNTEER_ALL;
    }

    private String processFormWithErrors(RedirectAttributes redirectAttributes,
                                         Map<String, String> requestValues,
                                         Map<String, ?> errorRelatedEntries) {

        errorRelatedEntries.entrySet().stream()
                .forEach(stringEntry -> redirectAttributes.addFlashAttribute(stringEntry.getKey(), stringEntry.getValue()));
        redirectAttributes.addAllAttributes(NeedRequestHelper.filterAttributes(requestValues));
        return REDIRECT_VOLUNTEER_ALL;
    }

    @GetMapping("/all")
    public String printAllVolunteers(Model model,
                                     @RequestParam Map<String, String> values) {
        VolunteerForm newForm = (VolunteerForm) model.asMap().get(NEW_VOLUNTEER_ATTR);
        if (newForm == null) {
            model.addAttribute(NEW_VOLUNTEER_ATTR, new VolunteerForm());
        } else {
            model.addAttribute(NEW_VOLUNTEER_ATTR, newForm);
        }
        VolunteerForm editForm = (VolunteerForm) model.asMap().get(EDIT_VOLUNTEER_ATTR);
        if (editForm == null) {
            model.addAttribute(EDIT_VOLUNTEER_ATTR, new VolunteerForm());
        } else {
            model.addAttribute(EDIT_VOLUNTEER_ATTR, editForm);
        }
        VolunteerFilterForm volunteerFilterForm = VolunteerHelper.addFilteringForm(values);
        model.addAttribute(FILTER_FORM_ATTR, volunteerFilterForm);
        addCommonModelAttributes(model, VolunteerHelper.filterAttributes(values),
                volunteerFilterForm);
        return VOLUNTEER_LIST_VIEW;
    }
    private void addCommonModelAttributes(Model model, Map<String, String> originalValues, VolunteerFilterForm volunteerFilterForm) {
        model.addAttribute(EDIT_FORM_ACTION_URL, EDIT_VOLUNTEER_URL);
        model.addAttribute(NEW_FORM_ACTION_URL, SUBMIT_NEW_VOLUNTEER_URL);
        model.addAttribute(TYPES_ATTR, volunteerService.getTypesOfHelp());
        model.addAttribute(VOLUNTEER_LIST_ATTR, volunteerService.getVolunteerFilteredList(volunteerFilterForm));
        originalValues.entrySet().stream()
                .forEach(entry -> model.addAttribute(entry.getKey(), entry.getValue()));
    }

    @GetMapping("/change-status")
    public String changeVolunteerStatus(Model model) {
        return getTestViewWithPageUnderConstructionMessage(model);
    }

    private String getTestViewWithPageUnderConstructionMessage(Model model) {
        model.addAttribute("message", "This page is under construction...");
        return REDIRECT_VOLUNTEER_ALL;
    }
}
