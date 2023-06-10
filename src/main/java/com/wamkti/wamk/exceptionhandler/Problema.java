package com.wamkti.wamk.exceptionhandler;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

//@JsonInclude(Include.NON_NULL)
public class Problema implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private OffsetDateTime dataHora;
	private String titulo;
	private List<Campo> campos = new ArrayList<>();
	
	public Problema() {
	}

	public Problema(Integer status, OffsetDateTime dataHora, String titulo, List<Campo> campos) {
		this.status = status;
		this.dataHora = dataHora;
		this.titulo = titulo;
		this.campos = campos;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public OffsetDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(OffsetDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}
}
