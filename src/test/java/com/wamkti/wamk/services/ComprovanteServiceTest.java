package com.wamkti.wamk.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.dtos.ComprovanteDTO;
import com.wamkti.wamk.repositories.ClienteRepository;

@SpringBootTest
public class ComprovanteServiceTest {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ComprovanteService comprovanteService;
	
	@Test
	void mustProcessVoucherSuccessfully() {
		clienteRepository.deleteAll();
		
		clienteRepository.save(new Cliente(null, "Julia", new BigDecimal("1000"), "12260053020"));
		clienteRepository.save(new Cliente(null, "Roberto", new BigDecimal("1000"), "19990050020"));
	
		
		Long transfereId = clienteRepository.findAll().get(0).getId();
		Long recebeId = clienteRepository.findAll().get(1).getId();
		
		ComprovanteDTO comprovanteDTO = comprovanteService.processarComprovante(transfereId, recebeId, new BigDecimal("500"));
		
		assertEquals("Julia", comprovanteDTO.getPagador());
		assertEquals("Roberto", comprovanteDTO.getReceptor());
		assertEquals(new BigDecimal("500"), comprovanteDTO.getValorTransferido());
	}
}
