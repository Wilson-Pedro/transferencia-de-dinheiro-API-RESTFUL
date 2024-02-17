package com.wamkti.wamk.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.exceptionhandler.exceptions.MesmoClienteException;
import com.wamkti.wamk.exceptionhandler.exceptions.SaldoInsuficienteException;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.services.TransacaoService;

@SpringBootTest
class TransacaoExceptionsTest {
	
	@Autowired
	TransacaoService transacaoService;
	
	@Autowired
	ClienteRepository clienteRepository;

	@Test
	void ExceptionWhenTryingToTransferToYourself() {
		clienteRepository.deleteAll();
		
		clienteRepository.save(new Cliente(null, "Julia", new BigDecimal("1000"), "12260053020"));
		clienteRepository.save(new Cliente(null, "Roberto", new BigDecimal("1000"), "19990050020"));
	
		
		Long transfereId = clienteRepository.findAll().get(0).getId();
		
		assertThrows(MesmoClienteException.class, () -> transacaoService.Transferir(transfereId, transfereId, new BigDecimal("500")));
	}
	
	@Test
	void InsufficientFundsExceptionWhenTryingToTransfer() {
		
		clienteRepository.save(new Cliente(null, "Julia", new BigDecimal("1000"), "21160022020"));
		clienteRepository.save(new Cliente(null, "Roberto", new BigDecimal("1000"), "99910090020"));
	
		Long transfereId = clienteRepository.findAll().get(0).getId();
		Long recebeId = clienteRepository.findAll().get(1).getId();
		
		assertThrows(SaldoInsuficienteException.class, () -> transacaoService.Transferir(transfereId, recebeId, new BigDecimal("2000")));
	}

}
