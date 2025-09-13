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
public class ValorIntegerDTORespuesta extends ValorDTORespuesta{
    private Integer valor;

    public ValorIntegerDTORespuesta(EnumTipoValor enumTipoValor, String etiqueta, Integer valor){
        super(etiqueta, enumTipoValor);
        this.valor = valor;
    }
}
