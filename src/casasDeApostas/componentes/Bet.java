package casasDeApostas.componentes;

/**
 * Created by Media Markt on 04/12/2018.
 */
public class Bet {
    private Match jogo;
    private BetOption opcao;

    public Bet(Match jogo, BetOption opcao){
        this.jogo = jogo;
        this.opcao = opcao;
    }

    public Match getJogo() {
        return jogo;
    }

    public BetOption getOpcao() {
        return opcao;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "jogo=" + jogo +
                ", opcao=" + opcao +
                '}';
    }


}
