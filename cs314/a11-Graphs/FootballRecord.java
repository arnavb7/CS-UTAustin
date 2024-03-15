

/**
 * Store information about a football teams record.
 * Stores wins and losses. Does not handle ties.
 *
 */
public class FootballRecord {

    private String name;
    private int wins;
    private int losses;

    /**
     * Construct a new Football Record object with the given name for the team.
     * @param n The name of the team
     */
    public FootballRecord(String n) {
        name = n;
    }

    /**
     * Add a win to the records.
     */
    public void win() {
        wins++;
    }

    /**
     * Add a loss to the record.
     */
    public void lose() {
        losses++;
    }

    /**
     * Return the win percentage of this team which is number of wins
     * divided by total number of games played.
     * @return the win percentage of this team.
     */
    public double winPercent() {
        return 1.0 * wins / (wins + losses);
    }

    /**
     * Are the two objects equal?
     */
    public boolean equals(Object other) {
        boolean result = other instanceof FootballRecord;
        if (result) {
            result = name.equals( ((FootballRecord) other).name );
        }
        return result;
    }

    /**
     * return the hashcode for this team.
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * The total number of games played by this team.
     * @return The total number of games played by this team.
     */
    public int gamesPlayed() {
        return wins + losses;
    }

    /**
     * The number of games this team has won.
     * @return The number of games this team has won.
     */
    public int getWins() {
        return wins;
    }

    /**
     * The number of games this team has losy.
     * @return The number of games this team has lost.
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Return a String version of this record.
     */
    public String toString() {
        return name + ", wins: " + wins + ", losses: " + losses;
    }
}