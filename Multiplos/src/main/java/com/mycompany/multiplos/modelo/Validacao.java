package com.mycompany.multiplos.modelo;

public class Validacao extends AbsPropriedades
{

    public Validacao(String numero1, String numero2, String numero3)
    {
        super(numero1, numero2, numero3);
        this.Validar(numero1, numero2, numero3);
    }

    public Validacao(String numero1)
    {
        super(numero1);
        this.Validar(numero1);
    }
    
    private void Validar(String numero1, String numero2, String numero3)
    {
        
    }
    
    private void Validar(String numero1)
    {
        this.mensagem = "";
        try
        {
            this.numero = Integer.valueOf(numero1);
        }
        catch (Exception e)
        {
            this.mensagem = "Erro de conversão";
        }
    }
    
    @Override
    public void Executar()
    {
    }
    
}
