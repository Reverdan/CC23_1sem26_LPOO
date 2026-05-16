# Integração de Funcionalidades — Projeto Multiplos

O projeto Multiplos representa uma evolução arquitetural significativa no aprendizado de Programação Orientada a Objetos (POO), consolidando diversas funcionalidades matemáticas (Verificação de Números Primos, Cálculo de Fatorial e Classificação de Triângulos) em uma única aplicação modular. Este projeto foca primordialmente na **navegação entre múltiplos formulários** e na implementação de um **sistema de menus**, permitindo uma experiência de usuário (UX) mais complexa e organizada.

A arquitetura mantém o rigor técnico dos projetos anteriores ([MEDIA](MEDIA.md), [PRIMO](PRIMO.md), [TRIANGULOS](TRIANGULOS.md)), utilizando os padrões **MVC (Model-View-Controller)** e **Facade**, além de aplicar intensivamente **Polimorfismo**, **Abstração** e **Encapsulamento**.

---

## Estrutura do Projeto e Organização de Pacotes

A organização segue a separação rigorosa de responsabilidades, garantindo que a lógica de negócio seja independente da interface de apresentação:

```
com.mycompany.multiplos
├── Multiplos.java                  ← Ponto de entrada (Main)
├── apresentacao
│   ├── frmPrincipal.java           ← Formulário mestre com sistema de menus
│   ├── frmPrimo.java               ← Interface para verificação de primos
│   ├── frmFatorial.java            ← Interface para cálculo de fatorial
│   └── frmTriangulos.java          ← Interface para classificação de triângulos
└── modelo
    ├── AbsPropriedades.java        ← Superclasse abstrata (Contrato e Propriedades)
    ├── IntMetodos.java             ← Interface definindo o contrato de execução
    ├── Controle.java               ← Orquestrador (Facade)
    ├── Validacao.java              ← Lógica de validação de entradas
    ├── Primo.java                  ← Algoritmo de verificação de número primo
    ├── Fatorial.java               ← Algoritmo de cálculo de fatorial
    └── Triangulos                  ← Pacote/Lógica de classificação de triângulos
```

---

## Múltiplos Formulários e Navegação

Um dos pilares deste projeto é a capacidade de gerenciar múltiplos contêineres de interface (`JDialog`), permitindo que a aplicação cresça em funcionalidades sem sobrecarregar uma única janela.

### 1. Sistema de Menus (`JMenuBar`)
A navegação centralizada é realizada através de um componente `JMenuBar` no formulário principal (`frmPrincipal`). Esta barra contém menus (`JMenu`) e itens de menu (`JMenuItem`) que disparam os eventos de transição.

*   **Implementação:** O uso de ouvintes de eventos (`ActionListener`) nos itens de menu permite instanciar as janelas filhas de forma sob demanda.
*   **Hierarquia Visual:** O `frmPrincipal` atua como a janela mestra, coordenando a exibição das subjanelas.

### 2. Paradigmas de Exibição: Formulários Modais vs. Não-Modais

A interação entre janelas em uma aplicação desktop é governada pelo conceito de **modalidade**. No contexto da biblioteca Swing do Java, a classe `JDialog` é a ferramenta primordial para gerenciar esses estados.

#### A. Formulários Modais (`Modal`)
Um formulário modal interrompe o fluxo de interação do usuário com as demais janelas da aplicação. Quando uma instância de `JDialog` é configurada como modal (`modal = true`), a janela pai (ou chamadora) permanece bloqueada até que o diálogo seja encerrado.

*   **Comportamento do Fluxo:** O método `setVisible(true)` em um diálogo modal é uma chamada bloqueante. O código subsequente na classe chamadora não será executado até que a janela modal seja fechada.
*   **Aplicação:** Ideal para fluxos que exigem uma resposta ou ação obrigatória do usuário antes de prosseguir (ex: validações, configurações críticas ou entrada de dados necessária para o próximo passo).
*   **Implementação em Java:**
    ```java
    // O segundo parâmetro 'true' define a modalidade
    frmPrimo frmP = new frmPrimo(null, true); 
    frmP.setVisible(true); // O fluxo para aqui até frmP fechar
    System.out.println("Janela fechada, continuando..."); 
    ```

#### B. Formulários Não-Modais (`Modeless`)
Janelas não-modais permitem que o usuário interaja simultaneamente com o formulário recém-aberto e com a janela pai. 

*   **Comportamento do Fluxo:** O método `setVisible(true)` não bloqueia a execução. O programa continua a processar as linhas seguintes imediatamente após a exibição da janela.
*   **Aplicação:** Útil para ferramentas auxiliares, paletas de cores ou janelas de log que podem permanecer abertas enquanto o usuário trabalha na janela principal.
*   **Implementação em Java:**
    ```java
    // O segundo parâmetro 'false' define como não-modal
    frmPrimo frmP = new frmPrimo(null, false); 
    frmP.setVisible(true); // O fluxo continua imediatamente
    System.out.println("Janela aberta, processamento paralelo..."); 
    ```

#### C. Lógica de Transição no Projeto Multiplos
No projeto Multiplos, optou-se predominantemente pelo uso de **diálogos modais** para garantir que o processamento matemático ocorra de forma atômica e controlada:

```java
private void mniPrimoActionPerformed(java.awt.event.ActionEvent evt) {
    // Instanciação como modal para controle de fluxo rigoroso
    frmPrimo frmP = new frmPrimo(null, true); 
    
    this.setVisible(false); // Oculta a principal para focar no cálculo
    frmP.setVisible(true);  // Exibe a tela (chamada bloqueante)
    this.setVisible(true);  // Restaura a principal somente após o fechamento da filha
}
```

---

## Camada de Modelo: Abstração e Polimorfismo

A lógica de negócio é sustentada por uma estrutura de herança robusta que demonstra o poder da programação orientada a abstrações.

### 1. Superclasse Abstrata: `AbsPropriedades`
A classe `AbsPropriedades` atua como a espinha dorsal do modelo. Ela:
*   Define os atributos comuns (números, strings de mensagem e resposta).
*   Implementa a interface `IntMetodos`, forçando todas as subclasses a possuírem o método `Executar()`.
*   Utiliza **Sobrecarga de Construtores** para aceitar diferentes tipos de entrada (Strings para validação inicial, Inteiros ou Doubles para cálculos diretos).

### 2. Interface `IntMetodos`
Define o contrato comportamental da aplicação. Ao garantir que toda classe de lógica possua um método `Executar()`, permite que o sistema trate diferentes algoritmos de forma polimórfica.

### 3. Polimorfismo de Inclusão
A classe `Controle` utiliza o polimorfismo para gerenciar o fluxo de dados. Ela pode referenciar diferentes objetos de cálculo através da superclasse `AbsPropriedades`, permitindo um código genérico e extensível.

---

## Padrões de Projeto Aplicados

1.  **Facade (Fachada):** A classe `Controle` simplifica a interação entre a interface e o complexo sistema de validação e cálculo. A `View` não precisa conhecer a lógica interna; ela apenas solicita uma ação ao `Controle`.
2.  **Template Method:** Definido na superclasse abstrata, o fluxo de "inicialização -> atribuição -> execução" é padronizado, enquanto os detalhes do algoritmo são delegados às subclasses concretas.
3.  **MVC:** Separação clara entre Dados (`modelo`), Interface (`apresentacao`) e Lógica de Fluxo (`Controle`).

---

## Fluxo de Execução Detalhado

1.  **Inicialização:** O `Multiplos.java` (ponto de entrada) executa e instancia o `frmPrincipal`.
2.  **Interação do Usuário:** O usuário utiliza a `JMenuBar` para selecionar a funcionalidade desejada (ex: "Calculos" -> "Primo").
3.  **Gestão de Janelas:** O evento aciona a criação de uma instância da janela correspondente (ex: `frmPrimo`), configurada como modal.
4.  **Entrada de Dados:** O usuário insere os valores nos campos de texto da janela secundária e aciona o botão de cálculo.
5.  **Processamento:** 
    *   A interface envia os dados para a classe `Controle`.
    *   `Controle` instancia `Validacao` para verificar a integridade dos dados.
    *   Se válido, `Controle` instancia a classe de lógica específica (ex: `Primo`) e recupera o resultado.
6.  **Saída:** O resultado ou mensagens de erro são exibidos na interface via `JLabel` ou `JOptionPane`.
7.  **Finalização:** Ao fechar a janela modal, o controle retorna à janela principal (`frmPrincipal`).

---

## Conclusão Acadêmica

O projeto **Multiplos** transcende o simples cálculo matemático, servindo como um laboratório de engenharia de software para o estudo de sistemas complexos. A implementação de múltiplos formulários e menus introduz o aluno ao gerenciamento de estados de aplicação e hierarquias de interface, enquanto a estrutura do modelo reforça a importância de contratos (`interfaces`) e abstrações para a criação de sistemas escaláveis e de fácil manutenção.
