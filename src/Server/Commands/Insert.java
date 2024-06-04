package Server.Commands;

import Client.Ask;
import Client.Models.Vehicle;
import Common.Response;
import Server.Managers.CollectionManager;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;
import Server.exeptions.NumberValueExeption;

/**
 * Команда которая осуществляет добавление нового элемента в колекцию.
 */
public class Insert extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Insert(Console console, CollectionManager collectionManager){
        super("insert {element}",  "добавление нового элемента в коллекцию.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute_add(String[] args, Response response, Vehicle vehicle) throws Ask.AskBreak, NumberValueExeption {
        Console console1 = new StandartConsole();
        if (args.length == 2 && !CollectionManager.getTable().containsKey(args[1])){
            try{
                console1.println("Начало заполнения коллекции.");
                CollectionManager.add(args[1], response, vehicle);
                response.setAnswer("Транспорт успеuшно добавлен");
                console1.println("Добавление нового элемента прошло успешно.");
            }catch (NumberFormatException e) {
                response.setAnswer("\u001B[31m" + "Вы ввели что-то неправильно, проверьте " +
                        "что вы ввели все согласно требованиям." + "\u001B[0m");
            }
        }
        else{
            response.setAnswer("Проверьте что все введенные вами данные соответсвтуют действительности, а ключи не совпадают.");
        }
    }
}
