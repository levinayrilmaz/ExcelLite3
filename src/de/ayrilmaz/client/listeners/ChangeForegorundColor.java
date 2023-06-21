package de.ayrilmaz.client.listeners;

import de.ayrilmaz.client.gui.TableGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

import static de.ayrilmaz.client.utils.ColumnHeader.getColumnIndex;

public class ChangeForegorundColor {
    public static void colorButton(JTable table, JTextField selectedCellField, JFrame frame, Map<Point, Color> cellBGColors, Map<Point, Color> cellFGColors){
        int selectedRow = table.getSelectedRow();
        int selectedColumn = table.getSelectedColumn();

        String selectedCells = selectedCellField.getText();
        System.out.println(selectedCells);
        if(selectedCells.contains(":")){
            String[] cells = selectedCellField.getText().split(":");
            System.out.println(cells[0]);
            System.out.println(cells[1]);
            Color color = JColorChooser.showDialog(frame, "Hintergrundfarbe auswählen", table.getForeground());
            if (color != null) {
                // Speichere die Hintergrundfarbe für die ausgewählte Zelle

                // A12 : B24

                int startRow = Integer.parseInt(cells[0].replaceAll("[^0-9]", ""));
                int endRow = Integer.parseInt(cells[1].replaceAll("[^0-9]", ""));
                int startColumn = getColumnIndex(cells[0].replaceAll("[^A-Za-z]", ""));
                int endColumn = getColumnIndex(cells[1].replaceAll("[^A-Za-z]", ""));

                for(int i = startRow-1; i < endRow; i++){
                    for(int j = startColumn; j <= endColumn; j++){
                        System.out.println("x: "+i+" y: "+j);
                        changeColor(table, i, j, color, cellBGColors, cellFGColors);

                    }
                }

            }
        }else{
            if (selectedRow != -1 && selectedColumn != -1) {
                Color color = JColorChooser.showDialog(frame, "Hintergrundfarbe auswählen", table.getBackground());
                if (color != null) {
                    changeColor(table, selectedRow, selectedColumn, color, cellBGColors, cellFGColors);

                }
            }
        }

    }

    private static void changeColor(JTable table, int selectedRow, int selectedColumn, Color color, Map<Point, Color> cellBGColors, Map<Point, Color> cellFGColors) {
        Point cell = new Point(selectedRow, selectedColumn);
        cellFGColors.put(cell, color);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Überprüfe, ob die aktuelle Zelle in der Map der Hintergrundfarben enthalten ist
                Point cell = new Point(row, column);
                if (cellFGColors.containsKey(cell)) {
                    if(cellBGColors.containsKey(cell)){
                        component.setBackground(cellBGColors.get(cell));
                    }
                    component.setForeground(cellFGColors.get(cell));
                } else {
                    /*
                    if(TableGUI.cellBGColors.containsKey(cell)){
                        component.setBackground(table.getBackground());
                    }

                     */
                    component.setForeground(table.getForeground());
                }
                Component old_component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                return old_component;
            }
        });

        table.repaint();
    }
}
