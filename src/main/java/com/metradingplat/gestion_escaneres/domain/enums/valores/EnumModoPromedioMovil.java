package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumModoPromedioMovil implements IEnumValores {
    EMA("crossLine.ema"),
    SMA("movingAverageMode.sma"),
    VMA("movingAverageMode.vma"),
    RMA("movingAverageMode.rma");

    private final String etiqueta;
}