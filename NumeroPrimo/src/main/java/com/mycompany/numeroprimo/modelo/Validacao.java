package com.mycompany.numeroprimo.modelo;

public class Validacao extends Propriedades
{
    public Validacao(String numero)
    {
        this.numero = numero;
        this.Executar();
    }
    
    private void Executar()
    {
        this.mensagem = "";
        try
        {
            this.num = Integer.valueOf(numero);
        }
        catch (Exception e)
        {
            this.mensagem = "Digite números válidos";
        }
    }

    public int getNum()
    {
        return num;
    }

    public String getMensagem()
    {
        return mensagem;
    }
    
    
    
}
