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

public class Horse extends AbstractHerbivore {
    private double weight = Parameters.WEIGHT_HORSE;

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Horse horse)) return false;
        return Double.compare(horse.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == horse.getMaxCountInLocation() && getMaxSpeed() == horse.getMaxSpeed() && Double.compare(horse.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), horse.getName()) && Objects.equals(getFood(), horse.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getMaxSpeed(), getAmountOfFood(), getFood());
    }

    private int maxCountInLocation = Parameters.MAX_COUNT_HORSE;
    private String name = Parameters.NAME_HORSE;
    static Horse[] horses;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_HORSE;
    private double amountOfFood = Parameters.FOOD_HORSE;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    private HashMap<Organism, Integer> food = new HashMap<>(){{
        put(new Herb(), 90);
        put(new Shrub(), 80);
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
            firstAnimalsInLocation[i] = new Horse();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        horses = new Horse[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return horses;
    }
}
