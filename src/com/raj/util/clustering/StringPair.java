package com.raj.util.clustering;
public class StringPair {

    private String key;
    private String value;

    public StringPair(String key, String value) {
        this.setKey(key);
        this.setValue(value);
    }
    
    
    
    @Override
    public boolean equals(Object obj) {
        return getKey().equals(((StringPair)obj).getValue());
    }
    
    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
    
    @Override
    public String toString() {
        return getKey();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
