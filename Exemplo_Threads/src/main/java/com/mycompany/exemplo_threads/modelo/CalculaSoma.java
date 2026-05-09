package com.mycompany.exemplo_threads.modelo;

public class CalculaSoma implements Runnable
{
    @Override
    public void run()
    {
        while (true)
        {
            if (Estaticos.n1 != null && Estaticos.n2 != null)
            {
                try
                {
                    double valor1 = Double.parseDouble(Estaticos.n1.replace(',', '.'));
                    double valor2 = Double.parseDouble(Estaticos.n2.replace(',', '.'));
                    double soma = valor1 + valor2;
                    Estaticos.resultado = String.valueOf(soma);
                }
                catch (NumberFormatException e)
                {
                    Estaticos.resultado = "Erro: valores invalidos";
                }
            }

            
        }
    }
}
