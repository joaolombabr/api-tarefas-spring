package com.exemplo.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TarefaService {

    private final TarefaRepository repository;

    // ==========================================
    // CREATE
    // ==========================================
    public TarefaDTO.Resposta criar(TarefaDTO.CriarRequest request) {
        log.debug("Criando nova tarefa: {}", request.getTitulo());

        Tarefa tarefa = Tarefa.builder()
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .status(Tarefa.Status.PENDENTE)
                .prioridade(request.getPrioridade() != null ? request.getPrioridade() : Tarefa.Prioridade.MEDIA)
                .build();

        Tarefa salva = repository.save(tarefa);
        log.info("Tarefa criada com ID: {}", salva.getId());
        return TarefaDTO.Resposta.fromEntity(salva);
    }

    // ==========================================
    // READ - listar todas
    // ==========================================
    @Transactional(readOnly = true)
    public List<TarefaDTO.Resposta> listarTodas() {
        log.debug("Listando todas as tarefas");
        return repository.findAll()
                .stream()
                .map(TarefaDTO.Resposta::fromEntity)
                .collect(Collectors.toList());
    }

    // ==========================================
    // READ - buscar por ID
    // ==========================================
    @Transactional(readOnly = true)
    public TarefaDTO.Resposta buscarPorId(Long id) {
        log.debug("Buscando tarefa com ID: {}", id);
        Tarefa tarefa = buscarEntidadePorId(id);
        return TarefaDTO.Resposta.fromEntity(tarefa);
    }

    // ==========================================
    // READ - filtros
    // ==========================================
    @Transactional(readOnly = true)
    public List<TarefaDTO.Resposta> buscarComFiltros(
            Tarefa.Status status,
            Tarefa.Prioridade prioridade,
            String titulo) {

        log.debug("Buscando tarefas com filtros: status={}, prioridade={}, titulo={}", status, prioridade, titulo);
        return repository.buscarComFiltros(status, prioridade, titulo)
                .stream()
                .map(TarefaDTO.Resposta::fromEntity)
                .collect(Collectors.toList());
    }

    // ==========================================
    // UPDATE - atualização completa (PUT)
    // ==========================================
    public TarefaDTO.Resposta atualizar(Long id, TarefaDTO.AtualizarRequest request) {
        log.debug("Atualizando tarefa ID: {}", id);

        Tarefa tarefa = buscarEntidadePorId(id);
        tarefa.setTitulo(request.getTitulo());
        tarefa.setDescricao(request.getDescricao());

        if (request.getStatus() != null) tarefa.setStatus(request.getStatus());
        if (request.getPrioridade() != null) tarefa.setPrioridade(request.getPrioridade());

        Tarefa atualizada = repository.save(tarefa);
        log.info("Tarefa ID {} atualizada com sucesso", id);
        return TarefaDTO.Resposta.fromEntity(atualizada);
    }

    // ==========================================
    // UPDATE - atualizar apenas status (PATCH)
    // ==========================================
    public TarefaDTO.Resposta atualizarStatus(Long id, TarefaDTO.AtualizarStatusRequest request) {
        log.debug("Atualizando status da tarefa ID: {} para {}", id, request.getStatus());

        Tarefa tarefa = buscarEntidadePorId(id);
        tarefa.setStatus(request.getStatus());

        Tarefa atualizada = repository.save(tarefa);
        log.info("Status da tarefa ID {} atualizado para {}", id, request.getStatus());
        return TarefaDTO.Resposta.fromEntity(atualizada);
    }

    // ==========================================
    // DELETE
    // ==========================================
    public void deletar(Long id) {
        log.debug("Deletando tarefa ID: {}", id);

        Tarefa tarefa = buscarEntidadePorId(id);
        repository.delete(tarefa);
        log.info("Tarefa ID {} deletada com sucesso", id);
    }

    // ==========================================
    // Estatísticas
    // ==========================================
    @Transactional(readOnly = true)
    public TarefaDTO.Estatisticas obterEstatisticas() {
        TarefaDTO.Estatisticas stats = new TarefaDTO.Estatisticas();
        stats.setTotal(repository.count());
        stats.setPendentes(repository.countByStatus(Tarefa.Status.PENDENTE));
        stats.setEmAndamento(repository.countByStatus(Tarefa.Status.EM_ANDAMENTO));
        stats.setConcluidas(repository.countByStatus(Tarefa.Status.CONCLUIDA));
        stats.setCanceladas(repository.countByStatus(Tarefa.Status.CANCELADA));
        return stats;
    }

    // ==========================================
    // Helper privado
    // ==========================================
    private Tarefa buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException("Tarefa não encontrada com ID: " + id));
    }
}