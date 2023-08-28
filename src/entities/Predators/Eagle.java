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

public class Eagle extends AbstractPredator {
    private double weight = Parameters.WEIGHT_EAGLE;
    @Override
    public double getWeight() {
        return weight;
    }
    private int maxCountInLocation = Parameters.MAX_COUNT_EAGLE;
    private String name = Parameters.NAME_EAGLE;
    static Eagle[] eagles;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_EAGLE;
    private double amountOfFood = Parameters.FOOD_EAGLE;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Eagle eagle)) return false;
        return Double.compare(eagle.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == eagle.getMaxCountInLocation() && getMaxSpeed() == eagle.getMaxSpeed() && Double.compare(eagle.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), eagle.getName()) && Objects.equals(getFood(), eagle.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getMaxSpeed(), getAmountOfFood(), getFood());
    }

    private HashMap<Organism, Integer> food = new HashMap<>(){{
        put(new Fox(), 10);
        put(new Rabbit(), 90);
        put(new Mouse(), 70);
        put(new Duck(), 80);
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
            firstAnimalsInLocation[i] = new Eagle();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;

    }
    @Override
    public Organism[] createFirstOrganizmInLocation() {
        eagles = new Eagle[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return eagles;
    }
}
