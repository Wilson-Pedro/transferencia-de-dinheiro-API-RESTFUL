package com.wamkti.wamk.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;


import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.services.exceptions.ObjectNotFoundException;
import com.wamkti.wamk.transferencia.Transferencia;

import jakarta.validation.Valid;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente save(Cliente cliente) {
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
				.orElseThrow(() -> new ObjectNotFoundException(
						"Obejto não encontrado! Id"
								+ id + ", Tipo: " + Cliente.class.getName()));
		
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
	
//	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
//		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
//		return clienteRepository.findAll(pageRequest);
//	}
	
	public int validarTransferencia(Long clienteTransfereId, @Valid Transferencia transferencia) {
		Cliente clienteTransfereO = findById(clienteTransfereId);
		BigDecimal valor_cliente = clienteTransfereO.getValor();
		BigDecimal valor_transferido = transferencia.getValor();
		int comaracao = valor_cliente.compareTo(valor_transferido);
		return comaracao;
	}

	public Cliente Transferir(Long clienteTransfereId, @Valid Transferencia transferencia, Long clienteRecebeId) {
		Cliente clienteTransfereO = findById(clienteTransfereId);
		Cliente clienteRecebeO = findById(clienteRecebeId);
		BigDecimal valor_cliente = clienteTransfereO.getValor();
		BigDecimal valor_transferido = transferencia.getValor();
		BigDecimal valor_atual = valor_cliente.subtract(valor_transferido);
		clienteTransfereO.setValor(valor_atual);
		atualizar(clienteTransfereO);
		clienteRecebeO.setValor(clienteRecebeO.getValor().add(valor_transferido));
		atualizar(clienteRecebeO);
		
		return clienteTransfereO;
	}
}
