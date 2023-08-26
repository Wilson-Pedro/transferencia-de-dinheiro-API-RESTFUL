package com.wamkti.wamk.entities.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ComprovanteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String Pagador;
	private String Receptor;
	private BigDecimal valorTransferido;
	private OffsetDateTime dataTransferencia;
	
	public ComprovanteDTO() {
	}
	
	public String getPagador() {
		return Pagador;
	}

	public void setPagador(String pagador) {
		Pagador = pagador;
	}

	public String getReceptor() {
		return Receptor;
	}

	public void setReceptor(String receptor) {
		Receptor = receptor;
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
