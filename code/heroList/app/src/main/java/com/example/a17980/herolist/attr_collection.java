package com.example.a17980.herolist;

public class attr_collection {
    private String m_attr;
    private float count;

    public attr_collection(String attr, float count) {
        this.m_attr = attr;
        this.count = count;
    }

    public String getM_attr() {
        return m_attr;
    }

    public float getCount() {
        return count;
    }

    public void setM_attr(String m_attr) {
        this.m_attr = m_attr;
    }

    public void setCount(float count) {
        this.count = count;
    }
}
