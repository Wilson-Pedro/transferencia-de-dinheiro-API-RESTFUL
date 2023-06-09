package com.wamkti.wamk.entities.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteRecodDTO(@NotBlank String nome, @NotNull BigDecimal valor) {

}
