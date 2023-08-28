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

public class Mouse extends AbstractHerbivore {
    private int valueOfBreed = 5;

    @Override
    public int getValueOfBreed() {
        return valueOfBreed;
    }
    private double weight = Parameters.WEIGHT_MOUSE;

    @Override
    public double getWeight() {
        return weight;
    }
    private int maxCountInLocation = Parameters.MAX_COUNT_MOUSE;
    private String name = Parameters.NAME_MOUSE;
    static Mouse[] mouses;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    private int maxSpeed = Parameters.MAX_SPEED_MOUSE;
    private double amountOfFood = Parameters.FOOD_MOUSE;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mouse mouse)) return false;
        return Double.compare(mouse.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == mouse.getMaxCountInLocation() && getMaxSpeed() == mouse.getMaxSpeed() && Double.compare(mouse.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), mouse.getName()) && Objects.equals(getFood(), mouse.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getName(), getMaxSpeed(), getAmountOfFood(), getFood());
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
            firstAnimalsInLocation[i] = new Mouse();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstAnimalsInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        mouses = new Mouse[ThreadLocalRandom.current().nextInt(maxCountInLocation)];
        return mouses;
    }

}
