package Server.Managers;

import Client.Ask;
import Client.Models.Vehicle;
import Common.Request;
import Common.Response;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;
import Server.exeptions.NumberValueExeption;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Класс отвечающий за работу с коллекцией и все методы
 */
public class CollectionManager {
    private int currid = 1;
    public static LinkedHashMap<String, Vehicle> table = new LinkedHashMap<>();
    private ZonedDateTime previous;
    private ZonedDateTime last;
    public static ZonedDateTime zonedDateTime;

    public CollectionManager() {
        table = new LinkedHashMap<>();
        zonedDateTime = ZonedDateTime.now();
        new OperationWithId();
    }

    /**
     * Добавление нового траспорта в коллекцию
     * @param key - ключ
     * @param vehicle - транспорт
     */
    public static void add(String key, Response response, Vehicle vehicle) {
        if (table == null) {
            table = new LinkedHashMap<>();
        }
        table.put(key, vehicle);
        vehicle.setId(vehicle.getId());
        response.setAnswer("Элемент успешно добавлен.");
    }

    /**
     * Функция подающая коллекцию на выход.
     * @return - возвращает коллекцию
     */
    public static LinkedHashMap<String, Vehicle> getTable() {
        return table;
    }

    public Vehicle getVehbyID(int id){
        return table.get(id);
    }

    public int getId(){
        while(getVehbyID(currid++)!= null){
            return currid;
        }
        return 0;
    }
    public static ZonedDateTime getZonedDateTime(){
        return zonedDateTime;
    }

    /**
     * Функция котороя обновляет значения коллекции id которого равен заданному
     * @param key - ключ
     * @throws Ask.AskBreak
     * @throws NumberValueExeption
     */
    public static void updateIdEquels(String key, Response response, Vehicle vehicle) throws Ask.AskBreak, NumberValueExeption {
        Console console = new StandartConsole();
        boolean fl = false;
        for(String keys : table.keySet()){
            if (key.equals(table.get(keys).getId().toString())){
                CollectionManager.add(keys, response, vehicle);
                fl = true;
                response.setAnswer("Элемент успешно заменен.");
                break;
            }
        }
        if (!fl) response.setAnswer("По указанному id ничего не лежит.");
    }

    /**
     * Удаляет значения элементов по заданному ключу
     * @param key - ключ
     */
    public static void deleteElementByKey(String key, Response response){
        Console console = new StandartConsole();
        boolean fl = false;
        for(String keys : table.keySet()){
            if (key.equals(keys)){
                OperationWithId.deleateId(table.get(key).getId());
                table.remove(key);
                fl = true;
                response.setAnswer("Элемент коллекции успешно удален.");
                break;
            }
        }
        if (!fl) response.setAnswer("По данному ключу ничего не лежит.");
    }

    /**
     * Функция которая осуществляет отчистку коллекции
     */
    public static void clearCollection(Response response){
        Console console = new StandartConsole();
        try{
            table.clear();
            OperationWithId.deleatAllId();
            response.setAnswer("Коллекция успешно очищена.");
        }catch (Exception e){
            response.setAnswer("Произошла ошибка во время очистки коллекции, проверьте правильность введенных вами данных.");
        }
    }

    /**
     * Фyнкция кторая удаляет из коллекции элементы id которых больше заданного
     * @param id
     */
    public static void removeGreaterElem(String id, Response response){
        Console console = new StandartConsole();
        int counter_of_del = 0;
        for(String key : table.keySet()){
            Long currId = Long.parseLong(id);
            if (table.get(key).getId() > currId){
                counter_of_del++;
                table.remove(key);
                OperationWithId.deleateId(table.get(key).getId());
            }
        }
        if (counter_of_del > 0) {
            response.setAnswer("Все элементы успешно удалены.");
        }else{
            response.setAnswer("Элементов больше данного нет.");
        }
    }

    /**
     * Фyнкция кторая удаляет из коллекции элементы id которых меньше заданного
     * @param id
     */
    public static void removeLowerElem(String id, Response response){
        Console console = new StandartConsole();
        int counter_of_del = 0;
        for(String key : table.keySet()){
            Long currId = Long.parseLong(id);
            if (table.get(key).getId() < currId){
                counter_of_del++;
                table.remove(key);
                OperationWithId.deleateId(table.get(key).getId());
            }
        }
        if (counter_of_del > 0) {
            response.setAnswer("Все элементы успешно удалены.");
        }else{
            response.setAnswer("Элементов меньше данного нет.");
        }
    }

    /**
     * Функция которая заменяет элемент если его id меньше заданного
     * @param id
     * @throws Ask.AskBreak
     * @throws NumberValueExeption
     */
    public static void replaceIfLower(String id, Response response, Vehicle vehicle) throws Ask.AskBreak, NumberValueExeption {
        Console console = new StandartConsole();
        boolean fl = false;
        for(String key : table.keySet()){
            Long currId = Long.parseLong(id);
            if (table.get(key).getId() < currId){
                OperationWithId.deleateId(table.get(key).getId());
                CollectionManager.add(key, response, vehicle);
                OperationWithId.deleateId(table.get(key).getId());
                table.get(key).setId(currId);
                OperationWithId.add(currId);
                fl = true;
                response.setAnswer("Элемент коллекции успешно заменен.");
                break;
            }
        }
        if (!fl){
            response.setAnswer("Нету id меньше данного.");
        }
    }

    /**
     * Функция которая удаляет из коллекции один элемент, значение поля fuelConsumption которого эквивалентно заданному
     * @param fuel
     */
    public static void removeByFuel(String fuel, Response response){
        Console console = new StandartConsole();
        long currFuel = Long.parseLong(fuel);
        boolean fl = false;
        for(String key : table.keySet()){
            if (table.get(key).getFuelConsumption() == currFuel){
                OperationWithId.deleateId(table.get(key).getId());
                table.remove(key);
                fl = true;
                response.setAnswer("Элемент успешно удален.");
                break;
            }
        }
        if (!fl){
            response.setAnswer("Поля равного данному в колеекции не существет.");
        }
    }

    /**
     * Функция которая  группирует элементы коллекции по значению поля fuelConsumption и выводит количетсво элементов в каждой группе
     */
    public static void groupByFuel(Response response){
        Console console = new StandartConsole();
        LinkedHashMap<Long, Integer> map = new LinkedHashMap<>();
        for(String key : table.keySet()){
            map.put(table.get(key).getFuelConsumption(), + 1);
        }
        String answer = "";
        answer += "Количество элементов в каждой группе: ";
        for(Long key : map.keySet()){
            answer += map.get(key) + " ";
        }
        response.setAnswer(answer);
    }

    /**
     * Функция которая выводит элементы, значение поля enginePower которых больше заданного
     * @param engine
     */
    public static void filterByEngine(String engine, Response response){
        Console console = new StandartConsole();
        ArrayList<Vehicle> listOFVehicles = new ArrayList<>();
        Double currEngine = Double.parseDouble(engine);
        for(String key : table.keySet()){
            if (table.get(key).getEnginePower() > currEngine){
                listOFVehicles.add(table.get(key));
            }
        }
        String answer = "";
        answer += ("Отсортированный список:");
        for(int i = 0; i < listOFVehicles.size(); i++){
            answer += listOFVehicles.get(i) + "\n";
        }
        response.setAnswer(answer);
    }
}
