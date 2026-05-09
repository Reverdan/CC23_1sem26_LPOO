package com.mycompany.verificartriangulos.modelo;

public class Triangulos extends absPropriedades
{

    public Triangulos(Double l1, Double l2, Double l3)
    {
        super(l1, l2, l3);
    }

    @Override
    public void verificar()
    {
        if (this.l1.equals(this.l2) &&
                this.l1.equals(this.l3))
        {
            this.resposta = "Triângulo equilátero";
        }
        else
        {
            if (!this.l1.equals(this.l2) &&
                    !this.l1.equals(this.l3) &&
                    !this.l2.equals(this.l3))
            {
                this.resposta = "Triângulo Escaleno";
            }
            else
            {
                this.resposta = "Triângulo Isósceles";
            }
        }
    }

    
}
