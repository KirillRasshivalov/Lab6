package Client;

import Client.Models.Vehicle;
import Common.Utilites.Commands;
import Common.Utilites.Console;
import Common.Utilites.StandartConsole;
import Common.Request;
import Server.exeptions.RootException;
import Server.exeptions.TheSameFileINScriptException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Stack;



public class client {
    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; //ридер читает с консоли

    private static Stack<File> st = new Stack<>();

    public static void main(String[] args){
        try{
            try{
                Console console = new StandartConsole();
                console.println("Добро пожаловать в программу, для получения информации о доступных командах напишите help");

                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress("localhost", 2432));

                reader = new BufferedReader(new InputStreamReader(System.in));
                Commands commands = new Commands();
                while(true){
                    if (!socketChannel.isConnected()){
                        System.out.println("Потеряно соединение с сервером.");
                        socketChannel.close();
                        System.exit(0);
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    String word = reader.readLine();
                    String command = word.split(" ")[0];
                    String key = null;
                    Object Vehicle = null;
                    if (command.equals("exit")){
                        console.println("Программа завершена.");
                        socketChannel.close();
                    }
                    else if (command.toLowerCase().equals("insert") || command.toLowerCase().equals("update") || command.toLowerCase().equals("replace_if_lower")){
                        key = word.split(" ")[1];
                        Vehicle = Ask.askVehicle(console, 1L);
                        Object request = new Request(Vehicle, command, key);
                        objectOutputStream.writeObject(request);
                        objectOutputStream.flush();
                        byte[] objectBytes = byteArrayOutputStream.toByteArray();
                        // Передаем байтовый массив через сокет
                        ByteBuffer buffer = ByteBuffer.allocate(objectBytes.length);
                        buffer.put(objectBytes);
                        buffer.flip();
                        socketChannel.write(buffer);
                    }
                    else if (command.toLowerCase().equals("execute_script")){
                        Console console1 = new StandartConsole();
                        try {
                            File file = new File(word.split(" ")[1]);
                            if (!file.canRead()) {
                                throw new RootException("У вас недостаточно прав для чтения данного файла.");
                            }

                            if (st.isEmpty()) {
                                st.add(file);
                            } else if (st.contains(file)) {
                                throw new TheSameFileINScriptException("Вы пытаетесь вызвать скрипт от самого себя.");
                            }
                            st.add(file);
                            String path = word.split(" ")[1];
                            var br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
                            String line;
                            String[] vehicle = new String[10];
                            while ((line = br.readLine()) != null) {
                                if (line.split(" ")[0].toLowerCase().equals("insert") || line.split(" ")[0].toLowerCase().equals("update")) {
                                    String keyy = line.split(" ")[1];
                                    for (int n = 1; n < 10; n++) {
                                        if ((line = br.readLine()) != null) {
                                            vehicle[n] = line;
                                        }
                                    }
                                    Object vehicle1 = new Vehicle(vehicle);
                                    Object request = new Request(vehicle1, command, key);
                                    objectOutputStream.writeObject(request);
                                    objectOutputStream.flush();
                                    byte[] objectBytes = byteArrayOutputStream.toByteArray();
                                    // Передаем байтовый массив через сокет
                                    ByteBuffer buffer = ByteBuffer.allocate(objectBytes.length);
                                    buffer.put(objectBytes);
                                    buffer.flip();
                                    socketChannel.write(buffer);
                                    buffer.clear();
                                } else {
                                    try {
                                        if (line.split(" ").length > 1) {
                                            String keyy = line.split(" ")[1];
                                            Object request = new Request(null, command, key);
                                            objectOutputStream.writeObject(request);
                                            objectOutputStream.flush();
                                            byte[] objectBytes = byteArrayOutputStream.toByteArray();
                                            // Передаем байтовый массив через сокет
                                            ByteBuffer buffer = ByteBuffer.allocate(objectBytes.length);
                                            buffer.put(objectBytes);
                                            buffer.flip();
                                            socketChannel.write(buffer);
                                            buffer.clear();
                                        }
                                        else{
                                            Object request = new Request(null, command, null);
                                            objectOutputStream.writeObject(request);
                                            objectOutputStream.flush();
                                            byte[] objectBytes = byteArrayOutputStream.toByteArray();
                                            // Передаем байтовый массив через сокет
                                            ByteBuffer buffer = ByteBuffer.allocate(objectBytes.length);
                                            buffer.put(objectBytes);
                                            buffer.flip();
                                            socketChannel.write(buffer);
                                            buffer.clear();
                                        }
                                    } catch (Exception e) {
                                        console1.println(e.getMessage());
                                    }
                                }
                            }
                            st.pop();
                        } catch (StackOverflowError e){
                            console1.println("Стэк переполнен.");
                        } catch (Exception e){
                            console1.println("Произошла ошибка во время выполнения скрипта.");
                        }
                        continue;
                    }
                    else{
                        if (word.split(" ").length == 2){
                            key = word.split(" ")[1];
                        }
                        Object request = new Request(Vehicle, command, key);
                        objectOutputStream.writeObject(request);
                        objectOutputStream.flush();
                        byte[] objectBytes = byteArrayOutputStream.toByteArray();
                        // Передаем байтовый массив через сокет
                        ByteBuffer buffer = ByteBuffer.allocate(objectBytes.length);
                        buffer.put(objectBytes);
                        buffer.flip();
                        socketChannel.write(buffer);
                        buffer.clear();
                    }
                    ByteBuffer buffer = ByteBuffer.allocate(2000);
                    int bytesRead = socketChannel.read(buffer);
                    if (bytesRead == -1) {
                        System.out.println("Потеряно соединение с сервером.");
                        socketChannel.close();
                        System.exit(0);
                    } else {
                        buffer.flip();
                        byte[] data = new byte[buffer.remaining()];
                        buffer.get(data);
                        String response = new String(data);
                        System.out.println(response);
                    }
                    buffer.clear();
                }
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }catch (Exception e){
            e.getMessage();
        }
    }
}