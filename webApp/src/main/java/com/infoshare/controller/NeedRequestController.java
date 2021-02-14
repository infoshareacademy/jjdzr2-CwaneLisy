package com.infoshare.controller;

import com.infoshare.domain.NeedRequest;
import com.infoshare.formobjects.NeedRequestForm;
import com.infoshare.formobjects.NeedRequestListObject;
import com.infoshare.formobjects.NeedRequestSearchForm;
import com.infoshare.formobjects.VolunteerSearchForm;
import com.infoshare.service.NeedRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("need-request")
public class NeedRequestController {
    private final NeedRequestService needRequestService;

    @Autowired
    public NeedRequestController(NeedRequestService needRequestService) {
        this.needRequestService = needRequestService;
    }

    @PostMapping("/submit-new-form")
    public String submitNeedRequestForm(@Valid @ModelAttribute("request") NeedRequestForm needRequestForm,
                                        BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("actionUrl", "submit-new-form");
            model.addAttribute("types", needRequestService.getTypesOfHelp());
            return "need-request-form";
        } else {
            needRequestService.createNeedRequest(needRequestForm.getName(), needRequestForm.getLocation(), needRequestForm.getPhone(), needRequestForm.getTypeOfHelp());
            return "redirect:/need-request/all";
        }
    }
    @PostMapping("/edit-need-request")
    public String editNeedRequestForm(@Valid @ModelAttribute("request") NeedRequestForm needRequestForm,
                                   BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("actionUrl", "edit-need-request");
            model.addAttribute("types", needRequestService.getTypesOfHelp());
            model.addAttribute("hasErrors", true);
            model.addAttribute("needRequestsList", getAllNeedRequests());
            return "need-request-list";
        } else {
            needRequestService.updateNeedRequest(needRequestForm.getName(),
                    needRequestForm.getLocation(),
                    needRequestForm.getPhone(), needRequestForm.getTypeOfHelp(), needRequestForm.getUuid());
            return "redirect:/need-request/all";
        }
    }

    @GetMapping("/create")
    public String showNeedRequestForm(Model model) {
        model.addAttribute("actionUrl", "submit-new-form");
        model.addAttribute("types", needRequestService.getTypesOfHelp());
        model.addAttribute("request", new NeedRequestForm());
        return "need-request-form";
    }

    @GetMapping("/all")
    public String printAllNeedRequest(Model model) {
        model.addAttribute("actionUrl", "edit-need-request");
        model.addAttribute("types", needRequestService.getTypesOfHelp());
        model.addAttribute("needRequestsList", getAllNeedRequests());
        model.addAttribute("request", new NeedRequestForm());
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
        model.addAttribute("needRequestSearchForm",new VolunteerSearchForm());
        return "need-request-search-form";
    }

    @PostMapping("/search/result")
    public String resultOfSearchForNeedRequest(@Valid @ModelAttribute("needRequestSearchForm") NeedRequestSearchForm needRequestSearchForm,
                                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "need-request-search-form";
        }
        model.addAttribute("needRequests",needRequestService.getNeedRequestFilteredList(
                needRequestSearchForm.getCity(),
                needRequestSearchForm.getTypeOfHelp()));
        return "need-request-search-results";
    }

    @GetMapping("/edit")
    public String editNeedRequest(Model model) {
        return getTestViewWithPageUnderConstructionMessage(model);
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
