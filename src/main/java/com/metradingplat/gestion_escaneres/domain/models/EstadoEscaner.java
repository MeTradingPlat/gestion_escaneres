package com.metradingplat.gestion_escaneres.domain.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.metradingplat.gestion_escaneres.domain.enums.EnumEstadoEscaner;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoEscaner {
    private EnumEstadoEscaner enumEstadoEscaner = EnumEstadoEscaner.DETENIDO;
    private LocalDate fechaRegistro;
}
