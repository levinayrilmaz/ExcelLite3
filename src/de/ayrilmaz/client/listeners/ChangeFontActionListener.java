package de.ayrilmaz.client.listeners;

import de.ayrilmaz.client.gui.TableGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

import static de.ayrilmaz.client.utils.ColumnHeader.getColumnIndex;

public class ChangeFontActionListener {
    public static void action(JTable table, JTextField selectedCellField, JFrame frame, JComboBox<?> fontComboBox, Map<Point, String> cellFonts){
        int selectedRow = table.getSelectedRow();
        int selectedColumn = table.getSelectedColumn();

        String selectedCells = selectedCellField.getText();
        System.out.println(selectedCells);
        if(selectedCells.contains(":")){

            String[] cells = selectedCellField.getText().split(":");
            System.out.println(cells[0]);
            System.out.println(cells[1]);


            // Speichere die Hintergrundfarbe für die ausgewählte Zelle

            // A12 : B24

            int startRow = Integer.parseInt(cells[0].replaceAll("[^0-9]", ""));
            int endRow = Integer.parseInt(cells[1].replaceAll("[^0-9]", ""));
            int startColumn = getColumnIndex(cells[0].replaceAll("[^A-Za-z]", ""));
            int endColumn = getColumnIndex(cells[1].replaceAll("[^A-Za-z]", ""));

            String selectedFont = (String) fontComboBox.getSelectedItem();
            Font font = new Font(selectedFont, Font.PLAIN, 12);

            for(int i = startRow-1; i < endRow; i++){
                for(int j = startColumn; j <= endColumn; j++){
                    changeFont(selectedRow, selectedColumn, table, cellFonts, selectedFont);
                }
            }

        }else{
            if (selectedRow != -1 && selectedColumn != -1) {

                String selectedFont = (String) fontComboBox.getSelectedItem();
                Font font = new Font(selectedFont, Font.PLAIN, 12);

               changeFont(selectedRow, selectedColumn, table, cellFonts, selectedFont);

            }
        }
    }

    private static void changeFont(int selectedRow, int selectedColumn, JTable table, Map<Point, String> cellFonts, String font){
        // Schriftart für die ausgewählte Zelle speichern
        Point cell = new Point(selectedRow, selectedColumn);
        cellFonts.put(cell, font);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Point cell = new Point(row, column);
                if (cellFonts.containsKey(cell)) {
                    String font = cellFonts.get(cell);
                    Font f = new Font(font, Font.PLAIN, 12);
                    component.setFont(f);
                }


                return component;
            }
        });

        table.repaint();

    }

    public static String getFont(int selectedRow, int selectedColumn, Map<Point, String> cellFonts){
        Point cell = new Point(selectedRow, selectedColumn);
        return cellFonts.getOrDefault(cell, "Arial");
    }
}
