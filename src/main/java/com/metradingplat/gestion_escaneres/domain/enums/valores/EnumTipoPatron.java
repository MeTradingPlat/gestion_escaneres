package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoPatron implements IEnumValores{
    BEARISH("direction.bearish"),
    BULLISH("direction.bullish");
    
    private final String etiqueta;
}