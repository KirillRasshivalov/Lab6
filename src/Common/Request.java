package Common;

import java.io.Serializable;

public class Request implements Serializable {
    private String command;
    private Object Vehicle;
    private String key;

    public Request(Object Vehicle, String command, String key){
        this.Vehicle = Vehicle;
        this.command = command;
        this.key = key;
    }

    public Object getVehicle(){
        return Vehicle;
    }

    public String getCommand(){
        return command;
    }
    public String getKey(){
        return key;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", Vehicle=" + Vehicle +
                ", key='" + key + '\'' +
                '}';
    }
}
