# 📦 Usuario Service

# 📦 Usuario Service

API REST para gerenciamento de usuários com autenticação JWT, gerenciamento de endereços e telefones, integração com ViaCEP, documentação Swagger/OpenAPI e ambiente totalmente dockerizado.

---

## 🚀 Tecnologias Utilizadas

* Java 17
* Spring Boot 3
* Spring Security (JWT)
* Spring Data JPA
* Spring Cloud OpenFeign
* PostgreSQL
* Lombok
* Swagger / OpenAPI 3
* Maven

---

## ✨ Funcionalidades

### 👤 Usuários

* Cadastro de usuário
* Login com autenticação JWT
* Busca de usuário por e-mail
* Atualização de dados cadastrais
* Exclusão de usuário

### 🏠 Endereços

* Cadastro de endereço para usuário autenticado
* Atualização de endereço
* Consulta de CEP através da API ViaCEP

### 📱 Telefones

* Cadastro de telefone para usuário autenticado
* Atualização de telefone

---

## 🔐 Segurança

A aplicação utiliza autenticação baseada em JWT.

### Recursos de segurança

* Senhas criptografadas com BCrypt
* Geração e validação de Token JWT
* Rotas protegidas por autenticação
* Tratamento de token expirado

### Endpoints Públicos

```http
POST /usuario
POST /usuario/login
GET /usuario/endereco/{cep}
```

### Endpoints Protegidos

Todos os demais endpoints exigem:

```http
Authorization: Bearer <token>
```

---

## 📁 Estrutura do Projeto

```text
com.javaeo.usuario
├── UsuarioApplication.java
│
├── business
│   ├── UsuarioService.java
│   ├── ViaCepService.java
│   ├── converter
│   └── dto
│
├── controller
│   ├── UsuarioController.java
│   └── GlobalExceptionHandler.java
│
├── infrastructure
│   ├── clients
│   ├── entity
│   ├── repository
│   ├── security
│   └── exceptions
```

---

## 📌 Endpoints

| Método | Endpoint                  | Descrição          |
| ------ | ------------------------- | ------------------ |
| POST   | /usuario                  | Cadastrar usuário  |
| POST   | /usuario/login            | Realizar login     |
| GET    | /usuario?email={email}    | Buscar usuário     |
| PUT    | /usuario                  | Atualizar usuário  |
| DELETE | /usuario/{email}          | Excluir usuário    |
| POST   | /usuario/endereco         | Cadastrar endereço |
| PUT    | /usuario/endereco?id={id} | Atualizar endereço |
| GET    | /usuario/endereco/{cep}   | Consultar CEP      |
| POST   | /usuario/telefone         | Cadastrar telefone |
| PUT    | /usuario/telefone?id={id} | Atualizar telefone |

---

## 🧪 Exemplos de Requisição

### Cadastro de Usuário

```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456",
  "enderecos": [
    {
      "rua": "Rua A",
      "numero": 100,
      "cidade": "São Paulo",
      "estado": "SP",
      "cep": "01001000",
      "complemento": "Apto 101"
    }
  ],
  "telefones": [
    {
      "ddd": "11",
      "numero": "912345678"
    }
  ]
}
```

### Login

```json
{
  "email": "joao@email.com",
  "senha": "123456"
}
```

### Cadastro de Endereço

```json
{
  "rua": "Rua B",
  "numero": 200,
  "cidade": "Rio de Janeiro",
  "estado": "RJ",
  "cep": "20000000",
  "complemento": "Casa 2"
}
```

---

## ⚠️ Tratamento de Exceções

A API retorna respostas padronizadas:

```json
{
  "timestamp": "2025-04-05T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Email não encontrado joao@email.com",
  "path": "/usuario"
}
```

---

## 🗄️ Modelagem do Banco de Dados

### Tabelas

#### usuario

```text
id
nome
email
senha
```

#### endereco

```text
id
rua
numero
complemento
cidade
estado
cep
usuario_id
```

#### telefone

```text
id
ddd
numero
usuario_id
```

### Relacionamentos

```text
Usuario 1:N Endereco
Usuario 1:N Telefone
```

---

## 🛠️ Como Executar o Projeto

### 1. Clonar o repositório

```bash
git clone <url-do-repositorio>
```

### 2. Configurar o banco de dados

Configure as credenciais no arquivo:

```properties
application.properties
```

### 3. Executar a aplicação

```bash
./mvnw spring-boot:run
```

### 4. Acessar a documentação

```text
http://localhost:8080/swagger-ui/index.html
```
## 🐳 Docker

A aplicação pode ser executada através do Docker e Docker Compose.

### Subir os containers

```bash
docker-compose up -d
```

### Verificar containers em execução

```bash
docker ps
```

### Derrubar os containers

```bash
docker-compose down
```
---

## 📖 Observações Técnicas

* Conversão entre DTO e Entity centralizada em `UsuarioConverter`.
* Integração com ViaCEP utilizando OpenFeign.
* Tratamento global de exceções via `GlobalExceptionHandler`.
* Captura de token expirado no `JwtRequestFilter`.
* Atualizações parciais utilizando campos não nulos.

---

## 🔮 Melhorias Futuras

* Implementar Bean Validation (`@Valid`)
* Adicionar paginação
* Criar serviços específicos para Endereço e Telefone
* Implementar logout com blacklist de tokens
* Adicionar testes unitários e de integração
* Pipeline CI/CD

---

## 👨‍💻 Autor

**Elvis Oliveira**

GitHub: https://github.com/elvis-oliveirax

LinkedIn: https://www.linkedin.com/in/elvis-oliveira-127a3739a/
