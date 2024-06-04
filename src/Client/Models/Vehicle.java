package Client.Models;

import Client.Managers.OperationWithId;
import Common.Utilites.Validator;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Vehicle implements Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double enginePower; //Поле не может быть null, Значение поля должно быть больше 0
    private Long numberOfWheels; //Поле не может быть null, Значение поля должно быть больше 0
    private VehicleType type; //Поле не может быть null
    private long fuelConsumption; //Значение поля должно быть больше 0

    public Vehicle(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Double enginePower, Long numberOfWheels, VehicleType type, long fuelConsumption){
        this.id = OperationWithId.generateId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.type = type;
        this.fuelConsumption = fuelConsumption;
    }

    public Vehicle(Long id, String name, Coordinates coordinates, Double enginePower, Long numberOfWheels, VehicleType type, long fuelConsumption){
        this(id, name, coordinates, ZonedDateTime.now(), enginePower, numberOfWheels, type, fuelConsumption);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Double getEnginePower() {
        return enginePower;
    }

    public Long getNumberOfWheels() {
        return numberOfWheels;
    }

    public VehicleType getType() {
        return type;
    }

    public long getFuelConsumption() {
        return fuelConsumption;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", enginePower=" + enginePower +
                ", numberOfWheels=" + numberOfWheels +
                ", type=" + type +
                ", fuelConsumption=" + fuelConsumption +
                '}';
    }

    /**
     * Приводит все типы к верным в формате xmlю
     * @return строку валидную для .xml файлов
     */
    public String toXML(){
        return "id=\"" + id + "\"" +
                " name=\"" + name + '\"' +
                " x=\"" + coordinates.getX() + "\"" +
                " y=\"" + coordinates.getY() + "\"" +
                " creationDate=\"" + creationDate + "\"" +
                " enginePower=\"" + enginePower + "\"" +
                " numberOfWheels=\"" + numberOfWheels + "\"" +
                " type=\"" + type + "\"" +
                " fuelConsumption=\"" + fuelConsumption + "\"";
    }

    public Vehicle(String[] data) throws Exception{
        Validator.checkId(data[1]);
        Validator.notEmpty(data[2]);
        Validator.checkCoordenateX(data[3]);
        Validator.checkCoordinatesY(data[4]);
        Validator.checkPower(data[6]);
        Validator.checkWheels(data[7]);
        Validator.checkType(data[8]);
        Validator.fuelComp(data[9]);


        this.id = Long.parseLong(data[1]);
        this.name = data[2];
        this.coordinates = new Coordinates(Long.parseLong(data[3]), Double.parseDouble(data[4]));
        this.creationDate = ZonedDateTime.parse(data[5]);
        this.enginePower = Double.parseDouble(data[6]);
        this.numberOfWheels = Long.parseLong(data[7]);
        this.type = VehicleType.valueOf(data[8]);
        this.fuelConsumption = Long.parseLong(data[9]);
    }

}
