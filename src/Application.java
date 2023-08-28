import entities.Location.Location;
import entities.Organism;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Application {

    private static CopyOnWriteArrayList<CopyOnWriteArrayList<ConcurrentHashMap<Organism, Integer>>> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        //Location location = new Location();
        System.out.println("before generate and fulling locations:");
        Location.printInformationOfIsland();
        CopyOnWriteArrayList<CopyOnWriteArrayList<ConcurrentHashMap<Organism, Integer>>> newList = Location.generateLocation(20, 20);

        System.out.println("generate is success");
        Location.fullLocation(newList);
        System.out.println("full location");
        System.out.println("*".repeat(30));
        System.out.println("Array List: ");
        // see our Array list:
        newList.forEach(System.out::println);

        System.out.println("*".repeat(30));
        //for (int count = 0; count > 10; count++) {

        animalsLiving(newList); //1
        animalsLiving(newList); //2
        animalsLiving(newList); //3
        animalsLiving(newList); //4
        animalsLiving(newList); //5
        animalsLiving(newList); //6
        animalsLiving(newList); //7
        animalsLiving(newList); //8
        animalsLiving(newList); //9
        animalsLiving(newList); //10
        //}

    }

    private static void animalsLiving(CopyOnWriteArrayList<CopyOnWriteArrayList<ConcurrentHashMap<Organism, Integer>>> newList) {
        for (int i = 0; i < newList.size(); i++) {
            for (int j = 0; j < newList.get(0).size(); j++) {
                ConcurrentHashMap<Organism, Integer> stringIntegerConcurrentHashMap = newList.get(i).get(j);
                try {
                    Location.movingAnimals(stringIntegerConcurrentHashMap, i, j);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                //Location.growingPlants(stringIntegerConcurrentHashMap, i, j);

            }
        }
        System.out.println("end move");
        newList.forEach(System.out::println);
        Location.printInformationOfIsland();

        for (int i = 0; i < newList.size(); i++) {
            for (int j = 0; j < newList.get(0).size(); j++) {
                ConcurrentHashMap<Organism, Integer> stringIntegerConcurrentHashMap = newList.get(i).get(j);

                Location.entitiesEating(stringIntegerConcurrentHashMap);

            }
        }
        System.out.println("end eat");
        newList.forEach(System.out::println);
        Location.printInformationOfIsland();
    }
}
