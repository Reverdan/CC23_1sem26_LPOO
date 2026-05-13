package com.mycompany.multiplos.modelo;

public abstract class AbsPropriedades implements IntMetodos
{
    protected String numero1;
    protected String numero2;
    protected String numero3;
    protected Double lado1;
    protected Double lado2;
    protected Double lado3;
    protected Integer numero;
    protected String resposta;
    protected String mensagem;

    public AbsPropriedades(Double lado1, Double lado2, Double lado3)
    {
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.lado3 = lado3;
        this.Executar();
    }

    public AbsPropriedades(String numero1, String numero2, String numero3)
    {
        this.numero1 = numero1;
        this.numero2 = numero2;
        this.numero3 = numero3;
        this.Executar();
    }

    public AbsPropriedades(String numero1)
    {
        this.numero1 = numero1;
        this.Executar();
    }

    public AbsPropriedades(Integer numero)
    {
        this.numero = numero;
        this.Executar();
    }

    public AbsPropriedades()
    {
    }

    public String getMensagem()
    {
        return mensagem;
    }

    @Override
    public String toString()
    {
        return this.mensagem; 
    }
    
    
    
    
    
    
}
