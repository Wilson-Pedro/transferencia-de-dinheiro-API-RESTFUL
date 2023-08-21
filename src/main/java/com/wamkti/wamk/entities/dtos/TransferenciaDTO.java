package com.wamkti.wamk.entities.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public class TransferenciaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long transfereId;
	
	@NotNull
	private Long recebeId;
	
	@NotNull
	private BigDecimal valor;

	public BigDecimal getValor() {
		return valor;
	}

	public Long getTransfereId() {
		return transfereId;
	}

	public Long getRecebeId() {
		return recebeId;
	}
}
