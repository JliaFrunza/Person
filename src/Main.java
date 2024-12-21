import java.util.OptionalInt;

public class Main {
    public static void main(String[] args) {
        Person mom = new PersonBuilder()
                .setName("Anna")
                .setSurname("Smith")
                .setAge(31)
                .setAddress("Sydney")
                .build();
        Person son = mom.newChildBuilder()
                .setName("Tom")
                .build();
        System.out.println("Mom: " + mom);
        System.out.println("Son: " + son);

        try {
            new PersonBuilder().build();
        } catch (IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());
        }

        try {
            new PersonBuilder().setAge(-100).build();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

class Person {
    protected final String name;
    protected final String surname;
    protected int age = -1;
    protected String address = "";

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public boolean hasAge() {
        return age != -1;
    }

    public boolean hasAddress() {
        return !address.isEmpty();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public OptionalInt getAge() {
        if (hasAge()) {
            return OptionalInt.of(age);
        } else {
            return OptionalInt.empty();
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void happyBirthday() {
        if (hasAge()) {
            age++;
        } else {
            age = 1;
        }
    }

    public PersonBuilder newChildBuilder() {
        return new PersonBuilder()
                .setSurname(surname)
                .setAddress(address);
    }

    @Override
    public String toString() {
        return name + " " + surname + (hasAge() ? ", " + age + " years old" : "") + (hasAddress() ? ", lives in " + address : "");
    }

    @Override
    public int hashCode() {
        return name.hashCode() ^ surname.hashCode();
    }
}

class PersonBuilder {
    private String name;
    private String surname;
    private int age = -1;
    private String address = "";

    public PersonBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public PersonBuilder setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
        return this;
    }

    public PersonBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public Person build() {
        if (name == null || name.isEmpty() || surname == null || surname.isEmpty()) {
            throw new IllegalStateException("Name and surname are required");
        }
        return new Person(name, surname, age);
    }
}

