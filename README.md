# Project Calculator

Calculadora desktop em Java Swing com interface simples e motor de calculo separado da UI.

## O que foi melhorado

- Logica de calculo extraida para `CalculatorEngine`, facilitando manutencao e testes.
- Operacoes `+`, `-`, `*` e `/` implementadas.
- Suporte a numeros decimais, troca de sinal, limpar entrada, apagar digito e limpar tudo.
- Tratamento de divisao por zero com recuperacao da interface.
- Teste automatizado simples em `test/CalculatorEngineTest.java`.
- Projeto ajustado para Java 21, que e a versao instalada no ambiente.

## Como compilar

```powershell
javac -d build\classes src\Calculator.java src\CalculatorEngine.java
```

## Como rodar os testes

```powershell
javac -cp build\classes -d build\test-classes test\CalculatorEngineTest.java
java -cp 'build\classes;build\test-classes' CalculatorEngineTest
```

## Como abrir a aplicacao

```powershell
java -cp build\classes Calculator
```
