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

public class Deer extends AbstractHerbivore {
    private double weight = Parameters.WEIGHT_DEER;

    @Override
    public double getWeight() {
        return weight;
    }
    private int maxCountInLocation = Parameters.MAX_COUNT_DEER;
    private String name = Parameters.NAME_DEER;
    static Deer[] deers;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_DEER;
    private double amountOfFood = Parameters.FOOD_DEER;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    private HashMap<Organism, Integer> food = new HashMap<>(){{
        put(new Herb(), 90);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deer deer)) return false;
        return Double.compare(deer.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == deer.getMaxCountInLocation() && getMaxSpeed() == deer.getMaxSpeed() && Double.compare(deer.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), deer.getName()) && Objects.equals(getFood(), deer.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getMaxSpeed(), getAmountOfFood(), getFood());
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
            firstAnimalsInLocation[i] = new Deer();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        deers = new Deer[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return deers;
    }

}
