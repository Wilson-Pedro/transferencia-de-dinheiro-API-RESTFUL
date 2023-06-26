package com.wamkti.wamk.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.repositories.ClienteRepository;
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

	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}

	public void delete(Cliente cliente) {
		clienteRepository.delete(cliente);
		
	}

//	public void Transferir(Cliente cliente, BigDecimal valor_transferido) {
//		cliente.setValor(cliente.getValor().add(valor_transferido));
//		clienteRepository.save(cliente);
//		
//	}

	public Page<Cliente> findAllPagable(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}
	
	public int validarTransferencia(Long clienteTransfereId, @Valid Transferencia transferencia) {
		Optional<Cliente> clienteTransfereO = findById(clienteTransfereId);
		BigDecimal valor_cliente = clienteTransfereO.get().getValor();
		BigDecimal valor_transferido = transferencia.getValor();
		int comaracao = valor_cliente.compareTo(valor_transferido);
		return comaracao;
	}

	public Cliente Transferir(Long clienteTransfereId, @Valid Transferencia transferencia, Long clienteRecebeId) {
		Optional<Cliente> clienteTransfereO = findById(clienteTransfereId);
		Optional<Cliente> clienteRecebeO = findById(clienteRecebeId);
		BigDecimal valor_cliente = clienteTransfereO.get().getValor();
		BigDecimal valor_transferido = transferencia.getValor();
		BigDecimal valor_atual = valor_cliente.subtract(valor_transferido);
		clienteTransfereO.get().setValor(valor_atual);
		atualizar(clienteTransfereO.get());
		clienteRecebeO.get().setValor(clienteRecebeO.get().getValor().add(valor_transferido));
		atualizar(clienteRecebeO.get());
		
		return clienteTransfereO.get();
	}
}
