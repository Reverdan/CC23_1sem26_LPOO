package com.mycompany.multiplos.modelo;

public class Controle extends AbsPropriedades
{
    public void CalcularFatorial(String numero1)
    {
        
    }
    
    public void VerificarPrimo(String numero1)
    {
        this.mensagem = "";
        AbsPropriedades validacao = new Validacao(numero1);
        if (validacao.toString().equals(""))
        {
            AbsPropriedades primo = new Primo(validacao.numero);
            this.mensagem = primo.toString();
        }
        else
        {
            this.mensagem = validacao.toString();
        }
    }
    
    public void VerificarTriangulo(String numero1, String numero2, String numero3)
    {
        
    }
    
    @Override
    public void Executar()
    {
    }
    
}
