package com.mycompany.media.modelo;

public class Validacao extends AbsPropriedades
{

    public Validacao(String np1, String np2, String exame)
    {
        super(np1, np2, exame);
    }

    
    @Override
    public void Executar()
    {
        this.mensagem = "";
        try
        {
            notaNp1 = Double.valueOf(np1);
            notaNp2 = Double.valueOf(np2);
            notaExame = Double.valueOf(exame);
        }
        catch (Exception e)
        {
            this.mensagem = "Digite notas válidas";
        }
    }
    
}
