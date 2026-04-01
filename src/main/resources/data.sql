-- Dados iniciais de exemplo
INSERT INTO tarefa (titulo, descricao, status, prioridade, criado_em, atualizado_em)
VALUES ('Configurar ambiente de desenvolvimento', 'Instalar JDK 17, Maven e IDE', 'CONCLUIDA', 'ALTA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO tarefa (titulo, descricao, status, prioridade, criado_em, atualizado_em)
VALUES ('Criar documentação da API', 'Documentar todos os endpoints REST', 'EM_ANDAMENTO', 'MEDIA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO tarefa (titulo, descricao, status, prioridade, criado_em, atualizado_em)
VALUES ('Implementar autenticação JWT', 'Adicionar segurança à API com tokens JWT', 'PENDENTE', 'ALTA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO tarefa (titulo, descricao, status, prioridade, criado_em, atualizado_em)
VALUES ('Escrever testes unitários', 'Cobertura mínima de 80% do código', 'PENDENTE', 'MEDIA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO tarefa (titulo, descricao, status, prioridade, criado_em, atualizado_em)
VALUES ('Deploy em produção', 'Subir aplicação no servidor cloud', 'PENDENTE', 'BAIXA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
