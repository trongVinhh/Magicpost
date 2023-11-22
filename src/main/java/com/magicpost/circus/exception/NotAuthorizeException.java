package com.magicpost.circus.exception;

public class NotAuthorizeException extends RuntimeException{
    private String roleName;
    private String fieldName;
    private String fieldValue;

    public NotAuthorizeException(String roleName, String fieldName, String fieldValue) {
        super(String.format("%s not authorize with %s : %s", roleName, fieldName, fieldValue));
        this.roleName = roleName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
