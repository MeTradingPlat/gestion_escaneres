package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarFiltro.DTOPetition;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroDtoPeticion {
    @NotNull(message = "filtro.enumFiltro.empty")
    private EnumFiltro enumFiltro;

    @NotNull(message = "filtro.parametros.empty")
    @Valid
    private List<ParametroDTOPeticion> parametros;
}
