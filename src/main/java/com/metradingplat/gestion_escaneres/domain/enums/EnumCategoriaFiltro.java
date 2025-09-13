package com.metradingplat.gestion_escaneres.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCategoriaFiltro {
    TODOS("filtro.categoria.todos"),
    VOLUMEN("enum.categoria.volumen"),
    PRECIO_Y_MOVIMIENTO("enum.categoria.precio_y_movimiento"),
    VOLATILIDAD("enum.categoria.volatilidad"),
    MOMENTUM_E_INDICADORES_TECNICOS("enum.categoria.momentum_e_indicadores_tecnicos"),
    CARACTERISTICAS_FUNDAMENTALES("enum.categoria.caracteristicas_fundamentales"),
    TIEMPO_Y_PATRONES_DE_PRECIO("enum.categoria.tiempo_y_patrones_de_precio");

    private final String etiqueta;
}