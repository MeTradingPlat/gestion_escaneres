package com.metradingplat.gestion_escaneres.application.output;

import java.util.List;

import com.metradingplat.gestion_escaneres.domain.models.Escaner;

public interface GestionarEscanerGatewayIntPort {
    public Boolean existeEscanerPorNombre(String nombre);
    public Boolean existeEscanerPorNombre(Long idEscaner, String nombre);
    public Boolean existeEscanerPorId(Long idEscaner);
    public Escaner crearEscaner(Escaner escaner);
    public Escaner actualizarEscaner(Escaner escaner);
    public Boolean eliminarEscaner(Long idEscaner);
    public Escaner obtenerEscanerPorId(Long idEscaner);
    public List<Escaner> obtenerEscaneresDesarchivados();
    public List<Escaner> obtenerEscaneresArchivados();
}
