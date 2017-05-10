package com.amazonaws.mobile.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "arn:aws:dynamodb:us-east-1:040667233965:table/Pantry")

public class PantryDO {
    private String _userId;
    /* private String _expiry;
     private Double _iD;*/
    private String _name;
   /* private String _quantity;
    private String _type;*/

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId=_userId;
    }

    /*@DynamoDBRangeKey(attributeName = "Expiry")
    @DynamoDBAttribute(attributeName = "Expiry")
    public String getExpiry() {
        return _expiry;
    }*/

    /*public void setExpiry(final String _expiry) {
        this._expiry = _expiry;
    }
    @DynamoDBAttribute(attributeName = "ID")
    public Double getID() {
        return _iD;
    }

    public void setID(final Double _iD) {
        this._iD = _iD;
    }*/
    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }
   /* @DynamoDBAttribute(attributeName = "Quantity")
    public String getQuantity() {
        return _quantity;
    }

    public void setQuantity(final String _quantity) {
        this._quantity = _quantity;
    }
    @DynamoDBAttribute(attributeName = "Type")
    public String getType() {
        return _type;
    }

    public void setType(final String _type) {
        this._type = _type;
    }*/

}
