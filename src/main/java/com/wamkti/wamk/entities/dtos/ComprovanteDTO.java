package com.wamkti.wamk.entities.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ComprovanteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String nomeOrigem;
	private String nomeDestino;
	private BigDecimal valorTransferido;
	private OffsetDateTime dataTransferencia;
	
	public ComprovanteDTO() {
	}

	public String getNomeOrigem() {
		return nomeOrigem;
	}

	public void setNomeOrigem(String nomeOrigem) {
		this.nomeOrigem = nomeOrigem;
	}

	public String getNomeDestino() {
		return nomeDestino;
	}

	public void setNomeDestino(String nomeDestino) {
		this.nomeDestino = nomeDestino;
	}

	public BigDecimal getValorTransferido() {
		return valorTransferido;
	}

	public void setValorTransferido(BigDecimal valorTransferido) {
		this.valorTransferido = valorTransferido;
	}

	public OffsetDateTime getDataTransferencia() {
		return dataTransferencia;
	}

	public void setDataTransferencia(OffsetDateTime dataTransferencia) {
		this.dataTransferencia = dataTransferencia;
	}
}
