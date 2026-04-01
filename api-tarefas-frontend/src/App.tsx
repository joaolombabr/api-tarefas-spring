import React, { useEffect, useState } from 'react';
import { tarefaService } from './services/api';
import { Tarefa } from './types/Tarefa';

function App() {
  const [tarefas, setTarefas] = useState<Tarefa[]>([]);

  // Esta função busca os dados do Java assim que a tela abre
  const carregarTarefas = async () => {
    try {
      const resposta = await tarefaService.listarTodas();
      setTarefas(resposta.data);
    } catch (erro) {
      console.error("Erro ao buscar tarefas do Java:", erro);
    }
  };

    useEffect(() => {
        tarefaService.listarTodas()
            .then(resposta => {
                console.log("Dados que chegaram do Java:", resposta.data);
                setTarefas(resposta.data);
            })
            .catch(erro => {
                console.error("Erro na conexão com o Java:", erro);
                alert("O Java está ligado? Erro: " + erro.message);
            });
    }, []);
  return (
      <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
        <h1>Minhas Tarefas (Spring + React)</h1>
        <hr />
        <ul>
          {tarefas.map(tarefa => (
              <li key={tarefa.id} style={{ marginBottom: '10px' }}>
                <strong>{tarefa.titulo}</strong> - {tarefa.status}
                <p>{tarefa.descricao}</p>
              </li>
          ))}
        </ul>
      </div>
  );
}

export default App;