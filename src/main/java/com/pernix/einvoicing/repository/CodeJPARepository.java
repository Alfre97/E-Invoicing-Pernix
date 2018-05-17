package com.pernix.einvoicing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.pernix.einvoicing.model.Code;

public interface CodeJPARepository<P> extends BaseJPARepository<Code, Long> {

	@Query("SELECT c FROM Code c WHERE c.service = null")
	List<Code> findCodesWithNoService();
}
