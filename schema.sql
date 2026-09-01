CREATE TABLE tb_usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    perfil VARCHAR(30) NOT NULL
);

CREATE TABLE tb_area_monitorada (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    cidade VARCHAR(120) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    nivel_vulnerabilidade VARCHAR(20) NOT NULL,
    criada_em TIMESTAMP NOT NULL
);

CREATE TABLE tb_fonte_dado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    url_acesso VARCHAR(255)
);

CREATE TABLE tb_observacao_orbital (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    area_id BIGINT NOT NULL,
    fonte_dado_id BIGINT NOT NULL,
    capturada_em TIMESTAMP NOT NULL,
    temperatura_superficie DECIMAL(5,2),
    umidade_relativa DECIMAL(5,2),
    indice_vegetacao DECIMAL(5,4),
    analise_ia VARCHAR(500) NOT NULL,
    FOREIGN KEY (area_id) REFERENCES tb_area_monitorada(id),
    FOREIGN KEY (fonte_dado_id) REFERENCES tb_fonte_dado(id)
);

CREATE TABLE tb_evento_monitoramento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    status VARCHAR(50) NOT NULL,
    severidade VARCHAR(20) NOT NULL,
    criado_em TIMESTAMP NOT NULL
);

CREATE TABLE tb_alerta_risco (
    id BIGINT PRIMARY KEY,
    probabilidade_percentual INT NOT NULL,
    recomendacao VARCHAR(700) NOT NULL,
    valido_ate TIMESTAMP NOT NULL,
    observacao_orbital_id BIGINT,
    FOREIGN KEY (id) REFERENCES tb_evento_monitoramento(id),
    FOREIGN KEY (observacao_orbital_id) REFERENCES tb_observacao_orbital(id)
);
