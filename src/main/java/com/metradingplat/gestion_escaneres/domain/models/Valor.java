package com.metradingplat.gestion_escaneres.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoValor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Valor {
    private String etiqueta;
    private EnumTipoValor enumTipoValor;
}
