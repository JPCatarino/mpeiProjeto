package MovieLensExample;

import java.util.LinkedList;

public class MlUser {
    private String id;
    private LinkedList<String> filmes;

    public MlUser(String id){
        this.id = id;
        this.filmes = new LinkedList<String>();
    }

    public void addData(String data){
        filmes.add(data);
    }

    public void printData(){
        for(String aux : filmes){
            System.out.println(aux);
        }
    }

    public LinkedList<String> getFilmes() {
        return filmes;
    }

    @Override
    public String toString() {
        return "MlUser{" +
                "id='" + id + '\'' +
                ", filmes=" + filmes +
                '}';
    }
}
