package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumTipoValorMedida implements IEnumValores {
    PRECIO("valueType.price"),
    PORCENTAJE("valueType.percentage");

    private final String etiqueta;
}