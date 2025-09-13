package com.metradingplat.gestion_escaneres.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.gestion_escaneres.domain.enums.EnumCategoriaFiltro;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaFiltro {
    private String etiqueta;
    private EnumCategoriaFiltro enumCategoriaFiltro;
}
