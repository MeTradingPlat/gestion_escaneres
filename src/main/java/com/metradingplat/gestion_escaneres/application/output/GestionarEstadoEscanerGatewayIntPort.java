package com.metradingplat.gestion_escaneres.application.output;

import com.metradingplat.gestion_escaneres.domain.enums.EnumEstadoEscaner;
import com.metradingplat.gestion_escaneres.domain.models.Escaner;
import com.metradingplat.gestion_escaneres.domain.models.EstadoEscaner;

public interface GestionarEstadoEscanerGatewayIntPort {
    public EnumEstadoEscaner obtenerEstadoDeEscanerActual(Long id);
    public EstadoEscaner cambiarEstadoDeEscaner(Escaner escaner, EnumEstadoEscaner nuevoEstadoEnum);
}