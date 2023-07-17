package com.wamkti.wamk.transferencia;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public class Transferencia implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long clienteRecebeId;
	
	@NotNull
	private BigDecimal valor;

	public BigDecimal getValor() {
		return valor;
	}

	public Long getClienteRecebeId() {
		return clienteRecebeId;
	}
}
