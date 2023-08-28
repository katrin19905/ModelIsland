package entities.Plants;

import constants.Parameters;
import entities.AbstractEntities.Organism;
import entities.AbstractEntities.Plant;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Herb extends Plant {//трава
    private double weight = Parameters.WEIGHT_PLANT;

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Herb herb)) return false;
        return Double.compare(herb.getWeight(), getWeight()) == 0 && Objects.equals(getName(), herb.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getName());
    }

    static Herb[] herbs;
    private String name = Parameters.NAME_HERB;

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
            firstPlantsInLocation[i] = new Herb();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstPlantsInLocation;
    }

    public static void setCountInLocation(AtomicInteger countInLocation) {
        Herb.countInLocation = countInLocation;
    }

    @Override
    public Organism[] createFirstOrganizmInLocation() {
        herbs = new Herb[ThreadLocalRandom.current().nextInt(PLANT)];
        return herbs;
    }
}
