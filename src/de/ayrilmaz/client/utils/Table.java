package de.ayrilmaz.client.utils;

import java.io.Serializable;

public class Table implements Serializable {
    public String[][] data;
    private String name;

    public Table(int rows, int columns) {
        data = new String[rows][columns];
    }

    public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return data[0].length;
    }

    public String getValueAt(int row, int column) {
        return data[row][column];
    }

    public void setValueAt(int row, int column, String value) {
        data[row][column] = value;
    }

    public String[][] getTableData() {
        return data;
    }

    public void setTableData(String[][] newData) {
        if (newData.length < getRowCount() || newData[0].length < getColumnCount()) {
            throw new IllegalArgumentException("Invalid table dimensions");
        }
        data = newData;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
