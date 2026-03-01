package org.softwareeng.group37.model;

import org.softwareeng.group37.utils.LogUtils;

import java.lang.reflect.Field;

import static org.softwareeng.group37.contants.Contants.*;

public abstract class Entity {

    private int id;
    /**
     * statu = 0  DEFAUL
     * statu = 1  ACTIVE
     * statu = 2  INACTIVE
     */
    private int status;


    public String toWrite() {
        if (id == -1) {
            return getHeader();
        }
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder output = new StringBuilder();
        output.append(String.format("%d,%s,", id, status));
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                output.append(field.get(this)).append(",");
            } catch (IllegalAccessException e) {
                output.append(field.getName()).append(",").append("NA");
                LogUtils.ERROR(getClass().getName(), "Error accessing field: ", e);
            }
        }
        if (output.toString().endsWith(",")) {
            output.deleteCharAt(output.length() - 1);
        }
        output.append("\n");
        return output.toString();
    }

    public String getHeader() {
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder output = new StringBuilder();
        output.append("ID,Status,");
        for (Field field : fields) {
            field.setAccessible(true);
            output.append(field.getName()).append(",");
        }
        if (output.toString().endsWith(",")) {
            output.deleteCharAt(output.length() - 1);
        }
        output.append("\n");
        return output.toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return castStatus(status);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private String castStatus(int value) {
        String status = "";
        switch (value) {
            case STATUS_ACTIVE:
                status = "ACTIVE";
                break;
            case STATUS_INACTIVE:
                status = "INACTIVE";
                break;
            case STATUS_DEFAULT:
            default:
                status = "DEFAUL";
                break;
        }
        return status;
    }

    public String toString() {
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder output = new StringBuilder();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                output.append(field.getName()).append(",").append(field.get(this));
            } catch (IllegalAccessException e) {
                output.append(field.getName()).append(",").append("NA");
                LogUtils.ERROR(getClass().getName(), "Error accessing field: ", e);
            }
        }
        output.append("\n");
        return output.toString();
    }
}
