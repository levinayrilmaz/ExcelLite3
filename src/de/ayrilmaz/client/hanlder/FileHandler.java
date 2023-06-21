package de.ayrilmaz.client.hanlder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.ayrilmaz.client.ExcelLite;
import de.ayrilmaz.client.extra.CellData;
import de.ayrilmaz.client.extra.Config;
import de.ayrilmaz.client.gui.TableGUI;
import de.ayrilmaz.client.utils.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler {

    public static void save(JFrame frame, JTable table_input, Table data){
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION){
            String filePath = fileChooser.getSelectedFile().getPath();
            ExcelLite.config.set("current_project_path", filePath);
            Config.current_project_path = filePath;
            saveFile(filePath);
        }
    }

    public static void saveFile(String filepath){
        try{
            if(!filepath.endsWith(".exlt")) filepath += ".exlt";
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            /*
            String[][] data_input = getAllCellData(table);

            int last_row = -1;
            int last_column = -1;

            for (int i = 0; i < data_input.length; i++) {
                for (int j = 0; j < data_input[i].length; j++) {
                    if (!data_input[i][j].equals("")) {
                        // Aktualisiere den Index der äußersten belegten Zeile
                        last_row = Math.max(last_row, i);
                        // Aktualisiere den Index der äußersten belegten Spalte
                        last_column = Math.max(last_column, j);
                    }
                }
            }


            String[][] stored_data = new String[last_row+1][last_column+1];

            for (int i = 0; i < stored_data.length; i++) {
                for (int j = 0; j < stored_data[i].length; j++) {
                    stored_data[i][j] = data_input[i][j];
                    if(!stored_data[i][j].equals("")){
                        System.out.println(stored_data[i][j]);
                    }

                }
            }

             */

            for(int i=0; i<TableGUI.data_list.size(); i++){
                Table data = TableGUI.data_list.get(i);
                JTable table = TableGUI.tables.get(i);

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                String[][] tableData = new String[model.getRowCount()][model.getColumnCount()];
                for(int row = 0; row < model.getRowCount(); row++){
                    for(int col = 0; col < model.getColumnCount(); col++){
                        tableData[row][col] = (String) model.getValueAt(row, col);
                    }
                }
                data.setTableData(tableData);

            }




            objectOutputStream.writeObject(TableGUI.data_list);

            objectOutputStream.writeObject(TableGUI.cellData_list);
            objectOutputStream.writeObject(TableGUI.cellBGColors_list);

            objectOutputStream.writeObject(Config.autoSave);
            objectOutputStream.writeObject(Config.current_project_path);

            objectOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean openFile(JFrame frame){
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION){
            String filePath = fileChooser.getSelectedFile().getPath();
            try{
                FileInputStream fileInputStream = new FileInputStream(filePath);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);


                //Data
                TableGUI.data_list = (ArrayList<Table>) objectInputStream.readObject();

                TableGUI.cellData_list  = (ArrayList<CellData>) objectInputStream.readObject();

                TableGUI.cellBGColors_list = (ArrayList<Map<Point, Color>>) objectInputStream.readObject();


                /*
                Object cell_data = objectInputStream.readObject();
                if (cell_data instanceof List<?> cellDataList) {
                    if (!cellDataList.isEmpty() && cellDataList.get(0) instanceof String[]) {
                        List<String[]> typedList = new ArrayList<>();
                        for (Object item : cellDataList) {
                            typedList.add((String[]) item);
                        }
                        CellData.cell_data = typedList;
                    }
                }

                Object cellColors = objectInputStream.readObject();
                if (cellColors instanceof HashMap<?, ?>) {
                    // Weise das geladene Objekt der Variable cellColors zu
                    TableGUI.cellBGColors = (HashMap<Point, Color>) cellColors;
                }

                 */



                Config.autoSave = (boolean) objectInputStream.readObject();
                Config.current_project_path = (String) objectInputStream.readObject();



                objectInputStream.close();



            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static void exportTableAsPDF(JTable table, JFrame frame){
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();

            Document document = new Document();

            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
                pdfTable.setWidthPercentage(100);

                // Spaltenüberschriften hinzufügen
                for (int column = 0; column < table.getColumnCount(); column++) {
                    PdfPCell cell = new PdfPCell(new Phrase(table.getColumnName(column)));
                    pdfTable.addCell(cell);
                }

                // Zelleninhalte hinzufügen
                for (int row = 0; row < table.getRowCount(); row++) {
                    for (int column = 0; column < table.getColumnCount(); column++) {
                        Object value = table.getValueAt(row, column);
                        PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(value)));
                        pdfTable.addCell(cell);
                    }
                }

                // PDF-Dokument die Tabelle hinzufügen
                document.add(pdfTable);

                document.close();
                writer.close();

                System.out.println("PDF exportiert: " + filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String[][] getAllCellData(JTable table) {
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        String[][] data = new String[rowCount][columnCount];

        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Object value = table.getValueAt(row, column);
                if (value == null) {
                    data[row][column] = "";
                }else {
                    data[row][column] = value.toString();
                }
            }
        }

        return data;
    }
}
