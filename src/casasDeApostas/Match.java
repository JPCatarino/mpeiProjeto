package casasDeApostas;

import java.util.Objects;

public class Match {
    private String home_team;
    private String away_team;
    private int home_score;
    private int away_score;

    public Match(String home_team, int home_score, String away_team, int away_score) {
        this.home_team = home_team;
        this.away_team = away_team;
        this.home_score = home_score;
        this.away_score = away_score;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return home_score == match.home_score &&
                away_score == match.away_score &&
                Objects.equals(home_team, match.home_team) &&
                Objects.equals(away_team, match.away_team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(home_team, away_team, home_score, away_score);
    }
}
