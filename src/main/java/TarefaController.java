package com.tasks.controller;

import com.tasks.model.Tarefa;
import com.tasks.model.TarefaDTO;
import com.tasks.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite requisições do frontend
public class TarefaController {

    private final TarefaService service;

    // ==========================================
    // POST /api/tarefas
    // Cria uma nova tarefa
    // ==========================================
    @PostMapping
    public ResponseEntity<TarefaDTO.Resposta> criar(
            @Valid @RequestBody TarefaDTO.CriarRequest request) {
        TarefaDTO.Resposta resposta = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    // ==========================================
    // GET /api/tarefas
    // Lista todas as tarefas (com filtros opcionais)
    // ==========================================
    @GetMapping
    public ResponseEntity<List<TarefaDTO.Resposta>> listar(
            @RequestParam(required = false) Tarefa.Status status,
            @RequestParam(required = false) Tarefa.Prioridade prioridade,
            @RequestParam(required = false) String titulo) {

        List<TarefaDTO.Resposta> tarefas;

        if (status != null || prioridade != null || titulo != null) {
            tarefas = service.buscarComFiltros(status, prioridade, titulo);
        } else {
            tarefas = service.listarTodas();
        }

        return ResponseEntity.ok(tarefas);
    }

    // ==========================================
    // GET /api/tarefas/{id}
    // Busca uma tarefa por ID
    // ==========================================
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO.Resposta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // ==========================================
    // PUT /api/tarefas/{id}
    // Atualiza uma tarefa completamente
    // ==========================================
    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO.Resposta> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TarefaDTO.AtualizarRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    // ==========================================
    // PATCH /api/tarefas/{id}/status
    // Atualiza apenas o status da tarefa
    // ==========================================
    @PatchMapping("/{id}/status")
    public ResponseEntity<TarefaDTO.Resposta> atualizarStatus(
            @PathVariable Long id,
            @RequestBody TarefaDTO.AtualizarStatusRequest request) {
        return ResponseEntity.ok(service.atualizarStatus(id, request));
    }

    // ==========================================
    // DELETE /api/tarefas/{id}
    // Remove uma tarefa
    // ==========================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // GET /api/tarefas/estatisticas
    // Retorna estatísticas das tarefas
    // ==========================================
    @GetMapping("/estatisticas")
    public ResponseEntity<TarefaDTO.Estatisticas> estatisticas() {
        return ResponseEntity.ok(service.obterEstatisticas());
    }
}
