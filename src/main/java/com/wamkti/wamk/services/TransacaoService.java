package com.wamkti.wamk.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wamkti.wamk.entities.Cliente;
import com.wamkti.wamk.entities.dtos.TransferenciaDTO;
import com.wamkti.wamk.services.exceptions.MesmoClienteException;
import com.wamkti.wamk.services.exceptions.SaldoInsuficienteException;

@Service
public class TransacaoService {
	
	@Autowired
	private ClienteService clienteService;

	public void validarTransferencia(TransferenciaDTO transferenciaDTO) {
		Long transfereId = transferenciaDTO.getTransfereId();
		Long recebeId = transferenciaDTO.getRecebeId();
		Cliente clienteTransfere = clienteService.findById(transfereId);
		BigDecimal valorCliente = clienteTransfere.getValor();
		BigDecimal valorTransferido = transferenciaDTO.getValor();
		int comparacao = valorCliente.compareTo(valorTransferido);
		if(transfereId == recebeId) 
			throw new MesmoClienteException("Você não pode fazer uma transação para você mesmo.");
		else if(comparacao < 0) 
			throw new SaldoInsuficienteException("Saldo Insuficiente!");
	}

	public void Transferir(TransferenciaDTO transferenciaDTO) {
		Long transfereId = transferenciaDTO.getTransfereId();
		Long recebeId = transferenciaDTO.getRecebeId();
		
		var clienteTransfere = clienteService.findById(transfereId);
		var clienteRecebe = clienteService.findById(recebeId);
		
		BigDecimal valorDoCliente = clienteTransfere.getValor();
		BigDecimal valorTransferido = transferenciaDTO.getValor();
		BigDecimal valorAtualDoCliente = valorDoCliente.subtract(valorTransferido);
		
		clienteTransfere.setValor(valorAtualDoCliente);
		clienteService.atualizar(clienteTransfere);
		
		clienteRecebe.setValor(clienteRecebe.getValor().add(valorTransferido));
		clienteService.atualizar(clienteRecebe);
	}
}
