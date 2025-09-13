package com.metradingplat.gestion_escaneres.application.input;

import java.util.List;

import com.metradingplat.gestion_escaneres.domain.models.Mercado;

public interface GestionarMercadoCUIntPort {
    public List<Mercado> listarEntidadesMercado();
}