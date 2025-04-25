import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

public class ToyRaffle {
    private PriorityQueue<Toy> toyQueue;  // Очередь с приоритетом для хранения игрушек
    private Random random;                // Генератор случайных чисел
    private String[] ids;                 // Массив для ID игрушек
    private String[] names;              // Массив для названий игрушек
    private int[] weights;               // Массив для весов игрушек

    // Конструктор принимает 3 массива (ID, названия, веса)
    public ToyRaffle(String[] ids, String[] names, int[] weights) {
        if (ids == null || names == null || weights == null || 
            ids.length < 3 || names.length < 3 || weights.length < 3) {
            throw new IllegalArgumentException("Все массивы должны содержать минимум 3 элемента");
        }
        
        this.ids = ids;
        this.names = names;
        this.weights = weights;
        this.random = new Random();
        // Создаем очередь с приоритетом, сортируем по весу (по убыванию)
        this.toyQueue = new PriorityQueue<>((t1, t2) -> t2.weight - t1.weight);
        
        initializeToys();  // Заполняем очередь игрушками
    }

    // Инициализация игрушек из массивов
    private void initializeToys() {
        for (int i = 0; i < ids.length; i++) {
            addToy(new Toy(ids[i], names[i], weights[i]));
        }
    }

    // Метод для добавления новой игрушки
    public void addToy(Toy toy) {
        toyQueue.add(toy);
    }

    // Метод для розыгрыша игрушки (с учетом веса)
    public Toy getToy() {
        int totalWeight = toyQueue.stream().mapToInt(t -> t.weight).sum();
        int randomNumber = random.nextInt(totalWeight) + 1;
        int cumulativeWeight = 0;

        for (Toy toy : toyQueue) {
            cumulativeWeight += toy.weight;
            if (randomNumber <= cumulativeWeight) {
                return toy;
            }
        }

        return null;
    }

    // Метод для проведения розыгрыша и сохранения результатов в файл
    public void performDrawAndSaveResults(String filename, int drawCount) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < drawCount; i++) {
                Toy drawnToy = getToy();
                if (drawnToy != null) {
                    writer.write("Розыгрыш #" + (i + 1) + ": " + drawnToy + "\n");
                }
            }
            writer.write("Розыгрыш завершен успешно.\n");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    // Вложенный класс для представления игрушки
    public static class Toy {
        String id;      // ID игрушки
        String name;    // Название игрушки
        int weight;     // Вес (частота выпадения)

        public Toy(String id, String name, int weight) {
            this.id = id;
            this.name = name;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Игрушка{ID='" + id + "', название='" + name + "', вес=" + weight + "}";
        }
    }
}