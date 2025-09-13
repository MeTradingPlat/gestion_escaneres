package com.metradingplat.gestion_escaneres.domain.states.escaner;

import com.metradingplat.gestion_escaneres.domain.states.GestorEstadoEscaner;

public class EstadoEscanerIniciado implements IEstadoEscaner {

    @Override
    public ResultadoGestorEscaner iniciarEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "escaner.estado.iniciado.denegar.iniciar");
    }

    @Override
    public ResultadoGestorEscaner detenerEscaner(GestorEstadoEscaner gestorEstado) {
        EstadoEscanerDetenido objEstado = new EstadoEscanerDetenido();
        gestorEstado.setEstadoActual(objEstado);
        return new ResultadoGestorEscaner(true, "escaner.estado.iniciado.permitir.detener");
    }

    @Override
    public ResultadoGestorEscaner archivarEscaner(GestorEstadoEscaner gestorEstado) {
        EstadoEscanerArchivado objEstado = new EstadoEscanerArchivado();
        gestorEstado.setEstadoActual(objEstado);
        return new ResultadoGestorEscaner(true, "escaner.estado.iniciado.permitir.archivar");
    }

    @Override
    public ResultadoGestorEscaner desarchivarEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "escaner.estado.iniciado.denegar.desarchivar");
    }

    @Override
    public String toString() {
        return "escaner.estado.iniciado.permitir";
    }
    
}
