/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javamssql;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.awt.Font;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class StudentsForm extends javax.swing.JFrame {

    public static class ReportGenerator {

        public void generateReport(StudentsForm form) {
            try {
                // Устанавливаем соединение с базой данных
                Connection conn = ConnectMSSQL.ConnectDB();

                // SQL запрос для получения данных
                String sql = "SELECT * FROM studTable";

                // Создаем объект Statement для выполнения запроса
                Statement stmt = conn.createStatement();

                // Выполняем запрос и получаем результат в виде ResultSet
                ResultSet rs = stmt.executeQuery(sql);

                // Загружаем шаблон дизайна отчета из XML файла
                JasperDesign jasperDesign = JRXmlLoader.load("report_template.jrxml");

                // Компилируем дизайн отчета в JasperReport
                JasperCompileManager.compileReportToFile(jasperDesign, "compiled_report.jasper");

                // Заполняем отчет данными из ResultSet
                JasperPrint jasperPrint = JasperFillManager.fillReport("compiled_report.jasper", new HashMap<>(), new JRResultSetDataSource(rs));

                // Отображаем отчет
                JasperViewer.viewReport(jasperPrint, false);

                // Закрываем ресурсы
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("Failure\n" + e.getMessage());
            }
        }
    }
    
    public class AddRecordDialog extends JDialog {
    private JTextField nameField;
    private JTextField specialtyField;

    public AddRecordDialog(JFrame parent) {
        super(parent, "Add Record", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        JLabel specialtyLabel = new JLabel("Specialty:");
        specialtyField = new JTextField(20);

        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRecord();
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(specialtyLabel);
        panel.add(specialtyField);
        panel.add(addButton);

        getContentPane().add(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
        
    private void addRecord() {
        String name = nameField.getText();
        String specialty = specialtyField.getText();

        // Добавление записи в базу данных
        try {
            Connection conn = ConnectMSSQL.ConnectDB();
            String sql = "INSERT INTO studTable (name, specialty) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, specialty);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Запись добавлена успешно!");
                dispose(); // Закрыть диалоговое окно после добавления записи
            }
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении записи: " + e.getMessage());
        }
    }
}

    public class UpdateRecordDialog extends JDialog {
    private JTextField nameField;
    private JTextField specialtyField;
    private int recordId; // Идентификатор выбранной записи

    public UpdateRecordDialog(JFrame parent, String name, String specialty, int id) {
        super(parent, "Update Record", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);

        recordId = id; // Сохраняем идентификатор записи

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        nameField.setText(name);

        JLabel specialtyLabel = new JLabel("Specialty:");
        specialtyField = new JTextField(20);
        specialtyField.setText(specialty);

        JButton updateButton = new JButton("Обновить");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRecord();
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(specialtyLabel);
        panel.add(specialtyField);
        panel.add(updateButton);

        getContentPane().add(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void updateRecord() {
        String name = nameField.getText();
        String specialty = specialtyField.getText();

        // Обновление записи в базе данных
        try {
            Connection conn = ConnectMSSQL.ConnectDB();
            String sql = "UPDATE studTable SET name = ?, specialty = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, specialty);
            statement.setInt(3, recordId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Запись обновлена успешно!");
                dispose(); // Закрыть диалоговое окно после обновления записи
            }
            statement.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении записи: " + e.getMessage());
        }
    }
}

    private JLabel timestampLabel;
    
    Connection conn = null;
    Statement state = null;
    ResultSet rs = null;
    
    private void showJasperReport(){
        try {
        conn = ConnectMSSQL.ConnectDB(); // Establish database connection
        String sql = "SELECT * FROM studTable"; // SQL query

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        // Fill the report with data from the ResultSet
        JasperPrint jasperPrint = JasperFillManager.fillReport("path_to_your_compiled_report.jasper", new HashMap<>(), new JRResultSetDataSource(rs));

        // Close the database resources
        rs.close();
        stmt.close();
        conn.close();

        // View the report using JasperViewer
        JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            System.out.println("Failure\n" + e.getMessage());
        }
    }
    
    private void showReports() {
    try {
        conn = ConnectMSSQL.ConnectDB(); // Подключение к базе данных
        String sql = "SELECT * FROM studTable"; // SQL запрос
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        JTable reportTable = new JTable(buildTableModel(rs));
        reportTable.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(reportTable);
        
        // Создание панели для компонентов
        JPanel panel = new JPanel(new BorderLayout());
        
        // Добавление надписи "Report" вверху по центру
        JLabel titleLabel = new JLabel("Report");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Установка шрифта с размером 16px
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Добавление таблицы отчета
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Добавление метки с датой создания отчета внизу
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        JLabel dateLabel = new JLabel("Report created on: " + dateFormat.format(new Date()));
        dateLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(dateLabel, BorderLayout.SOUTH);
        
        // Создание окна отчета и добавление панели в него
        JFrame reportFrame = new JFrame("Отчет");
        reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reportFrame.add(panel);
        reportFrame.pack();
        reportFrame.setVisible(true);

        // Обновление метки времени в главном окне
        updateTimestampLabel();

        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        System.out.println("Failure\n" + e.getMessage());
    }
}

    
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        Vector<String> columnNames = new Vector<>();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                row.add(rs.getObject(columnIndex));
            }
            data.add(row);
        }

        // Возврат модели таблицы
        return new DefaultTableModel(data, columnNames);
    }
    
    public void UpdateTable(){
            try {
                conn = ConnectMSSQL.ConnectDB();
                String sql = "SELECT [id], [name], [specialty] from [studTable]";
                state = conn.createStatement();
                rs = state.executeQuery(sql);
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new Object[]{"ID", "Name", "Specialty"});
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getString("specialty")});
                }
                jTable1.setModel(model);
            } catch (Exception e){
                System.out.println("Failure\n" + e.getMessage());
            }
    }
    
    public StudentsForm() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Update Table");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Default report");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Jasper report");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        addButton.setText("Add record");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Update record");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete record");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        UpdateTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        showReports();
        updateTimestampLabel();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        showJasperReport();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        timestampLabel = new JLabel();
        timestampLabel.setForeground(new Color(255, 0, 0)); // Устанавливаем цвет текста
        timestampLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // Устанавливаем шрифт и размер текста
        getContentPane().add(timestampLabel);
        timestampLabel.setBounds(20, 400, 300, 30);
    }//GEN-LAST:event_formWindowOpened

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        AddRecordDialog dialog = new AddRecordDialog(this);
        dialog.setVisible(true);
        UpdateTable();
    }//GEN-LAST:event_addButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        int selectedRow = jTable1.getSelectedRow();
    if (selectedRow != -1) {
        int id = (int) jTable1.getValueAt(selectedRow, 0); // Получаем идентификатор выбранной записи
        String name = (String) jTable1.getValueAt(selectedRow, 1); // Получаем имя выбранной записи
        String specialty = (String) jTable1.getValueAt(selectedRow, 2); // Получаем специальность выбранной записи
        
        UpdateRecordDialog dialog = new UpdateRecordDialog(this, name, specialty, id);
        dialog.setVisible(true);
        UpdateTable();
    } else {
        // Если ни одна запись не выбрана, выведите сообщение об ошибке или предупреждение
        System.out.println("Пожалуйста, выберите запись для обновления.");
    }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) jTable1.getValueAt(selectedRow, 0); // Получаем идентификатор выбранной записи

            // Подтверждение удаления записи
            int option = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить эту запись?", "Подтверждение удаления", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Удаление записи из базы данных
                try {
                    Connection conn = ConnectMSSQL.ConnectDB();
                    String sql = "DELETE FROM studTable WHERE id = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setInt(1, id);
                    int rowsDeleted = statement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Запись удалена успешно!");
                        UpdateTable(); // Обновляем таблицу после удаления записи
                    }
                    statement.close();
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Ошибка при удалении записи: " + e.getMessage());
                }
            }
        } else {
            // Если ни одна запись не выбрана, выведите сообщение об ошибке или предупреждение
            System.out.println("Пожалуйста, выберите запись для удаления.");
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void updateTimestampLabel() {
        // Форматируем текущую дату и время
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        
        // Устанавливаем отформатированную дату и время в компонент JLabel
        timestampLabel.setText("Дата и время создания отчета: " + timestamp);
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            StudentsForm form = new StudentsForm(); // Создание экземпляра StudentsForm
            form.setVisible(true);
            Connection conn1 = ConnectMSSQL.ConnectDB();
            ReportGenerator reportGenerator = new StudentsForm.ReportGenerator();
            reportGenerator.generateReport(form); // Вызов метода generateReport в контексте экземпляра класса StudentsForm
        });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
