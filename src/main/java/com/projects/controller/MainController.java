package com.projects.controller;

import com.projects.model.Business;
import com.projects.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lazarenko on 12.11.2015.
 */
@Controller
public class MainController {
    private BusinessService businessService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String actualDate = sdf.format(new java.util.Date());

    @Autowired(required = true)
    @Qualifier(value = "businessService")
    public void setBusinessService(BusinessService businessService) {
        this.businessService = businessService;
    }

    @RequestMapping(value = "/business", method = RequestMethod.GET)
    public String listBusnisses(Model model, String ms) {
        model.addAttribute("business", new Business());
        model.addAttribute("message", ms);
        model.addAttribute("modelName", "All");
        model.addAttribute("dateNow", actualDate);
        model.addAttribute("listBusiness", this.businessService.getAll());
        return "list";
    }

    @RequestMapping(value = "/business/done", method = RequestMethod.GET)
    public String listBusnissesDone(Model model, String ms) {
        model.addAttribute("business", new Business());
        model.addAttribute("message", ms);
        model.addAttribute("modelName", "Done");
        model.addAttribute("dateNow", actualDate);
        model.addAttribute("listBusiness", this.businessService.getDone());
        return "list";
    }

    @RequestMapping(value = "/business/notdone", method = RequestMethod.GET)
    public String listBusnissesNotDone(Model model, String ms) {
        model.addAttribute("business", new Business());
        model.addAttribute("message", ms);
        model.addAttribute("modelName", "NotDone");
        model.addAttribute("dateNow", actualDate);
        model.addAttribute("listBusiness", this.businessService.getNotDone());
        return "list";
    }

    //for adding business
    @RequestMapping(value = "/business/add", method = RequestMethod.POST)
    public String addPerson(@ModelAttribute("business") Business business, Model model) {

        if ((business.getName() == null) || (business.getName() == "") || (business.getEndDate() == null) || (business.getEndDate() == "")) {
            return listBusnisses(model, "Name or Date can't be empty");
        }

        //all for validation input date
        String date = business.getEndDate();

        if (!validDate(date)) {
            return listBusnisses(model, "Please, enter date like YYYY-MM-DD");
        }

        int ind = date.indexOf('-');

        String temp = date.substring(0, ind);

        int year = Integer.parseInt(temp);

        if (year <= 0)
            return listBusnisses(model, "Please, enter date right: Year");

        int ind2 = date.indexOf('-', ind + 1);

        int month = Integer.parseInt(date.substring(ind + 1, ind2));

        if (month <= 0 || month > 12)
            return listBusnisses(model, "Please, enter date right: Month");

        int day = Integer.parseInt(date.substring(date.lastIndexOf('-') + 1, date.length()));

        boolean yearV = ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0)));
        if (day <= 0 || day > 31) {
            return listBusnisses(model, "Please, enter date right: Day");
        } else if ((month == 4) || (month == 6) || (month == 9) || (month == 11) && (day == 31)) {
            return listBusnisses(model, "Please, enter date right: In this month 30 days");
        } else if (month == 2 && day >= 30 && yearV) {
            return listBusnisses(model, "Please, enter date right: In this month 29 days");
        } else if (month == 2 && day >= 30 && !yearV) {
            return listBusnisses(model, "Please, enter date right: In this month 28 days");
        }
        // end of validation date

        if (business.getId() == null) {
            //new business, add it
            businessService.add(business);
            return listBusnisses(model, "Business added");
        } else {
            //existing business, call update
            this.businessService.edit(business);
            return listBusnisses(model, "Business edited");
        }
    }

    //valide date for YYYY-MM-DD
    private boolean validDate(String date) {
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}");
        Matcher matcher = p.matcher(date);
        return matcher.matches();
    }

    // delete business
    @RequestMapping("/remove/{id}")
    public String removeBusiness(@PathVariable("id") int id, Model model) {

        this.businessService.delete(id);
        return listBusnisses(model, "Business deleted");
    }
    @RequestMapping("/removeDone/{id}")
    public String removeBusinessDone(@PathVariable("id") int id, Model model) {

        this.businessService.delete(id);
        return listBusnissesDone(model, "Business deleted");
    }

    @RequestMapping("/removeNotDone/{id}")
    public String removeBusinessNotDone(@PathVariable("id") int id, Model model) {

        this.businessService.delete(id);
        return listBusnissesNotDone(model, "Business deleted");
    }

    //edit business
    @RequestMapping("/edit/{id}")
    public String editBusiness(@PathVariable("id") int id, Model model) {
        model.addAttribute("business", this.businessService.getById(id));
        model.addAttribute("modelName", "All");
        model.addAttribute("listBusiness", this.businessService.getAll());
        return "list";
    }
    @RequestMapping("/editDone/{id}")
    public String editDoneBusiness(@PathVariable("id") int id, Model model) {
        model.addAttribute("business", this.businessService.getById(id));
        model.addAttribute("modelName", "Done");
        model.addAttribute("listBusiness", this.businessService.getDone());
        return "list";
    }
    @RequestMapping("/editNotDone/{id}")
    public String editNotDoneBusiness(@PathVariable("id") int id, Model model) {
        model.addAttribute("business", this.businessService.getById(id));
        model.addAttribute("modelName", "NotDone");
        model.addAttribute("listBusiness", this.businessService.getNotDone());
        return "list";
    }
    //update business when click done/notDone
    @RequestMapping("/updateAll/{id}")
    public String updateAllBusiness(@PathVariable("id") int id, Model model) {

        model = modelForAll(id, model);
        model.addAttribute("modelName", "All");
        model.addAttribute("listBusiness", this.businessService.getAll());
        return "list";
    }

    @RequestMapping("/updateDone/{id}")
    public String updateDoneBusiness(@PathVariable("id") int id, Model model) {

        model = modelForAll(id, model);
        model.addAttribute("modelName", "Done");
        model.addAttribute("listBusiness", this.businessService.getDone());
        return "list";
    }

    @RequestMapping("/updateNotDone/{id}")
    public String updateNotDoneBusiness(@PathVariable("id") int id, Model model) {

        model = modelForAll(id, model);
        model.addAttribute("modelName", "NotDone");
        model.addAttribute("listBusiness", this.businessService.getNotDone());
        return "list";
    }

    public Model modelForAll(@PathVariable("id") int id, Model model) {
        Business business = this.businessService.getById(id);
        boolean wasDo = business.getDo();
        if (wasDo)
            business.setDo(false);
        else
            business.setDo(true);
        this.businessService.edit(business);

        model.addAttribute("business", new Business());
        model.addAttribute("dateNow", actualDate);

        return model;
    }
}
