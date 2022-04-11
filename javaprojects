import java.util.*;

public class team {

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

    public team(String cName, String aName) {
        Random rand = new Random();

        name = cName + " " + aName;

        pD = 0;
        wins = 0;
        losses = 0;

        allStars = rand.nextInt(4) + 1;
        playMaking = (5 * allStars) + (rand.nextInt(41) + 50);
        shooting = (5 * allStars) + (rand.nextInt(41) + 50);
        guarding = (5 * allStars) + (rand.nextInt(41) + 50);
        chemistry = (5 * allStars) + (rand.nextInt(41) + 50);
        rebounding = (5 * allStars) + (rand.nextInt(41) + 50);

        luck = ((double) rand.nextInt(51) / 100) + 1;
        System.out.println(name + " " + allStars);
        System.out.println(
                playMaking + " " + shooting + " " + guarding + " " + chemistry + " " + rebounding + " " + luck);

    }

    // used to rotate list to help matchmaking be smoother
    public team[] rotateList(team[] teamsList) {
        team[] teams = new team[8];
        team temp = teamsList[0];
        for (int i = 0; i < 7; i++) {
            teams[i] = teamsList[i + 1];
        }
        teams[7] = temp;
        return teams;
    }

    public team[] simulateSeason(team[] teamsList) {
        // work on a way to get matchups right

        for (int i = 1; i < 8; i++) {
            System.out.println("Week " + i + "\n");
            for (int j = 0; j < 4; j++) {

                team opp1 = teamsList[j * 2];
                team opp2 = teamsList[(j * 2) + 1];

                int p1 = pointCounter(opp1);
                int p2 = pointCounter(opp2);

                if (p1 == p2) {
                    if (opp1.luck > opp2.luck) {
                        p1 += 3;
                    } else {
                        p2 += 3;
                    }
                }

                gameTracker(opp1, opp2, p1, p2);

                System.out.println(opp1.name + " " + p1 + ", " + opp2.name + " " + p2);
            }
            System.out.println();
            recursiveSort(teamsList);
            // implement point difference sort again
            for (int j = 7; j >= 0; j--) {
                team t = teamsList[j];
                System.out.println((8 - j) + " - " + t.name + ": " + t.wins + " wins, " + t.losses + " losses, " + t.pD
                        + " point difference");
            }
            System.out.println();
            teamsList = rotateList(teamsList);
        }
        return teamsList;
    }

    public void playOffSimulate(team[] teamsList) {

    }

    public void gameTracker(team opp1, team opp2, int p1, int p2) {
        if (p1 > p2) {
            opp1.wins++;
            opp2.losses++;
            opp1.pD += p1 - p2;
            opp2.pD += p2 - p1;
        } else {
            opp2.wins++;
            opp1.losses++;
            opp2.pD += p2 - p1;
            opp1.pD += p1 - p2;
        }
    }

    public int pointCounter(team team1) {
        Random rand = new Random();

        // adding random values values for a given game
        int pm = team1.playMaking + rand.nextInt(16);
        int s = team1.shooting + rand.nextInt(16);
        int g = team1.guarding + rand.nextInt(16);
        int c = team1.chemistry + rand.nextInt(16);
        int r = team1.rebounding + rand.nextInt(16);

        double sumStats = (double) (pm + s + g + c + r) * luck;
        // 5.3 is a value calibrated to give most consistent and most realistic
        // basketball scores
        int numPoints = (int) Math.round(sumStats / 5.3);
        return numPoints;
    }

    public static void recursiveSort(team[] teamsList) {
        // organize by games won, if same games won by point difference, if same point
        // difference then by alphabetical order
        int n = teamsList.length;
        team[] temp = new team[n];
        mergeSortHelper(teamsList, 0, n - 1, temp);
    }

    private static void mergeSortHelper(team[] elements, int from, int to, team[] temp) {
        if (from < to) {
            int middle = (from + to) / 2;
            mergeSortHelper(elements, from, middle, temp);
            mergeSortHelper(elements, middle + 1, to, temp);
            merge(elements, from, middle, to, temp);
        }
    }

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