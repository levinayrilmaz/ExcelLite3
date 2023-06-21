package de.ayrilmaz.client.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.itextpdf.text.pdf.PdfWriter;
import de.ayrilmaz.client.ExcelLite;
import de.ayrilmaz.client.events.selectCell.CellClickedEvent;
import de.ayrilmaz.client.events.InputFieldEvent;
import de.ayrilmaz.client.events.TableChangedEvent;
import de.ayrilmaz.client.extra.CellData;
import de.ayrilmaz.client.extra.Config;
import de.ayrilmaz.client.extra.TableToPDFExporter;
import de.ayrilmaz.client.listeners.ChangeBackgroundColor;
import de.ayrilmaz.client.hanlder.FileHandler;
import de.ayrilmaz.client.listeners.ChangeFontActionListener;
import de.ayrilmaz.client.listeners.ChangeForegorundColor;
import de.ayrilmaz.client.utils.ColumnHeader;
import de.ayrilmaz.client.utils.Table;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TableGUI {
    public JFrame frame;

    //public JTable[] table;
    public static ArrayList<JTable> tables = new ArrayList<>();

    //public Table[] data;
    public static ArrayList<Table> data_list = new ArrayList<>();

    public static ArrayList<CellData> cellData_list = new ArrayList<>();


    public JTextField inputField;
    public JTextField selectedCellField;
    public String selectedCell;
    public JTextField editingField;
    private boolean openFile = false;
    public JComboBox<String> fontComboBox;
    private JCheckBox autoSaveCheckBox;
    private JTabbedPane tabbedPane;
    public JCheckBox darkModeCheckBox;

    //public static Map<Point, Color> cellBGColors = new HashMap<>();
    public static ArrayList<Map<Point, Color>> cellBGColors_list = new ArrayList<>();

    //public static Map<Point, Color> cellFGColors = new HashMap<>();
    public static ArrayList<Map<Point, Color>> cellFGColors_list = new ArrayList<>();


    //public static Map<Point, String> cellFonts = new HashMap<>();
    public static ArrayList<Map<Point, String>> cellFonts_list = new ArrayList<>();


    public TableGUI(int rows, int columns, String name) {

        frame = new JFrame("Excel Lite");

        ImageIcon logo = new ImageIcon("img/logo.png");
        frame.setIconImage(logo.getImage());

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Datei");
        JMenuItem newMenuItem = new JMenuItem("Neu");
        JMenuItem openMenuItem = new JMenuItem("Öffnen");
        JMenuItem saveMenuItem = new JMenuItem("Speichern");

        JMenu exportMenu = new JMenu("Exportieren");
        JMenuItem exportPDFMenuItem = new JMenuItem("Exportieren als PDF");
        exportMenu.add(exportPDFMenuItem);

        JMenuItem deleteSheet = new JMenuItem("Tabelle löschen");

        JMenu aboutMenu = new JMenu("Über");
        JMenuItem authorMenuItem = new JMenuItem("Autor");
        JMenuItem aboutMenuItem = new JMenuItem("Über ExcelLite");

        JMenu functionsMenu = new JMenu("Funktionen");
        JMenuItem sumMenuItem = new JMenuItem("Summation");
        JMenuItem subMenuItem = new JMenuItem("Subtraction");
        JMenuItem mulMenuItem = new JMenuItem("Multiplication");
        JMenuItem divMenuItem = new JMenuItem("Division");
        JMenuItem avgMenuItem = new JMenuItem("Average");
        JMenuItem minMenuItem = new JMenuItem("Minimum");
        JMenuItem maxMenuItem = new JMenuItem("Maximum");
        JMenuItem countMenuItem = new JMenuItem("Count");



        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exportMenu);
        fileMenu.add(deleteSheet);
        menuBar.add(fileMenu);

        aboutMenu.add(aboutMenuItem);
        aboutMenu.add(authorMenuItem);
        menuBar.add(aboutMenu);

        functionsMenu.add(sumMenuItem);
        functionsMenu.add(subMenuItem);
        functionsMenu.add(mulMenuItem);
        functionsMenu.add(divMenuItem);

        functionsMenu.add(avgMenuItem);
        functionsMenu.add(minMenuItem);
        functionsMenu.add(maxMenuItem);
        functionsMenu.add(countMenuItem);

        menuBar.add(functionsMenu);

        frame.setJMenuBar(menuBar);


        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(frame, "Sind Sie sicher?\nAlle nicht gespeicherten Änderungen gehen verloren!", "Neue Datei", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    new TableGUI(tables.get(tabbedPane.getSelectedIndex()).getRowCount(), tables.get(tabbedPane.getSelectedIndex()).getColumnCount(), "unnamed");
                    frame.dispose();
                }

            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileHandler.save(frame, tables.get(tabbedPane.getSelectedIndex()), data_list.get(tabbedPane.getSelectedIndex()));
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(FileHandler.openFile(frame)){
                    openFile = true;
                    tables.clear();
                    tabbedPane.removeAll();
                    for(int i = 0; i < data_list.size(); i++){
                        tabbedPane.addTab(data_list.get(i).getName(), loadTable(cellData_list.get(i), data_list.get(i), cellBGColors_list.get(i)));

                    }
                    for(int i=0; i<tables.size(); i++){
                        JTable table = tables.get(i);
                        Table data = data_list.get(i);


                        for(int j=0; j<table.getRowCount(); j++){
                            for(int k=0; k<table.getColumnCount(); k++){
                                table.setValueAt(data.getTableData()[j][k], j, k);
                            }
                        }

                    }

                    autoSaveCheckBox.setSelected(Config.autoSave);
                    darkModeCheckBox.setSelected(Config.darkMode);

                    for(Table data: data_list){
                        System.out.println(data);
                    }

                    openFile = false;
                }
            }
        });

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Excel Lite\nVersion 3.0\n\nAuthor: Levin Ayrilmaz");
            }
        });

        exportPDFMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileHandler.exportTableAsPDF(tables.get(tabbedPane.getSelectedIndex()), frame);
            }
        });

        deleteSheet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(frame, "Sind Sie sicher?\nAlle nicht gespeicherten Änderungen gehen verloren!", "Tabelle löschen", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    tables.remove(tabbedPane.getSelectedIndex());
                    data_list.remove(tabbedPane.getSelectedIndex());
                    cellData_list.remove(tabbedPane.getSelectedIndex());
                    cellBGColors_list.remove(tabbedPane.getSelectedIndex());
                    cellFGColors_list.remove(tabbedPane.getSelectedIndex());
                    cellFonts_list.remove(tabbedPane.getSelectedIndex());
                    tabbedPane.remove(tabbedPane.getSelectedIndex());
                }
            }
        });




        //frame.getContentPane().add(panel, BorderLayout.CENTER);




        JPanel topPanel = new JPanel(new BorderLayout());


        JPanel inputPanel = new JPanel(new BorderLayout());

        inputField = new JTextField();
        selectedCellField = new JTextField(5);
        selectedCellField.setHorizontalAlignment(SwingConstants.CENTER);
        selectedCellField.setEditable(false);


        autoSaveCheckBox = new JCheckBox("Auto Save");
        autoSaveCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(autoSaveCheckBox.isSelected()){
                    if(Config.current_project_path.equals("")){
                        FileHandler.save(frame, tables.get(tabbedPane.getSelectedIndex()), data_list.get(tabbedPane.getSelectedIndex()));
                    }
                    Config.autoSave = true;
                } else {
                    Config.autoSave = false;
                }
            }
        });

        JButton newFileButton = new JButton("Neu");
        Icon newFileIcon = new ImageIcon("img/new_file.png");
        newFileButton.setIcon(newFileIcon);
        newFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(frame, "Sind Sie sicher?\nAlle nicht gespeicherten Änderungen gehen verloren!", "Neue Datei", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    new TableGUI(tables.get(tabbedPane.getSelectedIndex()).getRowCount(), tables.get(tabbedPane.getSelectedIndex()).getColumnCount(), "unnamed");
                    frame.dispose();
                }
            }
        });

        JButton saveFileButton = new JButton("Speichern");
        Icon saveFileIcon = new ImageIcon( "img/save_file.png");
        saveFileButton.setIcon(saveFileIcon);

        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileHandler.save(frame, tables.get(tabbedPane.getSelectedIndex()), data_list.get(tabbedPane.getSelectedIndex()));
            }
        });

        JButton openFileButton = new JButton("Öffnen");
        Icon openFileIcon = new ImageIcon("img/open_file.png");
        openFileButton.setIcon(openFileIcon);
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(FileHandler.openFile(frame)){
                    openFile = true;
                    tables.clear();
                    tabbedPane.removeAll();
                    for(int i = 0; i < data_list.size(); i++){
                        tabbedPane.addTab(data_list.get(i).getName(), loadTable(cellData_list.get(i), data_list.get(i), cellBGColors_list.get(i)));

                    }
                    for(int i=0; i<tables.size(); i++){
                        JTable table = tables.get(i);
                        Table data = data_list.get(i);


                        for(int j=0; j<table.getRowCount(); j++){
                            for(int k=0; k<table.getColumnCount(); k++){
                                table.setValueAt(data.getTableData()[j][k], j, k);
                            }
                        }

                    }

                    autoSaveCheckBox.setSelected(Config.autoSave);
                    darkModeCheckBox.setSelected(Config.darkMode);

                    for(Table data: data_list){
                        System.out.println(data);
                    }

                    openFile = false;
                }
            }
        });




        JButton colorButton = new JButton();
        //Icon ic = UIManager.getIcon("OptionPane.questionIcon");
        Icon colorIcon = new ImageIcon("img/paint_icon.png");
        colorButton.setIcon(colorIcon);
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeBackgroundColor.colorButton(tables.get(tabbedPane.getSelectedIndex()), selectedCellField, frame, cellBGColors_list.get(tabbedPane.getSelectedIndex()), cellFGColors_list.get(tabbedPane.getSelectedIndex()));
            }
        });

        JButton fontColorButton = new JButton();
        Icon fontColorIcon = new ImageIcon("img/paint_font.png");
        fontColorButton.setIcon(fontColorIcon);
        fontColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeForegorundColor.colorButton(tables.get(tabbedPane.getSelectedIndex()), selectedCellField, frame, cellBGColors_list.get(tabbedPane.getSelectedIndex()), cellFGColors_list.get(tabbedPane.getSelectedIndex()));
            }
        });

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fontNames);
        fontComboBox.setSelectedIndex(14);

        fontComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeFontActionListener.action(tables.get(tabbedPane.getSelectedIndex()), selectedCellField, frame, fontComboBox, cellFonts_list.get(tabbedPane.getSelectedIndex()));
            }
        });

        JButton addTableButton = new JButton("Tabelle hinzufügen");
        addTableButton.setPreferredSize(new Dimension(200, 30));

        darkModeCheckBox = new JCheckBox("Dark Mode (Beta)");
        darkModeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Config.position = frame.getLocation();
                if(darkModeCheckBox.isSelected()){
                    FlatDarkLaf.setup();
                    Config.darkMode = true;
                } else {
                    FlatLightLaf.setup();
                    Config.darkMode = false;
                }
                Config.changeDarkMode = true;
                frame.dispose();
                new TableGUI(tables.get(tabbedPane.getSelectedIndex()).getRowCount(), tables.get(tabbedPane.getSelectedIndex()).getColumnCount(), "unnamed");
            }
        });


        Config.used_font = (String) fontComboBox.getSelectedItem();


        inputPanel.add(colorButton, BorderLayout.EAST);

        inputPanel.add(selectedCellField, BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);


        JPanel designPanel = new JPanel(new FlowLayout());

        designPanel.add(autoSaveCheckBox);
        designPanel.add(newFileButton);
        designPanel.add(saveFileButton);
        designPanel.add(openFileButton);

        designPanel.add(fontComboBox);


        designPanel.add(colorButton);
        designPanel.add(fontColorButton);

        designPanel.add(addTableButton);
        designPanel.add(darkModeCheckBox);


        topPanel.add(designPanel, BorderLayout.WEST);
        topPanel.add(inputPanel, BorderLayout.SOUTH);



        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                InputFieldEvent.init(tables.get(tabbedPane.getSelectedIndex()), inputField, cellData_list.get(tabbedPane.getSelectedIndex()));

            }
        });

        sumMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tables.get(tabbedPane.getSelectedIndex()).getValueAt(tables.get(tabbedPane.getSelectedIndex()).getSelectedRow(), tables.get(tabbedPane.getSelectedIndex()).getSelectedColumn()) == null){
                    inputField.setText("=SUM()");
                }

            }
        });


        //table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);



        //customizeSelectedCells();

        frame.getContentPane().add(topPanel, BorderLayout.NORTH);


        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                frame.dispose();
                ExcelLite.config.set("current_project_path", "");
                new CreateOpenGUI();
            }
        });

        addTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Name der Tabelle:", "Tabelle hinzufügen", JOptionPane.PLAIN_MESSAGE);
                tabbedPane.addTab(name, createTable(rows, columns, name));
                tabbedPane.setSelectedComponent(tabbedPane.getComponentAt(tabbedPane.getTabCount()-1));
            }
        });


        tabbedPane = new JTabbedPane(SwingConstants.BOTTOM);
        if(!Config.changeDarkMode){
            tabbedPane.addTab(name, createTable(rows, columns, name));
            frame.setTitle("Excel Lite: "+tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
        }




        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //frame.setTitle("Excel Lite: "+tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
            }
        });

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);


        frame.pack();
        frame.setSize(1300, 780);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        if(Config.changeDarkMode){
            openFile = true;
            frame.setLocation(Config.position);
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
            tables.clear();
            tabbedPane.removeAll();
            for(int i = 0; i < data_list.size(); i++){



                tabbedPane.addTab(data_list.get(i).getName(), loadTable(cellData_list.get(i), data_list.get(i), cellBGColors_list.get(i)));
            }
            for(int i=0; i<tables.size(); i++){
                JTable table = tables.get(i);
                Table data = data_list.get(i);


                for(int j=0; j<table.getRowCount(); j++){
                    for(int k=0; k<table.getColumnCount(); k++){
                        table.setValueAt(data.getTableData()[j][k], j, k);
                    }
                }

            }

            System.out.println("1");

            autoSaveCheckBox.setSelected(Config.autoSave);
            darkModeCheckBox.setSelected(Config.darkMode);

            Config.changeDarkMode = false;
            openFile = false;
        }
    }

    // Methode zur Generierung der Buchstabenkette für die Spaltenüberschriften

    private JPanel createTable(int rows, int columns, String name){
        Table data = new Table(rows, columns);
        data.setName(name);
        data_list.add(data);

        CellData cellData = new CellData();
        cellData_list.add(cellData);

        Map<Point, Color> cellBGColors = new HashMap<>();
        cellBGColors_list.add(cellBGColors);

        Map<Point, Color> cellFGColors = new HashMap<>();
        cellFGColors_list.add(cellFGColors);

        Map<Point, String> cellFonts = new HashMap<>();
        cellFonts_list.add(cellFonts);

        JPanel panel = new JPanel(new BorderLayout());


        Object[] columnHeaders = new Object[columns];
        for (int i = 0; i < columns; i++) {
            columnHeaders[i] = ColumnHeader.getColumnHeaderText(i);
        }

        Object[][] tableData = new Object[rows][columns];

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnHeaders);
        JTable table = new JTable(tableModel);
        tables.add(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);


        // Setze den Zelleninhalt zentriert
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        //UIManager.put("Table.focusCellHighlightBorder", BorderFactory.createEmptyBorder());


        JScrollPane scrollPane = new JScrollPane(table);

        // Erstelle die Zeilenheader (vertikale Zahlen)
        JList<String> rowHeader = new JList<>(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return rows;
            }

            @Override
            public String getElementAt(int index) {
                return String.valueOf(index + 1);
            }
        });
        rowHeader.setFixedCellWidth(50);
        rowHeader.setFixedCellHeight(table.getRowHeight());
        scrollPane.setRowHeaderView(rowHeader);

        panel.add(scrollPane, BorderLayout.CENTER);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();

                if (selectedRow != -1 && selectedColumn != -1) {
                    // Zelle wurde geklickt
                    CellClickedEvent.init(table, inputField, selectedCellField, fontComboBox, cellData);
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                CellClickedEvent.init(table, inputField, selectedCellField, fontComboBox, cellData);
            }
        });



        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if(!openFile){
                    TableChangedEvent.init(table, inputField, cellData, selectedCellField);
                }
            }
        });

        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //table.setSelectionBackground(new Color(173, 216, 230, 255));

        Config.countTables++;

        return panel;

    }

    public JPanel loadTable(CellData cellData, Table data, Map<Point, Color> cellBGColors) {
        for(int i=0; i<data.getTableData().length; i++){
            for(int j=0; j<data.getTableData()[0].length; j++){
                if(data.getTableData()[i][j] != null){
                    System.out.println(data.getTableData()[i][j]);
                }
            }
        }

        System.out.println(data.getRowCount());

        JPanel panel = new JPanel(new BorderLayout());


        Object[] columnHeaders = new Object[data.getColumnCount()];
        for (int i = 0; i < data.getColumnCount(); i++) {
            columnHeaders[i] = ColumnHeader.getColumnHeaderText(i);
        }

        Object[][] tableData = data.getTableData();

        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnHeaders);
        JTable table = new JTable(tableModel);
        tables.add(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);


        // Setze den Zelleninhalt zentriert
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        //UIManager.put("Table.focusCellHighlightBorder", BorderFactory.createEmptyBorder());


        JScrollPane scrollPane = new JScrollPane(table);

        // Erstelle die Zeilenheader (vertikale Zahlen)
        JList<String> rowHeader = new JList<>(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return data.getRowCount();
            }

            @Override
            public String getElementAt(int index) {
                return String.valueOf(index + 1);
            }
        });
        rowHeader.setFixedCellWidth(50);
        rowHeader.setFixedCellHeight(table.getRowHeight());
        scrollPane.setRowHeaderView(rowHeader);

        panel.add(scrollPane, BorderLayout.CENTER);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();

                if (selectedRow != -1 && selectedColumn != -1) {
                    // Zelle wurde geklickt
                    CellClickedEvent.init(table, inputField, selectedCellField, fontComboBox, cellData);
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                CellClickedEvent.init(table, inputField, selectedCellField, fontComboBox, cellData);
            }
        });



        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if(!openFile){
                    TableChangedEvent.init(table, inputField, cellData, selectedCellField);
                }
            }
        });

        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //table.setSelectionBackground(new Color(0, 0, 0, 50));


        Config.countTables++;

        return panel;
    }


    private void refreshTable(JTable table, Table data) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setDataVector(data.getTableData(), ColumnHeader.getColumnHeaders(table));
        tableModel.fireTableDataChanged();
    }

}
