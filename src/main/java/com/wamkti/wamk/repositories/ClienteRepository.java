package com.wamkti.wamk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wamkti.wamk.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	boolean existsByCpf(String cpf);

}
