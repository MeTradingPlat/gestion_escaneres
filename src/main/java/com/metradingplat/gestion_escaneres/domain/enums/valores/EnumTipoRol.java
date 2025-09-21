package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoRol implements IEnumValores {
    RESISTENCIA("roleType.resistance"),
    SOPORTE("roleType.support");

    private final String etiqueta;
}