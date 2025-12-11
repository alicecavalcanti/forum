# Forum API

API REST para um fórum de discussão, construída com **Spring Boot 3** e **Kotlin**.

## Tecnologias

- **Kotlin 2.0** / **Java 21**
- **Spring Boot 3.3.1**
    - Spring Web
    - Spring Data JPA
    - Spring Security
    - Spring Cache
    - Spring Validation
- **H2 Database** (banco em memória)
- **Flyway** (migrações de banco de dados)
- **JWT** (autenticação stateless)
- **BCrypt** (hash de senhas)

## Funcionalidades

- Cadastro e autenticação de usuários com JWT
- CRUD completo de tópicos
- CRUD completo de respostas
- Paginação e ordenação
- Filtro por curso
- Relatório de tópicos por categoria
- Cache para otimização de consultas

## Estrutura do Projeto

```
src/main/kotlin/com/apiRest/forum/
├── config/          # Configurações (JWT, Security)
├── controller/      # Endpoints da API
├── dto/             # Objetos de transferência (Forms e Views)
├── exception/       # Tratamento de exceções
├── mapper/          # Conversores Entity <-> DTO
├── model/           # Entidades JPA
├── repositories/    # Repositórios Spring Data
├── security/        # Filtros de autenticação
└── service/         # Regras de negócio
```

## Pré-requisitos

- Java 21+
- Maven 3.8+ (ou use o Maven Wrapper incluído)

## Executando o Projeto

```bash
# Clonar o repositório
git clone <url-do-repositorio>
cd forum

# Executar com Maven Wrapper
./mvnw spring-boot:run

# Ou compilar e executar o JAR
./mvnw clean package
java -jar target/forum-0.0.1-SNAPSHOT.jar
```

A API estará disponível em `http://localhost:8080`

## Endpoints da API

### Autenticação (públicos)

| Método | Endpoint   | Descrição              |
|--------|------------|------------------------|
| POST   | /sign-up   | Cadastrar novo usuário |
| POST   | /sign-in   | Login (retorna token)  |

### Tópicos (requer autenticação)

| Método | Endpoint                      | Descrição                        |
|--------|-------------------------------|----------------------------------|
| GET    | /topicos                      | Listar tópicos (paginado)        |
| GET    | /topicos/{id}                 | Buscar tópico por ID             |
| GET    | /topicos/{id}/respostas       | Listar respostas de um tópico    |
| GET    | /topicos/relatorio            | Relatório por categoria          |
| POST   | /topicos                      | Criar novo tópico                |
| POST   | /topicos/{id}/respostas       | Adicionar resposta a um tópico   |
| PUT    | /topicos                      | Atualizar tópico                 |
| PUT    | /topicos/{id}/respostas       | Atualizar resposta               |
| DELETE | /topicos/{id}                 | Deletar tópico                   |
| DELETE | /topicos/respostas/{id}       | Deletar resposta                 |

## Exemplos de Uso

### Cadastrar usuário

```bash
curl -X POST http://localhost:8080/sign-up \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "password": "senha123"
  }'
```

### Fazer login

```bash
curl -X POST http://localhost:8080/sign-in \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@email.com",
    "password": "senha123"
  }'
```

Resposta:
```json
{
  "token": "Bearer eyJhbGciOiJIUzI1NiJ9..."
}
```

### Criar tópico (autenticado)

```bash
curl -X POST http://localhost:8080/topicos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-token>" \
  -d '{
    "titulo": "Dúvida sobre Kotlin",
    "mensagem": "Como declarar variáveis em Kotlin?",
    "idCurso": 1,
    "idAutor": 1
  }'
```

### Listar tópicos

```bash
curl -X GET "http://localhost:8080/topicos?page=0&size=10" \
  -H "Authorization: Bearer <seu-token>"
```

### Filtrar por curso

```bash
curl -X GET "http://localhost:8080/topicos?nomeCurso=Kotlin" \
  -H "Authorization: Bearer <seu-token>"
```

## Banco de Dados

O projeto utiliza H2 (banco em memória) com as seguintes tabelas:

- **curso** - Cursos disponíveis (Kotlin, Python, Excel)
- **usuario** - Usuários do sistema
- **topico** - Tópicos do fórum
- **respostas** - Respostas dos tópicos
- **role** - Perfis de acesso
- **usuario_role** - Relacionamento usuário-perfil

As migrações são gerenciadas pelo Flyway e executadas automaticamente na inicialização.