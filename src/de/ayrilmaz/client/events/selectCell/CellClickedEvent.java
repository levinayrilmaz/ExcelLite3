package de.ayrilmaz.client.events.selectCell;

import de.ayrilmaz.client.extra.CellData;
import de.ayrilmaz.client.gui.TableGUI;
import de.ayrilmaz.client.listeners.ChangeFontActionListener;
import de.ayrilmaz.client.utils.Table;

import javax.swing.*;

import java.awt.*;
import java.util.Map;

import static de.ayrilmaz.client.utils.ColumnHeader.getColumnHeaderText;

public class CellClickedEvent {

    JTable table;
    JTextField inputField;

    public static void init(JTable table, JTextField inputField, JTextField selectedCellField, JComboBox<?> fontComboBox, CellData cellData){
        int[] selectedRows = table.getSelectedRows();
        int[] selectedColumns = table.getSelectedColumns();


        // Überprüfe, ob tatsächlich Zellen ausgewählt wurden
        if (selectedRows.length > 0 && selectedColumns.length > 0) {
            int startRow = selectedRows[0];
            int endRow = selectedRows[selectedRows.length-1];
            int startColumn = selectedColumns[0];
            int endColumn = selectedColumns[selectedColumns.length - 1];


            if(startRow==endRow && startColumn==endColumn){
                Object selectedValue = table.getValueAt(startRow, startColumn);

                inputField.setText("");
                String cellText = getColumnHeaderText(startColumn) + (startRow+1);




                if(selectedValue != null){
                    //String selectedFont = (String) fontComboBox.getSelectedItem();
                    //Font font = new Font(selectedFont, Font.PLAIN, 12);

                    /*
                    String font = ChangeFontActionListener.getFont(startRow, startColumn, cellFonts);
                    System.out.println(font);
                    fontComboBox.setSelectedItem(font);

                     */



                    String s = selectedValue.toString();


/*
                                CellHandler cellHandler = new CellHandler(selectedValue.toString(), getColumnHeaders());
                                if(cellHandler.hasVariableReference()){
                                    System.out.println(cellHandler.getCellValue(selectedValue.toString(), table));
                                    table.setValueAt(cellHandler.getCellValue(selectedValue.toString(), table), startRow, startColumn);
                                }

             */




                    if(cellData.getData(cellText) != null) {
                        inputField.setText(cellData.getData(cellText)[2]);

                    }else{
                        inputField.setText(s);
                    }





                    //System.out.println(selectedValue.toString());

                }
                selectedCellField.setText(cellText);
            }else{
                String startChar = getColumnHeaderText(startColumn);
                String endChar = getColumnHeaderText(endColumn);

                String startCell = (startChar + (startRow+1));
                String endCell = (endChar + (endRow+1));

                selectedCellField.setText(startChar + (startRow+1) + ":" + endChar + (endRow+1));





                System.out.println(table.getValueAt(startRow, startColumn));
                System.out.println(table.getValueAt(endRow, endColumn));

                // Verwende die Werte von startRow, endRow, startColumn und endColumn für deine weitere Verarbeitung
            }




        }
    }

}
