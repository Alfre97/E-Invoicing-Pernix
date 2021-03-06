package com.pernix.einvoicing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
 
@NoRepositoryBean
interface BaseJPARepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

}
