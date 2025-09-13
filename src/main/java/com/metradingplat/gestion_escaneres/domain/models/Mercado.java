package com.metradingplat.gestion_escaneres.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.gestion_escaneres.domain.enums.EnumMercado;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mercado {
    private String etiqueta;
    private EnumMercado enumMercado;
}
