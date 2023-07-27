package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projections.SellerMinProjection;

import java.time.LocalDate;

public class SellerMinDTO {
	private String sellerName;
	private Double total;

	public  SellerMinDTO(){}

	public SellerMinDTO(String sellerName, Double total) {
		this.sellerName = sellerName;
		this.total = total;
	}

	public SellerMinDTO(SellerMinProjection projection) {
		sellerName = projection.getSellerName();
		total = projection.getTotal();
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}
}
