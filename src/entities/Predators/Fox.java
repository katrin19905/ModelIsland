package entities.Predators;

import constants.Parameters;
import entities.AbstractPredator;
import entities.Herbivores.*;
import entities.Organism;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Fox extends AbstractPredator {
    private double weight = Parameters.WEIGHT_FOX;

    @Override
    public double getWeight() {
        return weight;
    }
    private int maxCountInLocation = Parameters.MAX_COUNT_FOX;
    private String name = Parameters.NAME_FOX;
    static Fox[] foxes;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_FOX;
    private double amountOfFood = Parameters.FOOD_FOX;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fox fox)) return false;
        return Double.compare(fox.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == fox.getMaxCountInLocation() && getMaxSpeed() == fox.getMaxSpeed() && Double.compare(fox.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), fox.getName()) && Objects.equals(getFood(), fox.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getMaxSpeed(), getAmountOfFood(), getFood());
    }

    private HashMap<Organism, Integer> food = new HashMap<>(){{
        put(new Rabbit(), 70);
        put(new Mouse(), 90);
        put(new Duck(), 60);
        put(new Caterpillar(), 40);
    }};
    @Override
    public HashMap<Organism, Integer> getFood() {
        return food;
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int getMaxCountInLocation() {
        return maxCountInLocation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCountInIsland() {
        return countInIsland.get();
    }

    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public Organism[] createOrganizmInLocation() {
        countInLocation.set(0);
        Organism[] firstAnimalsInLocation = createFirstOrganizmInLocation();
        for (int i = 0; i < firstAnimalsInLocation.length; i++) {
            firstAnimalsInLocation[i] = new Fox();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }
    @Override
    public Organism[] createFirstOrganizmInLocation() {
        foxes = new Fox[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return foxes;
    }
}
