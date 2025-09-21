package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarFiltro.DTOPetition;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumCondicional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValorCondicionalDTOPeticion extends ValorDTOPeticion {
    @NotNull(message = "validation.enum.invalid")
    private EnumCondicional enumCondicional;

    @NotNull(message = "validation.parameter.value.required")
    private Number valor1;

    private Number valor2;
}