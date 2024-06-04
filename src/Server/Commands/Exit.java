package Server.Commands;

import Common.Response;
import Server.Managers.CollectionManager;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;

/**
 * Команда которая осуществляет выход из программы. Прерывание.
 */
public class Exit extends Command{
    private final CollectionManager collectionManager;
    public Exit (CollectionManager collectionManager){
        super("exit", "осуществляет выход из программы.");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args, Response response){
        try{
            response.setAnswer("Программа завершена.");
        } catch (Exception e){
            response.setAnswer(e.getMessage());
        }
    }
}
