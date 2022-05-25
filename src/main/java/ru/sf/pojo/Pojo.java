package ru.sf.pojo;

public class Pojo {
    private String name;
    private String surname;
    private int age;

    private Pojo(Builder builder) {
        name = builder.name;
        surname = builder.surname;
        age = builder.age;
    }

    public static final class Builder {
        private String name;
        private String surname;
        private int age;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder surname(String val) {
            surname = val;
            return this;
        }

        public Builder age(int val) {
            age = val;
            return this;
        }

        public Pojo build() {
            if (validatePojo()) return new Pojo(this);
            else return null;
        }

        private boolean validatePojo() {
            return name != null && !name.trim().isEmpty() &&
                    surname != null && !surname.trim().isEmpty() &&
                    age >= 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Pojo [name=" + name + ", surname=" + surname + ", age=" + age + "]";
    }
}
