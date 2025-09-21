package com.metradingplat.gestion_escaneres.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTipoEjecucion {
    UNA_VEZ("execution.once"),
    DIARIA("execution.daily"),;

    private final String etiqueta;
}