# Número Primo — Projeto NumeroPrimo

Este projeto verifica se um número digitado pelo usuário é primo. Ele segue a mesma arquitetura do [projeto Aula1](AULA1.md) — pacotes `apresentacao` e `modelo`, padrões MVC e Facade, responsabilidade única e boas práticas — portanto esses conceitos não serão repetidos aqui.

O foco desta documentação é o **novo conceito introduzido neste projeto: herança**.

---

## Estrutura de Pacotes

```
com.mycompany.numeroprimo
├── NumeroPrimo.java              ← ponto de entrada
├── apresentacao
│   └── frmPrincipal.java         ← interface gráfica
└── modelo
    ├── Propriedades.java         ← classe abstrata (superclasse)
    ├── Controle.java             ← orquestrador (herda Propriedades)
    ├── Validacao.java            ← validação (herda Propriedades)
    └── Primo.java                ← lógica de verificação (herda Propriedades)
```

---

## Herança

### O que é herança?

**Herança** é um mecanismo da Orientação a Objetos que permite que uma classe **herde atributos e métodos de outra classe**. A classe que fornece os membros é chamada de **superclasse** (ou classe pai), e a que herda é chamada de **subclasse** (ou classe filha).

Em Java, a herança é declarada com a palavra-chave `extends`:

```java
public class Subclasse extends Superclasse { }
```

A subclasse passa a ter **acesso direto** a todos os membros `public` e `protected` da superclasse, sem precisar declará-los novamente.

---

### A Superclasse: `Propriedades`

```java
public abstract class Propriedades
{
    public String numero;
    public int num;
    public String mensagem;
    public String resposta;
}
```

`Propriedades` é uma **classe abstrata** que centraliza todos os atributos compartilhados pelas classes do modelo. Ela não possui nenhum método — serve apenas como repositório de dados comum.

A palavra-chave `abstract` indica que essa classe **não pode ser instanciada diretamente** com `new Propriedades()`. Ela existe apenas para ser herdada. Tentar criar um objeto diretamente dela causaria erro de compilação.

| Atributo | Tipo | Uso |
|---|---|---|
| `numero` | `String` | O número digitado pelo usuário (texto bruto) |
| `num` | `int` | O número após conversão para inteiro |
| `mensagem` | `String` | Mensagem de erro, ou `""` se não houver erro |
| `resposta` | `String` | `"É primo"` ou `"Não é primo"` |

---

### As Subclasses

Todas as três classes do modelo herdam `Propriedades`:

```java
public class Validacao extends Propriedades { ... }
public class Controle  extends Propriedades { ... }
public final class Primo extends Propriedades { ... }
```

Isso significa que cada uma delas possui **automaticamente** os atributos `numero`, `num`, `mensagem` e `resposta`, sem precisar declará-los. Ao escrever `this.mensagem = ""` dentro de `Validacao`, está sendo acessado o atributo herdado de `Propriedades`.

**Diagrama de herança:**

```
         Propriedades   (abstract — superclasse)
         /     |     \
        /      |      \
  Validacao  Controle  Primo   (subclasses)
```

---

### Por que usar herança aqui?

Sem herança, cada classe teria que declarar os mesmos atributos repetidamente:

```java
// Sem herança — repetição em cada classe:
public class Validacao {
    public String numero;
    public int num;
    public String mensagem;
    public String resposta;
    ...
}
public class Controle {
    public String numero;
    public int num;
    public String mensagem;
    public String resposta;
    ...
}
```

Com herança, os atributos são declarados **uma única vez** em `Propriedades` e todas as subclasses os recebem automaticamente. Qualquer alteração feita na superclasse se propaga para todas as filhas de forma imediata.

---

### A Classe `final`: `Primo`

```java
public final class Primo extends Propriedades { ... }
```

A palavra-chave `final` em uma classe significa que ela **não pode ser herdada** por nenhuma outra classe — ela encerra a cadeia de herança. É uma decisão de design que protege a implementação do algoritmo de verificação de primo contra modificações acidentais por subclasses.

---

## Explicação do Código

### `Validacao.java`

```java
public Validacao(String numero)
{
    this.numero = numero;
    this.Executar();
}

private void Executar()
{
    this.mensagem = "";
    try
    {
        this.num = Integer.valueOf(numero);
    }
    catch (Exception e)
    {
        this.mensagem = "Digite números válidos";
    }
}
```

- Recebe o número como `String` e tenta convertê-lo para `int` com `Integer.valueOf()` (inteiro, pois primo não faz sentido para decimais).
- Os atributos `this.mensagem` e `this.num` são herdados de `Propriedades`.
- Se a conversão falhar, define a mensagem de erro.

---

### `Primo.java` — algoritmo de verificação

```java
public Primo(int num)
{
    this.num = num;
    this.Executar();
}

private void Executar()
{
    this.resposta = "É primo";
    for (int i = 2; i < num / 2 + 1; i++)
    {
        if (num % i == 0)
        {
            this.resposta = "Não é primo";
            break;
        }
        if (i > 2)
            i++;
    }
}
```

O algoritmo assume que o número **é primo** e tenta provar o contrário:

1. Começa testando divisores a partir de `2`.
2. Itera até `num / 2 + 1` (não precisa testar além da metade do número).
3. Se `num % i == 0` (divisível sem resto), o número **não é primo** — interrompe com `break`.
4. A linha `if (i > 2) i++` pula números pares após o 2, otimizando o laço.

O resultado fica no atributo herdado `this.resposta`.

---

### `Controle.java`

```java
public Controle(String numero)
{
    this.numero = numero;
    this.Executar();
}

private void Executar()
{
    this.mensagem = "";
    Validacao validacao = new Validacao(numero);
    if (validacao.getMensagem().equals(""))
    {
        Primo primo = new Primo(validacao.getNum());
        this.resposta = primo.getResposta();
    }
    else
    {
        this.mensagem = validacao.getMensagem();
    }
}
```

- Recebe apenas **um parâmetro** (o número como texto), diferente do Aula1 que recebia três.
- Orquestra a sequência: valida → verifica primo.
- Armazena o resultado em `this.resposta` (herdado) para a `frmPrincipal` recuperar via `getResposta()`.
- Se houver erro, copia a mensagem de `Validacao` para `this.mensagem`.

---

### `frmPrincipal.java` — diferencial: `JOptionPane`

Este projeto introduz um novo componente visual ausente no Aula1:

```java
Controle controle = new Controle(txfNumeroPrimo.getText());
if (controle.getMensagem().equals(""))
{
    lblResultado.setText(controle.getResposta());
}
else
{
    JOptionPane.showMessageDialog(null, controle.getMensagem());
    lblResultado.setText("");
}
```

**`JOptionPane.showMessageDialog()`** exibe uma janela de diálogo de alerta ao usuário. Recebe dois parâmetros:
- `null` — sem janela pai (a caixa aparece centralizada na tela).
- `controle.getMensagem()` — o texto a exibir na caixa.

A diferença de comportamento em relação ao Aula1:

| Aula1 | NumeroPrimo |
|---|---|
| Erro exibido diretamente no `lblResultado` | Erro exibido em uma caixa de diálogo separada (`JOptionPane`) |
| Label sempre mostra algo | Label é limpo com `setText("")` em caso de erro |

---

## Diferenças em Relação ao Aula1

| Aspecto | Aula1 | NumeroPrimo |
|---|---|---|
| Atributos compartilhados | Passados como parâmetros entre classes | Herdados da superclasse `Propriedades` |
| Superclasse do modelo | Nenhuma | `Propriedades` (abstract) |
| Classe de cálculo | Pode ser estendida | `Primo` é `final` |
| Exibição de erros | No próprio `lblResultado` | `JOptionPane` (caixa de diálogo) |
| Tipo numérico | `Double` (decimais) | `int` (inteiros) |
| Parâmetros do `Controle` | 3 (`num1`, `num2`, `op`) | 1 (`numero`) |
