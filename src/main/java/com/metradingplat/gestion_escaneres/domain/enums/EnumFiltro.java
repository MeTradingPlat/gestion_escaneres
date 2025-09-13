package com.metradingplat.gestion_escaneres.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumFiltro {
    // Categoría: Volumen
    VOLUME("filtro.volume.nombre", "filtro.volume.descripcion"),
    AVERAGE_VOLUME("filtro.averageVolume.nombre", "filtro.averageVolume.descripcion"),
    VOLUMEN_POST_PRE("filtro.volumenPostPre.nombre", "filtro.volumenPostPre.descripcion"),
    RELATIVE_VOLUME("filtro.relativeVolume.nombre", "filtro.relativeVolume.descripcion"),
    RELATIVE_VOLUME_SAME_TIME("filtro.relativeVolumeSameTime.nombre", "filtro.relativeVolumeSameTime.descripcion"),
    VOLUME_SPIKE("filtro.volumeSpike.nombre", "filtro.volumeSpike.descripcion"),

    // Categoría: Precio y Movimiento
    CHANGE("filtro.change.nombre", "filtro.change.descripcion"),
    PERCENTAGE_CHANGE("filtro.percentageChange.nombre", "filtro.percentageChange.descripcion"),
    PRECIO("filtro.precio.nombre", "filtro.precio.descripcion"),
    GAP_FROM_CLOSE("filtro.gapFromClose.nombre", "filtro.gapFromClose.descripcion"),
    POSITION_IN_RANGE("filtro.positionInRange.nombre", "filtro.positionInRange.descripcion"),
    PERCENTAGE_RANGE("filtro.percentageRange.nombre", "filtro.percentageRange.descripcion"),
    RANGE_DOLLARS("filtro.rangeDollars.nombre", "filtro.rangeDollars.descripcion"),
    CROSSING_ABOVE_BELOW("filtro.crossingAboveBelow.nombre", "filtro.crossingAboveBelow.descripcion"),
    HALT("filtro.halt.nombre", "filtro.halt.descripcion"),

    // Categoría: Volatilidad
    ATR("filtro.atr.nombre", "filtro.atr.descripcion"),
    ATRP("filtro.atrp.nombre", "filtro.atrp.descripcion"),
    RELATIVE_RANGE("filtro.relativeRange.nombre", "filtro.relativeRange.descripcion"),

    // Categoría: Momentum e Indicadores Técnicos
    RSI("filtro.rsi.nombre", "filtro.rsi.descripcion"),
    DISTANCE_FROM_VWAP("filtro.distanceFromVwap.nombre", "filtro.distanceFromVwap.descripcion"),
    DISTANCE_FROM_EMA("filtro.distanceFromEma.nombre", "filtro.distanceFromEma.descripcion"),
    DISTANCE_FROM_MA("filtro.distanceFromMa.nombre", "filtro.distanceFromMa.descripcion"),
    BACK_TO_EMA_ALERT("filtro.backToEmaAlert.nombre", "filtro.backToEmaAlert.descripcion"),
    THROUGH_EMA_VWAP_ALERT("filtro.throughEmaVwapAlert.nombre", "filtro.throughEmaVwapAlert.descripcion"),
    EMA_VWAP_SUPPORT_RESISTANCE("filtro.emaVwapSupportResistance.nombre", "filtro.emaVwapSupportResistance.descripcion"),

    // Categoría: Tiempo y Patrones de Precio
    BEARISH_BULLISH_ENGULFING("filtro.bearishBullishEngulfing.nombre", "filtro.bearishBullishEngulfing.descripcion"),
    CONSECUTIVE_CANDLES("filtro.consecutiveCandles.nombre", "filtro.consecutiveCandles.descripcion"),
    FIRST_CANDLE("filtro.firstCandle.nombre", "filtro.firstCandle.descripcion"),
    HIGH_LOW_OF_DAY("filtro.highLowOfDay.nombre", "filtro.highLowOfDay.descripcion"),
    NEW_CANDLE_HIGH_LOW("filtro.newCandleHighLow.nombre", "filtro.newCandleHighLow.descripcion"),
    PERCENTAGE_PULLBACK_HIGHS_LOWS("filtro.percentagePullbackHighsLows.nombre", "filtro.percentagePullbackHighsLows.descripcion"),
    BREAK_OVER_RECENT_HIGHS_LOWS("filtro.breakOverRecentHighsLows.nombre", "filtro.breakOverRecentHighsLows.descripcion"),
    OPENING_RANGE_BREAKDOWN("filtro.openingRangeBreakdown.nombre", "filtro.openingRangeBreakdown.descripcion"),
    OPENING_RANGE_BREAKOUT("filtro.openingRangeBreakout.nombre", "filtro.openingRangeBreakout.descripcion"),
    PIVOTS("filtro.pivots.nombre", "filtro.pivots.descripcion"),
    MINUTOS_IN_MARKET("filtro.minutosInMarket.nombre", "filtro.minutosInMarket.descripcion"),

    // Categoría: Características Fundamentales
    FLOAT("filtro.float.nombre", "filtro.float.descripcion"),
    SHARES_OUTSTANDING("filtro.sharesOutstanding.nombre", "filtro.sharesOutstanding.descripcion"),
    MARKET_CAP("filtro.marketCap.nombre", "filtro.marketCap.descripcion"),
    SHORT_INTEREST("filtro.shortInterest.nombre", "filtro.shortInterest.descripcion"),
    SHORT_RATIO("filtro.shortRatio.nombre", "filtro.shortRatio.descripcion"),
    DAYS_UNTIL_EARNINGS("filtro.daysUntilEarnings.nombre", "filtro.daysUntilEarnings.descripcion"),
    NOTICIAS("filtro.noticias.nombre", "filtro.noticias.descripcion"),
    
    // Filtro no encontrado
    UNKNOWN("filtro.unknown.nombre", "filtro.unknown.descripcion"),
    ;

    private final String etiquetaNombre;
    private final String etiquetaDescripcion;
}