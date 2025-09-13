package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEntidadMercado.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumMercado;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MercadoDTORespuesta {
    private String etiqueta;
    private EnumMercado enumMercado;
}
