package com.metradingplat.gestion_escaneres.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumParametro {
    // Parámetros Generales de Condición
    CONDICION("filtro.general.parametro.condicion"),

    // Parámetros de Volumen
    TIPO_VOLUMEN("filtro.volume.parametro.tipoVolumen"),
    TIMEFRAME_VOLUME("filtro.volume.parametro.timeframe"),
    TIMEFRAME_AVERAGE_VOLUME("filtro.averageVolume.parametro.timeframe"),
    TIMEFRAME_RELATIVE_VOLUME_PERCENT("filtro.relativeVolumePercent.parametro.timeframe"),
    NUMERO_VELAS_VOLUME_SPIKE("filtro.volumeSpike.parametro.numeroVelas"),
    TIMEFRAME_VOLUME_SPIKE("filtro.volumeSpike.parametro.timeframe"),
    PROPORCION_VOLUMEN_VOLUME_SPIKE("filtro.volumeSpike.parametro.proporcionVolumen"),

    // Parámetros de Precio y Movimiento
    PUNTO_REFERENCIA_CHANGE("filtro.change.parametro.puntoReferencia"),
    TIPO_MEDIDA_CHANGE("filtro.change.parametro.tipoMedida"),
    TIMEFRAME_PERCENTAGE_CHANGE_PERCENT("filtro.percentageChangePercent.parametro.timeframe"),
    FORMATO_GAP_FROM_CLOSE("filtro.gapFromClose.parametro.formato"),
    TIMEFRAME_POSITION_IN_RANGE("filtro.positionInRange.parametro.timeframe"),
    TIMEFRAME_PERCENTAGE_RANGE_PERCENT("filtro.percentageRangePercent.parametro.timeframe"),
    TIMEFRAME_RANGE_DOLLAR("filtro.rangeDollar.parametro.timeframe"),
    NIVEL_CRUCE_CROSSING_ABOVE_BELOW("filtro.crossingAboveBelow.parametro.nivelCruce"),
    PERIODO_EMA_CROSSING_ABOVE_BELOW("filtro.crossingAboveBelow.parametro.periodoEMA"),
    VALOR_HALT("filtro.halt.parametro.valor"),

    // Parámetros de Volatilidad
    LONGITUD_ATR("filtro.atr.parametro.longitud"),
    MODO_PROMEDIO_MOVIL_ATR("filtro.atr.parametro.modoPromedioMovil"),
    TIMEFRAME_ATR("filtro.atr.parametro.timeframe"),
    PERIODO_ATR_ATRP("filtro.atrp.parametro.periodoATR"),
    TIPO_PROMEDIO_MOVIL_ATRP("filtro.atrp.parametro.tipoPromedioMovil"),
    VALOR_PROMEDIO_MOVIL_ATRP("filtro.atrp.parametro.valorPromedioMovil"),
    TIMEFRAME_ATRP("filtro.atrp.parametro.timeframe"),

    // Parámetros de Momentum e Indicadores Técnicos
    PERIODO_RSI("filtro.rsi.parametro.periodoRsi"),
    TIMEFRAME_RSI("filtro.rsi.parametro.timeframe"),
    LINEA_REFERENCIA_DISTANCE_FROM_VWAP_EMA_MA("filtro.distanceFromVwapEmaMa.parametro.lineaReferencia"),
    MODO_DISTANCIA_DISTANCE_FROM_VWAP_EMA_MA("filtro.distanceFromVwapEmaMa.parametro.modoDistancia"),
    PERIODO_LINEA_DISTANCE_FROM_VWAP_EMA_MA("filtro.distanceFromVwapEmaMa.parametro.periodoLinea"),
    PERIODO_EMA_BACK_TO_EMA("filtro.backToEmaAlert.parametro.periodoEMA"),
    TIMEFRAME_BACK_TO_EMA("filtro.backToEmaAlert.parametro.timeframe"),
    THROUGH_EMA_VWAP_LINEA_CRUCE("filtro.throughEmaVwapAlert.parametro.lineaCruce"),
    THROUGH_EMA_VWAP_PERIODO_EMA("filtro.throughEmaVwapAlert.parametro.periodoEMA"),
    THROUGH_EMA_VWAP_DIRECCION_ROMPIMIENTO("filtro.throughEmaVwapAlert.parametro.direccionRompimiento"),
    LINEA_REFERENCIA_EMA_VWAP_SUPPORT("filtro.emaVwapSupportResistance.parametro.lineaReferencia"),
    PERIODO_EMA_EMA_VWAP_SUPPORT("filtro.emaVwapSupportResistance.parametro.periodoEMA"),
    TIPO_ROL_EMA_VWAP_SUPPORT("filtro.emaVwapSupportResistance.parametro.tipoRol"),

    // Parámetros de Tiempo y Patrones de Precio
    TIMEFRAME_BEARISH_BULLISH_ENGULFING_CANDLE("filtro.bearishBullishEngulfingCandle.parametro.timeframe"),
    TIPO_PATRON_BEARISH_BULLISH_ENGULFING_CANDLE("filtro.bearishBullishEngulfingCandle.parametro.tipoPatron"),
    TIPO_VELA_FIRTS_CANDLE("filtro.firstCandle.parametro.tipoVela"),
    NUMERO_VELAS_CONSECUTIVAS("filtro.consecutiveCandles.parametro.numeroVelasConsecutivas"),
    TIMEFRAME_CONSECUTIVE_CANDLES("filtro.consecutiveCandles.parametro.timeframe"),
    OPCION_EXTREMO_HIGH_LOW_DAY("filtro.highLowOfDay.parametro.opcionExtremo"),
    TIMEFRAME_HIGH_LOW_DAY("filtro.highLowOfDay.parametro.timeframe"),
    OPCION_EXTREMO_NEW_CANDLE("filtro.newCandleHighLow.parametro.opcionExtremo"),
    TIMEFRAME_NEW_CANDLE("filtro.newCandleHighLow.parametro.timeframe"),
    PUNTO_REFERENCIA_PULLBACK("filtro.percentagePullbackHighsLows.parametro.puntoReferenciaPullback"),
    PORCENTAJE_RETROCESO_PULLBACK("filtro.percentagePullbackHighsLows.parametro.porcentajeRetroceso"),
    OPCION_EXTREMO_BREAK_OVER("filtro.breakOverRecentHighsLows.parametro.opcionExtremo"),
    TIMEFRAME_BREAK_OVER("filtro.breakOverRecentHighsLows.parametro.timeframe"),
    TIMEFRAME_OPENING_RANGE_BREAKDOWN("filtro.openingRangeBreakdown.parametro.timeframe"),
    TIMEFRAME_OPENING_RANGE_BREAKOUT("filtro.openingRangeBreakout.parametro.timeframe"),
    TIMEFRAME_PIVOTS("filtro.pivots.parametro.timeframe"),
    MINUTOS_TRANSCURRIDOS("filtro.minutosInMarket.parametro.minutosTranscurridos"),

    // Parámetros de Características Fundamentales
    ESTADO_NOTICIA("filtro.noticias.parametro.estadoNoticia");

    private final String etiqueta;

}
