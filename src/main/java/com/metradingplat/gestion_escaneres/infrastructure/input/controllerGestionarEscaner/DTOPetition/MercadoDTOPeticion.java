package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEscaner.DTOPetition;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumMercado;

@Getter
@Setter
public class MercadoDTOPeticion {
    @NotNull(message = "escaner.mercado.enum.empty")
    private EnumMercado enumMercado;
}

