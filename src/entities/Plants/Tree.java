package entities.Plants;

import constants.Parameters;
import entities.Organism;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static entities.Plant.PLANT;

public class Tree extends Organism { //дерево
    private double weight = Parameters.WEIGHT_PLANT;

    @Override
    public double getWeight() {
        return weight;
    }
    static Tree[] trees;
    private String name = Parameters.NAME_TREE;

    public static volatile AtomicInteger countInIsland = new AtomicInteger(0);
    public static AtomicInteger countInLocation = new AtomicInteger(0);

    public static void setCountInLocation(AtomicInteger countInLocation) {
        Tree.countInLocation = countInLocation;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tree tree)) return false;
        return Double.compare(tree.getWeight(), getWeight()) == 0 && Objects.equals(getName(), tree.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getName());
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
        countInLocation.set(0);
        Organism[] firstPlantsInLocation = createFirstOrganizmInLocation();
        for (int i = 0; i < firstPlantsInLocation.length; i++) {
            firstPlantsInLocation[i] = new Tree();
            countInIsland.incrementAndGet();
            countInLocation.incrementAndGet();
        }
        return firstPlantsInLocation;
    }
    @Override
    public Organism[] createFirstOrganizmInLocation() {
        trees = new Tree[ThreadLocalRandom.current().nextInt(PLANT)];
        return trees;
    }


}
