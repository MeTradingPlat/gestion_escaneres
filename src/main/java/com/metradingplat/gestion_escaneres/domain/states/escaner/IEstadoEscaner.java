package com.metradingplat.gestion_escaneres.domain.states.escaner;

import com.metradingplat.gestion_escaneres.domain.states.GestorEstadoEscaner;

public interface IEstadoEscaner {
    ResultadoGestorEscaner iniciarEscaner(GestorEstadoEscaner gestorEstado);
    ResultadoGestorEscaner detenerEscaner(GestorEstadoEscaner gestorEstado);
    ResultadoGestorEscaner archivarEscaner(GestorEstadoEscaner gestorEstado);
    ResultadoGestorEscaner desarchivarEscaner(GestorEstadoEscaner gestorEstado);
}