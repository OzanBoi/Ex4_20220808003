import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class Ex4_20220808003 {
    public static void main(String[] args) {
        CPU cpu1 = new CPU("Intel i5", 2.4);
        RAM ram1 = new RAM("DDR4", 8);

        Desktop desktop = new Desktop(cpu1, ram1, "Monitor", "Keyboard", "Mouse");

        desktop.plugIn("Headphones");
        System.out.println(desktop);
        String peripheral = desktop.plugOut();
        System.out.println(peripheral);
        System.out.println(desktop);
        peripheral = desktop.plugOut(1);
        System.out.println(peripheral);
        System.out.println(desktop);

        Laptop laptop = new Laptop(cpu1, ram1, 5000);

        System.out.println(laptop.batteryPercentage()); // expected output: 30
        laptop.charge();
        System.out.println(laptop.batteryPercentage()); // expected output: 90
        laptop.run();
        System.out.println(laptop);
    }
}
class Computer {
protected CPU cpu;
protected RAM ram;

public Computer(CPU cpu, RAM ram) {
        this.cpu = cpu;
        this.ram = ram;
}
public void run(){
    int size = ram.getCapacity();
    int sum = 0;
    for (int i = 0; i < size; i++){
        sum += ram.getValue(i, i);
    }
    ram.setValue(0,0, cpu.compute(sum, 0));

}
public String toString(){
    return "Computer: " + cpu + " " + ram;
}
}

    class Laptop extends Computer{
    private int milliAmp;
    private int battery;
public Laptop(CPU cpu, RAM ram, int milliAmp){
    super(cpu, ram);
    this.milliAmp = milliAmp;
    this.battery = milliAmp * 30 / 100;
}
public int getMilliAmp(){
    return this.milliAmp;
}
public void setMilliAmp(int milliAmp){
    this.milliAmp = milliAmp;
}
public int getBattery(){
    return this.battery;
}
    public void setBattery(int battery){
        this.battery = battery;
    }
    public int batteryPercentage(){
    return battery * 100 / milliAmp;
    }
    public void charge(){
    while(batteryPercentage() < 90) {
        battery += milliAmp * 2 / 100;
    }
    }
    public void run(){
    if(batteryPercentage() > 5){
        super.run();
        battery -= milliAmp * 3/100;
    } else {
        charge();
    }
    }
    public String toString(){
    return super.toString() + " " + battery;
    }
}

class Desktop extends Computer{
    private ArrayList<String> peripherals;
    public Desktop(CPU cpu, RAM ram, String... peripherals) {
        super(cpu, ram);
        this.peripherals = new ArrayList<>(Arrays.asList(peripherals));
    }
    public ArrayList<String> getPeripherals(){
        return this.peripherals;
    }
    public void setPeripherals(ArrayList<String> peripherals){
        this.peripherals = peripherals;
    }
    public void run(){
        int sum = 0;
        for (int i = 0; i < ram.getCapacity(); i++) {
            for (int j = 0; j < ram.getCapacity(); j++) {
                sum = cpu.compute(sum, ram.getValue(i, j));
            }
        }
        ram.setValue(0, 0, sum);
    }
    public void plugIn(String peripheral){
        peripherals.add(peripheral);
    }
    public String plugOut(){
        if(peripherals.size() == 0) {
            return null;
        }
        return peripherals.remove(peripherals.size() - 1);
    }
    public String plugOut(int index){
        if(index < 0 || index >= peripherals.size()){
            return null;
        }
        return peripherals.remove(index);
    }
    public String toString(){
        String spacedPer = "";
        for(String peripheral : peripherals) {
            spacedPer += peripheral + " ";
        }
        return super.toString() + " " + spacedPer;
    }

}

class CPU{
    private String name;
    private double clock;
    public CPU(String name, double clock){
        this.name = name;
        this.clock = clock;
    }
    public int compute(int a, int b) {
        return a + b;
    }

    public String toString() {
        return "CPU: " + this.name + " " + this.clock + "Ghz";
    }

    public String getName() {
        return this.name;
    }

    public double getClock() {
        return this.clock;
    }
}
class RAM {
    private String type;
    private int capacity;
    private int[][] memory;

    public RAM(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
        initMemory();
    }

    private void initMemory() {
        this.memory = new int[this.capacity][this.capacity];
        Random ran = new Random();
        for (int i = 0; i < this.capacity; i++) {
            for (int j = 0; j < this.capacity; j++) {
                this.memory[i][j] = ran .nextInt(11); // Random number between 0 and 10
            }
        }
    }

    private boolean check(int i, int j) {
        if (i < 0 || i >= this.capacity || j < 0 || j >= this.capacity) {
            return false;
        }
        return true;
    }

    public int getValue(int i, int j) {
        if (!this.check(i, j)) {
            return -1;
        }
        return this.memory[i][j];
    }

    public void setValue(int i, int j, int value) {
        if (this.check(i, j)) {
            this.memory[i][j] = value;
        }
    }

    public String toString() {
        return "RAM: " + this.type + " " + this.capacity + "GB";
    }

    public String getType() {
        return this.type;
    }

    public int getCapacity() {
        return this.capacity;
    }
}