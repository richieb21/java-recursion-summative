import java.util.*;

class Main {
    public static void main(String[] args) {
        Random rand = new Random();

        String[] cities = { "Toronto", "Milwaukee", "Los Angeles", "New York", "Orlando", "Memphis", "Kansas City",
                "Dallas" };
        String[] animals = { "Raptors", "Bucks", "Hawks", "Grizzlies", "Night Owls", "Falcons", "Bulls", "Leopards" };

        ArrayList<String> usedNames = new ArrayList<String>();
        team[] simulation = new team[8];

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
        team teamObject = new team("x", "y");
        simulation = teamObject.simulateSeason(simulation);
    }
}