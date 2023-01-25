/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mkvapil.evidence.controller;

import com.mkvapil.evidence.model.Customer;
import com.mkvapil.evidence.model.Insurance;
import com.mkvapil.evidence.service.CustomerService;
import com.mkvapil.evidence.service.InsuranceService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author mlock
 */
@Controller
public class CustomerController {    
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private InsuranceService insuranceService;

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/customers")
    public String listCustomers(Model model) {        
        List<Customer> customers = customerService.findAll();
        if (customers == null || customers.isEmpty()) {
            model.addAttribute("errorMessage", "No customers found");
            return "error";
        }
        model.addAttribute("customers", customers);
        return "customers_list";
    }

    @GetMapping("/customer_detail/{id}")
    public String getCustomerById(@PathVariable(value = "id") Long customerId, Model model) {        
        Optional<Customer> customer = customerService.findById(customerId);
        model.addAttribute("customer", customer.orElse(null));
        model.addAttribute("insurances", insuranceService.findByCustomerId(customerId));
        return "customers_detail";
    }
    
    @GetMapping("/customers/{id}/insurances")
    public String getInsurancesByCustomerId(@PathVariable(value = "id") Long customerId, Model model) {
        List<Insurance> insurances = insuranceService.findByCustomerId(customerId);        
        model.addAttribute("insurances", insurances);
        return "insurances";
    }


    @GetMapping("/insurances")
    public String listInsurances(Model model) {
        List<Insurance> insurances = insuranceService.findAll();
        model.addAttribute("insurances", insurances);
        return "insurance_list";
    }

    @GetMapping("/insurances/{id}")
    public String getInsuranceById(@PathVariable(value = "id") Long insuranceId, Model model) {
        Optional<Insurance> insurance = insuranceService.findById(insuranceId);
        if(insurance.isPresent()){
            model.addAttribute("insurance", insurance);
        }else{
            model.addAttribute("errorMessage", "Nenalezena žádná pojištění");
            return "error";
        }
        return "insurance_detail";
    }
    
    @GetMapping("/customers/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers_create";
    }
    
    @PostMapping("/customers/create")
    public String createCustomer(@Valid Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Objevily se validační chyby. Prosím zkuste znovu.");
            return "customers_create";
        }
        customerService.save(customer);
        model.addAttribute("successMessage", "Pojištěnec byl vytvořen!");
        return "redirect:/customers";
    }
    
    @GetMapping("/customers/edit/{id}")
    public String editCustomerForm(@PathVariable("id") Long customerId, Model model) {
        Optional<Customer> customer = customerService.findById(customerId);
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.orElse(null));
            return "customer_edit";
        } else {
            model.addAttribute("errorMessage", "Pojištěnec s  id " + customerId + " nenalezen");
            return "error";
        }
    }
    
    @PostMapping("/customers/edit/{id}")
    public String editCustomer(@PathVariable("id") Long id, @Valid Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Objevily se validační chyby. Prosím zkuste znovu.");
            return "customer_edit";
        }
        Optional<Customer> customerToUpdate = customerService.findById(id);
        if (customerToUpdate.isPresent()) {
            customerToUpdate.get().setName(customer.getName());
            customerToUpdate.get().setSurname(customer.getSurname());
            customerToUpdate.get().setPhoneNumber(customer.getPhoneNumber());
            customerToUpdate.get().setEmail(customer.getEmail());
            customerToUpdate.get().setCity(customer.getCity());
            customerToUpdate.get().setStreet(customer.getStreet());
            customerToUpdate.get().setHouseNumber(customer.getHouseNumber());
            customerToUpdate.get().setPostalCode(customer.getPostalCode());
            customerService.save(customerToUpdate.get());
            model.addAttribute("successMessage", "Pojištěnec byl upraven!");
            return "redirect:/customers";
        } else {
            model.addAttribute("errorMessage", "Pojištěnec s  id " + id + " nenalezen");
            return "error";
        }
    }
    
    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long customerId, Model model) {
        Optional<Customer> customer = customerService.findById(customerId);
        if (customer.isPresent()) {
            customerService.deleteById(customerId);
            model.addAttribute("successMessage", "Pojištěnec byl smazán!");
            return "redirect:/customers";
        } else {
            model.addAttribute("errorMessage", "Pojištěnec s  id " + customerId + " nenalezen");
            return "error";
        }
    }

}
