# 🎟️🚶🏽‍♀️‍➡️ QUADRA FÁCIL - api de reserva de quadras esportivas.

> **quadra fácil** é uma `API` que têm como foco simplificar as reservas de quadras esportivas. Com essa API o responsável pelo espaço consegue cadastrar o endereço, horário de abertura e fechamento, regras para as reservas e etc.

## 🏗️ O que essa API permite:

### Quadra (`/quadra`):
- [X] registrar quadra
- [X] buscar quadra (`/id`)
- [X] buscar por quadras (`/email`)
- [X] buscar por quadras
- [X] editar quadra
- [X] deletar quadra (`/id`)

### Gestor (`/gestor`):
- [X] registrar gestor
- [X] buscar gestor (`/email`)
- [X] buscar por gestores
- [X] editar gestor (`/id` & `{body}`)
- [X] deletar gestor (`/id`)

### Reserva (`/reserva`):
- [X] criar uma reserva
- [X] buscar por reserva (`/id`)
- [X] buscar por reservas
- [X] buscar por reservas feitas em determinada quadra (`/quadra/quadraId`) 
- [X] deletar reserva (`/id`)

### Funcionamento: (`/funcionamento`):
- [X] registrar funcionamento (`/quadra_id` & `[{funcionamento}]`)
- [X] buscar por funcionamento (`/quadra_id`)
- [X] editar funcionamento (`{body}`)
- [X] editar disponibilidade (`/id/atualizar-disponibilidade?disponibilidade=boolean`)
- [X] deletar funcionamento (`/id`)

### Horarios Disponiveis: 
- [X] buscar por horarios disponiveis para reserva (`/horarios-disponiveis/quadra/quadraId/data/2025-03-15`)

### Responsavel (`/responsavel`):
- [X] registrar responsável
- [X] buscar por responavel (`/cpf`)
- [X] editar responsável (`/cpf` & `{body}`)
- [X] deletar responsável (`/cpf`)

## 🛠️ Acesso ao projeto:

Para acessar o projeto é necessário seguir os seguintes passos:

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
Para acessar a documentação, no seu navegador acesso o link:
🔗 http://localhost:8080/swagger-ui/index.html#/

## 🗒️ Atividades para o futuro:
- [ ] Adicionar testes unitários
- [ ] Implementar autenticação e controle de acesso para diferentes tipos de usuários (ex.: Gestor e Responsável), garantindo permissões específicas para cada endpoint.
- [ ] Melhorar a documentação
