package componentes;

/**
 * Implementação de um contador estocástico. Um contador estocástico é um contador que serve para aumentar o número de vezes que se podem contar de acordo com uma probabilidade.
 * É útil para contar grandes volumes de dados.
 * @author Oscar Pimentel
 * @author Jorge Catarino
 */
public class ContadorEstocastico{

    /**
     * Método para duplicar o número de eventos que se podem contar. Incrementa-se o contador com probabilidade de 0.5
     * @param size Tamanho da estrutura de dados sobre a qual queremos contar elementos.
     * @param prob Probability you want to set up.
     */
    public static int contadorEstocastico(int size, double prob){
        int count = 0;
        for(int i=0; i<= size; i++){
            double temp = Math.random();
            if(temp<prob){
                count++;
            }

        }
        return count;

    }

    public static int contadorEstocasticoIntervalo(double max, double min, int size){
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
