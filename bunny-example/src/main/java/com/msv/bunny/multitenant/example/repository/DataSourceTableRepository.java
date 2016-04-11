package com.msv.bunny.multitenant.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.msv.bunny.core.config.DataSourceConfigRepository;
import com.msv.bunny.multitenant.example.entity.DataSourceTable;

@RepositoryRestController
public interface DataSourceTableRepository extends DataSourceConfigRepository<DataSourceTable>, JpaRepository<DataSourceTable, Long> {

}
