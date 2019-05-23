public class Employee {

    private static int id;
    private String name;
    private String position;

    public Employee(String name, String position) {
        this.name = name;
        this.position = position;
        id++;
    }

    public static int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
