package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumLineaCruce implements IEnumValores{
    VWAP("lineaCruce.vwap"),
    EMA("lineaCruce.ema");
    
    private final String etiqueta;
}