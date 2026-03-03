package org.softwareeng.group37.model;

import org.softwareeng.group37.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.softwareeng.group37.contants.Contants.*;

/**
 * Abstract class serving as a base for all entity models.
 * Provides common properties and methods for managing and serializing entities.
 */
public abstract class Entity {

    /**
     * Unique identifier for the entity.
     */
    private int id;
    /**
     * Status of the entity.
     * Values:
     * - 0: DEFAULT
     * - 1: ACTIVE
     * - 2: INACTIVE
     */
    private int status;


    /**
     * Serializes the current state of the entity into a comma-separated string.
     * Handles both field values and headers for database or file storage.
     *
     * @return A string representation of the entity in CSV format.
     */
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

    /**
     * Generates the CSV header containing the field names of the entity.
     * Includes field names from the class hierarchy.
     *
     * @return A string representing the CSV header.
     */
    public String getHeader() {
        StringBuilder output = new StringBuilder();
        Class<?> current = this.getClass();
        while (current != null && current != Object.class) {
            Field[] fields = current.getDeclaredFields();
            StringBuilder classFieldName = new StringBuilder();
            for (Field field : fields) {
                classFieldName.append(field.getName()).append(",");
            }
            current = current.getSuperclass();
            if (null != current) {
                output = new StringBuilder(classFieldName.append(output));
            } else {
                output = new StringBuilder(classFieldName.toString());
            }
        }


        if (output.toString().endsWith(",")) {
            output.deleteCharAt(output.length() - 1);
        }
        output.append("\n");
        return output.toString();
    }

    /**
     * Sets the unique identifier for the entity.
     *
     * @param id The ID to assign to the entity.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the unique identifier of the entity.
     *
     * @return The ID of the entity.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the textual representation of the entity's status.
     *
     * @return The status as a string: "DEFAULT", "ACTIVE", or "INACTIVE".
     */
    public String getStatus() {
        return castStatus(status);
    }

    public int getStatusAsInt() {
        return status;
    }

    /**
     * Sets the status of the entity using an integer code.
     *
     * @param status The status code to assign (0, 1, or 2).
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Converts an integer status code into its corresponding string representation.
     *
     * @param value The status code to convert.
     * @return A string representing the status.
     */
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
                status = "DEFAULT";
                break;
        }
        return status;
    }

    /**
     * Provides a string representation of the entity's fields and values for debugging or logging.
     *
     * @return A string representation of the entity's state.
     */
    public String toString() {
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder output = new StringBuilder();
        Class<?> superClass = this.getClass().getSuperclass();
        if (null != superClass && (superClass.getSimpleName().equals(Entity.class.getSimpleName()))) {
            Field[] superClassFields = superClass.getDeclaredFields();
            StringBuilder classFieldName = new StringBuilder();
            for (Field field : superClassFields) {
                try {
                    classFieldName.append(field.get(this)).append(" | ");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            output.append(classFieldName.toString());
        }
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                output.append(field.getName()).append(":").append(field.get(this)).append(" | ");
            } catch (IllegalAccessException e) {
                output.append(field.getName()).append(",").append("NA");
                LogUtils.ERROR(getClass().getName(), "Error accessing field: ", e);
            }
        }
        output.append("\n");
        return output.toString();
    }
}
