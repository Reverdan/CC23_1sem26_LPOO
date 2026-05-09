package com.mycompany.numeroprimo.modelo;

public final class Primo extends Propriedades
{
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
                this. resposta = "Não é primo";
                break;
            }
            if (i > 2)
                i++;
        }
    }

    public String getResposta()
    {
        return resposta;
    }
    
    
}
