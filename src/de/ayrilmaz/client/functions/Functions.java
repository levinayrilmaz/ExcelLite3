package de.ayrilmaz.client.functions;

import javax.swing.*;

import static de.ayrilmaz.client.utils.ColumnHeader.getColumnIndex;

public class Functions {


    public static int count(String startCell, String endCell){
        int startRow = Integer.parseInt(startCell.split("")[1]);
        int endRow = Integer.parseInt(endCell.split("")[1]);
        int startColumn = getColumnIndex(startCell.split("")[0]);
        int endColumn = getColumnIndex(endCell.split("")[0]);

        return (endRow - startRow + 1) * (endColumn - startColumn + 1);

    }

    public static double average(int startRow, int endRow, int startColumn, int endColumn, JTable table){

        double sum = 0;
        int count = 0;
        for(int i = startRow; i <= endRow; i++){
            for(int j = startColumn; j <= endColumn; j++){
                if(table.getValueAt(i, j) != null){
                    try{
                        sum += Double.parseDouble(table.getValueAt(i, j).toString());
                        count++;
                    }catch (Exception e){
                        return 0;
                    }

                }

            }
        }
        return sum / count;
    }




    private String getColumnHeaderText(int index) {
        StringBuilder sb = new StringBuilder();
        while (index >= 0) {
            sb.insert(0, (char)('A' + index % 26));
            index = (index / 26) - 1;
        }
        return sb.toString();
    }



}
