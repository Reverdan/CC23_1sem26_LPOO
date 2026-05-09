package com.mycompany.aula1visual.modelo;

public class Calculos
{
    private Double n1;
    private Double n2; 
    private String op;
    private Double resultado;

    public Calculos(Double n1, Double n2, String op)
    {
        this.n1 = n1;
        this.n2 = n2;
        this.op = op;
        this.calcular();
    }
  
    private void calcular()
    {
        if (op.equals("+"))
            resultado = n1 + n2;
        if (op.equals("-"))
            resultado = n1 - n2;
        if (op.equals("*"))
            resultado = n1 * n2;
        if (op.equals("/"))
            resultado = n1 / n2;
    }

    public Double getResultado()
    {
        return resultado;
    }

}
