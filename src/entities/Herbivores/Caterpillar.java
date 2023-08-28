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

public class Caterpillar extends AbstractHerbivore {
    private int valueOfBreed = 100;

    @Override
    public int getValueOfBreed() {
        return valueOfBreed;
    }
    private double weight = Parameters.WEIGHT_CATERPILLAR;

    @Override
    public double getWeight() {
        return weight;
    }
    private int maxCountInLocation = Parameters.MAX_COUNT_CATERPILLAR;
    private String name = Parameters.NAME_CATERPILLAR;
    static Caterpillar[] caterpillars;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_CATERPILLAR;
    private double amountOfFood = Parameters.FOOD_CATERPILLAR;

    @Override
    public double getAmountOfFood() {
        return amountOfFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Caterpillar that)) return false;
        return Double.compare(that.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == that.getMaxCountInLocation() && getMaxSpeed() == that.getMaxSpeed() && Double.compare(that.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), that.getName()) && Objects.equals(getFood(), that.getFood());
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
            firstAnimalsInLocation[i] = new Caterpillar();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        caterpillars = new Caterpillar[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return caterpillars;
    }

}
