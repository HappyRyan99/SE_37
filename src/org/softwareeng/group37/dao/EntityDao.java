package org.softwareeng.group37.dao;


import org.softwareeng.group37.model.Entity;
import org.softwareeng.group37.utils.LogUtils;

import java.io.BufferedReader;
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
        return false;
    }

    @Override
    public Optional<T> read(int id) {
        // Check if the field is valid
        LogUtils.DEBUG(getClass().getSimpleName(), "=======dataMap==========" + dataMap.size());
        System.out.println(dataMap);
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
        try (BufferedReader br = new BufferedReader(new FileReader(mFileName))) {
            String line;
            List<String> csvHeader = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                System.out.println("[" + line + "]");
                if (csvHeader.isEmpty()) {
                    csvHeader.addAll(List.of(line.split(",")));
                    continue;
                }
                T entity = (T) mType.getDeclaredConstructor().newInstance();
                String[] values = line.split(",");
                LogUtils.DEBUG(getClass().getSimpleName(), String.format("ID STATUS ======> %s %s", values[0], values[1]));
                for (Field field : mFields) {
                    int index = csvHeader.indexOf(field.getName());
                    if (index != -1 && index < values.length) {
                        LogUtils.DEBUG(getClass().getSimpleName(), index + "============" + field.getName() + "==========" + values[index]);
                        field.setAccessible(true);
                        if (field.getType().equals(int.class)) {
                            field.set(entity, Integer.parseInt(values[index].trim()));
                        } else {
                            field.set(entity, values[index]);
                        }
                    }
                }
                dataMap.put(((Entity) entity).getId(), entity);
            }
        } catch (IOException e) {
            LogUtils.ERROR(getClass().getSimpleName(), "Error reading file: ", e);
            initData();
            return dataMap.values().stream().toList();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        if (dataMap.isEmpty()) {
            System.out.println("No users found in the database. initializing data...");
            initData();
        }
        return dataMap.values().stream().toList();
    }

    private void initData() {
//        T user = new T();
//        user.setId(-1);
//        dataMap.put(-1, user);
        System.out.println("init data");
    }
    public List<Integer> getAllId() {
        return dataMap.keySet().stream().toList();
    }



}
