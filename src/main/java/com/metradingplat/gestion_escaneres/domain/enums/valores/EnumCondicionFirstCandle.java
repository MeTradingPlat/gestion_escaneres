package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCondicionFirstCandle implements IEnumValores {
    ALCISTA("direction.bullish"),
    BAJISTA("direction.bearish");

    private final String etiqueta;
    
    @Override
    public String getName() {
        return this.name();
    }
}
