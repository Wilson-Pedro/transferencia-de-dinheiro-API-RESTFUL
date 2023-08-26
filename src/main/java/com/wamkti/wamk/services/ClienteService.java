package com.wamkti.wamk.services;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.dtos.ComprovanteDTO;
import com.wamkti.wamk.repositories.ClienteRepository;
import com.wamkti.wamk.services.exceptions.ObjectNotFoundException;

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
						"Id não encontrado!"));
		
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

	public ComprovanteDTO processarComprovante(Long transfereId, Long recebeId, BigDecimal valorTransferido) {
		var clienteTransfereOpt = findById(transfereId);
		var clienteRecebeO = findById(recebeId);
		ComprovanteDTO comprovanteDTO = new ComprovanteDTO();
		comprovanteDTO.setNomeOrigem(clienteTransfereOpt.getNome());
		comprovanteDTO.setNomeDestino(clienteRecebeO.getNome());
		comprovanteDTO.setValorTransferido(valorTransferido);
		comprovanteDTO.setDataTransferencia(OffsetDateTime.now());
		return comprovanteDTO;
	}
}
