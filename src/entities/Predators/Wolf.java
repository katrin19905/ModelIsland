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

public class Wolf extends AbstractPredator {
    private double weight = Parameters.WEIGHT_WOLF;

    @Override
    public double getWeight() {
        return weight;
    }
    private int maxCountInLocation = Parameters.MAX_COUNT_WOLF;
    private String name = Parameters.NAME_WOLF;
    static Wolf[] wolfs;
    private double amountOfFood = Parameters.FOOD_WOLF;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wolf wolf)) return false;
        return Double.compare(wolf.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == wolf.getMaxCountInLocation() && Double.compare(wolf.getAmountOfFood(), getAmountOfFood()) == 0 && getMaxSpeed() == wolf.getMaxSpeed() && Objects.equals(getName(), wolf.getName()) && Objects.equals(getFood(), wolf.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getAmountOfFood(), getFood(), getMaxSpeed());
    }

    private HashMap<Organism, Integer> food = new HashMap<>(){{
        put(new Horse(), 10);
        put(new Deer(), 15);
        put(new Rabbit(), 60);
        put(new Mouse(), 80);
        put(new Goat(), 65);
        put(new Sheep(), 70);
        put(new Boar(), 20);
        put(new Buffalo(), 8);
        put(new Duck(), 40);
    }};
    @Override
    public HashMap<Organism, Integer> getFood() {
        return food;
    }

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_WOLF;
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
            firstAnimalsInLocation[i] = new Wolf();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        wolfs = new Wolf[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return wolfs;
    }


}
