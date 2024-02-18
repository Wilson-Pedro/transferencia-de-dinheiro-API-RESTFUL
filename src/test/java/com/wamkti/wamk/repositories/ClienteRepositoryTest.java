package com.wamkti.wamk.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wamkti.wamk.entities.Cliente;

@DataJpaTest
class ClienteRepositoryTest {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Test
	void existsByCpfMustReturnFalse() {
		clienteRepository.deleteAll();
		
		assertFalse(clienteRepository.existsByCpf("12260053020"));
	}
	
	@Test
	void existsByCpfMustReturnTrue() {
		clienteRepository.save(new Cliente(null, "Julia", new BigDecimal("1000"), "12260053020"));
		
		assertTrue(clienteRepository.existsByCpf("12260053020"));
	}

}
