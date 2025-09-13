package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarFiltro.DTOPetition;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTOPeticion {
    @NotNull(message = "categoria.enumCategoriaFiltro.empty")
    private EnumCategoriaFiltro enumCategoriaFiltro;
}