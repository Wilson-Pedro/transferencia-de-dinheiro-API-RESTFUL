package com.wamkti.wamk.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.dtos.ClienteRecodDTO;
import com.wamkti.wamk.entities.dtos.TransferenciaDTO;
import com.wamkti.wamk.repositories.ClienteRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteControllerTest {
	
	private static String ENDPOINT  = "/clientes";
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ClienteRepository clienteRepository;

	@Test
	@Order(1)
	void mustSaveTheClientSuccessfully() throws Exception {
		clienteRepository.deleteAll();
		
		ClienteRecodDTO cliente = new ClienteRecodDTO("Carla", new BigDecimal("1000"), "13360053020");
		
		String jsonRequest = objectMapper.writeValueAsString(cliente);
		
		mockMvc.perform(post(ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", equalTo("Carla")))
				.andExpect(jsonPath("$.valor", equalTo(1000)))
				.andExpect(jsonPath("$.cpf", equalTo("13360053020")));
	}
	
	@Test
	@Order(2)
	void mustFetchAListOfClientsSuccessfully() throws Exception {
		
		mockMvc.perform(get(ENDPOINT))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	@Order(3)
	void mustPageAListOfClientsSuccessfully() throws Exception {
		
		mockMvc.perform(get(ENDPOINT + "/page"))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	@Order(4)
	void mustFindTheClientFromTheIdSuccessfully() throws Exception {
		
		Long id = clienteRepository.findAll().get(0).getId();
		
		mockMvc.perform(get(ENDPOINT + "/{id}", id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nome", equalTo("Carla")))
			.andExpect(jsonPath("$.valor", equalTo(1000.0)))
			.andExpect(jsonPath("$.cpf", equalTo("13360053020")));
	}
	
	@Test
	@Order(5)
	void mustUpdateTheClientSuccessfully() throws Exception {
		
		ClienteRecodDTO cliente = new ClienteRecodDTO("Laura", new BigDecimal("1000"), "13360053020");
		
		String jsonRequest = objectMapper.writeValueAsString(cliente);
		
		Long id = clienteRepository.findAll().get(0).getId();
		
		mockMvc.perform(put(ENDPOINT + "/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", equalTo("Laura")));
	}
	
	@Test
	@Order(6)
	void mustCompleteTheTransferSuccessfully() throws Exception {
		
		clienteRepository.save(new Cliente(null, "Julia", new BigDecimal("1000"), "00660022020"));
		
		Long transfereId = clienteRepository.findAll().get(0).getId();
		Long recebeId = clienteRepository.findAll().get(1).getId();
		
		TransferenciaDTO transferencia = new TransferenciaDTO(transfereId, recebeId, new BigDecimal("500"));
		
		String jsonRequest = objectMapper.writeValueAsString(transferencia);
		
		mockMvc.perform(post(ENDPOINT + "/transferencia")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.pagador", equalTo("Laura")))
				.andExpect(jsonPath("$.receptor", equalTo("Julia")))
				.andExpect(jsonPath("$.valorTransferido", equalTo(500)));
	}
	
	@Test
	@Order(7)
	void mustDeleteTheClientSuccessfully() throws Exception {
		
		Long id = clienteRepository.findAll().get(0).getId();
		
		assertEquals(2, clienteRepository.count());
		
		mockMvc.perform(delete(ENDPOINT + "/{id}", id))
			.andExpect(status().isOk());
		
		assertEquals(1, clienteRepository.count());
	}
}
