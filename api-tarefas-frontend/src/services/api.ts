import axios from 'axios';

// Verifique se a porta é 8080 (Java) e não 3000 (React)
const api = axios.create({
    baseURL: 'http://localhost:8080/api/tarefas'
});

export const tarefaService = {
    listarTodas: () => api.get('/'),
    // ... outras funções
};