package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumPuntoReferenciaPullback implements IEnumValores{
    ALTO("extremeOption.high"),
    BAJO("extremeOption.low");

    private final String etiqueta;
    
    @Override
    public String getName() {
        return this.name();
    }
}