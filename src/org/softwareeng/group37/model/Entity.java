package org.softwareeng.group37.model;

import org.softwareeng.group37.utils.LogUtils;

import java.lang.reflect.Field;

public abstract class Entity {
    private int id;
    private Status status;

    public abstract String toWrite();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatus(String status) {
        try {
            this.status = Status.valueOf(status);
        } catch (Exception e) {
            System.out.println("Invalid status, setting default status to INACTIVE");
            this.status = Status.INACTIVE;
        }
    }

    public String toString() {
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuffer output = new StringBuffer();
        for (Field field : fields) {
            try {
                output.append(field.getName()).append(",").append(field.get(this));
            } catch (IllegalAccessException e) {
                LogUtils.ERROR(getClass().getName(), "Error accessing field: ", e);
            }
//            String name = field.getName();
//            Object value = field.get(obj);
//            System.out.println(name + ": " + value);

        }
        return output.toString();
    }
}
