package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumPuntoReferencia implements IEnumValores {
    OPEN("crossLevel.open"),
    CLOSE("crossLevel.close"),
    CLOSE_POST_MARKET("crossLevel.closePostMarket"),
    CLOSE_PRE_MARKET("crossLevel.closePreMarket");

    private final String etiqueta;
}