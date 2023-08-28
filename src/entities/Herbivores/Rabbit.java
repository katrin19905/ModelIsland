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

public class Rabbit extends AbstractHerbivore {
    private double weight = Parameters.WEIGHT_RABBIT;

    @Override
    public double getWeight() {
        return weight;
    }
    private int maxCountInLocation = Parameters.MAX_COUNT_RABBIT;
    private String name = Parameters.NAME_RABBIT;
    static Rabbit[] rabbits;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_RABBIT;
    private double amountOfFood = Parameters.FOOD_RABBIT;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    private HashMap<Organism, Integer> food = new HashMap<>(){{
        put(new Herb(), 80);
        put(new Shrub(), 90);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rabbit rabbit)) return false;
        return Double.compare(rabbit.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == rabbit.getMaxCountInLocation() && getMaxSpeed() == rabbit.getMaxSpeed() && Double.compare(rabbit.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), rabbit.getName()) && Objects.equals(getFood(), rabbit.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getMaxSpeed(), getAmountOfFood(), getFood());
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
            firstAnimalsInLocation[i] = new Rabbit();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        rabbits = new Rabbit[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return rabbits;
    }

}
