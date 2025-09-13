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
    @NotBlank(message = "escaner.nombre.empty")
    @Size(min = 3, max = 100, message = "escaner.nombre.size")
    private String nombre;

    @NotBlank(message = "escaner.descripcion.empty")
    @Size(min = 5, max = 255, message = "escaner.descripcion.size")
    private String descripcion;

    @NotNull(message = "escaner.horaInicio.empty")
    private LocalTime horaInicio;

    @NotNull(message = "escaner.horaFin.empty")
    private LocalTime horaFin;

    @NotNull(message = "escaner.tiposEjecucion.empty")
    private TipoEjecucionDTOPeticion objTipoEjecucion;

    @NotNull(message = "escaner.mercados.empty")
    private List<MercadoDTOPeticion> mercados;
}
