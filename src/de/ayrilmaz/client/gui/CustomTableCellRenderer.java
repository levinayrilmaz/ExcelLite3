package de.ayrilmaz.client.gui;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

    JTable table;

    public CustomTableCellRenderer(JTable table) {
        this.table = table;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Überprüfen, ob die Zelle ausgewählt ist
        if (isSelected) {
            // Hintergrundfarbe der ausgewählten Zelle auf transparent setzen
            component.setBackground(new Color(0, 0, 0, 0));

        }

        boolean isCellSelected = isSelected || isCellInSelection(row, column);
        setCellBorder(component, isCellSelected);

        return component;
    }

    // Überprüft, ob die Zelle in der aktuellen Auswahl ist
    private boolean isCellInSelection(int row, int column) {
        int[] selectedRows = table.getSelectedRows();
        int[] selectedColumns = table.getSelectedColumns();

        for (int selectedRow : selectedRows) {
            for (int selectedColumn : selectedColumns) {
                if (row == selectedRow && column == selectedColumn) {
                    return true;
                }
            }
        }

        return false;
    }


    private void setCellBorder(Component component, boolean isSelected) {
        if (isSelected) {
            ((JComponent) component).setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        } else {
            ((JComponent) component).setBorder(BorderFactory.createEmptyBorder());
        }
    }
}
