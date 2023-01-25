/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mkvapil.evidence.service;

import com.mkvapil.evidence.dao.CustomerDAO;
import com.mkvapil.evidence.dao.InsuranceDAO;
import com.mkvapil.evidence.model.Customer;
import com.mkvapil.evidence.model.Insurance;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author mlock
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;
    private final InsuranceDAO insuranceDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO, InsuranceDAO insuranceDAO) {
        this.customerDAO = customerDAO;
        this.insuranceDAO = insuranceDAO;
    }

    @Override
    public Customer save(Customer customer) {
        return customerDAO.save(customer);
    }

    @Override
    public Insurance saveInsurances(Insurance insurance) {
        return insuranceDAO.save(insurance);
    }

    @Override
    public Optional<Customer> findById(Long id) {        
    Optional<Customer> customer = customerDAO.findById(id);
    if (customer.isPresent()) {        
    } else {
        System.out.println("No customer found with id: " + id);
    }
    return customer;        
    }

    @Override
    public Optional<Insurance> findInsuranceById(Long id) {
        return insuranceDAO.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = customerDAO.findAll();
        return customers;
    }

    @Override
    public void deleteById(Long id) {
        customerDAO.deleteById(id);
    }

    @Override
    public void deleteInsuranceById(Long id) {
        insuranceDAO.deleteById(id);
    }

    @Override
    public Insurance saveAndFlush(Insurance insurance) {
        return insuranceDAO.saveAndFlush(insurance);
    }

    @Override
    public Customer saveAndFlush(Customer customer) {
        return customerDAO.saveAndFlush(customer);
    }

}