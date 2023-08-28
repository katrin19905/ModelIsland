package entities.Predators;

import constants.Parameters;
import entities.AbstractEntities.AbstractPredator;
import entities.Herbivores.*;
import entities.AbstractEntities.Organism;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Boa extends AbstractPredator {
    private int valueOfBreed = 15;

    @Override
    public int getValueOfBreed() {
        return valueOfBreed;
    }
    private double weight = Parameters.WEIGHT_BOA;

    @Override
    public double getWeight() {
        return weight;
    }
    private int maxCountInLocation = Parameters.MAX_COUNT_BOA;
    private String name = Parameters.NAME_BOA;
    static Boa[] boas;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_BOA;
    private double amountOfFood = Parameters.FOOD_BOA;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Boa boa)) return false;
        return Double.compare(boa.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == boa.getMaxCountInLocation() && getMaxSpeed() == boa.getMaxSpeed() && Double.compare(boa.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), boa.getName()) && Objects.equals(getFood(), boa.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getMaxSpeed(), getAmountOfFood(), getFood());
    }

    private HashMap<Organism, Integer> food = new HashMap<>(){{
        put(new Fox(), 15);
        put(new Rabbit(), 20);
        put(new Mouse(), 40);
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
        countInLocation.set(0);
        Organism[] firstAnimalsInLocation =  createFirstOrganizmInLocation();
        for (int i = 0; i < firstAnimalsInLocation.length; i++) {
            firstAnimalsInLocation[i] = new Boa();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }
    @Override
    public Organism[] createFirstOrganizmInLocation() {
        boas = new Boa[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return boas;
    }
}
