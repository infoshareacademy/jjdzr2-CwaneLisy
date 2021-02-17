package com.infoshare.controller;

import com.infoshare.domain.NeedRequest;
import com.infoshare.domain.TypeOfHelp;
import com.infoshare.formobjects.NeedRequestForm;
import com.infoshare.formobjects.NeedRequestListObject;
import com.infoshare.formobjects.NeedRequestSearchForm;
import com.infoshare.formobjects.VolunteerSearchForm;
import com.infoshare.service.NeedRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("need-request")
public class NeedRequestController {
    public static final String ACTION_URL = "actionUrl";
    public static final String TYPES = "types";
    public static final String REDIRECT_NEED_REQUEST_ALL = "redirect:/need-request/all";
    public static final String ORIGINAL_CITY = "originalCity";
    public static final String ORIGINAL_TYPE_OF_HELP = "originalTypeOfHelp";
    private final NeedRequestService needRequestService;

    @Autowired
    public NeedRequestController(NeedRequestService needRequestService) {
        this.needRequestService = needRequestService;
    }

    @PostMapping("/submit-new-form")
    public String submitNeedRequestForm(@Valid @ModelAttribute("request") NeedRequestForm needRequestForm,
                                        BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute(ACTION_URL, "submit-new-form");
            model.addAttribute(TYPES, needRequestService.getTypesOfHelp());
            return "need-request-form";
        } else {
            needRequestService.createNeedRequest(needRequestForm.getName(), needRequestForm.getLocation(), needRequestForm.getPhone(), needRequestForm.getTypeOfHelp());
            return REDIRECT_NEED_REQUEST_ALL;
        }
    }

    @PostMapping("/edit-need-request")
    public String editNeedRequestForm(@Valid @ModelAttribute("request") NeedRequestForm needRequestForm,
                                      BindingResult br, Model model,
                                      @RequestBody MultiValueMap<String, String> values,
                                      RedirectAttributes redirectAttributes
    ) {
        Map<String, String> originalValues = originalSearchValues(values);
        if (br.hasErrors()) {
            model.addAttribute(ACTION_URL, "edit-need-request");
            model.addAttribute(TYPES, needRequestService.getTypesOfHelp());
            model.addAttribute("hasErrors", true);
            model.addAttribute("needRequestsList", getRequestFilteredList(originalValues.get(ORIGINAL_CITY),
                    originalValues.get(ORIGINAL_TYPE_OF_HELP)));
            model.addAttribute(ORIGINAL_TYPE_OF_HELP, originalValues.get(ORIGINAL_TYPE_OF_HELP));
            model.addAttribute(ORIGINAL_CITY, originalValues.get(ORIGINAL_CITY));
            return "need-request-list";
        } else {
            needRequestService.updateNeedRequest(needRequestForm.getName(),
                    needRequestForm.getLocation(),
                    needRequestForm.getPhone(), needRequestForm.getTypeOfHelp(), needRequestForm.getUuid());

            redirectAttributes.addAttribute("city", originalValues.get(ORIGINAL_CITY));
            redirectAttributes.addAttribute("typeOfHelp", originalValues.get(ORIGINAL_TYPE_OF_HELP));

            return REDIRECT_NEED_REQUEST_ALL;
        }
    }

    private Map<String, String> originalSearchValues(MultiValueMap<String, String> values) {

        if (!values.toSingleValueMap().get(ORIGINAL_CITY).isEmpty() && !values.toSingleValueMap().get(
                ORIGINAL_TYPE_OF_HELP).isEmpty()) {
            return Map.of(ORIGINAL_CITY, values.getFirst(ORIGINAL_CITY), ORIGINAL_TYPE_OF_HELP, values.getFirst(
                    ORIGINAL_TYPE_OF_HELP));

        } else {
            return Collections.emptyMap();
        }
    }

    @GetMapping("/create")
    public String showNeedRequestForm(Model model) {
        model.addAttribute(ACTION_URL, "submit-new-form");
        model.addAttribute(TYPES, needRequestService.getTypesOfHelp());
        model.addAttribute("request", new NeedRequestForm());
        return "need-request-form";
    }

    @GetMapping("/all")
    public String printAllNeedRequest(Model model,
                                      @RequestParam(required = false) String city,
                                      @RequestParam(required = false) String typeOfHelp
    ) {
        model.addAttribute(ACTION_URL, "edit-need-request");
        model.addAttribute(TYPES, needRequestService.getTypesOfHelp());
        model.addAttribute("statusesOfHelp", needRequestService.getHelpStatuses());
        model.addAttribute("request", new NeedRequestForm());
        model.addAttribute(ORIGINAL_CITY, city);
        model.addAttribute(ORIGINAL_TYPE_OF_HELP, typeOfHelp);
        model.addAttribute("needRequestsList", getRequestFilteredList(city, typeOfHelp));
        return "need-request-list";
    }

    public List<NeedRequestListObject> getAllNeedRequests() {
        return needRequestService.getAllNeedRequests().stream()
                .map(this::convertToNeedRequestForm)
                .collect(Collectors.toList());
    }

    private NeedRequestListObject convertToNeedRequestForm(NeedRequest needRequest) {
        return NeedRequestListObject.NeedRequestListObjectBuilder.aNeedRequestListObject()
                .withUuid(needRequest.getUuid())
                .withName(needRequest.getPersonInNeed().getName())
                .withPhone(needRequest.getPersonInNeed().getPhone())
                .withLocation(needRequest.getPersonInNeed().getLocation())
                .withTypeOfHelp(needRequest.getTypeOfHelp())
                .withStatusChange(needRequest.getStatusChange())
                .withHelpStatus(needRequest.getHelpStatus())
                .build();

    }

    @GetMapping("/search")
    public String searchForNeedRequest(Model model) {
        model.addAttribute("needRequestSearchForm", new VolunteerSearchForm());
        return "need-request-search-form";
    }

    @PostMapping("/search/result")
    public String resultOfSearchForNeedRequest(@Valid @ModelAttribute("needRequestSearchForm") NeedRequestSearchForm needRequestSearchForm,
                                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "need-request-search-form";
        }
        redirectAttributes.addAttribute("city", needRequestSearchForm.getCity());
        redirectAttributes.addAttribute("typeOfHelp", needRequestSearchForm.getTypeOfHelp());
        return REDIRECT_NEED_REQUEST_ALL;
    }

    private List<NeedRequestListObject> getRequestFilteredList(String city, String typeOfHelp) {
        if (city != null && typeOfHelp != null && !city.isEmpty() && !typeOfHelp.isEmpty()) {

            return needRequestService.getNeedRequestFilteredList(city, TypeOfHelp.valueOf(typeOfHelp)).stream()
                    .map(this::convertToNeedRequestForm)
                    .collect(Collectors.toList());
        } else return getAllNeedRequests();
    }

    @GetMapping("/associate-to-volunteer")
    public String associateNeedRequestToVolunteer(Model model) {
        return getTestViewWithPageUnderConstructionMessage(model);
    }

    @GetMapping("/add-comment")
    public String addCommentToNeedRequest(Model model) {
        return getTestViewWithPageUnderConstructionMessage(model);
    }

    @GetMapping("/browse-history")
    public String browseHistoryOfNeedRequest(Model model) {
        return getTestViewWithPageUnderConstructionMessage(model);
    }

    private String getTestViewWithPageUnderConstructionMessage(Model model) {
        model.addAttribute("message", "This page is under construction...");
        return "test-view";
    }
}