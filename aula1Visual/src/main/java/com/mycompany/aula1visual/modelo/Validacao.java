package com.mycompany.aula1visual.modelo;

public class Validacao
{
    private Double n1;
    private Double n2;
    private String num1;
    private String num2;
    private String op;
    private String mensagem;

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

    public Double getN1()
    {
        return n1;
    }

    public Double getN2()
    {
        return n2;
    }

    public String getMensagem()
    {
        return mensagem;
    }
    
    
}
