package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoPatron implements IEnumValores{
    BEARISH("tipoPatron.bearish"),
    BULLISH("tipoPatron.bullish");
    
    private final String etiqueta;
}