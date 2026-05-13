package com.mycompany.multiplos.modelo;

public class Primo extends AbsPropriedades
{

    public Primo(Integer numero)
    {
        super(numero);
    }
    
    
    @Override
    public void Executar()
    {
        this.resposta = "É primo";
        for (int i = 2; i < numero / 2 + 1; i++)
        {
            if (numero % i == 0)
            {
                this. resposta = "Não é primo";
                break;
            }
            if (i > 2)
                i++;
        }
        this.mensagem = this.resposta;
    }
    
}
