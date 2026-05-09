package com.mycompany.numeroprimo.modelo;

public class Controle extends Propriedades
{
    public Controle(String numero)
    {
        this.numero = numero;
        this.Executar();
    }
    
    private void Executar()
    {
        this.mensagem = "";
        Validacao validacao = new Validacao(numero);
        if (validacao.getMensagem().equals(""))
        {
            Primo primo = new Primo(validacao.getNum());
            this.resposta = primo.getResposta();
        }
        else
        {
            this.mensagem = validacao.getMensagem();
        }
    }

    public String getMensagem()
    {
        return mensagem;
    }

    public String getResposta()
    {
        return resposta;
    }
    
    
}
