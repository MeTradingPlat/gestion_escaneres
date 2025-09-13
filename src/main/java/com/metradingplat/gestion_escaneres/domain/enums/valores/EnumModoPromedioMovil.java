package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumModoPromedioMovil implements IEnumValores {
    EMA("modoPromedioMovil.ema"),
    SMA("modoPromedioMovil.sma"),
    VMA("modoPromedioMovil.vma"),
    RMA("modoPromedioMovil.rma");

    private final String etiqueta;
}