package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumTipoVolumen implements IEnumValores{
    CIERRE("tipoVolumen.cierre"),
    APERTURA("tipoVolumen.apertura");

    private final String etiqueta;
}