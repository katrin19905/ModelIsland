package entities.Herbivores;

import constants.Parameters;
import entities.AbstractEntities.AbstractHerbivore;
import entities.AbstractEntities.Organism;
import entities.Plants.Herb;
import entities.Plants.Shrub;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Buffalo extends AbstractHerbivore {
    private int valueOfBreed = 1;

    @Override
    public int getValueOfBreed() {
        return valueOfBreed;
    }
    private double weight = Parameters.WEIGHT_BUFFALO;

    @Override
    public double getWeight() {
        return weight;
    }
    private int maxCountInLocation = Parameters.MAX_COUNT_BUFFALO;
    private String name = Parameters.NAME_BUFFALO;
    static Buffalo[] buffalos;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_BUFFALO;
    private double amountOfFood = Parameters.FOOD_BUFFALO;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Buffalo buffalo)) return false;
        return Double.compare(buffalo.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == buffalo.getMaxCountInLocation() && getMaxSpeed() == buffalo.getMaxSpeed() && Double.compare(buffalo.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), buffalo.getName()) && Objects.equals(getFood(), buffalo.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getMaxSpeed(), getAmountOfFood(), getFood());
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
        firstAnimalsInLocation[i] = new Buffalo();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        buffalos = new Buffalo[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return buffalos;
    }

}
