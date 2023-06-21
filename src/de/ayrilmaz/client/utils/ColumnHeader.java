package de.ayrilmaz.client.utils;

import javax.swing.*;

public class ColumnHeader {
    public static String getColumnHeaderText(int index) {
        StringBuilder sb = new StringBuilder();
        while (index >= 0) {
            sb.insert(0, (char)('A' + index % 26));
            index = (index / 26) - 1;
        }
        return sb.toString();
    }

    public static Object[] getColumnHeaders(JTable table) {
        int columns = table.getColumnCount();
        Object[] columnHeaders = new Object[columns];
        for (int i = 0; i < columns; i++) {
            columnHeaders[i] = getColumnHeaderText(i);
        }
        return columnHeaders;
    }

    public static int getColumnIndex(String headerText) {
        int index = 0;
        for (int i = 0; i < headerText.length(); i++) {
            index = index * 26 + (headerText.charAt(i) - 'A' + 1);
        }
        return index - 1;
    }

}
