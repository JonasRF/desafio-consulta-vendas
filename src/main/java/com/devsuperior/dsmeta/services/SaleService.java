package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.projections.SellerMinProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);	}

    @Transactional(readOnly = true)
	public Page<SaleMinDTO>searchSales(String minDate, String maxDate, String name, Pageable pageable) {

		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate min = minDate.equals("") ? today.minusYears(1L) : LocalDate.parse(minDate);
		LocalDate max = maxDate.equals("") ? today : LocalDate.parse(maxDate);

		Page<Sale> result = repository.findReport(min, max, name, pageable);
		return result.map(x -> new SaleMinDTO(x));
	}

	@Transactional(readOnly = true)
	public List<SellerMinDTO> summarySales(String minDate, String maxDate) {

		LocalDate result = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate min = minDate.equals("") ? result.minusDays(365) : LocalDate.parse(minDate);
		LocalDate max = maxDate.equals("") ? result : LocalDate.parse(maxDate);

		List<SellerMinProjection> list = repository.findSummary(min, max);
		List<SellerMinDTO> result1 = list.stream().map(x -> new SellerMinDTO(x)).collect(Collectors.toList());
		return result1;
	}
}
