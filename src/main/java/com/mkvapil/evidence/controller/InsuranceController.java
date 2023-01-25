/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mkvapil.evidence.controller;

import com.mkvapil.evidence.dao.InsuranceDAO;
import com.mkvapil.evidence.model.Customer;
import com.mkvapil.evidence.model.Insurance;
import com.mkvapil.evidence.service.CustomerService;
import com.mkvapil.evidence.service.InsuranceService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/insurance/customers")
public class InsuranceController {
    
    @Autowired
    private InsuranceDAO insuranceDao;

    @Autowired
    private InsuranceService insuranceService;
    
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public ModelAndView listInsurances() {
        ModelAndView mav = new ModelAndView("insurance_list");
        mav.addObject("insurances", insuranceService.findAll());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView detailInsurance(@PathVariable("id") Long id) {        
        ModelAndView mav = new ModelAndView("insurance_detail");
        mav.addObject("insurance", insuranceService.findById(id));
        return mav;
    }

    @GetMapping("/{id}/insurances/create")
    public String createInsuranceForm(@PathVariable("id") Long customerId, Model model) {        
        Optional<Customer> customer = customerService.findById(customerId);
        if (customer.isPresent()) {
            Insurance insurance = new Insurance();
            insurance.setCustomer(customer.get());
            model.addAttribute("customer", customer.get());
            model.addAttribute("insurance", insurance);
            return "insurance_create";
        } else {
        model.addAttribute("errorMessage", "Pojištěnec s id " + customerId + " nenalezen");
            return "error";
        }
    }
    
    @PostMapping("/insurances/delete/{id}")
    public String deleteInsurance(@PathVariable("id") Long id, Model model) {        
        try {
            insuranceService.deleteById(id);
            model.addAttribute("successMessage", "Insurance deleted successfully");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error deleting insurance with id: " + id);
        }
        model.addAttribute("insurance", insuranceService.findById(id));
        return "redirect:/customer_detail/{id}";
    }

    @PostMapping("/{id}/insurances/create")
    public String createInsurance(@PathVariable("id") Long customerId, @Valid Insurance insurance, BindingResult bindingResult, Model model) {
       // System.out.println("volam");
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Objevily se validační chyby. Prosím zkuste znovu.");
            return "insurance_create";
        }
        Optional<Customer> customer = customerService.findById(customerId);
        if (customer.isPresent()) {
           Customer existingCustomer = customer.get();
           insurance.setCustomer(existingCustomer);
          // insuranceService.save(insurance); making mess in database, need to fix
           model.addAttribute("successMessage", "Pojištění bylo vytvořeno!");
           return "redirect:/customer_detail/{id}";
        } else {
            model.addAttribute("errorMessage", "Pojištěnec s id " + customerId + " nenalezen");
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editInsurance(@PathVariable("id") Long id) {
        System.out.println("callledd");
        ModelAndView mav = new ModelAndView("insurance_edit");
        mav.addObject("insurance", insuranceService.findById(id));
        return mav;
    }    
    
    @PostMapping("/edit/{id}")
    public String saveInsurance(@PathVariable("id") Long id, @Valid Insurance insurance, BindingResult bindingResult, Model model) {
        System.out.println("callledd");
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Objevily se validační chyby. Prosím zkuste znovu.");
            return "insurance_edit";
        }
        // insuranceService.save(insurance);  might be another problem tied to saving insurance
        model.addAttribute("successMessage", "Pojištění bylo úspěšně uloženo!");
        return "redirect:/insurances";
    }
}
