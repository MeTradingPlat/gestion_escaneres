package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumOpcionExtremo implements IEnumValores {
    HIGH("extremeOption.high"),
    LOW("extremeOption.low");

    private final String etiqueta;
}