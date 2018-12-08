package componentes;

/**
 * Implementação de um contador estocástico. Um contador estocástico é um contador que serve para aumentar o número de vezes que se podem contar de acordo com uma probabilidade.
 * É útil para contar grandes volumes de dados.
 * @author Oscar Pimentel
 * @author Jorge Catarino
 */
public class ContadorEstocastico{

    /**
     * Método para contar elementos dado uma probabilidade.
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

    /**
     * Metodo para contar elementos dado um itnervalo de probabilidade
     * @param max - Max do intervalo.
     * @param min - min do intervalo.
     * @param size - Numero de elementos.
     * @return
     */
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
