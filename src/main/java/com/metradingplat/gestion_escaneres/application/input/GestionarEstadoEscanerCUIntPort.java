package com.metradingplat.gestion_escaneres.application.input;

import com.metradingplat.gestion_escaneres.domain.models.EstadoEscaner;

public interface GestionarEstadoEscanerCUIntPort {
    public EstadoEscaner iniciarEscaner(Long id);
    public EstadoEscaner detenerEscaner(Long id);
    public EstadoEscaner archivarEscaner(Long id);
    public EstadoEscaner desarchivarEscaner(Long id);
}
