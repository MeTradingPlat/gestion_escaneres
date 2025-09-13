package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarFiltro.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoValor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValorFloatDTORespuesta extends ValorDTORespuesta{
    private Float valor;

    public ValorFloatDTORespuesta(EnumTipoValor enumTipoValor, String etiqueta, Float valor){
        super(etiqueta, enumTipoValor);
        this.valor = valor;
    }
}
