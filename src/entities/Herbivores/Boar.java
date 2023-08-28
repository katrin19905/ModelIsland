package entities.Herbivores;

import constants.Parameters;
import entities.AbstractHerbivore;
import entities.Organism;
import entities.Plants.Herb;
import entities.Plants.Shrub;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Boar extends AbstractHerbivore {
    private double weight = Parameters.WEIGHT_BOAR;

    @Override
    public double getWeight() {
        return weight;
    }

    static Boar[] boars;
    private String name = Parameters.NAME_BOAR;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_BOAR;
    private int maxCountInLocation = Parameters.MAX_COUNT_BOAR;//кабан
    private double amountOfFood = Parameters.FOOD_BOAR;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Boar boar)) return false;
        return Double.compare(boar.getWeight(), getWeight()) == 0 && getMaxSpeed() == boar.getMaxSpeed() && getMaxCountInLocation() == boar.getMaxCountInLocation() && Double.compare(boar.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), boar.getName()) && Objects.equals(getFood(), boar.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getName(), getMaxSpeed(), getMaxCountInLocation(), getAmountOfFood(), getFood());
    }

    private HashMap<Organism, Integer> food = new HashMap<>(){



        {
        put(new Mouse(), 50);
        put(new Caterpillar(), 80);
        put(new Herb(), 90);
        put(new Shrub(), 95);
    }};
    @Override
    public HashMap<Organism, Integer> getFood() {
        return food;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
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
    public int getMaxCountInLocation() {
        return maxCountInLocation;
    }

    @Override
    public Organism[] createOrganizmInLocation() {
        countInLocation.set(0);
        Organism[] firstAnimalsInLocation =  createFirstOrganizmInLocation();
        for (int i = 0; i < firstAnimalsInLocation.length; i++) {
            firstAnimalsInLocation[i] = new Boar();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        boars = new Boar[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return boars;
    }


}
