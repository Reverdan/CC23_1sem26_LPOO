package com.mycompany.aula1visual.modelo;

public class Controle
{
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
}
