# 🎟️🚶🏽‍♀️‍➡️ QUADRA FÁCIL - api de reserva de quadras esportivas.

> **quadra fácil** é uma `API` que têm como foco simplificar as reservas de quadras esportivas. Com essa API o responsável pelo espaço consegue cadastrar o endereço, horário de abertura e fechamento, regras para as reservas e etc.

</br>

## 🛠️ Acesso ao projeto:

> Para acessar o projeto é necessário seguir os seguintes passos:

### 1. Fazer o clone do projeto:
Abra o terminal e execute:
```
git clone https://github.com/NataliaFrancisca/quadrafacil
cd quadrafacil
```

### 2. Instalar Dependências
Certifique-se de ter as seguintes ferramentas instaladas:
- JDK 17+ (ou outra versão necessária)
- Maven (caso esteja usando)
- MySQL Server
  
### 3. Configurar o Banco de Dados
1. Instale e inicie o MySQL
2. Crie um banco de dados para o projeto. Abra o terminal do MySQL (ou um client como MySQL Workbench) e execute: `CREATE DATABASE quadralivre;`
4. Certifique-se de ter um usuário e senha configurados.
5. Atualize as configurações do MySQL no projeto. Procure pelo arquivo `application.properties` e edite ele:
  ```
    spring.application.name=quadralivre
    spring.datasource.url=jdbc:mysql://localhost:3306/quadralivre
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
  ```

### 4. Rodar a Aplicação
Agora, execute o projeto com:
```
mvn spring-boot:run
```

### 5. Acessar a API
Para acessar a documentação, no seu navegador acesso o link: http://localhost:8080/swagger-ui/index.html#/

</br>

## 🏗️ O que essa API permite:
Essa API permite o gerenciamento de reservas de quadras esportivas. As principais entidades são:
- **Responsável**: Usuário que realiza reservas de horários para utilizar a quadra.
- **Gestor**: Usuário que administra a quadra, sendo responsável por criá-la e definir seus horários de funcionamento.
- **Quadra**: Representa as informações básicas da quadra esportiva.
- **Funcionamento**: Entidade relacionada à Quadra, responsável por definir seus horários de funcionamento ao longo da semana.
- **Horários Disponíveis**: Lista de horários livres para reserva.
- **Reserva**: Contém os dados necessários para a criação de uma reserva, incluindo o responsável, a quadra e o horário reservado.

[Documentação Completa](https://nat-francisca.notion.site/doc-API-quadralivre-1c1fdff88f3a8051b227d0f9e8629475)


</br>

## 🗒️ Atividades para o futuro:
- [ ] Adicionar testes unitários
- [ ] Implementar autenticação e controle de acesso para diferentes tipos de usuários (ex.: Gestor e Responsável), garantindo permissões específicas para cada endpoint.
- [ ] Melhorar a documentação
