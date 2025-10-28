package com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarMercado.controller;

import lombok.RequiredArgsConstructor;
import com.metradingplat.gestion_escaneres.application.input.GestionarMercadoCUIntPort;
import com.metradingplat.gestion_escaneres.application.output.FuenteMensajesIntPort;
import com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarMercado.DTOAnswer.MercadoDTORespuesta;
import com.metradingplat.gestion_escaneres.infrastructure.input.controllerGestionarMercado.mapper.MercadoMapperInfraestructuraDominio;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/escaner/mercado")
@RequiredArgsConstructor
@Validated
public class MercadoRestController {

    private final GestionarMercadoCUIntPort objGestionarMercadoCUInt;
    private final MercadoMapperInfraestructuraDominio objMapper;
    private final FuenteMensajesIntPort objFuenteMensajes;

    @GetMapping
    public ResponseEntity<List<MercadoDTORespuesta>> listarMercados() {
        List<MercadoDTORespuesta> listaDto = this.objMapper.mappearListaDeMercadoARespuesta(this.objGestionarMercadoCUInt.listarEntidadesMercado());
        listaDto = this.objFuenteMensajes.internacionalizarMercados(listaDto);
        return ResponseEntity.ok(listaDto);
    }

}
