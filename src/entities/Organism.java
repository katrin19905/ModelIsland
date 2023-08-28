package entities;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;

public abstract class Organism  {
    private double weight;
    private int maxCountInLocation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organism organism)) return false;
        return Double.compare(organism.getWeight(), getWeight()) == 0 && getMaxCountInLocation() == organism.getMaxCountInLocation() && getMaxSpeed() == organism.getMaxSpeed() && Double.compare(organism.getAmountOfFood(), getAmountOfFood()) == 0 && Objects.equals(getName(), organism.getName()) && Objects.equals(getFood(), organism.getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getMaxCountInLocation(), getMaxSpeed(), getName(), getFood(), getAmountOfFood());
    }

    private int maxSpeed;
    private String name;
    private HashMap<Organism, Integer> food;
    public double amountOfFood;
    public double getAmountOfFood() {
        return amountOfFood;
    }
    public abstract Organism[] createOrganizmInLocation();
    public abstract Organism[] createFirstOrganizmInLocation();
    public double getWeight() {
        return weight;
    }
    public int getMaxSpeed() {
        return maxSpeed;
    }
    public int getMaxCountInLocation() {
        return maxCountInLocation;
    }
    public String getName() {
        return this.getName();
    }
    public HashMap<Organism, Integer> getFood() {
        return food;
    }
    abstract public int getCountInIsland();


}
