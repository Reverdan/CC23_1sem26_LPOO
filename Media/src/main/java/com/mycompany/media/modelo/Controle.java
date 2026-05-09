package com.mycompany.media.modelo;

public class Controle extends AbsPropriedades
{

    public Controle(String np1, String np2, String exame)
    {
        super(np1, np2, exame);
    }

    @Override
    public void Executar()
    {
        this.mensagem = "";
        AbsPropriedades validacao = new Validacao(np1, np2, exame);
        if (validacao.getMensagem().equals(""))
        {
            AbsPropriedades media = new Media(
                    validacao.notaNp1 , 
                    validacao.notaNp2, 
                    validacao.notaExame);
            this.mensagem = media.toString();
        }
        else
        {
            this.mensagem = validacao.getMensagem();
        }
    }
    
}
