/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mkvapil.evidence.service;

import com.mkvapil.evidence.model.Customer;
import com.mkvapil.evidence.model.Insurance;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author mlock
 */
public interface CustomerService {
    
    // create new customer
    Customer save(Customer customer);

    // create new insurance for customer
    Insurance saveInsurances(Insurance insurance);

    // view detail of customer from database
    Optional<Customer> findById(Long id);

    // view detail of insurance
    Optional<Insurance> findInsuranceById(Long id);

    // show list of customers
    List<Customer> findAll();

    // remove customer including all of his insurances
    void deleteById(Long id);

    // remove insurance from customer
    void deleteInsuranceById(Long id);

    // edit insurance
    Insurance saveAndFlush(Insurance insurance);

    // edit customer
    Customer saveAndFlush(Customer customer);
}

