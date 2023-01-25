/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mkvapil.evidence.service;

import com.mkvapil.evidence.dao.InsuranceDAO;
import com.mkvapil.evidence.model.Insurance;
import com.mkvapil.evidence.exception.ResourceNotFoundException;
import com.mkvapil.evidence.model.Customer;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author mlock
 */
@Service
@Transactional
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceDAO insuranceDAO;

    public InsuranceServiceImpl(InsuranceDAO insuranceDAO) {
        this.insuranceDAO = insuranceDAO;
    }

    @Override
    public Insurance create(Insurance insurance) {
        return insuranceDAO.save(insurance);
    }

    @Override
    public Optional<Insurance> findById(Long id) {
        return insuranceDAO.findById(id);
    }

    @Override
    public List<Insurance> findAll() {
        return insuranceDAO.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Optional<Insurance> insurance = insuranceDAO.findById(id);
        if(insurance.isPresent()) {
            Customer customer = insurance.get().getCustomer();
            customer.getInsurances().remove(insurance.get());
            insuranceDAO.deleteById(id);
        }
    }   

    @Override
    public Insurance update(Long id, Insurance insurance) {
        Insurance oldInsurance = insuranceDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Insurance", "id", id));
        oldInsurance.setType(insurance.getType());
        oldInsurance.setValue(insurance.getValue());
        oldInsurance.setSubject(insurance.getSubject());
        oldInsurance.setValidFrom(insurance.getValidFrom());
        oldInsurance.setValidTo(insurance.getValidTo());
        return insuranceDAO.saveAndFlush(oldInsurance);
    }

    @Override
    public List<Insurance> findByCustomerId(Long customerId) {
        return insuranceDAO.findByCustomer_Id(customerId);
    }
    
    @Override
    public Insurance save(Insurance insurance) {
        return insuranceDAO.save(insurance);
    }
}
