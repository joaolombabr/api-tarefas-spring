package com.exemplo.tasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

public class TarefaDTO {

    /**
     * DTO para criação de uma nova tarefa (POST)
     */
    @Data
    public static class CriarRequest {

        @NotBlank(message = "Título é obrigatório")
        @Size(min = 3, max = 100, message = "Título deve ter entre 3 e 100 caracteres")
        private String titulo;

        @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
        private String descricao;

        private Tarefa.Prioridade prioridade = Tarefa.Prioridade.MEDIA;
    }

    /**
     * DTO para atualização completa de uma tarefa (PUT)
     */
    @Data
    public static class AtualizarRequest {

        @NotBlank(message = "Título é obrigatório")
        @Size(min = 3, max = 100, message = "Título deve ter entre 3 e 100 caracteres")
        private String titulo;

        @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
        private String descricao;

        private Tarefa.Status status;
        private Tarefa.Prioridade prioridade;
    }

    /**
     * DTO para atualização parcial de status (PATCH)
     */
    @Data
    public static class AtualizarStatusRequest {
        private Tarefa.Status status;
    }

    /**
     * DTO de resposta (retornado ao cliente)
     */
    @Data
    public static class Resposta {
        private Long id;
        private String titulo;
        private String descricao;
        private Tarefa.Status status;
        private Tarefa.Prioridade prioridade;
        private LocalDateTime criadoEm;
        private LocalDateTime atualizadoEm;

        public static Resposta fromEntity(Tarefa tarefa) {
            Resposta dto = new Resposta();
            dto.setId(tarefa.getId());
            dto.setTitulo(tarefa.getTitulo());
            dto.setDescricao(tarefa.getDescricao());
            dto.setStatus(tarefa.getStatus());
            dto.setPrioridade(tarefa.getPrioridade());
            dto.setCriadoEm(tarefa.getCriadoEm());
            dto.setAtualizadoEm(tarefa.getAtualizadoEm());
            return dto;
        }
    }

    /**
     * Resumo estatístico
     */
    @Data
    public static class Estatisticas {
        private long total;
        private long pendentes;
        private long emAndamento;
        private long concluidas;
        private long canceladas;
    }
}
