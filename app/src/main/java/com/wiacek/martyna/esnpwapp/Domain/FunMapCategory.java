package com.wiacek.martyna.esnpwapp.Domain;

/**
 * Created by Martyna on 2015-05-18.
 */
public class FunMapCategory implements Comparable {

    private String id;
    private String name;

    public FunMapCategory() {}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

   @Override
   public String toString() {
        return this.name;
    }

    public int compareTo(Object anotherPerson) throws ClassCastException {
        if (!(anotherPerson instanceof FunMapCategory))
            throw new ClassCastException("A Person object expected.");
        String anotherName = ((FunMapCategory) anotherPerson).getName();
        return this.name.compareTo(anotherName);
    }
}
