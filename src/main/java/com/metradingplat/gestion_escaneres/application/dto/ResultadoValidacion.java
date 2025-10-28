package com.metradingplat.gestion_escaneres.application.dto;

import com.metradingplat.gestion_escaneres.domain.enums.EnumParametro;

public record ResultadoValidacion (EnumParametro enumParametro, String mensaje, Object... args) {}
