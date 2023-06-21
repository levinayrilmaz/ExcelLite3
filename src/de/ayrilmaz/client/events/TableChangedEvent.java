package de.ayrilmaz.client.events;

import de.ayrilmaz.client.extra.CellData;
import de.ayrilmaz.client.extra.Config;
import de.ayrilmaz.client.hanlder.CellHandler;
import de.ayrilmaz.client.hanlder.FileHandler;
import de.ayrilmaz.client.hanlder.MathHandler;

import javax.swing.*;

import static de.ayrilmaz.client.utils.ColumnHeader.*;

public class TableChangedEvent {

    private JTable table;
    private JTextField inputField;

    public static void init(JTable table, JTextField inputField, CellData cellData, JTextField selectedCellField) {
        int selectedRow = table.getSelectedRow();
        int selectedColumn = table.getSelectedColumn();




        String selectedCells = selectedCellField.getText();
        System.out.println(selectedCells);
        if(selectedCells.contains(":")) {
            String[] cells = selectedCellField.getText().split(":");
            int startRow = Integer.parseInt(cells[0].replaceAll("[^0-9]", ""));
            int endRow = Integer.parseInt(cells[1].replaceAll("[^0-9]", ""));
            int startColumn = getColumnIndex(cells[0].replaceAll("[^A-Za-z]", ""));
            int endColumn = getColumnIndex(cells[1].replaceAll("[^A-Za-z]", ""));

            String input;
            if(table.getValueAt(startRow, startColumn) != null){
                input = table.getValueAt(startRow, startColumn).toString();
            }else if (table.getValueAt(startRow, endColumn) != null){
                input = table.getValueAt(startRow, endColumn).toString();
            }else if(table.getValueAt(endRow, startColumn) != null) {
                input = table.getValueAt(endRow, startColumn).toString();
            }else{
                input = table.getValueAt(endRow, endColumn).toString();
            }


            for(int i = startRow-1; i < endRow; i++){
                for(int j = startColumn; j <= endColumn; j++){
                    table.setValueAt(input, i, j);
                }
            }
            inputField.setText(input);

        }else{
            String input = table.getValueAt(selectedRow, selectedColumn).toString();
            MathHandler mathHandler = new MathHandler(input);
            double erg = 0;

            CellHandler cellHandler = new CellHandler(input, getColumnHeaders(table));
            if(cellHandler.hasVariableReference()){
                System.out.println(cellHandler.getCellValue(input, table));
                String value = (String) cellHandler.getCellValue(input, table);
                cellData.insertData(selectedRow, selectedColumn, value, input);
                table.setValueAt(value, selectedRow, selectedColumn);
            }
            //System.out.println(selectedValue.toString());



            if(mathHandler.checkForMathOperations(input)) {
                erg = mathHandler.calculate();
                if (!(erg == Double.MAX_VALUE)) {
                    cellData.insertData(selectedRow, selectedColumn, String.valueOf(erg), input);
                    table.setValueAt(String.valueOf(erg), selectedRow, selectedColumn);
                }
            }
            inputField.setText(input);
        }





        if(Config.autoSave){
            FileHandler.saveFile(Config.current_project_path);
        }

    }

}
