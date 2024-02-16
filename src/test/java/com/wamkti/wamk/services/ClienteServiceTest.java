package com.wamkti.wamk.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.repositories.ClienteRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteServiceTest {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ClienteRepository clienteRepository;

	@Test
	@Order(1)
	void mustSaveTheClientSuccessfully() {
		Cliente wilson = new Cliente();
		wilson.setId(null);
		wilson.setNome("Wilson");
		wilson.setCpf("23361153018");
		wilson.setValor(new BigDecimal("1000"));
		
		Cliente pedro = new Cliente();
		pedro.setId(null);
		pedro.setNome("Pedro");
		pedro.setCpf("77036102080");
		pedro.setValor(new BigDecimal("1000"));
		
		assertEquals(0, clienteRepository.count());
		
		clienteService.save(wilson);
		clienteService.save(pedro);
		
		assertEquals(2, clienteRepository.count());
	}
	
	@Test
	@Order(2)
	void mustFetchAListOfClientsSuccessfully() {
		
		List<Cliente> list = clienteService.findAll();
		
		assertEquals(list.size(), 2);
		assertEquals(list.size(), clienteRepository.count());
	}
	
	@Test
	@Order(3)
	void mustPaginateAListOfCustomersSuccessfully() {
		
		Page<Cliente> pages = clienteService.findAllPagable(PageRequest.of(0, 10));
		
		assertNotNull(pages);
		assertEquals(pages.getContent().size(), clienteRepository.count());
	}
	
	@Test
	@Order(4)
	void mustFindTheClientFromTheIdSuccessfully() {
		
		Long id = clienteService.findAll().get(0).getId();
		
		Cliente cliente = clienteService.findById(id);
		
		assertNotNull(cliente);
		assertEquals("Wilson", cliente.getNome());
		assertEquals("23361153018", cliente.getCpf());
		assertEquals(new BigDecimal("1000.00"), cliente.getValor());
	}
	
	@Test
	@Order(5)
	void mustUpdateTheClientSuccessfully() {
		
		Long id = clienteService.findAll().get(0).getId();
		
		Cliente cliente = clienteService.findById(id);
		cliente.setNome("Neto");
		
		cliente = clienteService.atualizar(cliente);
	}
	
	@Test
	@Order(6)
	void mustDeleteTheClientSuccessfully() {
		
		Long id = clienteService.findAll().get(0).getId();
		
		Cliente cliente = clienteService.findById(id);
		
		clienteService.delete(cliente);
		
		assertEquals(1, clienteRepository.count());
	}
}
