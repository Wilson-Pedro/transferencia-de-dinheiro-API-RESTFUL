package com.wamkti.wamk.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}

	public void delete(Cliente cliente) {
		clienteRepository.delete(cliente);
		
	}

	public void Transferir(Cliente cliente, BigDecimal valor_transferido) {
		cliente.setValor(cliente.getValor().add(valor_transferido));
		clienteRepository.save(cliente);
		
	}

	public Page<Cliente> findAllPagable(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}
}
