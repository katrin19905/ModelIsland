package Application;

import constants.AnimalsListEmodji;
import constants.PlantsListEmodji;
import entities.AbstractEntities.Animal;
import entities.Herbivores.*;
import entities.AbstractEntities.Organism;
import entities.AbstractEntities.Plant;
import entities.Plants.Herb;
import entities.Plants.Shrub;
import entities.Plants.Tree;
import entities.Predators.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Application implements Runnable {
   /* private static volatile ArrayList<Class<? extends Animal>> classesOfAnimals = new ArrayList<>() {{
        add(Bear.class);
        add(Boa.class);
        add(Boar.class);
        add(Buffalo.class);
        add(Caterpillar.class);
        add(Deer.class);
        add(Duck.class);
        add(Goat.class);
        add(Horse.class);
        add(Mouse.class);
        add(Rabbit.class);
        add(Sheep.class);
        add(Eagle.class);
        add(Fox.class);
        add(Wolf.class);
    }};
    */

    private static ArrayList<Organism> listOfAnimalsOnly = new ArrayList<>() {{
        add(new Boar());
        add(new Buffalo());
        add(new Caterpillar());
        add(new Deer());
        add(new Duck());
        add(new Goat());
        add(new Horse());
        add(new Mouse());
        add(new Rabbit());
        add(new Sheep());
        add(new Bear());
        add(new Boa());
        add(new Eagle());
        add(new Fox());
        add(new Wolf());
    }};

    private static ArrayList<Animal> animals = new ArrayList<>() {{
        add(new Boar());
        add(new Buffalo());
        add(new Caterpillar());
        add(new Deer());
        add(new Duck());
        add(new Goat());
        add(new Horse());
        add(new Mouse());
        add(new Rabbit());
        add(new Sheep());
        add(new Bear());
        add(new Boa());
        add(new Eagle());
        add(new Fox());
        add(new Wolf());
    }};

    private static ArrayList<Organism> listOfAllEntities = new ArrayList<>() {{
        addAll(listOfAnimalsOnly);
        add(new Herb());
        add(new Shrub());
        add(new Tree());
    }};

    private static ArrayList<Organism> listOfPlantsOnly = new ArrayList<>() {{
        add(new Herb());
        add(new Shrub());
        add(new Tree());
    }};


    /*private static volatile ArrayList<Class<? extends Plant>> classsesOfPlants = new ArrayList<>() {{
        add(Herb.class);
        add(Shrub.class);
        add(Tree.class);
    }};
     */

    public static CopyOnWriteArrayList<CopyOnWriteArrayList<ConcurrentHashMap<Organism, Integer>>> list = new CopyOnWriteArrayList<>();
    private static AtomicInteger testAtomicInteger = new AtomicInteger(0);
    private static AtomicInteger atomicIntegerOfEatenAnimals = new AtomicInteger(0);
    private static AtomicInteger atomicIntegerOfEatenPlants = new AtomicInteger(0);
    private static AtomicInteger atomicIntegerOfDeadAnimals = new AtomicInteger(0);
    private static AtomicInteger atomicIntegerOfBornAnimals = new AtomicInteger(0);

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Приветствую! Это запрограммированная модель острова \n" +
                "с животными и растениями. \n" +
                "Введи размер острова, только целые числа:"
        );
        Integer sizeX = sc.nextInt();
        Integer sizeY = sc.nextInt();
        System.out.println("Создаем остров с размерами" + sizeX + " и " + sizeY);
        Thread.sleep(500);
        System.out.println("Информация об острове до его заполнения:");
        printInformationOfIsland();
        // создаем локации и заполняем их перед запуском потоков
        generateLocation(sizeX, sizeY);

        System.out.println("Остров сгенерирован!");
        fullLocation(list);
        System.out.println("Заполняем остров...");
        Thread.sleep(500);
        System.out.println("*".repeat(30));
        System.out.println("Локации острова: ");
        // see our Array list:
        list.forEach(System.out::println);
        System.out.println("*".repeat(30));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(4);
        executorService.scheduleAtFixedRate(new Application(), 500, 500,
                TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Генерация закончилась, печатаем статистику...");
        Thread.sleep(500);
        printInformationOfIsland();

    }

    public static void printInformationOfIsland() {
        for (Organism oneEntity : listOfAnimalsOnly) {
            String nameOfEntity = oneEntity.getName();
            String emodji = AnimalsListEmodji.map.get(nameOfEntity);
            int countInLocation = oneEntity.getCountInIsland();
            System.out.println(emodji + " " + nameOfEntity + "s in Island is: " + countInLocation);
        }
        for (Organism oneEntity : listOfPlantsOnly) {
            String nameOfEntity = oneEntity.getName();
            String emodji = PlantsListEmodji.map.get(nameOfEntity);
            int countInLocation = oneEntity.getCountInIsland();
            System.out.println(emodji + " " + nameOfEntity + "s in Island is: " + countInLocation);
        }
        System.out.println("Съедено животных = " + atomicIntegerOfEatenAnimals);
        System.out.println("Съедено растений = " + atomicIntegerOfEatenPlants);
        System.out.println("Умерло животных = " + atomicIntegerOfDeadAnimals);
        System.out.println("Родилось животных = " + atomicIntegerOfBornAnimals);
    }


    public static CopyOnWriteArrayList<CopyOnWriteArrayList<ConcurrentHashMap<Organism, Integer>>>
    generateLocation(int x, int y) {
        list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < x; i++) {
            list.add(i, new CopyOnWriteArrayList<>());
            for (int j = 0; j < y; j++) {
                list.get(i).add(j, new ConcurrentHashMap<>());
            }
        }
        return list;
    }

    public static CopyOnWriteArrayList<CopyOnWriteArrayList<ConcurrentHashMap<Organism, Integer>>>
    fullLocation(CopyOnWriteArrayList<CopyOnWriteArrayList<ConcurrentHashMap<Organism, Integer>>> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(0).size(); j++) {
                createOrganizmsInLocation();
                ConcurrentHashMap<Organism, Integer> stringAtomicIntegerConcurrentHashMap = list.get(i).get(j);
                fullLocationByAnimals(stringAtomicIntegerConcurrentHashMap);
            }
        }
        return list;
    }

    private static void createOrganizmsInLocation() {
        for (Organism organism : listOfAllEntities) {
            organism.createOrganizmInLocation();
        }
    }

    private static void fullLocationByAnimals(ConcurrentHashMap<Organism, Integer> stringAtomicIntegerConcurrentHashMap) {
        stringAtomicIntegerConcurrentHashMap.put(new Bear(),
                Bear.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Boa(),
                Boa.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Boar(),
                Boar.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Buffalo(),
                Buffalo.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Caterpillar(),
                Caterpillar.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Deer(),
                Deer.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Duck(),
                Duck.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Goat(),
                Goat.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Horse(),
                Horse.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Mouse(),
                Mouse.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Rabbit(),
                Rabbit.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Sheep(),
                Sheep.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Eagle(),
                Eagle.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Fox(),
                Fox.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Wolf(),
                Wolf.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Herb(),
                Herb.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Shrub(),
                Shrub.countInLocation.intValue());
        stringAtomicIntegerConcurrentHashMap.put(new Tree(),
                Tree.countInLocation.intValue());
    }

    @Override
    public void run() {

        synchronized (list) {
            int a = testAtomicInteger.incrementAndGet();
            System.out.println(" Поток № " + a);
            list.forEach(System.out::println);
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.get(0).size(); j++) {
                    ConcurrentHashMap<Organism, Integer> stringIntegerConcurrentHashMap = list.get(i).get(j);
                    try {
                        movingAnimals(stringIntegerConcurrentHashMap, i, j);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    growingPlants(stringIntegerConcurrentHashMap, i, j);

                }
            }
            System.out.println("Животные переместились между локациями: ");
            list.forEach(System.out::println);
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.get(0).size(); j++) {
                    ConcurrentHashMap<Organism, Integer> stringIntegerConcurrentHashMap = list.get(i).get(j);

                    entitiesEating(stringIntegerConcurrentHashMap);
                }
            }
            System.out.println("Животные покушали");
            list.forEach(System.out::println);
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.get(0).size(); j++) {
                    ConcurrentHashMap<Organism, Integer> stringIntegerConcurrentHashMap = list.get(i).get(j);

                    reproductionAnimals(stringIntegerConcurrentHashMap);
                }
            }

            System.out.println("Животные размножились");
            list.forEach(System.out::println);
            System.out.println("Статистика по завершению работы потока:");
            printInformationOfIsland();
        }
    }

    private void reproductionAnimals(ConcurrentHashMap<Organism, Integer> stringIntegerConcurrentHashMap) {
        for (Animal animal : animals) {
            Integer countAnimalInLocation = stringIntegerConcurrentHashMap.get(animal);
            int countOfFemale = 0;
            if (countAnimalInLocation > 1) {
                countOfFemale = countAnimalInLocation / 2;
            }
            if (countOfFemale != 0) {
                int countOfReproduction = countOfFemale * animal.getValueOfBreed();
                if (countOfReproduction != 0) {
                    if (countOfReproduction+countAnimalInLocation > animal.getMaxCountInLocation()) {
                        countOfReproduction = animal.getMaxCountInLocation() - countAnimalInLocation;
                    }
                    stringIntegerConcurrentHashMap.replace(animal, countAnimalInLocation, countAnimalInLocation + countOfReproduction);
                    atomicIntegerOfBornAnimals.addAndGet(countOfReproduction);
                }
            }
        }
    }

    public static void entitiesEating(ConcurrentHashMap<Organism, Integer> stringIntegerConcurrentHashMap) {

        for (Organism animal : listOfAnimalsOnly) {
            HashMap<Organism, Integer> food = animal.getFood();
            eatingFood(stringIntegerConcurrentHashMap, animal, food);

        }


    }

    private static void eatingFood(ConcurrentHashMap<Organism, Integer> stringIntegerConcurrentHashMap, Organism animal, HashMap<Organism, Integer> food) {
        // рассматриваем одну локацию и одно животное, пищу этого животного;
        Organism entity = animal;
        //Integer countInLocation = stringIntegerConcurrentHashMap.get(animal);
        // проверяем что получили точно животное, а не растение
        if (!entity.getName().equals("Herb") && !entity.getName().equals("Shrub") && !entity.getName().equals("Tree")) {

            //рандомная вероятность съедания
            int randomValue = ThreadLocalRandom.current().nextInt(100);
            // поиск ближайшей к рандому вероятности
            Integer probabilityEating = 0;
            if (!(food.values().stream().filter(x -> x <= randomValue).toList().isEmpty())) {
                probabilityEating = food.values().stream()
                        .filter(x -> x <= randomValue)
                        .max(Comparator.comparingInt(x -> x))
                        .get();
            } else {
                probabilityEating = food.values().stream()
                        .filter(x -> x >= randomValue)
                        .min(Comparator.comparingInt(x -> x))
                        .get();
            }
            //Organism needFood = null;
            // поиск пищи по полученной вероятности в карте с пищей.
            Organism needFood = getNeedFood(food, probabilityEating);
            changeCountOfAnimalInLocation(needFood, entity, stringIntegerConcurrentHashMap, food);

        }
        //}

    }

    private static void changeCountOfAnimalInLocation(Organism needFood, Organism entity, ConcurrentHashMap<Organism, Integer> stringIntegerConcurrentHashMap, HashMap<Organism, Integer> food) {
        if (needFood != null) {
            //&& stringIntegerConcurrentHashMap.get(needFood) != null) {
            // необходимое количество килограмм одному животному:
            double weightOfFood = entity.getAmountOfFood();
            // количество голодных животных:
            int valueOfHungryAnimals = stringIntegerConcurrentHashMap.get(entity);
            if (valueOfHungryAnimals > 0) {
                // нужное количество пищи всем голодным животным в локации:
                double weightOfFoodForAnimalsInLocation = valueOfHungryAnimals * 1.0 * weightOfFood;
                // количество имеющихся жертв в локации:
                int valueOfFoodInLocation = stringIntegerConcurrentHashMap.get(needFood);
                // вес жертвы:
                double weightOfOneFood = needFood.getWeight();
                // имеющийся общий вес жертв в локации:
                double weightOfFoodInLocation = valueOfFoodInLocation * 1.0 * weightOfOneFood;

                // необходимое число экземпляров пищи голодным животным
                int valueOfFood = (int) Math.floor(weightOfFoodForAnimalsInLocation / weightOfOneFood);

                if (valueOfFood > stringIntegerConcurrentHashMap.get(needFood) && valueOfFoodInLocation != 0) {
                    valueOfFood = stringIntegerConcurrentHashMap.get(needFood);
                }
                //int countInLocationOfFood = stringIntegerConcurrentHashMap.get(needFood);
                if (negativeCountAnimalsAfterShifting(valueOfFoodInLocation, valueOfFood) && valueOfFoodInLocation != 0) {
                    stringIntegerConcurrentHashMap.replace(needFood, valueOfFoodInLocation, valueOfFoodInLocation - valueOfFood);
                    if (needFood.getName().equals("Herb") || needFood.equals("Shrub")) {
                        atomicIntegerOfEatenPlants.addAndGet(valueOfFood);
                    } else {
                        atomicIntegerOfEatenAnimals.addAndGet(valueOfFood);
                    }
                } else if (valueOfFoodInLocation == 0) {
                    //if (!food.isEmpty()) {
                    HashMap<Organism, Integer> newFood = new HashMap<>() {{
                        putAll(food);
                        remove(needFood);

                    }};
                    if (!newFood.isEmpty()) {
                        eatingFood(stringIntegerConcurrentHashMap, entity, newFood);
                    } else {
                        animalsDies(stringIntegerConcurrentHashMap, entity);
                    }
                    //}
                }
            }
        }
    }

    private static void animalsDies(ConcurrentHashMap<Organism, Integer> stringIntegerConcurrentHashMap, Organism entity) {
        Integer valueOfAnimalInLocation = stringIntegerConcurrentHashMap.get(entity);
        stringIntegerConcurrentHashMap.replace(entity, valueOfAnimalInLocation, 0);
        atomicIntegerOfDeadAnimals.addAndGet(valueOfAnimalInLocation);
    }


    private static Organism getNeedFood(HashMap<Organism, Integer> food, Integer integer) {
        Organism needFood = null;
        for (Map.Entry<Organism, Integer> entry : food.entrySet()) {
            Integer value = entry.getValue();
            if (value == integer) {
                needFood = entry.getKey();
                return needFood;
            }
        }
        return null;
    }

    public static void growingPlants(ConcurrentHashMap<Organism, Integer> stringIntegerConcurrentHashMap, int x, int y) {
        Set<Map.Entry<Organism, Integer>> collect = stringIntegerConcurrentHashMap.entrySet().stream().filter(o -> o.getKey().getName().equals("Herb") || o.getKey().getName().equals("Shrub") || o.getKey().getName().equals("Tree")).collect(Collectors.toSet());
        for (Map.Entry<Organism, Integer> stringIntegerEntry : collect) {
            Integer value = stringIntegerEntry.getValue();
            if (value < Plant.PLANT) {
                Integer growingPlants = ThreadLocalRandom.current().nextInt(Plant.PLANT);
                if (growingPlants + value < Plant.PLANT) {
                    //list.get(x).get(y).replace(stringIntegerEntry.getKey(), value, growingPlants+value);
                    stringIntegerEntry.setValue(growingPlants + value);
                } else {
                    //list.get(x).get(y).replace(stringIntegerEntry.getKey(), value, Plant.PLANT);
                    stringIntegerEntry.setValue(Plant.PLANT);
                }
            }
        }
    }

    public static void movingAnimals(ConcurrentHashMap<Organism, Integer> map, int x, int y) throws
            NoSuchFieldException, IllegalAccessException {
        Set<Map.Entry<Organism, Integer>> entries = map.entrySet();
        for (Map.Entry<Organism, Integer> entry : entries) {
            Integer countInLocation = entry.getValue();
            if (countInLocation > 0) {
                String nameOfAnimal = entry.getKey().getName();
                if (!(nameOfAnimal.equals("Herb")) &&
                        !(nameOfAnimal.equals("Shrub")) &&
                        !(nameOfAnimal.equals("Tree")) &&
                        !(nameOfAnimal.equals("Caterpillar"))) {
                    int speed = entry.getKey().getMaxSpeed();
                    Integer maxSpeed = speed;

                    if (maxSpeed >= 2) {
                        maxSpeed = ThreadLocalRandom.current().nextInt(1, speed);
                    }
                    Integer maxCountInLocation = entry.getKey().getMaxCountInLocation();
                    Integer randomShiftingCount = countInLocation;
                    if (countInLocation >= 2) {
                        randomShiftingCount = ThreadLocalRandom.current().nextInt(1, countInLocation);
                    }
                    randomMove(nameOfAnimal, randomShiftingCount, maxSpeed, x, y, maxCountInLocation, entry.getKey());

                }
            }
        }
    }

    private static void randomMove(String nameOfAnimal, Integer countInLocation, Integer maxSpeed,
                                   int x, int y, Integer maxCountInLocation, Organism organism) {
        if (maxSpeed > 0 && checkCountMoreThanZero(list.get(x).get(y).get(organism))) {
            if (chekFirst(x)) {
                if (chekFirst(y)) {
                    changeCountInFirstArray(x, y, maxSpeed, countInLocation, maxCountInLocation, organism);
                }
                changeCountOnlyY(x, y, maxSpeed, countInLocation, nameOfAnimal, maxCountInLocation, organism);
            } else if (chekFirst(y)) {
                changeCountOnlyX(x, y, maxSpeed, countInLocation, nameOfAnimal, maxCountInLocation, organism);
            } else if (checkLastX(x)) {
                if (checkLastY(x, y)) {
                    changeCountInLastArray(x, y, maxSpeed, countInLocation, nameOfAnimal, maxCountInLocation, organism);
                }
                changeCountOnlyY(x, y, maxSpeed, countInLocation, nameOfAnimal, maxCountInLocation, organism);
            } else if (checkLastY(x, y)) {
                changeCountOnlyX(x, y, maxSpeed, countInLocation, nameOfAnimal, maxCountInLocation, organism);
            } else if (countInLocation % 3 == 0) {
                changeCountOnlyY(x, y, maxSpeed, countInLocation, nameOfAnimal, maxCountInLocation, organism);
            } else {
                changeCountOnlyX(x, y, maxSpeed, countInLocation, nameOfAnimal, maxCountInLocation, organism);
            }
        }
    }

    private static void changeCountInLastArray(int x, int y, Integer maxSpeed,
                                               Integer countInLocation, String nameOfAnimal,
                                               Integer maxCountInLocation, Organism organism) {
        if (countInLocation % 2 == 0 && checkCountMoreThanZero(list.get(x).get(y).get(organism))) {
            int x1 = x - maxSpeed;
            if (chekingHeightOfArray(x1)) {
                shiftCountsInLocations(countInLocation, x, y, x1, y, maxCountInLocation, organism);
            } else {
                shiftCountsInLocations(countInLocation, x, y, 0, y, maxCountInLocation, organism);
            }
        } else {
            int y1 = y - maxSpeed;
            if (chekingWidthOfArray(y1)) {
                shiftCountsInLocations(countInLocation, x, y, x, y1, maxCountInLocation, organism);
            } else {
                shiftCountsInLocations(countInLocation, x, y, x, 0, maxCountInLocation, organism);
            }
        }
    }

    private static boolean checkLastY(int x, int y) {
        if (y == list.get(x).size()) return true;
        return false;
    }

    private static boolean checkLastX(int x) {
        if (x == list.size()) return true;
        return false;
    }

    private static boolean checkCountMoreThanZero(Integer countInLocation) {
        if (countInLocation != null & countInLocation > 0) {
            return true;
        }
        return false;
    }

    private static void changeCountOnlyX(int x, int y, Integer maxSpeed, Integer countInLocation,
                                         String nameOfAnimal, Integer maxCountInLocation, Organism organism) {
        if (countInLocation % 2 == 0 && checkCountMoreThanZero(list.get(x).get(y).get(organism))) {
            int x1 = x - maxSpeed;
            if (chekingWidthOfArray(x1)) {
                shiftCountsInLocations(countInLocation, x, y, x1, y, maxCountInLocation, organism);
            } else {
                shiftCountsInLocations(countInLocation, x, y, 0, y, maxCountInLocation, organism);
            }
        } else {
            int x1 = x + maxSpeed;
            if (chekingWidthOfArray(x1)) {
                shiftCountsInLocations(countInLocation, x, y, x1, y, maxCountInLocation, organism);
            } else {
                shiftCountsInLocations(countInLocation, x, y, list.size() - 1, y, maxCountInLocation, organism);
            }
        }
    }

    private static void changeCountOnlyY(int x, int y, Integer maxSpeed, Integer countInLocation,
                                         String nameOfAnimal, Integer maxCountInLocation, Organism organism) {
        if (countInLocation % 2 == 0 && checkCountMoreThanZero(list.get(x).get(y).get(organism))) {
            int y1 = y - maxSpeed;
            if (chekingWidthOfArray(y1)) {
                shiftCountsInLocations(countInLocation, x, y, x, y1, maxCountInLocation, organism);
            } else {
                shiftCountsInLocations(countInLocation, x, y, x, 0, maxCountInLocation, organism);
            }
        } else {
            int y1 = y + maxSpeed;
            if (chekingWidthOfArray(y1)) {
                shiftCountsInLocations(countInLocation, x, y, x, y1, maxCountInLocation, organism);
            } else {
                shiftCountsInLocations(countInLocation, x, y, x, list.get(x).size() - 1, maxCountInLocation, organism);
            }
        }

    }

    private static boolean chekingWidthOfArray(Integer y1) {
        if (y1 > 0 && y1 < list.get(0).size()) {
            return true;
        }
        return false;
    }

    private static void changeCountInFirstArray(int x, int y, Integer maxSpeed,
                                                Integer countInLocation, Integer maxCountInLocation, Organism organism) {
        if (countInLocation % 2 == 0 && checkCountMoreThanZero(list.get(x).get(y).get(organism))) {
            int x1 = x + maxSpeed;
            if (chekingHeightOfArray(x1)) {
                shiftCountsInLocations(countInLocation, x, y, x1, y, maxCountInLocation, organism);
            } else {
                shiftCountsInLocations(countInLocation, x, y, list.size() - 1, y, maxCountInLocation, organism);
            }
        } else {
            int y1 = y + maxSpeed;
            if (chekingWidthOfArray(y1)) {
                shiftCountsInLocations(countInLocation, x, y, x, y1, maxCountInLocation, organism);
            } else {
                shiftCountsInLocations(countInLocation, x, y, x, list.get(x).size() - 1, maxCountInLocation, organism);
            }
        }
    }

    private static boolean chekingHeightOfArray(int x1) {
        if (x1 > 0 && x1 < list.size()) {
            return true;
        }
        return false;
    }

    private static boolean chekFirst(int value) {
        if (value == 0) return true;
        else return false;
    }

    private static void shiftCountsInLocations(Integer countInLocation, int x, int y,
                                               int x1, int y1, Integer maxCountInLocation, Organism organism) {
        int valueInOldHashMap = list.get(x).get(y).get(organism);
        int valueInNewHashMap = 0;
        if (list.get(x1).get(y1).get(organism) != null) {
            valueInNewHashMap = list.get(x1).get(y1).get(organism);
        }
        if (!checkNumberOfAnimalInLocation(valueInNewHashMap, maxCountInLocation, countInLocation)) {
            if (negativeCountAnimalsAfterShifting(valueInOldHashMap, countInLocation)) {
                // меняем значение числа животных в старой клетке
                list.get(x).get(y).replace(organism,
                        valueInOldHashMap, valueInOldHashMap - countInLocation);
                // меняем значение в новой клетке
                list.get(x1).get(y1).replace(organism,
                        valueInNewHashMap, valueInNewHashMap + countInLocation);
                //System.out.println("переместили "+countInLocation+" животных "+key + " из клетки "+x +" " + y + " в клетку "+x1 +" " + y1);
            }
        } else {
            int movingAnimals = maxCountInLocation - valueInNewHashMap;
            if (negativeCountAnimalsAfterShifting(valueInOldHashMap, movingAnimals)) {
                // меняем значение числа животных в старой клетке
                list.get(x).get(y).replace(organism,
                        valueInOldHashMap, valueInOldHashMap - movingAnimals);
                // меняем значение в новой клетке
                list.get(x1).get(y1).replace(organism,
                        valueInNewHashMap, valueInNewHashMap + movingAnimals);
                //System.out.println("переместили "+movingAnimals+" животных "+key+ " из клетки "+x +" " + y + " в клетку "+x1 +" " + y1);
            }
        }

    }

    private static boolean checkNumberOfAnimalInLocation(int numberOfAnimals, int maxValue, int numberOfAddingAnimals) {
        if (numberOfAnimals + numberOfAddingAnimals > maxValue) {
            return true;
        }
        return false;
    }

    private static boolean negativeCountAnimalsAfterShifting(int currentNumberOfAnimals, int numberOfAddingAnimals) {
        if (currentNumberOfAnimals - numberOfAddingAnimals >= 0) {
            return true;
        }
        return false;
    }
}
