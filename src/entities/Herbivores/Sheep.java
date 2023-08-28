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

public class Sheep extends AbstractHerbivore {
    private int valueOfBreed = 2;

    @Override
    public int getValueOfBreed() {
        return valueOfBreed;
    }
    private double weight = Parameters.WEIGHT_SHEEP;

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sheep sheep)) return false;
        return Double.compare(sheep.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == sheep.getMaxCountInLocation() && getMaxSpeed() == sheep.getMaxSpeed() && Double.compare(sheep.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), sheep.getName()) && Objects.equals(getFood(), sheep.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getMaxSpeed(), getAmountOfFood(), getFood());
    }

    private int maxCountInLocation = Parameters.MAX_COUNT_SHEEP;
    private String name = Parameters.NAME_SHEEP;
    static Sheep[] sheeps;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_SHEEP;
    private double amountOfFood = Parameters.FOOD_SHEEP;

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
            firstAnimalsInLocation[i] = new Sheep();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        sheeps = new Sheep[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return sheeps;
    }

}
