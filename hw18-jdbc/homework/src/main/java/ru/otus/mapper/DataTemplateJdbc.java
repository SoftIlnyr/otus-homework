package ru.otus.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    //Пришлось добавить класс метадаты, потому что мы больше никак не узнаем какую либо метаинформацию
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id), rs -> {
            try {
                if (rs.next()) {
                    List<Object> fieldValues = new ArrayList<>();
                    Object idField = rs.getObject(entityClassMetaData.getIdField().getName(), entityClassMetaData.getIdField().getType());
                    fieldValues.add(idField);
                    for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                        Object fieldValue = rs.getObject(field.getName(), field.getType());
                        fieldValues.add(fieldValue);
                    }

                    T t = entityClassMetaData.getConstructor().newInstance(fieldValues.toArray(new Object[0]));
                    return t;
                }
                return null;
            } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                Collections.emptyList(), rs -> {
                    try {
                        List<T> resultList = new ArrayList<>();
                        while (rs.next()) {
                            List<Object> fieldValues = new ArrayList<>();
                            Object idField = rs.getObject(entityClassMetaData.getIdField().getName(), entityClassMetaData.getIdField().getType());
                            fieldValues.add(idField);
                            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                                Object fieldValue = rs.getObject(field.getName(), field.getType());
                                fieldValues.add(fieldValue);
                            }

                            T t = entityClassMetaData.getConstructor().newInstance(fieldValues.toArray(new Object[0]));
                            resultList.add(t);
                        }
                        return resultList;
                    } catch (SQLException | InstantiationException | IllegalAccessException |
                             InvocationTargetException e) {
                        throw new DataTemplateException(e);
                    }
                }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T type) {
        try {
            List<Object> fieldValueList = new ArrayList<>();

            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                Object value = getFieldValue(type, field);
                fieldValueList.add(value);
            }

            String resultSql = entitySQLMetaData.getInsertSql();

            return dbExecutor.executeStatement(
                    connection,
                    resultSql,
                    fieldValueList);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private static <T> Object getFieldValue(T type, Field field) throws IllegalAccessException {
        boolean isAccessible = field.canAccess(type);
        field.setAccessible(true);
        Object value = field.get(type);
        field.setAccessible(isAccessible);
        return value;
    }

    @Override
    public void update(Connection connection, T type) {
        try {
            List<Object> fieldValueList = new ArrayList<>();

            for (Field field : entityClassMetaData.getFieldsWithoutId()) {
                Object value = getFieldValue(type, field);
                fieldValueList.add(value);
            }

            Field idField = entityClassMetaData.getIdField();
            Object idValue = getFieldValue(type, idField);

            fieldValueList.add(idValue);

            dbExecutor.executeStatement(
                    connection,
                    entitySQLMetaData.getUpdateSql(),
                    fieldValueList);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
