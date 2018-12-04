package componentes;

/**
 * Implementação de um contador estocástico.
 * @author Oscar Pimentel
 */
public class ContadorEstocastico <T> {


    public int contadorEstocastico1(int size){
        int count = 0;
        for(int i=0; i<= size; i++){
            double temp = Math.random();
            if(temp<0.5){
                count++;
            }

        }
        return count;

    }

    public int contadorEstocastico2valores(double max, double min, int size){
        int count = 0;
        for(int i=0; i<= size; i++){
            double temp = Math.random();
            if(temp>= min && temp <= max){
                count++;
            }

        }
        return count;

    }


}
