package com.wamkti.wamk.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.exceptionhandler.exceptions.CpfExistenteException;
import com.wamkti.wamk.exceptionhandler.exceptions.ObjectNotFoundException;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.services.ClienteService;

@SpringBootTest
class ClienteExceptionsTest {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Test
	void ObjectNotFoundExceptionWhenTryingToFindTheCliente() {
		
		assertThrows(ObjectNotFoundException.class, () -> clienteService.findById(70L));
	}

	@Test
	void ExistingCepExceptionWhenTryingToSaveTheCliente() {
		
		clienteRepository.deleteAll();
		
		clienteService.save(new Cliente(null, "Ana", new BigDecimal("1000"), "12260053020"));
		
		Cliente cliente = new Cliente(null, "Julia", new BigDecimal("2000"), "12260053020");
		
		assertThrows(CpfExistenteException.class, () -> clienteService.save(cliente));
	}

}
