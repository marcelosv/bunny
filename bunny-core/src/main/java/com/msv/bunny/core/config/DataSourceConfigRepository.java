package com.msv.bunny.core.config;

import java.util.List;

/**
 * 
 * Interface que deve ser usada no repositório para a tabela com os dados do banco.
 * 
 * @author Marcelo de Souza Vieira
 *
 */
public interface DataSourceConfigRepository<T> {

    DataSourceConfig findByName(String nome);
	List<T> findAll();
	
}