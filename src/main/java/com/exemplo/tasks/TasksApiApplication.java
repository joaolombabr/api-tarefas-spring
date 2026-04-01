package com.exemplo.tasks; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TasksApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasksApiApplication.class, args);
        System.out.println("""
                
                ╔══════════════════════════════════════════╗
                ║         API de Tarefas Iniciada!         ║
                ╠══════════════════════════════════════════╣
                ║  Endpoints:                              ║
                ║  http://localhost:8080/api/tarefas       ║
                ║                                          ║
                ║  H2 Console:                             ║
                ║  http://localhost:8080/h2-console        ║
                ║  JDBC URL: jdbc:h2:mem:tasksdb           ║
                ╚══════════════════════════════════════════╝
                """);
    }
}
