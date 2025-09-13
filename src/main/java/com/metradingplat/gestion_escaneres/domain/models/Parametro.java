package com.metradingplat.gestion_escaneres.domain.models;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parametro {
    private EnumParametro enumParametro;
    private String etiqueta;
    private Valor objValorSeleccionado;
    private List<Valor> opciones = new ArrayList<Valor>();
}
