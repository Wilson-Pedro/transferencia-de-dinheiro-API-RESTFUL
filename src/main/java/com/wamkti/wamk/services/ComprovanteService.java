package com.wamkti.wamk.services;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wamkti.wamk.entities.dtos.ComprovanteDTO;

@Service
public class ComprovanteService {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private TransacaoService transacaoService;

	public ComprovanteDTO processarComprovante(Long transfereId, Long recebeId, BigDecimal valorTransferido) {
		transacaoService.Transferir(transfereId, recebeId, valorTransferido);
		
		var clienteTransfere = clienteService.findById(transfereId);
		var clienteRecebe = clienteService.findById(recebeId);
		
		ComprovanteDTO comprovanteDTO = new ComprovanteDTO();
		
		comprovanteDTO.setPagador(clienteTransfere.getNome());
		comprovanteDTO.setReceptor(clienteRecebe.getNome());
		comprovanteDTO.setValorTransferido(valorTransferido);
		comprovanteDTO.setDataTransferencia(OffsetDateTime.now());
		
		return comprovanteDTO;
	}
}
