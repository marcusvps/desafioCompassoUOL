# Desafio CompassoUOL


Este projeto foi desenvolvido para atender ao desafio da CompassoUOl para a vaga  
de desenvolvedor Java.

Tecnologias utilizadas:
* Java 8;
* Spring Boot;
* Maven;
* Junit5;
* H2Database(Banco de Dados em Memória);
* Swagger;
* Docker;

# Execução do Projeto

1 - Compile o projeto  
`mvn clean install`

2 - Execute o projeto com springBoot  
`mvn spring-boot:run` ou execute com o docker `docker run -p 8080:8080 /compassouol/challenge-app`

3 - A url padrão da aplicação é http://localhost:8080


# Acesso a console do H2Database
O projeto foi utiliza do H2Database, que é um banco de dados em memória que é criado junto com o build. Para  
a criação do banco é utilzado o arquivo `data.sql` que está em `src/main/resources`.

1 - Acesse `http://localhost:8080/h2-database`

2 - Preencha as informações:
* Driver Class = `org.h2.Driver`
* JDBC URL: `jdbc:h2:mem:compassoUOLDS`
* User Name: `admin`
* Password: `admin`

# Swagger
O  Swagger está versionado no arquivo `swagger.yaml`  e pode ser visualizado em https://editor.swagger.io/.

# Postman
As requisições de exemplo podem ser importadas no postman, através do arquivo `CompassoUOL.postman_collection.json`



  
