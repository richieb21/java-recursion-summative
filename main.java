
// importing necessary libraries
import java.util.*;

class Main {
    public static void main(String[] args) {
        // random object to use random methods
        Random rand = new Random();

        // String arrays of city and animal names
        String[] cities = { "Toronto", "Milwaukee", "Los Angeles", "New York", "Orlando", "Memphis", "Kansas City",
                "Dallas" };
        String[] animals = { "Raptors", "Bucks", "Hawks", "Grizzlies", "Night Owls", "Falcons", "Bulls", "Leopards" };

        // initalizing array list holding duplicate names and array of teams
        ArrayList<String> usedNames = new ArrayList<String>();
        team[] simulation = new team[8];

        // looping to create non-duplicate names and initialize team objects into an
        // array of team objects
        int i = 0;
        while (i < 8) {
            String cityName = cities[rand.nextInt(cities.length)];
            String animalName = animals[rand.nextInt(animals.length)];

            if (usedNames.contains(cityName) || usedNames.contains(animalName)) {
                continue;
            } else {
                usedNames.add(cityName);
                usedNames.add(animalName);
            }
            simulation[i] = new team(cityName, animalName);
            i++;
        }
        // creating a teamObject to run playoffs and simulations
        team teamObject = new team("x", "y");

        // reassigning the simulation team array to hold the standings
        simulation = teamObject.simulateSeason(simulation);

        // simulating play offs
        teamObject.playOffSimulate(simulation);
    }
}
