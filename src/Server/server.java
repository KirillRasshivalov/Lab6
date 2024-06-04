package Server;

import Client.Models.Vehicle;
import Common.Request;
import Common.Response;
import Common.Utilites.Commands;
import Common.Utilites.StandartConsole;
import Server.Managers.CollectionManager;
import Server.Managers.CommandManager;
import Server.Managers.Parser;
import Server.exeptions.RootException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;


public class server {
    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", 2432));
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Сервер успешно запущен.");

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isAcceptable()) {
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = serverChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);


                        System.out.println("Разрешено подключение из: " + clientChannel.getRemoteAddress());

                    } else if (key.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                        int bytesRead;
                        while ((bytesRead = clientChannel.read(buffer)) > 0) {
                            buffer.flip();
                            outputStream.write(buffer.array(), buffer.position(), buffer.remaining());
                            buffer.clear();
                        }

                        if (bytesRead == -1) {
                            clientChannel.close();
                        } else {
                            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                            Request request = new Request(null, null, null);
                            CommandManager commandManager = new CommandManager();

                            Response response = new Response();

                            try {
                                Parser.read("Parse.xml", false, response);
                                request = (Request) objectInputStream.readObject();
                                Vehicle vehicle = (Vehicle) request.getVehicle();
                                commandManager.startExecuting(request.getCommand().toLowerCase() + " " + request.getKey(), response, vehicle);
                                String answer = response.getAnswer();
                                ByteBuffer responseBuffer = ByteBuffer.wrap(answer.getBytes());
                                responseBuffer.wrap(answer.getBytes());
                                clientChannel.write(responseBuffer);
                                Parser.write("Parse.xml");
                                responseBuffer.clear();
                                buffer.clear();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            objectInputStream.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
