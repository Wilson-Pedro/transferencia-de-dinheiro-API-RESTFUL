package com.wamkti.wamk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.exceptionhandler.exceptions.CpfExistenteException;
import com.wamkti.wamk.exceptionhandler.exceptions.ObjectNotFoundException;
import com.wamkti.wamk.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente save(Cliente cliente) {
		validarCpf(cliente.getCpf());
		return clienteRepository.save(cliente);
	}

	public Cliente atualizar(Cliente cliente) {
		cliente.setId(cliente.getId());
		return clienteRepository.save(cliente);
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente findById(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Id não encontrado!"));
		
	}

	public void delete(Cliente cliente) {
		clienteRepository.delete(cliente);
		
	}

	public Page<Cliente> findAllPagable(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
	private void validarCpf(String cpf) {
		if(clienteRepository.existsByCpf(cpf))
			throw new CpfExistenteException();
	}
}
