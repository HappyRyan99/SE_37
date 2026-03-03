package org.softwareeng.group37.dao;


import org.softwareeng.group37.model.Entity;
import org.softwareeng.group37.utils.FileUtils;
import org.softwareeng.group37.utils.LogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * EntityDao is a generic Data Access Object (DAO) class for managing entities stored in CSV files.
 * It provides functionalities for reading, writing, and querying entity data.
 *
 * @param <T> the type of entity this DAO manages.
 */
public class EntityDao<T> extends CSVReadWriter<T> {

    /**
     * Default filename for storing user entities.
     */
    final static String FILENAME_USER = "users.csv";

    /**
     * Field name representing the unique ID of an entity.
     */
    final static String FIELD_ID = "id";

    /**
     * The filename of the CSV file used to store the entity data.
     */
    String mFileName;

    /**
     * Constructor that initializes EntityDao with a specific filename and entity type.
     * If the filename is blank, the default user filename is used.
     *
     * @param filename the name of the CSV file for storing entities.
     * @param type     the Class object representing the type of entity managed by this DAO.
     */
    public EntityDao(String filename, Class<?> type) {
        super(type);
        mFileName = filename.isBlank() ? FILENAME_USER : filename;
        this.readAll(); // Load existing data from the CSV file upon initialization.
    }

    /**
     * Constructor that initializes EntityDao with an entity type.
     * The filename must be set manually before performing read/write operations.
     *
     * @param type the Class object representing the type of entity managed by this DAO.
     */
    public EntityDao(Class<?> type) {
        super(type);
    }

    /**
     * Reads an entity by its unique ID from the data map.
     *
     * @param id the unique ID of the entity to read.
     * @return an Optional containing the entity if found, or an empty Optional otherwise.
     */
    @Override
    public Optional<T> read(int id) {
        // Log the size of the data map for debugging purposes.
        LogUtils.DEBUG(getClass().getSimpleName(), "=======dataMap==========" + dataMap.size());
        for (Field field : mFields) {
            LogUtils.DEBUG(getClass().getSimpleName(), "=======field.getName()==========" + field.getName());
            try {
                if (field.getName().equalsIgnoreCase(FIELD_ID)) {
                    for (T o : dataMap.values()) {
                        field.setAccessible(true);
                        if (field.get(o).equals(id)) {
                            return Optional.of(o);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                LogUtils.ERROR(getClass().getSimpleName(), "Error accessing field: " + field.getName(), e);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Reads all entities from the CSV file and populates the in-memory data map.
     * If no data is found, initializes default data.
     *
     * @return a list of all entities.
     */
    @Override
    public List<T> readAll() {
        File datafile = new File(mFileName);
        if (!datafile.exists()) {
            try {
                datafile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(mFileName))) {
            String line;
            List<String> csvHeader = new ArrayList<>();
            boolean hasHeader = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("id")) {
                    csvHeader.addAll(List.of(line.split(",")));
                    hasHeader = true;
                    continue;
                }
                Entity entity = (Entity) mType.getDeclaredConstructor().newInstance();
                String[] values = line.split(",");
                for (Field field : mFields) {
                    int index = 0;
                    for (String header : csvHeader) {
                        if (header.equalsIgnoreCase(field.getName())) {
                            break;
                        }
                        index++;
                    }
                    if (index != -1 && index < values.length) {
                        setFieldValue(field, entity,values[index]);
                    }
                }
                dataMap.put(((Entity) entity).getId(), (T) entity);
            }
            if (dataMap.isEmpty() && !hasHeader) {
                System.out.println("No Entity found in the database. initializing data...");
            }
            return dataMap.values().stream().toList();
        } catch (Exception e) {
            LogUtils.ERROR(getClass().getSimpleName(), "Error reading file: " + mFileName, e);
            return dataMap.values().stream().toList();
        }
    }

    /**
     * Reads all entities where the given field matches the specified value.
     *
     * @param fieldName the name of the field to filter by.
     * @param value     the value of the field to match.
     * @return a list of entities matching the criteria.
     */
    public List<T> readByField(String fieldName, String value) {
        // Check if the specified field is valid.
        List<T> result = new ArrayList<>();
        for (Field field : mFields) {
            try {
                if (field.getName().equalsIgnoreCase(fieldName)) {
                    for (T o : dataMap.values()) {
                        field.setAccessible(true);
                        // skip null values
                        if (o == null || field.get(o) == null) continue;
                        if (field.get(o).toString().equalsIgnoreCase(value)) {
                            result.add(o);
                        }
                    }
                }
            } catch (Exception e) {
                LogUtils.ERROR(getClass().getName(), "Error accessing field: ", e);
            }
        }
        return result;
    }
    /**
     * Retrieves all entity IDs from the data map.
     *
     * @return a list of all entity IDs.
     */
    public List<Integer> getAllId() {
        return dataMap.keySet().stream().toList();
    }

    /**
     * Generates and returns a new unique ID for an entity.
     * IDs are generated based on the current size of the data map.
     *
     * @return a new unique ID.
     */
    public int getANewId() {
        synchronized (Entity.class) {
            int value = dataMap.size() + 1;
            return (value);
        }
    }

    public int add(T t) {
        int id = getANewId();
        dataMap.put(getANewId(), t);
        return id;
    }

    public boolean delete(T t) {
        dataMap.remove(t);
        return true;
    }

    public boolean update(int id, T t) {
        dataMap.put(Integer.valueOf(id), t);
        return true;
    }

    public List<T> queryAll() {
        return dataMap.values().stream().toList();
    }

    public boolean writeToFile() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<T> iterator = dataMap.values().iterator();
        try {
            T title = (T) mType.getDeclaredConstructor().newInstance();
            stringBuilder.append(((Entity) title).getHeader());
        } catch (Exception e) {
            return false;
        }

        while (iterator.hasNext()) {
            T t = iterator.next();
            Field[] fields = t.getClass().getDeclaredFields();
            StringBuilder output = new StringBuilder();
            if (t instanceof Entity) {
                Entity e = (Entity) t;
                output.append(String.format("%d,%d,", e.getId(), e.getStatusAsInt()));
            }
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    output.append(field.get(t)).append(",");
                } catch (IllegalAccessException e) {
                    output.append(field.getName()).append(",").append("NA");
                    LogUtils.ERROR(getClass().getName(), "Error accessing field: ", e);
                }
            }
            if (output.toString().endsWith(",")) {
                output.deleteCharAt(output.length() - 1);
            }
            output.append("\n");
            stringBuilder.append(output.toString());
        }
        FileUtils.fileWriteString(mFileName, stringBuilder.toString());
        return true;
    }

    protected void setFieldValue(Field field, Object entity, String value) throws IllegalAccessException {
        if(field == null || entity == null ) {
            LogUtils.WARNING(getClass().getSimpleName(), "field or entity is null");
            return;
        }
        field.setAccessible(true);
        if (field.getType().equals(int.class)) {
            field.set(entity, Integer.parseInt(value.trim()));
        } else if (field.getType().equals(long.class)) {
            field.set(entity, Long.parseLong(value.trim()));
        } else if (field.getType().equals(double.class)) {
            field.set(entity, Double.parseDouble(value.trim()));
        } else if (field.getType().equals(float.class)) {
            field.set(entity, Float.parseFloat(value.trim()));
        } else if (field.getType().equals(boolean.class)) {
            field.set(entity, Boolean.parseBoolean(value.trim()));
        } else if (List.class.isAssignableFrom(field.getType())) {
            if (field.getType().equals(List.class)) {
                if (field.getType().getTypeName().contains("Integer")) {
                    List<Integer> listValues = Stream.of(value.split("\\|"))
                            .map(Integer::parseInt)
                            .toList();
                    field.set(entity, listValues);
                } else {
                    List<String> listValues = List.of(value.split("\\|"));
                    field.set(entity, listValues);
                }
            } else {
                field.set(entity, value.trim());
            }
        } else {
            field.set(entity, value.trim());
        }
    }

}
