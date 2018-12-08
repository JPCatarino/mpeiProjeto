package testes;

import componentes.ContadorEstocastico;
/**
 *Teste do contador estocástico
 * @author Oscar Pimentel
 */


public class testContador {
    public static void main(String[] args){
        int array[] = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200};

        System.out.println(ContadorEstocastico.contadorEstocastico(array.length,0.5));

    }
}
