package org.softwareeng.group37.dao;

import org.softwareeng.group37.model.Entity;
import org.softwareeng.group37.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.*;


/**
 * This abstract class provides a base implementation for reading from and writing to CSV files.
 * It supports generic entity types and provides abstract methods for customizing the CRUD operations.
 *
 * @param <T> the type of entity this class operates on
 */
public abstract class CSVReadWriter<T> {

    /**
     * A map to store entities indexed by their unique identifiers.
     */
    protected Map<Integer, T> dataMap = new HashMap<Integer, T>();

    /**
     * The class type of the generic entity being handled.
     */
    protected final Class<?> mType;

    /**
     * A list of fields (including inherited fields) for the provided class type.
     * These fields are used to dynamically process the entity's data.
     */
    protected final List<Field> mFields;

    /**
     * Constructor to initialize the CSVReadWriter with a specific entity type.
     * Also initializes the list of fields by inspecting the given class.
     *
     * @param type the class type of the entity
     */
    public CSVReadWriter(Class<?> type) {
        this.mType = type;
        mFields = new ArrayList<>();
        setFields();
    }

    /**
     * Dynamically retrieves and stores all fields (including inherited ones)
     * of the provided class type and its superclasses for later use.
     */
    private   void setFields() {
        Class<?> current = mType;
        while (current != null && current != Object.class) {
            Collections.addAll(mFields, current.getDeclaredFields());
            current = current.getSuperclass();
        }
    }

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
