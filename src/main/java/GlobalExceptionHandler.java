package com.tasks.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==========================================
    // 404 - Recurso não encontrado
    // ==========================================
    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<ApiError> handleNotFound(TarefaNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        HttpStatus.NOT_FOUND.value(),
                        "Recurso não encontrado",
                        ex.getMessage()
                ));
    }

    // ==========================================
    // 400 - Validação de campos
    // ==========================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            erros.put(campo, mensagem);
        });

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                "Um ou mais campos são inválidos"
        );
        apiError.setErrosCampos(erros);
        return ResponseEntity.badRequest().body(apiError);
    }

    // ==========================================
    // 400 - Argumentos ilegais
    // ==========================================
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiError(
                        HttpStatus.BAD_REQUEST.value(),
                        "Requisição inválida",
                        ex.getMessage()
                ));
    }

    // ==========================================
    // 500 - Erro interno genérico
    // ==========================================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Erro interno do servidor",
                        ex.getMessage()
                ));
    }

    // ==========================================
    // Classe de resposta de erro
    // ==========================================
    @Data
    @AllArgsConstructor
    public static class ApiError {
        private int status;
        private String erro;
        private String mensagem;
        private LocalDateTime timestamp = LocalDateTime.now();
        private Map<String, String> errosCampos;

        public ApiError(int status, String erro, String mensagem) {
            this.status = status;
            this.erro = erro;
            this.mensagem = mensagem;
            this.timestamp = LocalDateTime.now();
        }
    }
}
