package com.tasks.repository;

import com.tasks.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // Buscar por status
    List<Tarefa> findByStatus(Tarefa.Status status);

    // Buscar por prioridade
    List<Tarefa> findByPrioridade(Tarefa.Prioridade prioridade);

    // Buscar por status e prioridade
    List<Tarefa> findByStatusAndPrioridade(Tarefa.Status status, Tarefa.Prioridade prioridade);

    // Buscar por título (contendo texto, ignorando maiúsculas)
    List<Tarefa> findByTituloContainingIgnoreCase(String titulo);

    // Contar por status
    long countByStatus(Tarefa.Status status);

    // Busca customizada com JPQL
    @Query("SELECT t FROM Tarefa t WHERE " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:prioridade IS NULL OR t.prioridade = :prioridade) AND " +
           "(:titulo IS NULL OR LOWER(t.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')))")
    List<Tarefa> buscarComFiltros(
            @Param("status") Tarefa.Status status,
            @Param("prioridade") Tarefa.Prioridade prioridade,
            @Param("titulo") String titulo
    );
}
