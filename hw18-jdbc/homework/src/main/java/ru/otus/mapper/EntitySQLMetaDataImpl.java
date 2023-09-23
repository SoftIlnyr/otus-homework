package ru.otus.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        String allFields = ((List<Field>) entityClassMetaData.getAllFields()).stream().map(Field::getName).collect(Collectors.joining(", "));
        return String.format("select %s from %s;",
                allFields,
                entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        String allFields = ((List<Field>) entityClassMetaData.getAllFields()).stream().map(Field::getName).collect(Collectors.joining(", "));

        return String.format("select %s from %s where %s = ?;",
                allFields,
                entityClassMetaData.getName(),
                entityClassMetaData.getIdField().getName()
        );
    }

    @Override
    public String getInsertSql() {
        List<Field> fieldsWithoutId = ((List<Field>) entityClassMetaData.getFieldsWithoutId()).stream()
                .collect(Collectors.toList());

        String fieldsWithoutIdString = fieldsWithoutId.stream().map(Field::getName)
                .collect(Collectors.joining(", "));

        String valuesToInsert = fieldsWithoutId.stream().map(field -> "?").collect(Collectors.joining(", "));

        String format = String.format("insert into %s (%s) values(%s);",
                entityClassMetaData.getName(),
                fieldsWithoutIdString,
                valuesToInsert
        );
        return format;
    }

    @Override
    public String getUpdateSql() {
        String fieldsWithoutId = ((List<Field>) entityClassMetaData.getFieldsWithoutId()).stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(", "));

        String result = String.format("update %s set %s where %s = ?;",
                entityClassMetaData.getName(),
                fieldsWithoutId,
                entityClassMetaData.getIdField().getName()
        );
        return result;
    }
}
