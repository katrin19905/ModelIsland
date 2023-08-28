package entities.Plants;

import constants.Parameters;
import entities.Organism;
import entities.Plant;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Shrub extends Plant { //кустарник
    private double weight = Parameters.WEIGHT_PLANT;

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shrub shrub)) return false;
        return Double.compare(shrub.getWeight(), getWeight()) == 0 && Objects.equals(getName(), shrub.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getName());
    }

    static Shrub[] shrubs;
    private String name = Parameters.NAME_SHRUB;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);
    @Override
    public String toString() {
        return name;
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
    public Organism[] createOrganizmInLocation() {
        // сбросим на ноль countBearsInLocation и посчитаем для локации
        Organism[] firstPlantsInLocation = createFirstOrganizmInLocation();
        countInLocation.set(0);
        for (int i = 0; i < firstPlantsInLocation.length; i++) {
            firstPlantsInLocation[i] = new Shrub();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstPlantsInLocation;
    }

    public static void setCountInLocation(AtomicInteger countInLocation) {
        Shrub.countInLocation = countInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        shrubs = new Shrub[ThreadLocalRandom.current().nextInt(PLANT)];
        return shrubs;
    }
}
