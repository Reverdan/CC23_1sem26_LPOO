package com.mycompany.calculofatorial.modelo;

public class Validacao extends AbsPropriedades
{

    public Validacao(String numero)
    {
        super(numero);
    }

    @Override
    public void Executar()
    {
        Estaticos.mensagem = "";
        try
        {
            this.num = Integer.valueOf(numero);
        }
        catch (Exception e)
        {
            Estaticos.mensagem = "Número inválido";
        }
    }
    
}
