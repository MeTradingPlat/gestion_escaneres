package com.metradingplat.gestion_escaneres.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTipoEjecucion {
    UNA_VEZ("escaner.tipoEjecucion.una_vez", 1),
    DIARIA("escaner.tipoEjecucion.diaria", 2),;

    private final String etiqueta;
    private final Integer id;
}