package Server.Commands;

import Common.Response;
import Server.Managers.CollectionManager;
import Server.Managers.CommandManager;
import Client.Models.Vehicle;
import Common.Utilites.StandartConsole;
import Server.exeptions.RootException;
import Server.exeptions.TheSameFileINScriptException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

/**
 * Команда которая читает скрипт  из файла
 */
public class Execute_script extends Command {
    private static Stack<File> st = new Stack<>();
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;

    public Execute_script(CommandManager commandManager, CollectionManager collectionManager) {
        super("execute_script", "осуществляет чтение команд из файла.");
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
    }
}
