package com.metradingplat.gestion_escaneres.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumMercado {
    NYSE("mercado.nyse",1),
    NASDAQ("mercado.nasdaq",2),
    AMEX("mercado.amex",3),
    ETF("mercado.etf",4),
    OTC("mercado.otc",5);

    private final String etiqueta;
    private final Integer id;
}

