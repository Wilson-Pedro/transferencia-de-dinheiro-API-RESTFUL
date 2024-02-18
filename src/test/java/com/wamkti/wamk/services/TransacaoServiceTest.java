package com.wamkti.wamk.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.repositories.ClienteRepository;

@SpringBootTest
class TransacaoServiceTest {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TransacaoService transacaoService;

	@Test
	void mustCompleteTheTransferSuccessfully() {
		clienteRepository.deleteAll();
		
		clienteRepository.save(new Cliente(null, "Julia", new BigDecimal("1000"), "12260053020"));
		clienteRepository.save(new Cliente(null, "Roberto", new BigDecimal("1000"), "19990050020"));
	
		Long transfereId = clienteRepository.findAll().get(0).getId();
		Long recebeId = clienteRepository.findAll().get(1).getId();
		
		transacaoService.Transferir(transfereId, recebeId, new BigDecimal("500"));
		
		var julia = clienteRepository.findById(transfereId).get();
		var roberto = clienteRepository.findById(recebeId).get();
		
		assertEquals(new BigDecimal("500.00"), julia.getValor());
		assertEquals(new BigDecimal("1500.00"), roberto.getValor());
	}
}
