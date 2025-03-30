# 🤾🏽‍♀️❇️ QUADRA FÁCIL

**Quadra Fácil** é uma `API` desenvolvida para simplificar a reserva de quadras esportivas. Com ela, os responsáveis pelos espaços podem cadastrar suas quadras e gerar automaticamente horários de reserva para diferentes dias da semana.

## 📦 Stack:
- Java 
- Spring Boot
- MySQL
- Swagger
- Insominia  
- Caelum Stella
  
## 🛠️ Funcionalidades:
- **Gerenciamento de usuários**: Criação de diferentes tipos de usuários: Gestor (administrador do espaço esportivo) e Responsável (usuário que realiza reservas).
- **Cadastro de quadras**: Permite que gestores registrem quadras e configurem seus horários de funcionamento para os dias da semana.
- **Listagem de horários**: Consulta dos horários disponíveis para reserva. 
- **Reserva**: Permite que os responsáveis realizem reservas para quadras em horários específicos.


## ⏭️ Melhorias para o futuro:
- [ ] Adicionar testes unitários
- [ ] Implementar autenticação e controle de acesso para diferentes tipos de usuários (ex.: Gestor e Responsável), garantindo permissões específicas para cada endpoint.


## 📃 Documentação:

[Documentação Completa](https://nat-francisca.notion.site/doc-API-quadralivre-1c1fdff88f3a8051b227d0f9e8629475)


## 🚩 Respostas Padrão

Todas as requisições retornam um objeto com a seguinte estrutura:

```json
{
  "statusHTTP": Integer,
  "mensagem": String,
  "body": Object,
  "erros": Object
}
```

#### Códigos de Status

| Código | Descrição | Exemplo |
|--------|-----------|---------|
| 200 | Operação realizada com sucesso | `{"statusHTTP": 200, "mensagem": "Gestor deletado com sucesso."}` |
| 201 | Recurso criado com sucesso | `{"statusHTTP": 201, "mensagem": "Gestor cadastrado com sucesso.", "body": {...}}` |
| 400 | Dados inválidos | `{"statusHTTP": 400, "mensagem": "Digite um e-mail válido. Ex.: email@email.com"}` |
| 404 | Recurso não encontrado | `{"statusHTTP": 404, "mensagem": "Gestor com e-mail: baddunny@gmail.com não encontrado."}` |
| 409 | Conflito com dados já cadastrados | `{"statusHTTP": 409, "mensagem": "Já existe um cadastro com esse e-mail: baddunny@gmail.com"}` |


## 🚏Rotas:

Todas as rotas da aplicação, seus métodos e descrição.

### Gestor:

| Método | Rota | Descrição |
|--------|------|-----------|
| POST   | `/gestor` | Criar Gestor |
| GET    | `/gestor` | Listar Gestores |
| GET    | `/gestor/email?valor={email}` | Buscar Gestor por Email |
| PUT    | `/gestor/email?valor={email}` | Atualizar Gestor |
| DELETE | `/gestor/email?valor={email}` | Deletar Gestor |

### Quadra:
| Método | Rota | Descrição |
|--------|------|-----------|
| POST   | `/quadra` | Criar Quadra |
| GET    | `/quadra` | Listar Quadras |
| GET    | `/quadra/{id}` | Buscar Quadra por ID |
| GET    | `/quadra/gestor?email={email}` | Buscar Quadras por Gestor |
| PUT    | `/quadra` | Atualizar Quadra |
| DELETE | `/quadra/{id}` | Deletar Quadra |

### Responsavel:
| Método | Rota | Descrição |
|--------|------|-----------|
| POST   | `/responsavel` | Criar Responsável |
| GET    | `/responsavel/cpf?valor={cpf}` | Buscar Responsável por CPF |
| PUT    | `/responsavel/cpf?valor={cpf}` | Atualizar Responsável |
| DELETE | `/responsavel/cpf?valor={cpf}` | Deletar Responsável |

### Funcionamento:
| Método | Rota | Descrição |
|--------|------|-----------|
| POST   | `/funcionamento` | Adicionar Horário de Funcionamento |
| GET    | `/funcionamento?quadraId={id}` | Consultar Horário de Funcionamento |
| PUT    | `/funcionamento` | Atualizar Horário de Funcionamento |
| PATCH  | `/funcionamento?quadraId={id}&diaSemana={dia}` | Atualizar Disponibilidade do Dia |
| DELETE | `/funcionamento?quadraId={id}&diaSemana={dia}` | Deletar Horário de Funcionamento |

### Horários Disponíveis:
| Método | Rota | Descrição |
|--------|------|-----------|
| GET    | `/horarios-disponiveis?quadraId={id}&data={data}` | Listar Horários Disponíveis |

### Reserva:
| Método | Rota | Descrição |
|--------|------|-----------|
| POST   | `/reserva` | Criar Reserva |
| GET    | `/reserva/{id}` | Buscar Reserva por ID |
| GET    | `/reserva/quadra/{id}` | Listar Reservas de uma Quadra |



## 🏗️ Como rodar essa aplicação na minha máquina?

Para acessar o projeto, siga os seguintes passos:

### 1. Fazer o clone do projeto:
Abra o terminal e execute:
```
git clone https://github.com/NataliaFrancisca/quadrafacil
cd quadrafacil
```

### 2. Instalar dependências
Certifique-se de ter as seguintes ferramentas instaladas:
- JDK 17+ (ou outra versão necessária)
- Maven (caso esteja usando)
- MySQL
  
### 3. Configurar o banco de dados
- [ ] Instale e inicie o **MySQL**.
- [ ] Crie um banco de dados para o projeto. 
- [ ] Abra o terminal do MySQL (ou um client como MySQL Workbench) e execute: 
```
CREATE DATABASE quadralivre
```
- [ ] Certifique-se de ter um usuário e senha configurados.
- [ ] Atualize as configurações do MySQL no projeto. 
- [ ] Procure pelo arquivo `application.properties` e edite ele:
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
