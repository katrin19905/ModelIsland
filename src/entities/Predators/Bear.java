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

public class Bear extends AbstractPredator {
    private double weight = Parameters.WEIGHT_BEAR;

    @Override
    public double getWeight() {
        return weight;
    }
    private int maxCountInLocation = Parameters.MAX_COUNT_BEAR;//медведь
    private String name = Parameters.NAME_BEAR;
    static Bear[] bears;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_BEAR;
    private double amountOfFood = Parameters.FOOD_BEAR;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bear bear)) return false;
        return Double.compare(bear.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == bear.getMaxCountInLocation() && getMaxSpeed() == bear.getMaxSpeed() && Double.compare(bear.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), bear.getName()) && Objects.equals(getFood(), bear.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getMaxSpeed(), getAmountOfFood(), getFood());
    }

    private HashMap<Organism, Integer> food = new HashMap<>(){{
        put(new Boa(), 75);
        put(new Horse(), 40);
        put(new Deer(), 80);
        put(new Rabbit(), 85);
        put(new Mouse(), 90);
        put(new Goat(), 70);
        put(new Sheep(), 65);
        put(new Boar(), 50);
        put(new Buffalo(), 20);
        put(new Duck(), 10);
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
        // сбросим на ноль countBearsInLocation и посчитаем для локации
        countInLocation.set(0);
        Organism[] firstAnimalsInLocation = createFirstOrganizmInLocation();
        for (int i = 0; i < firstAnimalsInLocation.length; i++) {
            firstAnimalsInLocation[i] = new Bear();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }
    @Override
    public Organism[] createFirstOrganizmInLocation() {
        bears = new Bear[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return bears;
    }
}
