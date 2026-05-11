# Threads — Documento Técnico

Este documento descreve a arquitetura de concorrência utilizada no projeto `Exemplo_Threads`, demonstrando como a interface gráfica (Swing) é separada da lógica de processamento.

## Visão Geral do Fluxo

No projeto `Exemplo_Threads`, existem duas linhas de execução principais:

1. A thread da interface gráfica (Event Dispatch Thread - EDT do Swing), responsável por exibir a janela e tratar o clique no botão "Somar".
2. A thread de cálculo (daemon), responsável por ler valores compartilhados e atualizar continuamente o resultado.

A proposta didática é evidenciar que a interface pode permanecer responsiva enquanto outra thread executa processamento em segundo plano.

## Criação e Uso das Threads

### 1) Classe Principal

Arquivo: `Exemplo_Threads/src/main/java/com/mycompany/exemplo_threads/Exemplo_Threads.java`

No método `main`:

- Cria-se uma instância de `Thread` com `new Thread(new CalculaSoma())`.
- Marca-se a thread como daemon por meio de `setDaemon(true)`.
- Inicia-se a thread com `start()`.
- Exibe-se a janela `frmPrincipal`.

**Observações sobre threads daemon:**

- Uma thread daemon não impede o encerramento da JVM.
- Quando a thread principal termina e não há threads não-daemon ativas, a aplicação encerra.
- Neste projeto, a thread de cálculo atua como suporte em segundo plano.

### 2) Thread de Cálculo

Arquivo: `Exemplo_Threads/src/main/java/com/mycompany/exemplo_threads/modelo/CalculaSoma.java`

A classe `CalculaSoma` implementa `Runnable` e define o método `run()`.

No método `run()` há um laço infinito (`while (true)`) que:

1. Verifica se `n1` e `n2` foram preenchidos.
2. Converte os valores para `double` (aceitando vírgulas e convertendo-as para ponto decimal).
3. Soma os valores.
4. Grava o resultado em uma variável compartilhada.
5. Em caso de erro de formato, registra uma mensagem de erro.

Portanto, essa thread permanece continuamente disponível para processar novos valores.

### 3) Thread da Interface

Arquivo: `Exemplo_Threads/src/main/java/com/mycompany/exemplo_threads/apresentacao/frmPrincipal.java`

No evento do botão "Somar":

1. O texto dos campos é gravado em variáveis compartilhadas (`n1` e `n2`).
2. A interface aguarda 50 ms com `LockSupport.parkNanos(50_000_000L)`.
3. O label de resposta é atualizado com o valor do resultado.

A espera curta existe para permitir que a thread de cálculo processe os novos dados antes da leitura pela interface.

## Comunicação entre Threads

Arquivo: `Exemplo_Threads/src/main/java/com/mycompany/exemplo_threads/modelo/Estaticos.java`

A comunicação é realizada por três campos `static volatile`:

- `n1`
- `n2`
- `resultado`

### Por que `static`?

- Permite acesso global sem necessidade de instância de objeto.
- Facilita a demonstração didática de compartilhamento de estado entre classes e threads.

### Por que `volatile`?

- Garante visibilidade entre threads.
- Quando uma thread escreve um valor, outra thread passa a enxergar o valor atualizado, sem utilizar cache local desatualizado.

**Observação importante:**

- `volatile` não torna operações compostas atômicas.
- Neste exemplo, a simplicidade do fluxo reduz a probabilidade de inconsistências graves, mas em cenários reais normalmente utiliza-se sincronização mais robusta.

## Sequência Completa de Execução

1. A aplicação inicia e cria a thread de cálculo (daemon).
2. A janela Swing é exibida para o usuário.
3. O usuário digita os dois números e clica em "Somar".
4. A EDT grava os valores em `n1` e `n2`.
5. A thread `CalculaSoma` detecta os valores, calcula e grava o resultado.
6. A EDT aguarda alguns nanossegundos e atualiza o label com o resultado.

## Aspectos Didáticos Relevantes

- Demonstra separação entre interface e processamento concorrente.
- Introduz os conceitos de `Runnable`, `Thread`, daemon e variáveis compartilhadas.
- Apresenta o uso de `volatile` para visibilidade de memória entre threads.

## Limitações e Melhorias Recomendadas

Embora o projeto seja adequado como exemplo didático, há aspectos que podem ser aprimorados para utilização em produção:

1. Laço de espera ativa (`busy-wait`):
   - O `while (true)` sem pausa consome CPU continuamente.
   - Melhorias possíveis: `Thread.sleep()`, `wait/notify`, `BlockingQueue` ou `ExecutorService`.

2. Sincronização baseada em tempo fixo:
   - A espera de 50 ms na interface não garante resultados em máquinas lentas ou sob carga.
   - Melhorias possíveis: `Future`, callback na EDT (`SwingUtilities.invokeLater`), `CountDownLatch` ou estruturas de fila.

3. Estado global compartilhado:
   - Campos `static` facilitam a implementação, mas aumentam o acoplamento entre classes.
   - Melhorias possíveis: encapsular o estado em um objeto dedicado com mecanismos de sincronização claros.

4. Ausência de encerramento controlado:
   - Como a thread é daemon e o laço é infinito, não há protocolo de parada.
   - Em sistemas reais, recomenda-se usar uma flag de encerramento e fechar a thread de forma controlada.

## Conclusão

O projeto `Exemplo_Threads` cumpre seu propósito didático de introduzir concorrência em Java com um exemplo simples de soma. Ele evidencia conceitos fundamentais (`Runnable`, thread daemon, `volatile` e compartilhamento de dados) e proporciona base para discutir práticas mais robustas de sincronização e coordenação entre threads em aplicações reais.

