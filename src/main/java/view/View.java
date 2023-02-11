/*
 * Created by JFormDesigner on Sat Jan 23 11:01:06 CST 2021
 */

package view;

import com.formdev.flatlaf.FlatIntelliJLaf;
import executor.AsyncTaskExecutor;
import executor.ConvertTask;
import utils.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author charlottexiao
 */
public class View extends JFrame {
    public View() {
        FlatIntelliJLaf.setup();
        initComponents();
    }

    private void button1MouseClicked(MouseEvent e) {
        int returnVal = jFileChooser1.showOpenDialog(panel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = jFileChooser1.getSelectedFiles();
            ArrayList<File> arrayList = new ArrayList<>();
            for (File file : files) {
                Utils.listAllFiles(arrayList, file);
            }
            for (File file : arrayList) {
                ((DefaultTableModel) table.getModel()).addRow(new String[]{file.getName(), file.getAbsolutePath(), String.valueOf(file.length()), "准备转换"});
            }
        }
    }

    private void button2MouseClicked(MouseEvent e) {
        int returnVal = jFileChooser2.showOpenDialog(panel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser2.getSelectedFile();
            String outFilePath = file.getAbsolutePath();
            for (int i = 0; i < table.getModel().getRowCount(); i++) {
                if (table.getModel().getValueAt(i, 3).equals("准备转换")) {
                    String ncmFilePath = (String) table.getModel().getValueAt(i, 1);
                    AsyncTaskExecutor.execute(new ConvertTask(ncmFilePath, outFilePath, table.getModel(), i));
                }
            }
        }
    }

    private void button3MouseClicked(MouseEvent e) {
        int rowCount = table.getModel().getRowCount();
        for (int i = 1; i <= rowCount; i++) {
            ((DefaultTableModel) table.getModel()).removeRow(rowCount - i);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        panel = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        scrollPane = new JScrollPane();
        table = new JTable();

        //======== this ========
        setIconImage(new ImageIcon(Objects.requireNonNull(View.class.getResource("/image/ico.png"))).getImage());
        setTitle("NCM2MP3");
        setMinimumSize(null);
        setVisible(true);
        setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));
        setResizable(false);
        setMaximizedBounds(null);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel ========
        {
            panel.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.
                    border.EmptyBorder(0, 0, 0, 0), "author:charlottexiao", javax.swing.border.TitledBorder.CENTER
                    , javax.swing.border.TitledBorder.BOTTOM, new Font("Dia\u006cog", Font
                    .BOLD, 12), Color.red), panel.getBorder()));
            panel.addPropertyChangeListener(
                    e -> {
                        if ("bord\u0065r"
                                .equals(e.getPropertyName())) throw new RuntimeException();
                    });
            panel.setLayout(new GridLayout(1, 3, 2, 2));

            //---- button1 ----
            button1.setText("\u9009\u62e9\u6587\u4ef6");
            button1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button1MouseClicked(e);
                }
            });
            panel.add(button1);

            //---- button2 ----
            button2.setText("\u5f00\u59cb\u8f6c\u6362");
            button2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button2MouseClicked(e);
                }
            });
            panel.add(button2);

            //---- button3 ----
            button3.setText("\u6e05\u7a7a\u5217\u8868");
            button3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button3MouseClicked(e);
                }
            });
            panel.add(button3);
        }
        contentPane.add(panel, BorderLayout.SOUTH);

        //======== scrollPane ========
        {
            scrollPane.setPreferredSize(new Dimension(700, 300));

            //---- table ----
            table.setPreferredSize(null);
            table.setPreferredScrollableViewportSize(new Dimension(0, 0));
            table.setModel(new DefaultTableModel(
                    new Object[][]{
                    },
                    new String[]{
                            "\u97f3\u4e50\u540d", "\u6587\u4ef6\u8def\u5f84", "\u6587\u4ef6\u5927\u5c0f", "\u72b6\u6001"
                    }
            ) {
                Class<?>[] columnTypes = new Class<?>[]{
                        String.class, String.class, String.class, String.class
                };

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }
            });
            {
                TableColumnModel cm = table.getColumnModel();
                cm.getColumn(0).setPreferredWidth(110);
                cm.getColumn(1).setPreferredWidth(400);
                cm.getColumn(2).setPreferredWidth(80);
                cm.getColumn(3).setPreferredWidth(70);
            }
            table.setRowHeight(30);
            table.setRowMargin(3);
            table.setAutoCreateRowSorter(true);
            table.setFillsViewportHeight(true);
            table.setOpaque(false);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.setFocusable(false);
            table.setEnabled(false);
            scrollPane.setViewportView(table);
        }
        contentPane.add(scrollPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

        // JFormChooser1
        jFileChooser1 = new JFileChooser();
        jFileChooser1.setDialogTitle("请选择NCM音乐文件或文件夹");
        jFileChooser1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jFileChooser1.setMultiSelectionEnabled(true);
        jFileChooser1.setFileFilter(new FileNameExtensionFilter("网易云NCM格式音乐", "ncm"));
        //JFormChooser2
        jFileChooser2 = new JFileChooser();
        jFileChooser2.setDialogTitle("请选择保存目录");
        jFileChooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser2.setMultiSelectionEnabled(false);
        //table
        table.getTableHeader().setReorderingAllowed(false);
        //window close ways
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel panel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JScrollPane scrollPane;
    private JTable table;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    //JFormChooser1,JFormChooser2
    private JFileChooser jFileChooser1;
    private JFileChooser jFileChooser2;
}
