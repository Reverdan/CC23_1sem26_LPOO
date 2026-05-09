package com.mycompany.calculofatorial.modelo;

public class CalcFatorial extends AbsPropriedades
{

    public CalcFatorial(Integer num)
    {
        super(num);
    }

    @Override
    public void Executar()
    {
        if (num < 0) return;
        Integer resultado = 1;
        for (int i = 2; i <= num; i++)
        {
            resultado *= i;
        }
        num = resultado;
    }
   
}
