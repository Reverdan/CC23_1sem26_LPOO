package com.mycompany.calculofatorial.modelo;

public class Controle extends AbsPropriedades
{

    public Controle(String numero)
    {
        super(numero);
    }

    @Override
    public void Executar()
    {
        Validacao validacao = new Validacao(numero);
        if (Estaticos.mensagem.equals(""))
        {
            CalcFatorial fatorial = new CalcFatorial(validacao.num);
            this.num = fatorial.num;
        }
    }
    
}
