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

public class Duck extends AbstractHerbivore {
    private double weight = Parameters.WEIGHT_DUCK;

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Duck duck)) return false;
        return Double.compare(duck.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == duck.getMaxCountInLocation() && getMaxSpeed() == duck.getMaxSpeed() && Double.compare(duck.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), duck.getName()) && Objects.equals(getFood(), duck.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getMaxSpeed(), getAmountOfFood(), getFood());
    }

    private int maxCountInLocation = Parameters.MAX_COUNT_DUCK;
    private String name = Parameters.NAME_DUCK;
    static Duck[] ducks;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_DUCK;
    private double amountOfFood = Parameters.FOOD_DUCK;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    private HashMap<Organism, Integer> food = new HashMap<>(){{
        put(new Caterpillar(), 90);
        put(new Herb(), 80);
        put(new Shrub(), 95);
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
            firstAnimalsInLocation[i] = new Duck();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        ducks = new Duck[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return ducks;
    }
}
