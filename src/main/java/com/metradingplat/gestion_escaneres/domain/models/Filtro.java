package com.metradingplat.gestion_escaneres.domain.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.gestion_escaneres.domain.enums.EnumFiltro;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filtro {
    private EnumFiltro enumFiltro;
    private String etiquetaNombre;
    private String etiquetaDescripcion;
    private CategoriaFiltro objCategoria;
    private List<Parametro> parametros;
}