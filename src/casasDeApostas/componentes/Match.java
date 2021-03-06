package casasDeApostas.componentes;

/**<h1>Match</h1>
 * @author Jorge Catarino
 * @author Oscar Pimentel
*/

public class Match {
    private String home_team;
    private String away_team;
    private int home_score;
    private int away_score;
    private int id;
    private String liga;


    /**
     * Class Constructor for Match.
     * @param home_team Name of the team that plays at home in a game.
     * @param away_team Name of the team that plays away in a game.
     * @param home_score Number of goals the home team has scored in the match.
     * @param away_score Number of goals the away team has scored in the match.
     * @param id Match id in the dataset.
     * @param liga Name of the Competition (League) where the match is played.
     */
    public Match(String home_team, int home_score, String away_team, int away_score, int id, String liga) {
        this.home_team = home_team;
        this.away_team = away_team;
        this.home_score = home_score;
        this.away_score = away_score;
        this.id = id;
        this.liga = liga;
    }

    public String getHome_team() {
        return home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public int getHome_score() {
        return home_score;
    }

    public int getAway_score() {
        return away_score;
    }

    public BetOption findStateGame(){
        if(home_score == away_score){
            return BetOption.Draw;
        }
        else if(home_score > away_score){
            return BetOption.Home;
        }
        else
            return BetOption.Away;
    }

    public int getId() { return id; }

    public String getLiga() { return liga; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (home_score != match.home_score) return false;
        if (away_score != match.away_score) return false;
        if (id != match.id) return false;
        if (home_team != null ? !home_team.equals(match.home_team) : match.home_team != null) return false;
        if (away_team != null ? !away_team.equals(match.away_team) : match.away_team != null) return false;
        return liga != null ? liga.equals(match.liga) : match.liga == null;
    }

    @Override
    public int hashCode() {
        int result = home_team != null ? home_team.hashCode() : 0;
        result = 31 * result + (away_team != null ? away_team.hashCode() : 0);
        result = 31 * result + home_score;
        result = 31 * result + away_score;
        result = 31 * result + id;
        result = 31 * result + (liga != null ? liga.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  home_team + " vs " + away_team + " - " + home_score + ":" + away_score;
    }
}
