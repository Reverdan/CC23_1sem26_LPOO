package com.mycompany.media.modelo;

public abstract class AbsPropriedades implements IntMetodos
{
    protected String np1;
    protected String np2;
    protected String exame;
    protected Double notaNp1;
    protected Double notaNp2;
    protected Double notaExame;
    protected String resposta;
    protected String mensagem;

    public AbsPropriedades(String np1, String np2, String exame)
    {
        this.np1 = np1;
        this.np2 = np2;
        this.exame = exame;
        this.Executar();
    }

    public AbsPropriedades(Double notaNp1, Double notaNp2, Double notaExame)
    {
        this.notaNp1 = notaNp1;
        this.notaNp2 = notaNp2;
        this.notaExame = notaExame;
        this.Executar();
    }

    @Override
    public String toString()
    {
        return "Media = " + resposta + " - " + mensagem;
    }

    public String getMensagem()
    {
        return mensagem;
    }
    
    
}
