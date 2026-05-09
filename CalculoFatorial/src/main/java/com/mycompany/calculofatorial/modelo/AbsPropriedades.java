package com.mycompany.calculofatorial.modelo;

public abstract class AbsPropriedades implements IMetodos
{
    protected String numero;
    public Integer num;

    public AbsPropriedades(String numero)
    {
        this.numero = numero;
        this.Executar();
    }

    public AbsPropriedades(Integer num)
    {
        this.num = num;
        this.Executar();
    }
    
    
}
