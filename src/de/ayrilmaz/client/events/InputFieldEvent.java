package de.ayrilmaz.client.events;

import de.ayrilmaz.client.extra.CellData;
import de.ayrilmaz.client.hanlder.MathHandler;

import javax.swing.*;

public class InputFieldEvent {

    JTable table;
    JTextField inputField;

    public static void init(JTable table, JTextField inputField, CellData cellData) {
        int selectedRow = table.getSelectedRow();
        int selectedColumn = table.getSelectedColumn();
        if (selectedRow != -1 && selectedColumn != -1) {
            String input = inputField.getText();
            MathHandler mathHandler = new MathHandler(input);
            double erg = 0;
            if(mathHandler.checkForMathOperations(input)){
                erg = mathHandler.calculate();
                if(erg == Double.MAX_VALUE){
                    //JOptionPane.showMessageDialog(frame, "Fehlerhafte Eingabe!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                table.setValueAt(String.valueOf(erg), selectedRow, selectedColumn);
                cellData.insertData(selectedRow, selectedColumn, String.valueOf(erg), input);
            }else{
                table.setValueAt(input, selectedRow, selectedColumn);
            }


        }
    }
}
