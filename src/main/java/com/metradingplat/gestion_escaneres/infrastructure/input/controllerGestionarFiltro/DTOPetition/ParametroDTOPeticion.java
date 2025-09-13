package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarFiltro.DTOPetition;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParametroDTOPeticion {
    @NotNull(message = "parametro.enumParametro.empty")
    private EnumParametro enumParametro;

    @NotNull(message = "parametro.objValorSeleccionado.empty")
    @Valid
    private ValorDTOPeticion objValorSeleccionado;
}