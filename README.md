# Transferenca de dinheiro

O projeto é uma API REST em Java para transferência de dinheiro entre contas bancárias. Ele permite que os usuários autenticados realizem transações seguras, gerenciem suas contas e consultem o histórico de transações. Implementado com Spring Boot, oferece endpoints para criar, atualizar e consultar contas, bem como realizar transferências de forma eficiente e segura.


# Tecnologias utilizadas
## Back end
- Java
- Spring Boot
- JPA / Hibernate
- Maven

- ## Banco de dados
- PostgreSQL

Para rodar o projeto localmente faça o download das seguintes ferramentas:

- [STS](https://spring.io.xy2401.com/tools3/sts/all/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Postman](https://www.postman.com/downloads/)
- [Git](https://git-scm.com/downloads)

Depois de ter instalado as ferramentas, crie uma pasta e de um nome a ela. Então abra o seu git bash nessa pasta. e digite o seguinte comando:

![ABRIR-GIT-BASH](https://github.com/Wilson-Pedro/images/blob/main/git-bash/abrir-git-bash.png)

```bash
git clone git@github.com:Wilson-Pedro/transferencia-de-dinheiro-API-RESTFUL.git
```

Após isso abra o projeto no STS ou qualquer IDE que suporte o SPRING.

-> Com o STS vá em 'TransferenciaApplication.java' e clique com o potão direito.

-> Vá em 'Run As'.

-> E clique em 'Spring Boot App'

![START-PROJECT](https://github.com/Wilson-Pedro/images/blob/main/transfer%C3%AAncia/start-peoject-transferencia.png)

# Abra o [Postman](https://www.postman.com/downloads/) e teste o seguintes endpoints:


# POST
```
http://localhost:8080/clientes
```
![POST](https://github.com/Wilson-Pedro/images/blob/main/transfer%C3%AAncia/endpoints/POST.PNG)

# GET ALL
```
http://localhost:8080/clientes
```
![GET ALL](https://github.com/Wilson-Pedro/images/blob/main/transfer%C3%AAncia/endpoints/GET-ALL.PNG)

# GET ONE
```
http://localhost:8080/clientes/1
```
![GET ONE](https://github.com/Wilson-Pedro/images/blob/main/transfer%C3%AAncia/endpoints/GET-ONE.PNG)

# PUT
```
http://localhost:8080/clientes/8
```
![PUT](https://github.com/Wilson-Pedro/images/blob/main/transfer%C3%AAncia/endpoints/PUT.PNG)

# DELETE
```
http://localhost:8080/clientes/8
```
![DELETE](https://github.com/Wilson-Pedro/images/blob/main/transfer%C3%AAncia/endpoints/DELETE.PNG)

# PUT
```
http://localhost:8080/clientes/2/transferencia
```
![DELETE](https://github.com/Wilson-Pedro/images/blob/main/transfer%C3%AAncia/endpoints/TRANSFERENCIA.PNG)


## Autor

- [@Wilson Pedro](https://github.com/Wilson-Pedro)
