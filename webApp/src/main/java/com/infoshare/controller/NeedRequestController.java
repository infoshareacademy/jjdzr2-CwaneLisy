package com.infoshare.controller;

import com.infoshare.formobjects.NeedRequestForm;
import com.infoshare.formobjects.NeedRequestSearchForm;
import com.infoshare.formobjects.VolunteerSearchForm;
import com.infoshare.service.NeedRequestService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("need-request")
public class NeedRequestController {
    private final NeedRequestService needRequestService;

    @Autowired
    public NeedRequestController(NeedRequestService needRequestService) {
        this.needRequestService = needRequestService;
    }

    @PostMapping("/submit-new-form")
    public String submitNeedRequestForm(@Valid @ModelAttribute("needRequestForm") NeedRequestForm needRequestForm, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("types", needRequestService.getTypesOfHelp());
            return "need-request-form";
        } else {
            needRequestService.createNeedRequest(needRequestForm.getName(), needRequestForm.getLocation(), needRequestForm.getPhone(), needRequestForm.getTypeOfHelp());
            return "redirect:/need-request/all";
        }
    }

    @GetMapping("/create")
    public String showNeedRequestForm(Model model) {
        model.addAttribute(new NeedRequestForm());
        model.addAttribute("types", needRequestService.getTypesOfHelp());
        return "need-request-form";
    }

    @GetMapping("/all")
    public String printAllNeedRequest(Model model) {
        model.addAttribute("nr", needRequestService.getAllNeedRequests());
        return "need-request-list";
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
