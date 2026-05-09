package com.mycompany.exemplo_threads;

import com.mycompany.exemplo_threads.apresentacao.frmPrincipal;
import com.mycompany.exemplo_threads.modelo.CalculaSoma;

public class Exemplo_Threads 
{
    

    public static void main(String[] args) 
    {
        Thread threadCalculo = new Thread(new CalculaSoma());
        threadCalculo.setDaemon(true);
        threadCalculo.start();
        
        frmPrincipal frmP = new frmPrincipal(null, true);
        frmP.setVisible(true);
    }
}
