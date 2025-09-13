package com.metradingplat.gestion_escaneres.domain.usecases;

import lombok.RequiredArgsConstructor;
import com.metradingplat.gestion_escaneres.application.input.GestionarMercadoCUIntPort;
import com.metradingplat.gestion_escaneres.domain.enums.EnumMercado;
import com.metradingplat.gestion_escaneres.domain.models.Mercado;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class GestionarMercadoCUAdapter implements GestionarMercadoCUIntPort {

    @Override
    public List<Mercado> listarEntidadesMercado() {
        List<Mercado> mercados = Arrays.stream(EnumMercado.values())
                .map(enumMercado -> new Mercado(
                        enumMercado.getEtiqueta(),
                        enumMercado
                ))
                .toList();
        return mercados;
    }
}