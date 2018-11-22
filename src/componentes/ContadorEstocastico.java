package componentes;

/**
 * Created by Media Markt on 20/11/2018.
 */
public class ContadorEstocastico {

    //array só para testar, depois alterar para a estrutura de dados que queremos
    public static int conta(int[] array){
        int count = 0;
        for(int i=0; i<= array.length; i++){
            double temp = Math.random();
            if(temp<0.5){
                count++;
            }

        }
        return count;


    }

}
