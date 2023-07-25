package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = "SELECT sale.id, sale.date, sale.amount, seller.name " +
            "FROM tb_sales sale " +
            "INNER JOIN tb_seller seller ON sale.seller_id = seller.id " +
            "WHERE sale.date BETWEEN :min AND :max AND UPPER(seller.name) " +
            "LIKE UPPER(CONCAT('%', :name, '%')) " +
            "ORDER BY sale.id")
    Page<Sale> saleSummary(LocalDate min, LocalDate max, String name, Pageable pageable);
}
