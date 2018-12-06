package casasDeApostas.componentes;

/**
 * <h1>Bet</h1>
 * @author Oscar Pimentel
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
