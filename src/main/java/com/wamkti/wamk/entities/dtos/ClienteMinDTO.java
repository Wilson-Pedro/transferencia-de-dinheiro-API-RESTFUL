package com.wamkti.wamk.entities.dtos;

import java.math.BigDecimal;
import java.util.Objects;

public class ClienteMinDTO {

	private String nome;
	private BigDecimal valor;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteMinDTO other = (ClienteMinDTO) obj;
		return Objects.equals(nome, other.nome) && Objects.equals(valor, other.valor);
	}
}
