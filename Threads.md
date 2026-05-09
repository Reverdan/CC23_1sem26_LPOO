# THREADS

Este documento explica como o projeto Exemplo_Threads usa threads para separar a interface grafica (Swing) da logica de processamento.

## Visao geral do fluxo

No projeto Exemplo_Threads existem duas linhas principais de execucao:

1. Thread da interface grafica (EDT do Swing), responsavel por exibir a janela e tratar clique no botao Somar.
2. Thread de calculo (daemon), responsavel por ler valores compartilhados e atualizar o resultado continuamente.

O objetivo didatico e mostrar que a interface pode continuar responsiva enquanto outra thread realiza processamento em paralelo.

## Onde as threads sao criadas e usadas

### 1) Classe principal

Arquivo: Exemplo_Threads/src/main/java/com/mycompany/exemplo_threads/Exemplo_Threads.java

No metodo main:

- E criada uma Thread com new Thread(new CalculaSoma()).
- A thread e marcada como daemon com setDaemon(true).
- A thread e iniciada com start().
- Depois disso, a janela frmPrincipal e exibida.

Importante sobre daemon:

- Uma thread daemon nao impede o encerramento da JVM.
- Quando a thread principal termina e nao existem threads nao-daemon ativas, a aplicacao pode encerrar.
- Nesse projeto, a thread de calculo serve como suporte em segundo plano.

### 2) Thread de calculo

Arquivo: Exemplo_Threads/src/main/java/com/mycompany/exemplo_threads/modelo/CalculaSoma.java

A classe CalculaSoma implementa Runnable e define o metodo run().

Dentro de run() existe um loop infinito (while (true)) que:

1. Verifica se n1 e n2 foram preenchidos.
2. Faz parse dos valores para double (aceitando virgula, convertendo para ponto).
3. Soma os valores.
4. Escreve o resultado em uma variavel compartilhada.
5. Em caso de erro de formato, grava mensagem de erro.

Resumo: essa thread fica sempre "escutando" novos valores para calcular.

### 3) Thread da interface (evento do botao)

Arquivo: Exemplo_Threads/src/main/java/com/mycompany/exemplo_threads/apresentacao/frmPrincipal.java

No evento do botao Somar:

1. O texto dos campos e gravado em variaveis compartilhadas (n1 e n2).
2. A interface espera por 50 ms com LockSupport.parkNanos(50_000_000L).
3. O label de resposta recebe o valor de resultado.

A espera curta existe para dar tempo da thread de calculo processar os novos dados antes da leitura pela interface.

## Comunicacao entre threads

Arquivo: Exemplo_Threads/src/main/java/com/mycompany/exemplo_threads/modelo/Estaticos.java

A comunicacao e feita por tres campos static volatile:

- n1
- n2
- resultado

### Por que static?

- Permite acesso global sem instancia de objeto.
- Facilita demonstracao didatica de compartilhamento de estado entre classes e threads.

### Por que volatile?

- Garante visibilidade entre threads.
- Quando uma thread escreve um valor, outra thread passa a enxergar o valor atualizado sem cache local desatualizado.

Observacao importante:

- volatile nao torna operacoes compostas atomicas.
- Neste exemplo, a simplicidade do fluxo reduz a chance de inconsistencias graves, mas em cenarios reais normalmente usamos sincronizacao mais robusta.

## Sequencia completa de execucao

1. A aplicacao inicia e cria a thread de calculo (daemon).
2. A janela Swing e exibida para o usuario.
3. O usuario digita os dois numeros e clica em Somar.
4. A EDT grava n1 e n2.
5. A thread CalculaSoma detecta os valores, calcula e grava resultado.
6. A EDT aguarda alguns nanossegundos e atualiza o label com resultado.

## Pontos positivos no contexto didatico

- Mostra separacao entre interface e processamento concorrente.
- Introduz Runnable, Thread, daemon e variaveis compartilhadas.
- Apresenta o uso de volatile para visibilidade de memoria.

## Limitacoes e melhorias recomendadas

Embora funcione para demonstracao, existem pontos a melhorar para producao:

1. Busy-wait no loop infinito
- O while (true) sem pausa consome CPU continuamente.
- Melhorias possiveis: Thread.sleep(), wait/notify, BlockingQueue, ou ExecutorService.

2. Sincronizacao baseada em tempo fixo
- Esperar 50 ms na interface nao garante resultado em maquinas lentas ou sob carga.
- Melhorias possiveis: Future, callback na EDT (SwingUtilities.invokeLater), CountDownLatch ou filas.

3. Estado global compartilhado
- Campos static facilitam, mas acoplam as classes.
- Melhorias possiveis: encapsular estado em objeto proprio, com mecanismos de sincronizacao claros.

4. Ausencia de encerramento controlado
- Como a thread e daemon e loop infinito, ela nao possui protocolo de parada.
- Em sistemas reais, vale usar flag de encerramento e fechamento gracioso.

## Conclusao

O Exemplo_Threads cumpre bem o papel de introduzir concorrencia em Java com um caso simples de soma. O projeto evidencia conceitos basicos (Runnable, thread daemon, volatile e compartilhamento de dados), ao mesmo tempo que abre espaco para discutir praticas mais robustas de sincronizacao e coordenacao entre threads em aplicacoes reais.
