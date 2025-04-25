public class Main {
    public static void main(String[] args) {
        // Инициализируем данные
        String[] ids = {"1", "2", "3", "4"};
        String[] names = {"Кукла", "Машинка", "Конструктор", "Пазл"};
        int[] weights = {10, 20, 30, 40}; // Чем больше вес, тем выше вероятность выпадения
        
        // Создаем систему розыгрыша
        ToyRaffle raffle = new ToyRaffle(ids, names, weights);
        
        // Добавляем дополнительные игрушки при необходимости
        raffle.addToy(new ToyRaffle.Toy("5", "Медвежонок", 15));
        
        // Проводим 10 розыгрышей и сохраняем результаты в файл
        raffle.performDrawAndSaveResults("результаты_розыгрыша.txt", 10);
        
        System.out.println("Розыгрыш завершен. Результаты сохранены в файл 'результаты_розыгрыша.txt'");
    }
}