package com.metradingplat.gestion_escaneres.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.gestion_escaneres.domain.enums.EnumTipoEjecucion;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoEjecucion {
    private String etiqueta;
    private EnumTipoEjecucion enumTipoEjecucion;
}
