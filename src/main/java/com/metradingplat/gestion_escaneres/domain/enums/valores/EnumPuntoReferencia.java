package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumPuntoReferencia implements IEnumValores {
    OPEN("puntoReferencia.open"),
    CLOSE("puntoReferencia.close"),
    CLOSE_POST_MARKET("puntoReferencia.closePostMarket"),
    CLOSE_PRE_MARKET("puntoReferencia.closePreMarket");

    private final String etiqueta;
}