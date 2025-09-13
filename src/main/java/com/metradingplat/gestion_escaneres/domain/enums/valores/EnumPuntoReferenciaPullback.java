package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumPuntoReferenciaPullback implements IEnumValores{
    ALTO("puntoReferenciaPullback.alto"),
    BAJO("puntoReferenciaPullback.bajo");

    private final String etiqueta;
}