package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT obj FROM Sale obj INNER JOIN obj.seller " +
            "WHERE obj.date BETWEEN :min AND :max AND UPPER(obj.seller.name) " +
            "LIKE UPPER(CONCAT('%', :name, '%')) ORDER BY obj.id")
    Page<Sale> findReport(LocalDate min, LocalDate max, String name, Pageable pageable);

    @Query("SELECT obj FROM Sale obj JOIN FETCH obj.seller WHERE obj IN :sales")
    List<Sale> filterDataSalesAndNameSeller(List<Sale> sales);
}
