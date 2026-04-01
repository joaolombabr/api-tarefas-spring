import { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
    // Estado para armazenar a lista de tarefas
    const [tarefas, setTarefas] = useState<any[]>([]);

    useEffect(() => {
        // Faz a chamada para a sua API Spring Boot
        axios.get("http://localhost:8080/api/tarefas")
            .then((response: any) => {
                // Salva os dados recebidos no estado
                setTarefas(response.data);
            })
            .catch((error: any) => {
                console.error("Erro ao buscar tarefas:", error);
            });
    }, []);

    return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
            <h1>Minhas Tarefas (Backend Java)</h1>

            {tarefas.length === 0 ? (
                <p>Carregando tarefas ou nenhuma tarefa encontrada...</p>
            ) : (
                <ul style={{ listStyleType: 'none', padding: 0 }}>
                    {tarefas.map((tarefa: any) => (
                        <li
                            key={tarefa.id}
                            style={{
                                border: '1px solid #ccc',
                                marginBottom: '10px',
                                padding: '10px',
                                borderRadius: '8px',
                                backgroundColor: '#f9f9f9'
                            }}
                        >
                            <h3 style={{ margin: '0 0 5px 0' }}>{tarefa.titulo}</h3>
                            <p style={{ margin: '0', color: '#666' }}>{tarefa.descricao}</p>
                            <span style={{
                                fontSize: '0.8rem',
                                fontWeight: 'bold',
                                color: tarefa.status === 'CONCLUIDA' ? 'green' : 'orange'
                            }}>
                Status: {tarefa.status}
              </span>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default App;