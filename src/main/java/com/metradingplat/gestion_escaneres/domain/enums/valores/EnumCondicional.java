package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCondicional implements IEnumValores {
    MAYOR_QUE("condicion.mayor_que"),
    MENOR_QUE("condicion.menor_que"),
    ENTRE("condicion.entre"),
    FUERA("condicion.fuera"),
    IGUAL_A("condicion.igual_a");

    private final String etiqueta;
}