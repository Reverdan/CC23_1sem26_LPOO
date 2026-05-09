# Aula 1 — Projeto aula1Visual

Este projeto implementa uma calculadora com interface gráfica em Java (Swing), demonstrando os conceitos de separação de responsabilidades, comunicação entre classes e organização em camadas.

---

## Estrutura de Pacotes

```
com.mycompany.aula1visual
├── Aula1Visual.java          ← ponto de entrada da aplicação
├── apresentacao
│   └── frmPrincipal.java     ← interface gráfica (front-end)
└── modelo
    ├── Controle.java         ← coordenador / orquestrador
    ├── Validacao.java        ← regras de validação dos dados
    └── Calculos.java         ← lógica de cálculo (back-end)
```

---

## Conceito de Separação entre Camadas

O projeto é dividido em dois pacotes principais:

| Pacote         | Responsabilidade                                                 |
|----------------|------------------------------------------------------------------|
| `apresentacao` | Exibir a interface ao usuário e capturar eventos (cliques, texto) |
| `modelo`       | Processar, validar e calcular os dados recebidos                 |

Essa separação garante que a interface gráfica **não sabe como calcular**, e as classes de lógica **não sabem como desenhar a tela**. Se você trocar a interface (ex.: virar um app web), o `modelo` não precisa mudar.

---

## Conceito de Responsabilidade Única

Cada classe tem **uma única razão para existir e mudar**:

- **`frmPrincipal`** — só cuida de mostrar e coletar dados na tela.
- **`Controle`** — só coordena o fluxo: chama validação e depois o cálculo.
- **`Validacao`** — só verifica se os dados de entrada são válidos.
- **`Calculos`** — só realiza a operação matemática.

Se a regra de divisão por zero mudar, você altera **apenas** `Validacao`. Se quiser adicionar uma nova operação matemática, altera **apenas** `Calculos`.

---

## Conceito de Front-end e Back-end

| Conceito    | Neste projeto                                                  |
|-------------|----------------------------------------------------------------|
| **Front-end** | Pacote `apresentacao` — janela, campos de texto, botões e labels |
| **Back-end**  | Pacote `modelo` — validação, controle e cálculo                  |

O front-end **não executa lógica de negócio**. Ele apenas coleta o que o usuário digitou, repassa para o back-end e exibe o resultado recebido.

---

## Código Limpo e Boas Práticas de Programação

Este projeto aplica diversas boas práticas que tornam o código mais legível, organizado e fácil de manter. Abaixo estão as práticas identificadas e onde elas aparecem.

### Nomenclatura Significativa

Os nomes das classes, variáveis e métodos descrevem claramente sua função:

| Elemento | O que representa |
|---|---|
| `Calculos` | Classe responsável pelos cálculos |
| `Validacao` | Classe responsável por validar os dados |
| `Controle` | Classe que controla o fluxo da operação |
| `txfPrimeiroNumero` | Campo de texto (`txf`) do primeiro número |
| `btnSomar` | Botão (`btn`) da operação de soma |
| `lblResultado` | Label (`lbl`) que exibe o resultado |
| `getMensagem()` | Retorna a mensagem de erro ou vazio |
| `getResultado()` | Retorna o resultado do cálculo |

O prefixo nos componentes visuais (`txf`, `btn`, `lbl`) é uma convenção que facilita identificar o tipo do componente só de ler o nome.

### Separação de Responsabilidades (Single Responsibility Principle)

Cada classe faz **uma coisa só**. Isso é um dos princípios fundamentais do código limpo (Clean Code, Robert C. Martin):

- `Validacao` não calcula — só valida.
- `Calculos` não valida — só calcula.
- `frmPrincipal` não valida nem calcula — só exibe.
- `Controle` não valida nem calcula diretamente — só coordena quem faz cada parte.

Isso facilita encontrar e corrigir erros: se o resultado está errado, o problema está em `Calculos`. Se aceita entrada inválida, o problema está em `Validacao`.

### Encapsulamento

Os atributos das classes são declarados como `private` e acessados apenas por métodos públicos (`getters`):

```java
// Em Calculos.java
private Double resultado;

public Double getResultado()
{
    return resultado;
}
```

Isso impede que outras classes modifiquem os dados internamente de forma não controlada. Só é possível **ler** o resultado, não alterá-lo diretamente.

### Métodos Curtos e com Propósito Único

Nenhum método deste projeto faz mais do que uma coisa. O método `calcular()` em `Calculos` apenas realiza a operação. O método `validar()` em `Validacao` apenas verifica os dados. O método `executar()` em `Controle` apenas orquestra a sequência.

Isso está alinhado à diretriz do Clean Code: **um método deve fazer uma única coisa, e fazê-la bem**.

### Tratamento de Erros na Entrada

A validação da entrada do usuário é centralizada em `Validacao`, usando `try/catch` para capturar conversões inválidas e verificação explícita para divisão por zero:

```java
try
{
    n1 = Double.valueOf(num1); // pode lançar exceção se não for número
    n2 = Double.valueOf(num2);
    if (op.equals("/") && n2 == 0.0)
        mensagem = "Divisão por zero";
}
catch (Exception e)
{
    mensagem = "Erro de conversão";
}
```

A mensagem de erro retorna para o front-end como uma `String` comum, e o `lblResultado` a exibe sem que a interface precise saber o que deu errado — apenas exibe o que receber.

### Não Repetir Lógica (DRY — Don't Repeat Yourself)

O método `calcular` em `frmPrincipal` centraliza o comportamento comum dos quatro botões:

```java
public void calcular(String op)
{
    Controle controle = new Controle();
    lblResultado.setText(controle.executar(..., op));
}

private void btnSomarActionPerformed(...) { calcular("+"); }
private void btnSubtrairActionPerformed(...) { calcular("-"); }
private void btnMultiplicarActionPerformed(...) { calcular("*"); }
private void btndividirActionPerformed(...) { calcular("/"); }
```

Em vez de repetir o mesmo bloco de código quatro vezes (uma para cada botão), a lógica foi extraída para o método `calcular(String op)`, que recebe apenas o operador como variação. Isso é o princípio **DRY**.

### Inicialização no Construtor

As classes `Validacao` e `Calculos` já executam sua lógica principal dentro do próprio construtor (`this.validar()` e `this.calcular()`). Isso garante que o objeto nunca existe em um estado incompleto ou inconsistente — ao ser criado, já está pronto para ser consultado.

---

## Padrões de Projeto Aplicados

Sim, este projeto aplica dois padrões de projeto clássicos, mesmo que de forma implícita.

### MVC — Model-View-Controller

O **MVC** é um padrão arquitetural que divide a aplicação em três camadas com responsabilidades distintas:

| Camada | Papel | Neste projeto |
|--------|-------|---------------|
| **Model** (Modelo) | Dados e regras de negócio | `Validacao.java` + `Calculos.java` |
| **View** (Visão) | Interface com o usuário | `frmPrincipal.java` |
| **Controller** (Controlador) | Intermediário entre View e Model | `Controle.java` |

**Como funciona no projeto:**

```
View (frmPrincipal)
    ↓ chama
Controller (Controle)
    ↓ delega para
Model (Validacao + Calculos)
    ↓ retorna resultado para
Controller
    ↓ devolve String para
View
```

A `View` nunca acessa o `Model` diretamente. Ela sempre passa pelo `Controller`. Isso mantém as camadas desacopladas: a interface gráfica não sabe nada sobre a lógica de negócio, e o modelo não sabe nada sobre a interface.

---

### Facade — Fachada

O padrão **Facade** fornece uma **interface simplificada** para um conjunto de classes mais complexo. Em vez de o cliente precisar conhecer e coordenar múltiplos objetos, ele interage com um único ponto de entrada.

No projeto, `Controle` é a fachada:

```java
// A frmPrincipal só precisa conhecer Controle e chamar um único método:
Controle controle = new Controle();
controle.executar(num1, num2, op);
```

Por baixo, `Controle.executar()` esconde toda a complexidade:
1. Cria e executa `Validacao`
2. Verifica se há erro
3. Cria e executa `Calculos` (somente se válido)
4. Retorna o resultado final

A `frmPrincipal` não sabe que `Validacao` e `Calculos` existem. Para ela, o back-end inteiro é representado por um único método: `executar()`. Essa é a essência do padrão Facade — **simplificar o acesso a um subsistema complexo**.

---

### Relação entre os dois padrões

Os dois padrões se complementam neste projeto:

- O **MVC** define **quem faz o quê** — separa interface, controle e modelo.
- O **Facade** define **como a comunicação acontece** — o `Controle` age como porta de entrada única para o modelo, protegendo a View da complexidade interna.

---

## Componentes Visuais Utilizados

Todos os componentes pertencem à biblioteca **Java Swing** (`javax.swing`).

### `JDialog` — a janela principal
```java
public class frmPrincipal extends javax.swing.JDialog
```
`JDialog` é uma janela de diálogo (secundária). Ao contrário de `JFrame` (janela principal independente), um `JDialog` é associado a uma janela pai.  
O construtor recebe dois parâmetros:
- `java.awt.Frame parent` — a janela pai (passada como `null` aqui, pois não há janela pai).
- `boolean modal` — `true` significa que enquanto a janela estiver aberta, nenhuma outra janela da aplicação pode ser usada.

### `JLabel` — rótulos de texto
```java
lblPrimeiroNumero = new javax.swing.JLabel();
lblPrimeiroNumero.setText("Digite primeiro número");
```
`JLabel` exibe um texto estático na tela. É usado para identificar os campos de entrada e para mostrar o resultado final (`lblResultado`).

### `JTextField` — campos de entrada de texto
```java
txfPrimeiroNumero = new javax.swing.JTextField();
txfSegundoNumero  = new javax.swing.JTextField();
```
`JTextField` é uma caixa de texto de uma linha onde o usuário digita. O conteúdo digitado é recuperado com o método `.getText()`, que retorna uma `String`.

### `JButton` — botões de ação
```java
btnSomar     = new javax.swing.JButton();
btnSubtrair  = new javax.swing.JButton();
btnMultiplicar = new javax.swing.JButton();
btndividir   = new javax.swing.JButton();
```
`JButton` é um botão clicável. Cada botão possui um **ActionListener** registrado que define o que acontece quando o botão é clicado.

---

## Comunicação entre Classes

O fluxo de uma operação segue este caminho:

```
Usuário clica em "Somar"
        ↓
frmPrincipal.btnSomarActionPerformed()
        ↓
frmPrincipal.calcular("+")
        ↓
Controle.executar(num1, num2, "+")
        ↓
Validacao(num1, num2, "+")   →  valida os dados
        ↓ (se válido)
Calculos(n1, n2, "+")        →  calcula o resultado
        ↓
resultado volta para frmPrincipal
        ↓
lblResultado.setText(resultado)
```

A `frmPrincipal` **nunca fala diretamente** com `Validacao` ou `Calculos`. Ela fala apenas com `Controle`, que é o ponto de entrada do back-end. Isso é chamado de **encapsulamento de camada**.

---

## Explicação Detalhada do Código

### `Aula1Visual.java` — ponto de entrada
```java
public static void main(String[] args)
{
    frmPrincipal frmc = new frmPrincipal(null, true);
    frmc.setVisible(true);
}
```
- `new frmPrincipal(null, true)` — **instancia** (cria um objeto) da classe `frmPrincipal`, passando `null` como janela pai e `true` para modal.
- `frmc.setVisible(true)` — torna a janela visível na tela.

---

### `frmPrincipal.java` — interface gráfica

#### Registro de evento com ActionListener
```java
btnSomar.addActionListener(new java.awt.event.ActionListener()
{
    public void actionPerformed(java.awt.event.ActionEvent evt)
    {
        btnSomarActionPerformed(evt);
    }
});
```
Aqui é criado um objeto anônimo que implementa a interface `ActionListener`. Quando o botão é clicado, o método `actionPerformed` é chamado automaticamente, que por sua vez chama `btnSomarActionPerformed`.

#### Método `calcular`
```java
public void calcular(String op)
{
    Controle controle = new Controle();
    lblResultado.setText(controle.executar(txfPrimeiroNumero.getText(), txfSegundoNumero.getText(), op));
}
```
- `new Controle()` — instancia a classe de controle do back-end.
- `txfPrimeiroNumero.getText()` e `txfSegundoNumero.getText()` — recuperam as Strings digitadas pelo usuário nos campos de texto.
- `controle.executar(...)` — passa os dois valores e o operador para o back-end processar. O retorno é uma `String` com o resultado ou uma mensagem de erro.
- `lblResultado.setText(...)` — exibe o resultado no label da tela.

---

### `Controle.java` — orquestrador
```java
public String executar(String num1, String num2, String op)
{
    String mensagem = "";
    Validacao validacao = new Validacao(num1, num2, op);
    mensagem = validacao.getMensagem();
    if (mensagem.equals(""))
    {
        Calculos calculos = new Calculos(validacao.getN1(), validacao.getN2(), op);
        mensagem = calculos.getResultado().toString();
    }
    return mensagem;
}
```
- Instancia `Validacao` passando os três parâmetros. A validação já ocorre no construtor.
- Se `getMensagem()` retornar `""` (vazio), significa que não há erro — os dados são válidos.
- Só então instancia `Calculos`, passando os valores já convertidos para `Double` (obtidos via `validacao.getN1()` e `validacao.getN2()`).
- Retorna o resultado como `String` para a camada de apresentação.

---

### `Validacao.java` — validação dos dados
```java
public Validacao(String num1, String num2, String op)
{
    this.num1 = num1;
    this.num2 = num2;
    this.op = op;
    this.validar();
}

private void validar()
{
    this.mensagem = "";
    try
    {
        n1 = Double.valueOf(num1);
        n2 = Double.valueOf(num2);
        if (op.equals("/") && n2 == 0.0)
            mensagem = "Divisão por zero";
    }
    catch (Exception e)
    {
        mensagem = "Erro de conversão";
    }
}
```
- `Double.valueOf(num1)` — tenta converter a `String` recebida para um número `Double`. Se o usuário digitou letras ou deixou em branco, lança uma exceção.
- O bloco `try/catch` captura esse erro e define a mensagem `"Erro de conversão"`.
- O caso especial de divisão por zero também é verificado aqui.
- Os valores convertidos (`n1`, `n2`) ficam disponíveis via `getN1()` e `getN2()` para `Controle` repassar a `Calculos`.

---

### `Calculos.java` — lógica de cálculo
```java
public Calculos(Double n1, Double n2, String op)
{
    this.n1 = n1;
    this.n2 = n2;
    this.op = op;
    this.calcular();
}

private void calcular()
{
    if (op.equals("+")) resultado = n1 + n2;
    if (op.equals("-")) resultado = n1 - n2;
    if (op.equals("*")) resultado = n1 * n2;
    if (op.equals("/")) resultado = n1 / n2;
}
```
- Recebe dois `Double` e o operador como parâmetros do construtor.
- O cálculo é feito imediatamente no construtor através de `this.calcular()`.
- O resultado fica armazenado no atributo `resultado` e disponível via `getResultado()`.

---

## Método Construtor

O **construtor** é um método especial de uma classe que é executado **automaticamente** no momento em que um objeto é criado com `new`. Ele serve para inicializar os atributos do objeto com os valores recebidos como parâmetros.

Características do construtor:
- Tem **o mesmo nome da classe**.
- **Não possui tipo de retorno** (nem `void`).
- É chamado uma única vez, no momento da criação do objeto.

Exemplos deste projeto:

```java
// Construtor de Calculos — recebe os dois números e o operador
public Calculos(Double n1, Double n2, String op)
{
    this.n1 = n1;
    this.n2 = n2;
    this.op = op;
    this.calcular(); // já executa o cálculo ao criar o objeto
}

// Construtor de Validacao — recebe os valores como String e já valida
public Validacao(String num1, String num2, String op)
{
    this.num1 = num1;
    this.num2 = num2;
    this.op = op;
    this.validar(); // já executa a validação ao criar o objeto
}

// Construtor de Controle — sem parâmetros, nenhuma inicialização necessária
public Controle() { }
```

Perceba que em `Calculos` e `Validacao` o construtor não só atribui os valores, como também chama um método interno (`calcular()` e `validar()`). Isso significa que toda a lógica já é executada no instante em que o objeto é criado.

---

## A Cláusula `this`

`this` é uma referência que aponta para o **próprio objeto atual**. Ela é usada para diferenciar os **atributos da classe** dos **parâmetros do construtor** quando ambos possuem o mesmo nome.

Exemplo de ambiguidade (sem `this`):
```java
// n1 do lado esquerdo seria o parâmetro, não o atributo — confuso!
public Calculos(Double n1, Double n2, String op)
{
    n1 = n1; // não faz nada útil — atribui o parâmetro a ele mesmo
}
```

Exemplo correto (com `this`):
```java
public Calculos(Double n1, Double n2, String op)
{
    this.n1 = n1; // this.n1 → atributo da classe | n1 → parâmetro recebido
    this.n2 = n2;
    this.op = op;
}
```

`this` também pode ser usado para chamar métodos do próprio objeto:
```java
this.calcular(); // chama o método calcular() deste próprio objeto
this.validar();  // chama o método validar() deste próprio objeto
```

Resumo dos usos de `this` neste projeto:

| Uso | Significado |
|-----|-------------|
| `this.n1 = n1` | Atribui o parâmetro `n1` ao atributo `n1` do objeto |
| `this.calcular()` | Chama o método `calcular()` do próprio objeto |
| `this.mensagem = ""` | Inicializa o atributo `mensagem` do próprio objeto |

---

## Instância de Classe e Parâmetros

**Instanciar** uma classe significa criar um **objeto** a partir dela usando a palavra-chave `new`.

```java
// Sintaxe:
NomeDaClasse nomeDoObjeto = new NomeDaClasse(parametro1, parametro2);

// Exemplos deste projeto:
Controle controle       = new Controle();                        // sem parâmetros
Validacao validacao     = new Validacao(num1, num2, op);         // 3 parâmetros String
Calculos calculos       = new Calculos(validacao.getN1(), validacao.getN2(), op); // 2 Double + 1 String
```

Os **parâmetros** são valores passados ao construtor da classe para que o objeto seja criado já com as informações necessárias para funcionar. Cada classe decide quais parâmetros precisa declarando-os no seu construtor.

