package com.metradingplat.gestion_escaneres.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoValor;
import com.metradingplat.gestion_escaneres.domain.enums.valores.EnumCondicional;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class ValorCondicional extends Valor{
    private EnumCondicional enumCondicional;
    private Number valor1;
    private Number valor2;

    public ValorCondicional(String etiqueta, EnumTipoValor enumTipoValor, EnumCondicional enumCondicional, Number valor1, Number valor2) {
        super(etiqueta, enumTipoValor);
        this.valor1 = valor1;
        this.valor2 = valor2;
        this.enumCondicional = enumCondicional;
    }

    
    public ValorCondicional(){
        super();
    }
}
