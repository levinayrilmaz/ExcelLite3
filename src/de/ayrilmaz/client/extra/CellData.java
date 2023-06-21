package de.ayrilmaz.client.extra;

import java.io.Serializable;
import java.util.*;

import static de.ayrilmaz.client.utils.ColumnHeader.getColumnHeaderText;

public class CellData implements Serializable {
    public List<String[]> cell_data;

    /*

    data[0] = A2 (Zelle)
    data[1] = 1 (Wert)
    data[2] = 2 * 1 (Input)

     */

    public CellData() {
        cell_data = new ArrayList<>();
    }


    public void insertData(int row, int column, String value, String input) {
        String[] data = new String[3];
        data[0] = getColumnHeaderText(column)+(row+1);
        data[1] = value;
        data[2] = input;

        cell_data.add(data);
    }

    public String[] getData(String cell) {
        for (String[] strings : cell_data) {
            if (strings[0].equals(cell)) {
                return strings;
            }
        }
        return null;
    }
}
