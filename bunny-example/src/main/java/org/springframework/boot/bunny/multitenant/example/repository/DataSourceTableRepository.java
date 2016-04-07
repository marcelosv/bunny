package org.springframework.boot.bunny.multitenant.example.repository;

import org.springframework.boot.bunny.multitenant.config.DataSourceConfigRepository;
import org.springframework.boot.bunny.multitenant.example.entity.DataSourceTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface DataSourceTableRepository extends DataSourceConfigRepository<DataSourceTable>, JpaRepository<DataSourceTable, Long> {

}
