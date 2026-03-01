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
import java.util.List;
import java.util.Optional;

public class EntityDao<T> extends CSVReadWriter<T> {

    final static String FILENAME_USER = "users.csv";
    final static String FIELD_ID = "id";
    String mFileName;

    public EntityDao(String filename, Class<?> type) {
        super(type);
        mFileName = filename.isBlank() ? FILENAME_USER : filename;
        this.readAll();
    }

    public EntityDao(Class<?> type) {
        super(type);
    }

    @Override
    public boolean write(T data) {
        if (data instanceof Entity) {
            boolean result = FileUtils.fileWriteString(mFileName, ((Entity) data).toWrite());
            if (result) {
                dataMap.put(((Entity) data).getId(), data);
            }
            return result;
        } else {
            return false;
        }

    }

    @Override
    public Optional<T> read(int id) {
        // Check if the field is valid
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
            while ((line = br.readLine()) != null) {
                if (line.startsWith("id")) {
                    csvHeader.addAll(List.of(line.split(",")));
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
                        field.setAccessible(true);
                        if (field.getType().equals(int.class)) {
                            field.set(entity, Integer.parseInt(values[index].trim()));
                        } else {
                            field.set(entity, values[index]);
                        }
                    }
                }
                dataMap.put(((Entity) entity).getId(), (T) entity);
            }
            if (dataMap.isEmpty()) {
                System.out.println("No users found in the database. initializing data...");
                initData();
            }
            return dataMap.values().stream().toList();
        } catch (Exception e) {
            LogUtils.ERROR(getClass().getSimpleName(), "Error reading file: " + mFileName, e);
            return dataMap.values().stream().toList();
        }
    }

    public List<T> readByField(String fieldName, String value) {
        //check if field is valid
        List<T> result = new ArrayList<>();
        for (Field field : mFields) {
            try {
                if (field.getName().equalsIgnoreCase(fieldName)) {
                    for (T o : dataMap.values()) {
                        Entity entity = (Entity) o;
                        field.setAccessible(true);
                        // skip null values
                        System.out.println(field.get(o));
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

    private void initData() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Entity entity = (Entity) mType.getDeclaredConstructor().newInstance();
        entity.setId(-1);
        dataMap.put(-1, (T) entity);
        write((T) entity);
        LogUtils.DEBUG(getClass().getSimpleName(), "init data");
    }

    public List<Integer> getAllId() {
        return dataMap.keySet().stream().toList();
    }

    public int getANewId() {
        synchronized (Entity.class) {
            int value = dataMap.size() + 1;
            return (value);
        }
    }

}
