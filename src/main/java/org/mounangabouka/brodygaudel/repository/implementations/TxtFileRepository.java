

/*
 * Copyright (c) 2022-2023 the original author Brody Gaudel MOUNANGA
 * You may use this file and this project under the terms defined by the license
 */

package org.mounangabouka.brodygaudel.repository.implementations;

import org.jetbrains.annotations.NotNull;
import org.mounangabouka.brodygaudel.repository.Repository;


import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * this class implements Repository interface
 * @param <T>
 */
public class TxtFileRepository <T> implements Repository<T> {

    private final   String filePath;
    private final Class<T> objectType;

    public TxtFileRepository(String filePath, Class<T> objectType) {
        this.filePath = filePath;
        this.objectType = objectType;
    }

    @Override
    public T findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T object = createObjectFromLine(line);
                if (object != null && hasMatchingId(object, id)) {
                    return object;
                }
            }
        } catch (IOException e) {
            // Gérez les erreurs de lecture du fichier
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public T save(@NotNull T object) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            Field[] fields = object.getClass().getDeclaredFields();
            StringBuilder lineBuilder = new StringBuilder();

            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(object);
                lineBuilder.append(value).append(",");
            }

            // Supprimez la virgule finale si nécessaire
            if (lineBuilder.length() > 0) {
                lineBuilder.deleteCharAt(lineBuilder.length() - 1);
            }

            String line = lineBuilder.toString();
            writer.write(line);
            writer.newLine();
        } catch (IOException | IllegalAccessException e) {
            // Gérez les erreurs d'écriture dans le fichier ou d'accès aux champs
            e.printStackTrace();
        }

        return object;
    }


    @Override
    public void deleteById(String id) {
        List<T> objects = findAll();
        boolean found = false;

        for (Iterator<T> iterator = objects.iterator(); iterator.hasNext();) {
            T object = iterator.next();
            if (hasMatchingId(object, id)) {
                iterator.remove();
                found = true;
                break;
            }
        }

        if (found) {
            saveAll(objects);
        }
    }


    @Override
    public List<T> findAll() {
        List<T> objects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T object = createObjectFromLine(line);
                objects.add(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }

    @Override
    public T update(T object) {
        List<T> objects = findAll();
        boolean found = false;

        for (int i = 0; i < objects.size(); i++) {
            T existingObject = objects.get(i);
            if (hasMatchingId(existingObject, getIdValue(object))) {
                objects.set(i, object);
                found = true;
                break;
            }
        }

        if (found) {
            saveAll(objects);
            return object;
        } else {
            return null;
        }
    }

    private String getIdValue(T object) {
        try {
            Field idField = object.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Object fieldValue = idField.get(object);
            return fieldValue.toString();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    private T createObjectFromLine(String line) {
        try {
            Class<?> objectClass = getGenericTypeClass();
            T object = (T) objectClass.getDeclaredConstructor().newInstance();
            String[] fieldValues = line.split(",");
            Field[] fields = objectClass.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                setValueToField(object, field, fieldValues[i]);
            }

            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Class<?> getGenericTypeClass() {
        return objectType;
    }


    private void setValueToField(T object, Field field, String value) throws IllegalAccessException {
        Class<?> fieldType = field.getType();

        if (fieldType == String.class) {
            field.set(object, value);
        } else if (fieldType == int.class || fieldType == Integer.class) {
            field.set(object, Integer.parseInt(value));
        } else if (fieldType == double.class || fieldType == Double.class) {
            field.set(object, Double.parseDouble(value));
        } else if (fieldType == float.class || fieldType == Float.class) {
            field.set(object, Float.parseFloat(value));
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            field.set(object, Boolean.parseBoolean(value));
        } else if (fieldType == long.class || fieldType == Long.class) {
            field.set(object, Long.parseLong(value));
        } else if (fieldType == BigDecimal.class) {
            field.set(object, value);
        }
        // Ajoutez des conditions pour d'autres types de champs si nécessaire
    }

    /**
     * La méthode hasMatchingId() utilise la réflexion pour accéder
     * au champ "id" de l'objet et comparer sa valeur avec l'identifiant recherché.
     * Cette implémentation suppose que le champ "id" est une chaîne de caractères (String).
     *
     * @param object model
     * @param id id
     * @return boolean
     */
    private boolean hasMatchingId(T object, String id) {
        try {
            Field idField = object.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Object fieldValue = idField.get(object);
            return id.equals(fieldValue.toString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Gérez les erreurs d'accès au champ ou si le champ "id" n'existe pas
            e.printStackTrace();
            return false;
        }
    }


    private void saveAll(List<T> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T object : objects) {
                String line = createLineFromObject(object);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String createLineFromObject(T object) {
        StringBuilder lineBuilder = new StringBuilder();
        Field[] fields = object.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(object);
                lineBuilder.append(value).append(",");
            }

            // Supprimez la virgule finale si nécessaire
            if (lineBuilder.length() > 0) {
                lineBuilder.deleteCharAt(lineBuilder.length() - 1);
            }
        }catch (IllegalAccessException e) {
            // Gérez les erreurs d'accès aux champs
            e.printStackTrace();
        }

        return lineBuilder.toString();
    }



}
