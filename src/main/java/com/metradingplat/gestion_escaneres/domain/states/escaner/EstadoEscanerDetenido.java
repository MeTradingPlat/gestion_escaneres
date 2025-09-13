package com.metradingplat.gestion_escaneres.domain.states.escaner;

import com.metradingplat.gestion_escaneres.domain.states.GestorEstadoEscaner;

public class EstadoEscanerDetenido implements IEstadoEscaner {
    @Override
    public ResultadoGestorEscaner iniciarEscaner(GestorEstadoEscaner gestorEstado) {
        EstadoEscanerIniciado objEstado = new EstadoEscanerIniciado();
        gestorEstado.setEstadoActual(objEstado);
        return new ResultadoGestorEscaner(true, "escaner.estado.detenido.permitir.iniciar");
    }

    @Override
    public ResultadoGestorEscaner detenerEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "escaner.estado.detenido.denegar.detener");
    }

    @Override
    public ResultadoGestorEscaner archivarEscaner(GestorEstadoEscaner gestorEstado) {
        EstadoEscanerArchivado objEstado = new EstadoEscanerArchivado();
        gestorEstado.setEstadoActual(objEstado);
        return new ResultadoGestorEscaner(true, "escaner.estado.detenido.permitir.archivar");
    }

    @Override
    public ResultadoGestorEscaner desarchivarEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "escaner.estado.detenido.denegar.desarchivar");
    }
    
    @Override
    public String toString() {
        return "escaner.estado.detenido.permitir";
    }
}
