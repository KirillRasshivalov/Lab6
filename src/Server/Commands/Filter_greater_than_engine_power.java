package Server.Commands;

import Common.Response;
import Server.Managers.CollectionManager;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;

/**
 * Команда которая выводит элементы, значение поля enginePower которых больше заданного
 */
public class Filter_greater_than_engine_power extends Command{
    private final CollectionManager collectionManager;
    public Filter_greater_than_engine_power(CollectionManager collectionManager){
        super("filter_greater_than_engine_power", "выводит значения полей больше заданного.");
        this.collectionManager = collectionManager;
    }
    @Override
    public void execute(String[] args, Response response){
        try{
            collectionManager.filterByEngine(args[1], response);
        }catch(Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
