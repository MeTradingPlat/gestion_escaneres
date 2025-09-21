package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumLineaReferencia implements IEnumValores{
    VWAP("crossLine.vwap"),
    MA("referenceLine.ma"),
    EMA("crossLine.ema");
    
    private final String etiqueta;
}