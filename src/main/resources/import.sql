-- Tipos de ejecución
INSERT IGNORE INTO tipos_ejecucion (id_tipo_ejecucion, enum_tipo_ejecucion) VALUES (1, 'UNA_VEZ');
INSERT IGNORE INTO tipos_ejecucion (id_tipo_ejecucion, enum_tipo_ejecucion) VALUES (2, 'DIARIA');

-- Mercados
INSERT IGNORE INTO mercados (id_mercado, enum_mercado) VALUES (1, 'NYSE');
INSERT IGNORE INTO mercados (id_mercado, enum_mercado) VALUES (2, 'NASDAQ');
INSERT IGNORE INTO mercados (id_mercado, enum_mercado) VALUES (3, 'AMEX');
INSERT IGNORE INTO mercados (id_mercado, enum_mercado) VALUES (4, 'ETF');
INSERT IGNORE INTO mercados (id_mercado, enum_mercado) VALUES (5, 'OTC');