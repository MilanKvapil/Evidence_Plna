/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mkvapil.evidence.dao;

import com.mkvapil.evidence.model.Insurance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mlock
 */
@Repository
public interface InsuranceDAO extends JpaRepository<Insurance, Long> {
    // JpaRepository<Entity, Type of ID>
    List<Insurance> findByCustomer_Id(Long id);
}