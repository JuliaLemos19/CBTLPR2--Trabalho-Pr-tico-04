CREATE DATABASE aulajava;
   GO

   USE aulajava;
   GO

   CREATE TABLE tbcargos (
       cd_cargo SMALLINT PRIMARY KEY,
       ds_cargo CHAR(20) NOT NULL
   );

   CREATE TABLE tbfuncs (
       cod_func DECIMAL(9) PRIMARY KEY,
       nome_func CHAR(30) NOT NULL,
       sal_func MONEY NOT NULL,
       cod_cargo SMALLINT FOREIGN KEY REFERENCES tbcargos(cd_cargo)
   );
   GO

   INSERT INTO tbcargos VALUES (1, 'Administrativo'), (2, 'Técnico'), (3, 'Gerente');
   INSERT INTO tbfuncs VALUES (101, 'Marcelo', 2000.00, 1), (102, 'Joana', 2500.00, 2);