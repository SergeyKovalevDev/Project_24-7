package ru.sf.pojo;

public class PojoMain {
    public static void main(String[] args) {
        Pojo pojo = new Pojo.Builder().name("asd").surname("sdf").age(123).build();
        System.out.println(pojo);
        Pojo pojo1 = new Pojo.Builder().name("asd").build();
    }
}
