package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarEscaner.DTOPetition;

import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EscanerDTOPeticion {
    @NotBlank(message = "validation.scanner.name.required")
    @Size(min = 3, max = 100, message = "validation.scanner.name.size")
    private String nombre;

    @NotBlank(message = "validation.scanner.description.required")
    @Size(min = 5, max = 255, message = "validation.scanner.description.size")
    private String descripcion;

    @NotNull(message = "validation.required.field")
    private LocalTime horaInicio;

    @NotNull(message = "validation.required.field")
    private LocalTime horaFin;

    @NotNull(message = "validation.scanner.executionType.required")
    private TipoEjecucionDTOPeticion objTipoEjecucion;

    @NotNull(message = "validation.scanner.market.required")
    private List<MercadoDTOPeticion> mercados;
}
