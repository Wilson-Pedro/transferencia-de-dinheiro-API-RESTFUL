package com.wamkti.wamk.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.dtos.ClienteRecodDTO;
import com.wamkti.wamk.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<Cliente> salvarCliente(@Valid @RequestBody ClienteRecodDTO clienteRecodDTO) {
		var cliente = new Cliente();
		BeanUtils.copyProperties(clienteRecodDTO, cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
	}
}
