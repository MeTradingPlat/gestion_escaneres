package com.metradingplat.gestion_escaneres.domain.enums.valores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumEstadoNoticia implements IEnumValores{
    COMPRA("noticia.estado.compra"),
    VENTA("noticia.estado.venta"),
    NINGUNA("noticia.estado.ninguna");

    private final String etiqueta;
}