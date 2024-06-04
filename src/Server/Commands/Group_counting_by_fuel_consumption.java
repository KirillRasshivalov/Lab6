package Server.Commands;

import Common.Response;
import Server.Managers.CollectionManager;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;


/**
 * Команда которая группирует элементы коллекции по значению поля fuelConsumption, выводит количество элементов в каждой группе
 */
public class Group_counting_by_fuel_consumption extends Command {
    private final CollectionManager collectionManager;
    public Group_counting_by_fuel_consumption(CollectionManager collectionManager){
        super("group_counting_by_fuel_consumption", "группирует элементы коллекции по значению fuel, выводит " +
                "количество элементов в каждой группе.");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args, Response response){
        try{
            collectionManager.groupByFuel(response);
        }catch(Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
