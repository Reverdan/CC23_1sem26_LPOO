# Verificar Triângulos — Projeto VerificarTriangulos

Este projeto, além de validar e classificar triângulos, é um exemplo avançado de aplicação de técnicas de orientação a objetos (O.O.) e padrões de projeto em Java, indo além do que foi apresentado em AULA1 e PRIMO.

---

## Técnicas de Orientação a Objetos (O.O.)

### 1. Interface como Contrato (Programação para Interface)
A interface `intMetodos` define o método obrigatório `verificar()`. Isso permite que diferentes classes implementem sua própria lógica de verificação, promovendo:
- **Polimorfismo**: objetos de diferentes classes podem ser tratados de forma uniforme.
- **Baixo acoplamento**: o código depende do contrato, não da implementação.

### 2. Classe Abstrata e Herança
A classe abstrata `absPropriedades` centraliza atributos e construtores comuns, além de forçar a implementação do método `verificar()` nas subclasses. Benefícios:
- **Reuso de código**: evita duplicidade de atributos e lógica básica.
- **Padronização**: todas as subclasses seguem a mesma estrutura.
- **Facilidade de manutenção**: alterações em atributos comuns são feitas em um só lugar.

### 3. Polimorfismo via Sobrescrita
As classes `Controle`, `Validacao` e `Triangulos` sobrescrevem o método `verificar()`, cada uma com sua lógica específica. Isso permite:
- **Flexibilidade**: cada classe executa sua lógica sem alterar a interface de uso.
- **Extensibilidade**: novas regras podem ser adicionadas criando novas subclasses.

### 4. Encapsulamento e Estado Válido
- Os atributos são centralizados e inicializados nos construtores.
- O método `verificar()` é chamado automaticamente, garantindo que o objeto sempre esteja em estado válido.
- O acesso aos resultados é feito por meio de atributos públicos ou métodos de acesso.

### 5. Responsabilidade Ãšnica e Separação de Papéis
Cada classe tem uma função clara:
- `Validacao`: valida os dados de entrada (regra do triângulo).
- `Triangulos`: classifica o tipo de triângulo.
- `Controle`: orquestra o fluxo, decide se pode classificar ou se há erro.

Essa separação facilita testes, manutenção e evolução do sistema.

### 6. Extensibilidade e Reuso
- Novos tipos de validação ou classificação podem ser adicionados facilmente, bastando criar novas subclasses de `absPropriedades`.
- O uso de interface e classe abstrata permite que o sistema cresça sem modificar código já existente (princípio Open/Closed).

---

## Padrões de Projeto Utilizados

### 1. **Facade**
A classe `Controle` atua como uma fachada (Facade), simplificando o uso do sistema para a interface gráfica. Ela esconde a complexidade das etapas de validação e classificação, expondo apenas métodos simples para o usuário da interface.

**Benefícios:**
- Reduz o acoplamento entre a interface e as regras de negócio.
- Facilita a manutenção e evolução do sistema.

### 2. **MVC (Model-View-Controller)**
O projeto segue o padrão MVC:
- **Model**: pacote `modelo` (lógica de negócio, validação, classificação).
- **View**: pacote `apresentacao` (interface gráfica Swing).
- **Controller**: classe `Controle` (coordena a comunicação entre view e model).

**Benefícios:**
- Separação de responsabilidades.
- Facilidade para modificar a interface sem alterar a lógica de negócio.
- Testabilidade e manutenção.

### 3. **Template Method**
A classe abstrata `absPropriedades` define o esqueleto do algoritmo (construtor + chamada de `verificar()`), enquanto as subclasses implementam os detalhes do método `verificar()`. Isso caracteriza o padrão Template Method.

**Benefícios:**
- Permite definir o fluxo principal em um lugar e delegar detalhes para subclasses.
- Facilita a extensão do comportamento sem alterar a estrutura geral.

---

## Fluxo de Execução
1. O usuário informa os lados na interface gráfica.
2. A interface cria um objeto `Controle`, que inicia a validação dos lados.
3. Se os lados forem válidos, um objeto `Triangulos` é criado para classificar o tipo.
4. O resultado é retornado para a interface e exibido ao usuário.

---

## Resumo
O projeto VerificarTriangulos demonstra:
- Uso avançado de interface, classe abstrata e polimorfismo.
- Encapsulamento e estado válido garantido.
- Separação de responsabilidades e baixo acoplamento.
- Aplicação dos padrões Facade, MVC e Template Method.
- Estrutura extensível e de fácil manutenção.

Essas práticas tornam o código robusto, flexível e preparado para evoluções futuras, servindo como referência para projetos orientados a objetos sofisticados e profissionais.

