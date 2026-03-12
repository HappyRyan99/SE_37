package org.softwareeng.group37.dao;

import java.util.*;


/**
 * This abstract class provides a base implementation for reading from and writing to data.
 * It supports generic entity types and provides abstract methods for customizing the CRUD operations.
 *
 * @param <T> the type of entity this class operates on
 */
public  interface GenericDataStore<T> {


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
    public abstract List<T> readAll();


    /**
     * Adds a new entity to the CSV storage.
     *
     * @param t the entity to be added
     * @return the ID assigned to the entity upon successful addition, or -1 if addition fails
     */
    public int add(T t);

    /**
     * Deletes an existing entity from the CSV storage.
     *
     * @param t the entity to delete
     * @return true if the entity was successfully deleted, false otherwise
     */
    public boolean delete(T t);

    /**
     * Updates an entity in the CSV storage with the provided ID.
     *
     * @param id the ID of the entity to update
     * @param t  the updated entity to replace the existing one
     * @return true if the update was successful, false otherwise
     */
    public boolean update(int id, T t);

    /**
     * Writes all the pending changes to the CSV file to ensure data persistence.
     */
    public void flush();

}
