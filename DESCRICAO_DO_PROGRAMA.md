# Descricao do Programa

## Visao geral

O `Project_Calculator` e uma calculadora desktop desenvolvida em Java com Swing. O programa foi projetado para oferecer operacoes matematicas basicas em uma interface moderna, com estilo visual futurista e foco em usabilidade.

## Objetivo

O objetivo do programa e permitir que o usuario realize calculos rapidos de forma simples, com uma interface grafica intuitiva e uma logica interna organizada para facilitar manutencao e evolucao do projeto.

## Funcionalidades principais

- Soma, subtracao, multiplicacao e divisao.
- Suporte a numeros decimais.
- Troca de sinal (`+/-`).
- Limpeza da entrada atual (`CE`).
- Limpeza completa da operacao (`C`).
- Exclusao do ultimo digito (`DEL`).
- Tratamento de erro para divisao por zero.
- Suporte a atalhos de teclado:
  - Numeros de `0` a `9`
  - Operadores `+`, `-`, `*` e `/`
  - `Enter` para calcular
  - `Backspace` para apagar
  - `Delete` para limpar a entrada
  - `Esc` para limpar tudo

## Estrutura do projeto

O projeto esta organizado em partes principais:

- `src/Calculator.java`
  - Responsavel pela interface grafica.
  - Controla o layout, os botoes, o visor e os atalhos de teclado.

- `src/CalculatorEngine.java`
  - Responsavel pela logica da calculadora.
  - Processa numeros, operadores e resultados.

- `test/CalculatorEngineTest.java`
  - Contem testes automatizados para validar o comportamento da logica principal.

## Interface

A interface foi estilizada com uma proposta futurista, usando:

- Fundo com gradiente.
- Cartoes visuais para organizar as secoes.
- Botoes personalizados com cores diferentes para numeros, operadores e a tecla de igual.
- Visor destacado para facilitar a leitura do valor atual.

## Como executar

Para compilar:

```powershell
javac -d build\classes src\Calculator.java src\CalculatorEngine.java
```

Para executar:

```powershell
java -cp build\classes Calculator
```

## Como testar

Para compilar os testes:

```powershell
javac -cp build\classes -d build\test-classes test\CalculatorEngineTest.java
```

Para executar os testes:

```powershell
java -cp "build\classes;build\test-classes" CalculatorEngineTest
```

## Consideracoes finais

Este programa e um exemplo de aplicacao Java Swing com separacao entre interface e regra de negocio. Isso torna o projeto mais facil de manter, testar e melhorar no futuro.
