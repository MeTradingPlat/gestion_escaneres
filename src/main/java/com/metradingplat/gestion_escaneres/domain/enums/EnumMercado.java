package com.metradingplat.gestion_escaneres.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumMercado {
    NYSE("market.nyse"),
    NASDAQ("market.nasdaq"),
    AMEX("market.amex"),
    ETF("market.etf"),
    OTC("market.otc");

    private final String etiqueta;
}

