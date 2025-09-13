package com.metradingplat.gestion_escaneres.domain.states.escaner;

import com.metradingplat.gestion_escaneres.domain.states.GestorEstadoEscaner;

public class EstadoEscanerArchivado implements IEstadoEscaner {

    @Override
    public ResultadoGestorEscaner iniciarEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "escaner.estado.archivado.denegar.iniciar");
    }

    @Override
    public ResultadoGestorEscaner detenerEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "escaner.estado.archivado.denegar.detener");
    }

    @Override
    public ResultadoGestorEscaner archivarEscaner(GestorEstadoEscaner gestorEstado) {
        return new ResultadoGestorEscaner(false, "escaner.estado.archivado.denegar.archivar");
    }

    @Override
    public ResultadoGestorEscaner desarchivarEscaner(GestorEstadoEscaner gestorEstado) {
        EstadoEscanerDetenido objEstado = new EstadoEscanerDetenido();
        gestorEstado.setEstadoActual(objEstado);
        return new ResultadoGestorEscaner(true, "escaner.estado.archivado.permitir.desarchivar");
    }
    
    @Override
    public String toString() {
        return "escaner.estado.archivado.permitir";
    }
}
