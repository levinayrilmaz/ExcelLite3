package de.ayrilmaz.client.hanlder;

import javax.swing.*;

public class CellHandler {
    String input;
    Object[] columnheader;

    public CellHandler(String input, Object[] columnheader) {
        this.input = input;
        this.columnheader = columnheader;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Object[] getColumnheader() {
        return columnheader;
    }

    public void printColumnheader(){
        for (Object o : columnheader) {
            System.out.println(o);
        }
    }

    public Object getCellValue(String cellReference, JTable table) {
        String columnLetter = cellReference.replaceAll("[^A-Z]", ""); // Extrahiere den Buchstaben (Spaltenbuchstabe) aus dem Zellbezug
        int columnIndex = getColumnIndex(columnLetter); // Ermittle den Spaltenindex aus dem Buchstaben
        int rowIndex = Integer.parseInt(cellReference.replaceAll("[^0-9]", "")) - 1; // Extrahiere die Zeilennummer aus dem Zellbezug und konvertiere sie in einen Index (0-basiert)
        return table.getValueAt(rowIndex, columnIndex); // Zugriff auf den Wert in der Tabelle an der angegebenen Zeile und Spalte
    }

    private int getColumnIndex(String columnLetter) {
        int index = 0;
        int power = 1;

        // Iteriere rückwärts durch den Buchstaben und berechne den Spaltenindex
        for (int i = columnLetter.length() - 1; i >= 0; i--) {
            char letter = columnLetter.charAt(i);
            int value = letter - 'A' + 1; // Konvertiere den Buchstabenwert in eine Zahl (A=1, B=2, usw.)
            index += value * power;
            power *= 26; // Es gibt 26 Buchstaben im Alphabet (A-Z)
        }

        return index - 1; // Subtrahiere 1, um den Index von 0 an zu beginnen
    }


    public boolean hasVariableReference() {
        // Überprüfe, ob der Zell-Input eine Variablenreferenz enthält
        String pattern = "[A-Z]\\d+"; // Regulärer Ausdruck für das Muster: ein Buchstabe gefolgt von einer oder mehreren Ziffern

        if(this.input.matches(".*" + pattern + ".*")){
            String variableReference = input.replaceAll("[^A-Z]", ""); // Extrahiere nur den Buchstaben aus der Variablenreferenz
            return containsColunms(variableReference);
        }
        return false;
    }

    private boolean containsColunms(String variableReference){
        for (Object o : columnheader) {
            if(o.equals(variableReference)){
                return true;
            }
        }
        return false;
    }


    public void setColumnheader(Object[][] columnheader) {
        this.columnheader = columnheader;
    }
}
