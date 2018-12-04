package componentes;

/**
 * Created by Media Markt on 20/11/2018.
 */
public class ContadorEstocastico <T> {


    public int contadorEstocástico1(int size){
        int count = 0;
        for(int i=0; i<= size; i++){
            double temp = Math.random();
            if(temp<0.5){
                count++;
            }

        }
        return count;

    }

    public int contadorEstocástico2valores(double max, double min, int size){
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
