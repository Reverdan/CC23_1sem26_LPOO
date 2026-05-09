# Média — Projeto Media

O projeto Media calcula a média de notas de um aluno, considerando NP1, NP2 e exame, e determina sua situação (Aprovado, Exame ou Reprovado). Ele utiliza técnicas intermediárias e avançadas de orientação a objetos (O.O.) e padrões de projeto para garantir organização, reuso, extensibilidade e robustez.

---

## Estrutura do Projeto

```
com.mycompany.media
├── Media.java                  ← ponto de entrada
├── apresentacao
│   └── frmPrincipal.java       ← interface gráfica
└── modelo
    ├── AbsPropriedades.java    ← classe abstrata (superclasse)
    ├── Controle.java           ← orquestrador (herda AbsPropriedades)
    ├── Validacao.java          ← validação (herda AbsPropriedades)
    ├── Media.java              ← cálculo da média (herda AbsPropriedades)
    └── IntMetodos.java         ← interface para método Executar()
```

---

## Temas de Orientação a Objetos (O.O.)

### 1. Interface como Contrato (Programação para Interface)
A interface `IntMetodos` define o método obrigatório `Executar()`. Isso permite:
- **Polimorfismo**: diferentes classes podem ser tratadas de forma uniforme, facilitando a troca de implementações.
- **Baixo acoplamento**: o código depende do contrato, não da implementação concreta, facilitando manutenção e testes.

### 2. Classe Abstrata e Herança
A classe abstrata `AbsPropriedades` centraliza atributos e construtores comuns, além de forçar a implementação do método `Executar()` nas subclasses. Benefícios:
- **Reuso de código**: evita duplicidade de atributos e lógica básica.
- **Padronização**: todas as subclasses seguem a mesma estrutura e contrato.
- **Facilidade de manutenção**: alterações em atributos comuns são feitas em um só lugar.

### 3. Polimorfismo via Sobrescrita
As classes `Controle`, `Validacao` e `Media` sobrescrevem o método `Executar()`, cada uma com sua lógica específica:
- `Validacao` converte e valida as notas.
- `Media` calcula a média e define a situação.
- `Controle` orquestra o fluxo, decidindo se pode calcular ou se há erro.

O polimorfismo permite que o método correto seja chamado conforme o tipo do objeto, mesmo quando referenciado de forma genérica.

### 4. Encapsulamento e Estado Válido
- Os atributos são protegidos (`protected`), garantindo acesso controlado.
- O método `Executar()` é chamado automaticamente nos construtores, garantindo que o objeto sempre esteja em estado válido logo após ser criado.
- O acesso aos resultados é feito por meio de métodos de acesso (`getMensagem()`, `toString()`), promovendo encapsulamento.

### 5. Responsabilidade Única e Separação de Papéis
Cada classe tem uma função clara e única:
- `Validacao`: valida as notas e converte para o tipo correto.
- `Media`: realiza o cálculo da média e determina a situação do aluno.
- `Controle`: coordena o fluxo, decide se pode calcular ou se há erro.

Essa separação facilita testes, manutenção e evolução do sistema, seguindo o princípio SOLID da Responsabilidade Única.

### 6. Extensibilidade e Reuso (Open/Closed)
- Novas regras de validação ou cálculo podem ser adicionadas facilmente, bastando criar novas subclasses de `AbsPropriedades`.
- O uso de interface e classe abstrata permite que o sistema cresça sem modificar código já existente, seguindo o princípio Open/Closed.

### 7. Sobrecarga de Construtores
- `AbsPropriedades` possui construtores sobrecarregados para aceitar tanto strings (entrada do usuário) quanto doubles (dados já validados), facilitando integração entre etapas.

---

## Polimorfismo de Objeto no Projeto

Um exemplo importante de polimorfismo de objeto ocorre quando se declara uma variável do tipo da superclasse abstrata e instancia uma subclasse concreta, como:

```java
AbsPropriedades validacao = new Validacao(np1, np2, exame);
```

Neste caso:
- O tipo da variável é `AbsPropriedades`, mas o objeto criado é da subclasse `Validacao`.
- Isso permite tratar diferentes subclasses de forma uniforme, usando o tipo mais genérico.
- O método sobrescrito (`Executar()`) chamado será sempre o da subclasse (`Validacao`), mesmo que a referência seja do tipo da superclasse.

Esse é o chamado **polimorfismo de objeto** (ou polimorfismo de inclusão), fundamental para flexibilidade, extensibilidade e reuso em orientação a objetos. Ele permite que o sistema seja facilmente expandido com novas regras ou comportamentos, sem alterar o código que utiliza a superclasse.

---

## Relação com Padrões de Projeto

O uso de polimorfismo de objeto, como em:

```java
AbsPropriedades validacao = new Validacao(np1, np2, exame);
```

está diretamente relacionado ao padrão de projeto **Factory Method** e ao princípio de **Programação para Interface/Superclasse**:

- **Factory Method**: Embora o projeto não implemente explicitamente uma fábrica, a ideia de criar objetos de subclasses e manipulá-los por referências da superclasse é a base desse padrão. O Factory Method permite que subclasses decidam qual classe instanciar, promovendo flexibilidade e desacoplamento.
- **Programação para Interface/Superclasse**: Esse princípio, fundamental em O.O., é amplamente utilizado em padrões de projeto, pois permite que o código cliente trabalhe com abstrações, facilitando a extensão e manutenção.

Portanto, além dos padrões já citados, o projeto também se beneficia dos conceitos e fundamentos do Factory Method e da programação orientada a abstrações, tornando o sistema mais flexível e preparado para mudanças.

---

## Padrões de Projeto Utilizados

### 1. Facade
A classe `Controle` atua como fachada (Facade), simplificando o uso do sistema para a interface gráfica. Ela esconde a complexidade das etapas de validação e cálculo, expondo apenas métodos simples para o usuário da interface.

**Benefícios:**
- Reduz o acoplamento entre a interface e as regras de negócio.
- Facilita a manutenção e evolução do sistema.

### 2. MVC (Model-View-Controller)
O projeto segue o padrão MVC:
- **Model**: pacote `modelo` (lógica de negócio, validação, cálculo).
- **View**: pacote `apresentacao` (interface gráfica Swing).
- **Controller**: classe `Controle` (coordena a comunicação entre view e model).

**Benefícios:**
- Separação de responsabilidades.
- Facilidade para modificar a interface sem alterar a lógica de negócio.
- Testabilidade e manutenção.

### 3. Template Method
A classe abstrata `AbsPropriedades` define o esqueleto do algoritmo (construtor + chamada de `Executar()`), enquanto as subclasses implementam os detalhes do método `Executar()`. Isso caracteriza o padrão Template Method.

**Benefícios:**
- Permite definir o fluxo principal em um lugar e delegar detalhes para subclasses.
- Facilita a extensão do comportamento sem alterar a estrutura geral.

---

## Fluxo de Execução Detalhado
1. O usuário informa as notas na interface gráfica.
2. A interface cria um objeto `Controle`, que inicia a validação das notas.
3. Se as notas forem válidas, um objeto `Media` é criado para calcular a média e situação.
4. O resultado (média e situação) é retornado para a interface e exibido ao usuário.
5. Se houver erro de validação, a mensagem de erro é exibida.

---

## Princípios SOLID Aplicados
- **S** (Single Responsibility): cada classe tem uma responsabilidade única.
- **O** (Open/Closed): o sistema pode ser estendido sem modificar código existente.
- **L** (Liskov Substitution): subclasses podem ser usadas no lugar da superclasse.
- **I** (Interface Segregation): a interface é pequena e específica.
- **D** (Dependency Inversion): a interface é usada como contrato, não implementações concretas.

---

## Resumo
O projeto Media demonstra uso avançado de interface, classe abstrata, polimorfismo, encapsulamento, separação de responsabilidades, sobrecarga de construtores, princípios SOLID e aplicação dos padrões Facade, MVC e Template Method. Isso torna o código robusto, flexível, extensível e preparado para evoluções futuras, servindo como referência para projetos orientados a objetos profissionais.