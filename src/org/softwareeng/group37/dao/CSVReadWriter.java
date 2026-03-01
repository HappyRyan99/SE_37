package org.softwareeng.group37.dao;

import org.softwareeng.group37.model.Entity;
import org.softwareeng.group37.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.*;

public abstract class CSVReadWriter<T> {

    protected Map<Integer, T> dataMap = new HashMap<Integer, T>();
    protected final Class<?> mType;
    protected final List<Field> mFields;

    public CSVReadWriter(Class<?> type) {
        this.mType = type;
    mFields = new ArrayList<>();
        setFields();
    }

    private   void setFields() {
        Class<?> current = mType;
        while (current != null && current != Object.class) {
            Collections.addAll(mFields, current.getDeclaredFields());
            current = current.getSuperclass();
        }
    }

    /**
     * Writes the provided data to a CSV file.
     *
     * @param data the data to be written
     * @return true if the write operation was successful, false otherwise
     */
    abstract boolean write(T data);

    /**
     * Reads data from the CSV file by its ID.
     *
     * @param id the ID of the data to read
     * @return an Optional containing the data if found, or an empty Optional if not
     */
//    abstract Optional<T> read(int id);
    public abstract Optional<T> read(int id) ;

    /**
     * Reads data from the CSV file by a specified field and its value.
     *
     * @param field the field to search by
     * @param value the value to match in the specified field
     * @return an List containing the data if a matching record is found, or an empty Optional if not
     */
    public abstract List<T> readByField(String fieldName, String value);

    /**
     * Reads all data from the CSV file.
     *
     * @return a list of all data records
     */
    abstract List<T> readAll();
}
