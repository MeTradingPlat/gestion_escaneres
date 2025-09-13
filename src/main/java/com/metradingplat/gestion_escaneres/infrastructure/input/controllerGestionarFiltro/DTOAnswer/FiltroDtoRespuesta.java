package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarFiltro.DTOAnswer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroDtoRespuesta {
    private EnumFiltro enumFiltro;
    private String etiquetaNombre;
    private String etiquetaDescripcion;
    private CategoriaDTORespuesta objCategoria;
    private List<ParametroDTORespuesta> parametros;
}
