# 📋 API REST de Tarefas — Spring Boot + H2

API CRUD completa desenvolvida com **Spring Boot 3.2**, banco de dados **H2 em memória** e boas práticas REST.

---

## 🚀 Como Executar

```bash
# 1. Acesse o diretório do projeto
cd tasks-api

# 2. Execute com Maven
./mvnw spring-boot:run

# Ou com Maven instalado:
mvn spring-boot:run
```

A API sobe em: **http://localhost:8080**

---

## 🗂️ Estrutura do Projeto

```
tasks-api/
├── src/main/java/com/tasks/
│   ├── TasksApiApplication.java       ← Ponto de entrada
│   ├── model/
│   │   ├── Tarefa.java                ← Entidade JPA
│   │   └── TarefaDTO.java             ← DTOs (Request/Response)
│   ├── repository/
│   │   └── TarefaRepository.java      ← Repositório JPA
│   ├── service/
│   │   └── TarefaService.java         ← Regras de negócio
│   ├── controller/
│   │   └── TarefaController.java      ← Endpoints REST
│   └── exception/
│       ├── TarefaNaoEncontradaException.java
│       └── GlobalExceptionHandler.java
├── src/main/resources/
│   ├── application.properties          ← Configurações
│   └── data.sql                        ← Dados iniciais
└── pom.xml
```

---

## 📡 Endpoints da API

### 🟢 GET — Listar todas as tarefas
```http
GET /api/tarefas
```
**Query params opcionais:**
- `status` → `PENDENTE | EM_ANDAMENTO | CONCLUIDA | CANCELADA`
- `prioridade` → `BAIXA | MEDIA | ALTA | URGENTE`
- `titulo` → busca parcial (case-insensitive)

```http
GET /api/tarefas?status=PENDENTE&prioridade=ALTA
```

---

### 🟢 GET — Buscar tarefa por ID
```http
GET /api/tarefas/{id}
```
**Exemplo:**
```http
GET /api/tarefas/1
```

---

### 🟣 POST — Criar nova tarefa
```http
POST /api/tarefas
Content-Type: application/json

{
  "titulo": "Implementar testes",
  "descricao": "Cobertura de 80% com JUnit 5",
  "prioridade": "ALTA"
}
```
**Retorna:** `201 Created` com a tarefa criada

---

### 🟡 PUT — Atualizar tarefa completa
```http
PUT /api/tarefas/{id}
Content-Type: application/json

{
  "titulo": "Título atualizado",
  "descricao": "Nova descrição",
  "status": "EM_ANDAMENTO",
  "prioridade": "URGENTE"
}
```

---

### 🔵 PATCH — Atualizar apenas o status
```http
PATCH /api/tarefas/{id}/status
Content-Type: application/json

{
  "status": "CONCLUIDA"
}
```

---

### 🔴 DELETE — Remover tarefa
```http
DELETE /api/tarefas/{id}
```
**Retorna:** `204 No Content`

---

### 🟢 GET — Estatísticas
```http
GET /api/tarefas/estatisticas
```
**Resposta:**
```json
{
  "total": 5,
  "pendentes": 3,
  "emAndamento": 1,
  "concluidas": 1,
  "canceladas": 0
}
```

---

## 📊 Modelo de Dados

| Campo        | Tipo       | Descrição                              |
|--------------|------------|----------------------------------------|
| id           | Long       | ID auto-gerado                         |
| titulo       | String     | Obrigatório, 3–100 chars               |
| descricao    | String     | Opcional, até 500 chars                |
| status       | Enum       | PENDENTE / EM_ANDAMENTO / CONCLUIDA / CANCELADA |
| prioridade   | Enum       | BAIXA / MEDIA / ALTA / URGENTE         |
| criadoEm     | DateTime   | Preenchido automaticamente             |
| atualizadoEm | DateTime   | Atualizado automaticamente             |

---

## 🗄️ Console H2

Acesse o banco de dados em memória:

- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** `jdbc:h2:mem:tasksdb`
- **Username:** `sa`
- **Password:** *(vazio)*

---

## 🧪 Testando com Postman / Insomnia

### Collection Postman (importe manualmente):
1. Abra o Postman
2. Crie uma nova Collection "Tasks API"
3. Adicione os requests conforme os endpoints acima
4. Defina a variável `{{base_url}}` = `http://localhost:8080`

### Exemplo cURL:

```bash
# Criar tarefa
curl -X POST http://localhost:8080/api/tarefas \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Testar API","prioridade":"ALTA"}'

# Listar todas
curl http://localhost:8080/api/tarefas

# Buscar por ID
curl http://localhost:8080/api/tarefas/1

# Atualizar status
curl -X PATCH http://localhost:8080/api/tarefas/1/status \
  -H "Content-Type: application/json" \
  -d '{"status":"CONCLUIDA"}'

# Deletar
curl -X DELETE http://localhost:8080/api/tarefas/1
```

---

## ⚠️ Respostas de Erro

```json
{
  "status": 404,
  "erro": "Recurso não encontrado",
  "mensagem": "Tarefa não encontrada com ID: 99",
  "timestamp": "2024-03-15T10:30:00"
}
```

```json
{
  "status": 400,
  "erro": "Erro de validação",
  "mensagem": "Um ou mais campos são inválidos",
  "errosCampos": {
    "titulo": "Título deve ter entre 3 e 100 caracteres"
  }
}
```

---

## 🛠️ Tecnologias

| Tecnologia | Versão |
|------------|--------|
| Java | 17 |
| Spring Boot | 3.2.3 |
| Spring Data JPA | — |
| H2 Database | — |
| Lombok | — |
| Bean Validation | — |
