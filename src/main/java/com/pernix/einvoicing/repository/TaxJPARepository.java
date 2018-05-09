package com.pernix.einvoicing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.pernix.einvoicing.model.Tax;

public interface TaxJPARepository<P> extends BaseJPARepository<Tax, Long> {

	 @Query("SELECT t FROM Tax t WHERE t.service = null")
		List<Tax> findNoService();
}
