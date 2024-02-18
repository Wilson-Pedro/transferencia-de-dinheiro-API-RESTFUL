package com.wamkti.wamk.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wamkti.wamk.exceptionhandler.exceptions.MesmoClienteException;
import com.wamkti.wamk.exceptionhandler.exceptions.SaldoInsuficienteException;

@Service
public class TransacaoService {
	
	@Autowired
	private ClienteService clienteService;

	public void Transferir(Long transfereId, Long recebeId, BigDecimal valorTransferido) {
		
		var clienteTransfere = clienteService.findById(transfereId);
		var clienteRecebe = clienteService.findById(recebeId);
		
		BigDecimal valorDoCliente = clienteTransfere.getValor();
		
		validarTransferencia(transfereId, recebeId, valorTransferido, valorDoCliente);
		
		BigDecimal valorAtualDoCliente = valorDoCliente.subtract(valorTransferido);
		
		clienteTransfere.setValor(valorAtualDoCliente);
		clienteService.atualizar(clienteTransfere);
		
		clienteRecebe.setValor(clienteRecebe.getValor().add(valorTransferido));
		clienteService.atualizar(clienteRecebe);
	}
	
	private void validarTransferencia(Long transfereId, Long recebeId, BigDecimal valorTransferido, BigDecimal valorDoCliente) {
		
		int comparacao = valorDoCliente.compareTo(valorTransferido);
		
		if(transfereId == recebeId) 
			throw new MesmoClienteException("Você não pode fazer uma transação para você mesmo.");
		else if(comparacao < 0) 
			throw new SaldoInsuficienteException("Saldo Insuficiente!");
	}
}
