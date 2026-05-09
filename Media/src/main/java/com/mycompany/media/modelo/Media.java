package com.mycompany.media.modelo;

public class Media extends AbsPropriedades
{

    public Media(Double notaNp1, Double notaNp2, Double notaExame)
    {
        super(notaNp1, notaNp2, notaExame);
    }

    @Override
    public void Executar()
    {
        Double media = 0.0;
        media = (notaNp1 + notaNp2) / 2;
        
        if (!notaExame.equals(0.0))
        {
            media = (media + notaExame) / 2;
            if (media < 5) this.mensagem = "Reprovado";
            else this.mensagem = "Aprovado";
        }
        else
        {
            if (media < 7) this.mensagem = "Exame";
            else this.mensagem = "Aprovado";
        }
        
        this.resposta = media.toString();
    }
}
