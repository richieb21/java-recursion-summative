
// importing necessary libraries
import java.util.*;

public class team {

    // initializing all necessary variables unique to each team object
    // point difference --> pD
    private int pD;
    private int wins;
    private int losses;

    private int allStars;
    private int playMaking;
    private int shooting;
    private int guarding;
    private int chemistry;
    private int rebounding;

    private double luck;

    private String name;

    // constructor to initialize random stats and names
    public team(String cName, String aName) {
        Random rand = new Random();

        name = cName + " " + aName;

        pD = 0;
        wins = 0;
        losses = 0;

        // randomizing team stats
        allStars = rand.nextInt(3) + 2;
        playMaking = (3 * allStars) + (rand.nextInt(36) + 65);
        shooting = (3 * allStars) + (rand.nextInt(36) + 65);
        guarding = (3 * allStars) + (rand.nextInt(36) + 65);
        chemistry = (3 * allStars) + (rand.nextInt(36) + 65);
        rebounding = (3 * allStars) + (rand.nextInt(36) + 65);

        // creating a luck values between 1.00 and 1.50
        luck = ((double) rand.nextInt(51) / 100) + 1;
    }

    // used to rotate list to easily schedule different matches for each week
    public team[] rotateList(team[] teamsList) {

        // we keep the first teamsList element constant, so create new array to swap
        // places
        team[] teams = new team[7];

        // initializing values in array apart from the first element in the teamsList
        // array
        for (int i = 1; i < 8; i++) {
            teams[i - 1] = teamsList[i];
        }

        // move indexes 0,1 to 1,2 --> original 2 is moved to index 0
        team temp = teams[0];
        teams[0] = teams[2];
        team temp1 = teams[1];
        teams[2] = temp1;
        teams[1] = temp;

        // move indexes 4,5,6 to 3,4,5 --> original 3 is moved to index 6
        team temp2 = teams[4];
        team temp3 = teams[5];
        team temp4 = teams[6];
        teams[6] = teams[3];
        teams[3] = temp2;
        teams[4] = temp3;
        teams[5] = temp4;

        // now swap first and last indexes
        team first = teams[0];
        teams[0] = teams[6];
        teams[6] = first;

        for (int i = 0; i < 7; i++) {
            teamsList[i + 1] = teams[i];
        }

        return teamsList;
    }

    // simulates an entire 14 week season
    public team[] simulateSeason(team[] teamsList) {

        // this array is used to store final standings for every week, the teamsList
        // array is used to store teams and generate their matchups regardless of table
        // position
        team[] finalStandings = new team[8];

        // copying the teamsList values over to the finalStandings array, there must be
        // a separate array for standings and generating matchups
        for (int i = 0; i < 8; i++) {
            finalStandings[i] = teamsList[i];
        }

        // loops for each week
        for (int i = 1; i < 15; i++) {
            System.out.println("Week " + i + " Matchups\n");

            // loops for all games in a week
            for (int j = 0; j < 4; j++) {

                // add 4 to j since that's how to get unique weekly matchups with the rotateList
                // method
                team opp1 = teamsList[j];
                team opp2 = teamsList[j + 4];

                int p1 = pointCounter(opp1);
                int p2 = pointCounter(opp2);
                // adds point differences and wins/losses for respective teams
                gameTracker(opp1, opp2, p1, p2);

                // prints out final result of the game
                System.out.println(opp1.name + " " + p1 + ", " + opp2.name + " " + p2);
            }
            System.out.println();

            // sort teams by wins
            recursiveSort(finalStandings);

            // sorting the sorted list and now factoring point difference
            organizeStandings(finalStandings);

            // printing out standings at the end of each week
            System.out.println("Week " + i + " Standings\n");
            for (int j = 7; j >= 0; j--) {
                team t = finalStandings[j];
                System.out.println((8 - j) + " - " + t.name + ": " + t.wins + " win(s), " + t.losses + " loss(es), "
                        + t.pD + " point difference");
            }

            System.out.println();

            teamsList = rotateList(teamsList);
        }
        return finalStandings;
    }

    // method to track a games stats using points
    public void gameTracker(team opp1, team opp2, int p1, int p2) {
        if (p1 > p2) {
            opp1.wins++;
            opp2.losses++;
            opp1.pD += p1 - p2;
            opp2.pD += p2 - p1;
        } else if (p2 > p1) {
            opp2.wins++;
            opp1.losses++;
            opp2.pD += p2 - p1;
            opp1.pD += p1 - p2;
        } else {
            // if it's a tie, the team with higher luck wins
            if (opp1.luck > opp2.luck) {
                opp1.wins++;
                opp2.losses++;
                p1 += 3;
            } else {
                opp2.wins++;
                opp1.losses++;
                p2 += 3;
            }
        }
    }

    // counts points for a team in a game, adds variability so teams with worse
    // stats can still win
    public int pointCounter(team team1) {
        Random rand = new Random();

        // adding random values values for a given game
        int pm = team1.playMaking + rand.nextInt(31);
        int s = team1.shooting + rand.nextInt(31);
        int g = team1.guarding + rand.nextInt(31);
        int c = team1.chemistry + rand.nextInt(31);
        int r = team1.rebounding + rand.nextInt(31);

        // summing all stats
        double sumStats = (double) (pm + s + g + c + r) * luck;

        // 5.7 is a value calibrated to give most consistent and most realistic
        // basketball scores
        int numPoints = (int) Math.round(sumStats / 5.7);
        return numPoints;
    }

    // simulates the playoffs using the final standings
    public void playOffSimulate(team[] teamsList) {
        ArrayList<team> knockouts = new ArrayList<team>();

        // quarter finals
        for (int i = 0; i < 4; i++) {
            System.out.println("Quarter Finals Game #" + (i + 1));
            knockouts = trackPlayoffs(pointCounter(teamsList[i]), pointCounter(teamsList[7 - i]), teamsList[i],
                    teamsList[7 - i], knockouts);
        }

        // semi finals
        System.out.println("Semi Finals Game #1");
        knockouts = trackPlayoffs(pointCounter(knockouts.get(0)), pointCounter(knockouts.get(2)), knockouts.get(0),
                knockouts.get(2), knockouts);

        System.out.println("Semi Finals Game #2");
        knockouts = trackPlayoffs(pointCounter(knockouts.get(1)), pointCounter(knockouts.get(3)), knockouts.get(1),
                knockouts.get(3), knockouts);

        // finals, since winners are added with quarter final winners, we access indexes
        // that are greater than 3 to get semi finals winners
        System.out.println("NBA Finals");
        knockouts = trackPlayoffs(pointCounter(knockouts.get(4)), pointCounter(knockouts.get(5)), knockouts.get(4),
                knockouts.get(5), knockouts);

        team winner = knockouts.get(6);
        System.out.println(winner.name + " wins the NBA Championship!");
    }

    // tracks a playoff games, prints names and scores and adds the winner to an
    // array list, uses same logic as the gameTracker method
    public ArrayList<team> trackPlayoffs(int p1, int p2, team opp1, team opp2, ArrayList<team> knockouts) {
        if (p1 > p2) {
            System.out.println(opp1.name + " " + p1 + ", " + opp2.name + " " + p2);
            System.out.println(opp1.name + " wins!\n");
            knockouts.add(opp1);
        } else if (p2 > p1) {
            System.out.println(opp1.name + " " + p1 + ", " + opp2.name + " " + p2);
            System.out.println(opp2.name + " wins!\n");
            knockouts.add(opp2);
        } else {
            if (opp1.luck > opp2.luck) {
                System.out.println(opp1.name + " " + (p1 + 3) + ", " + opp2.name + " " + p2);
                System.out.println(opp1.name + " wins!\n");
                knockouts.add(opp1);
            } else {
                System.out.println(opp1.name + " " + p1 + ", " + opp2.name + " " + (p2 + 3));
                System.out.println(opp2.name + " wins!\n");
                knockouts.add(opp2);
            }
        }
        return knockouts;
    }

    // organizes standings with point difference in mind
    public void organizeStandings(team[] teamsList) {
        for (int i = 1; i < 8; i++) {
            if (teamsList[i].wins == teamsList[i - 1].wins) {
                if (teamsList[i].pD < teamsList[i - 1].pD) {
                    team temp = teamsList[i];
                    teamsList[i] = teamsList[i - 1];
                    teamsList[i - 1] = temp;
                }
            }
        }
    }

    // recursively sorts finalStandings (standings) by wins
    public static void recursiveSort(team[] finalStandings) {

        int n = finalStandings.length;
        team[] temp = new team[n];
        mergeSortHelper(finalStandings, 0, n - 1, temp);
    }

    // part of recursive merge sort method
    private static void mergeSortHelper(team[] elements, int from, int to, team[] temp) {
        if (from < to) {
            int middle = (from + to) / 2;
            mergeSortHelper(elements, from, middle, temp);
            mergeSortHelper(elements, middle + 1, to, temp);
            merge(elements, from, middle, to, temp);
        }
    }

    // merges sorted elements to original array
    private static void merge(team[] elements, int from, int mid, int to, team[] temp) {
        int i = from;
        int j = mid + 1;
        int k = from;

        while (i <= mid && j <= to) {
            if (elements[i].wins < elements[j].wins) {
                temp[k] = elements[i];
                i++;
            } else {
                temp[k] = elements[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            temp[k] = elements[i];
            i++;
            k++;
        }

        while (j <= to) {
            temp[k] = elements[j];
            j++;
            k++;
        }

        for (k = from; k <= to; k++) {
            elements[k] = temp[k];
        }
    }

}
