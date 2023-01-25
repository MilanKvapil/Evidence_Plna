/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mkvapil.evidence.service;

import com.mkvapil.evidence.model.Insurance;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author mlock
 */
public interface InsuranceService {
    // create new insurance
    Insurance create(Insurance insurance);

    // view detail of insurance
    Optional<Insurance> findById(Long id);

    // show list of insurances
    List<Insurance> findAll();

    // remove insurance
    void deleteById(Long id);

    // edit insurance
    Insurance update(Long id, Insurance insurance);

    // retrieve all insurances of a specific customer
    List<Insurance> findByCustomerId(Long customerId);
    
    //save insurance
    Insurance save(Insurance insurance);
}
