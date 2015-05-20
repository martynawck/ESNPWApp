package com.wiacek.martyna.esnpwapp.Domain;

/**
 * Created by Martyna on 2015-05-18.
 */
public class FunMapCategory {

    String id;
    String name;

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
}
