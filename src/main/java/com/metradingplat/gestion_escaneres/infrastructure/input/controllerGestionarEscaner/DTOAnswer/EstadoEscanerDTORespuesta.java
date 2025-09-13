package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEscaner.DTOAnswer;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.metradingplat.gestion_escaneres.domain.enums.EnumEstadoEscaner;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoEscanerDTORespuesta {
    private EnumEstadoEscaner enumEstadoEscaner;
    private LocalDate fechaRegistro;
}
