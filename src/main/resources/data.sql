DROP TABLE IF EXISTS CLIENTE;

DROP TABLE IF EXISTS CIDADE;
CREATE TABLE CIDADE(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(250) NOT NULL,
    estado VARCHAR(250) NOT NULL
);


CREATE TABLE CLIENTE(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomeCompleto VARCHAR(250) NOT NULL,
    sexo VARCHAR(20) NOT NULL,
    dataNascimento Date NOT NULL,
    idade INT NOT NULL,
    idCidade INT NOT NULL,
    FOREIGN KEY(idCidade) REFERENCES CIDADE(id)
);


INSERT INTO CIDADE (nome,estado) VALUES ('Taguatinga', 'Distrito Federal');
INSERT INTO CIDADE (nome,estado) VALUES ('Rio de Janeiro', 'Rio de Janeiro');
INSERT INTO CIDADE (nome,estado) VALUES ('Macapa', 'Amapa');
INSERT INTO CIDADE (nome,estado) VALUES ('Palmas', 'Tocantins');

INSERT INTO CLIENTE(nomeCompleto,sexo,dataNascimento,idade,idCidade) VALUES ('Jose de Assis','MASCULINO', '1995-9-21',25,1);
INSERT INTO CLIENTE(nomeCompleto,sexo,dataNascimento,idade,idCidade) VALUES ('Maria do Carmo','FEMININO',DATE '1956-2-9',65,2);
INSERT INTO CLIENTE(nomeCompleto,sexo,dataNascimento,idade,idCidade) VALUES ('Maria Antonieta','FEMININO',DATE '1990-9-23',31,3);
INSERT INTO CLIENTE(nomeCompleto,sexo,dataNascimento,idade,idCidade) VALUES ('Ricardo de Almeida','MASCULINO',DATE '1980-5-21',41,4);