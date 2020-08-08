/*Author Diego Pavoni
Student ID 300319337
github: https://github.com/Di-crypto-code/Diego_Pavoni_300319337
 */

package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

public class Main extends JFrame {

    private JLabel label_customer_number;
    private JLabel label_customer_name;
    private JLabel label_initial_deposit;
    private JLabel label_number_of_years;
    private JLabel label_type_of_savings;


    private JTextField txt_name;
    private JTextField txt_number;
    private JTextField txt_deposit;
    private JTextField txt_years;

    private JComboBox txt_savings;


    private JScrollPane scroll_Pane_principal;
    private JTable table_principal;

    private JScrollPane scroll_Pane_sec;
    private JTable table_sec;

    private JButton b_add;
    private JButton b_edit;
    private JButton b_delete;

    Connection con;
    PreparedStatement insert;

    String number;

    public Main(){
        initComponents();
    }

    private void initComponents(){

        label_customer_number = new JLabel();
        label_customer_name = new JLabel();
        label_initial_deposit = new JLabel();
        label_number_of_years = new JLabel();
        label_type_of_savings = new JLabel();

        txt_name = new JTextField();
        txt_number = new JTextField();
        txt_deposit = new JTextField();
        txt_years = new JTextField();

        Vector items = new Vector();
        items.add("Savings-Deluxe");
        items.add("Savings-Regular");
        txt_savings = new JComboBox(items);

        scroll_Pane_principal = new JScrollPane();
        table_principal = new JTable();

        scroll_Pane_sec = new JScrollPane();
        table_sec = new JTable();

        b_add = new JButton();
        b_edit = new JButton();
        b_delete = new JButton();

        var contentPane = getContentPane();
        /*contentPane.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]",
                // rows
                "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]"));*/
        contentPane.setLayout(new MigLayout());
        label_customer_number.setText("Enter The Customer Number");
        contentPane.add(label_customer_number, "spanx 3");
        contentPane.add(txt_number, "spanx 4, growx 1, align right, wrap");

        label_customer_name.setText("Enter The Customer Name");
        contentPane.add(label_customer_name, "spanx 3");
        contentPane.add(txt_name, "spanx 4, growx 1, align right, wrap");

        label_initial_deposit.setText("Enter The Initial Deposit");
        contentPane.add(label_initial_deposit, "spanx 3");
        contentPane.add(txt_deposit, "spanx 4, growx 1, align right, wrap");

        label_number_of_years.setText("Enter The Number of Years");
        contentPane.add(label_number_of_years, "spanx 3");
        contentPane.add(txt_years, "spanx 4, growx 1, align right, wrap");

        label_type_of_savings.setText("Enter The Initial Deposit");
        contentPane.add(label_type_of_savings, "spanx 3");
        contentPane.add(txt_savings, "spanx 4, growx 1, align right, wrap");

        {
            table_principal.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table_MouseClicked(e);
                }
            });
            scroll_Pane_principal.setViewportView(table_principal);
        }
        contentPane.add(scroll_Pane_principal, "spanx 3 ,height 100:100:100");

        {
            table_sec.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //table_MouseClicked(e);
                }
            });
            scroll_Pane_sec.setViewportView(table_sec);
        }
        contentPane.add(scroll_Pane_sec, "spanx 3 ,height 100:100:100, wrap");

        b_add.setText("Add");
        b_add.addActionListener(e -> {
            try {
                button_addActionPerformed(e);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        contentPane.add(b_add, "width 150:150:150");

        b_edit.setText("Edit");
        b_edit.addActionListener(e -> {
            try {
                button_editActionPerformed(e);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
        contentPane.add(b_edit, "width 150:150:150");

        b_delete.setText("Delete");
        b_delete.addActionListener(e -> {
            try {
                button_deleteActionPerformed(e);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        contentPane.add(b_delete, "width 150:150:150");

        pack();
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void button_addActionPerformed(ActionEvent e) throws ClassNotFoundException, SQLException {

        String name, number, years, deposit, saving;

        name = txt_name.getText();
        number = txt_number.getText();
        years = txt_years.getText();
        deposit = txt_deposit.getText();
        saving = txt_savings.getSelectedItem() + "";


        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/savings","root","");


        if(e.getSource()==b_add) {


            insert = con.prepareStatement("Select * from savingstable where custno = ?");

            insert.setString(1, number);



            ResultSet rs = insert.executeQuery();

            if(rs.isBeforeFirst()){

                JOptionPane.showMessageDialog(null,"The name you are trying to enter already exists ");

                txt_name.setText("");
                txt_number.setText("");
                txt_years.setText("");
                txt_savings.setSelectedIndex(0);
                txt_number.requestFocus();

                return;
            }


            insert = con.prepareStatement("insert into savingstable values(?,?,?,?,?)");

            insert.setString(1, number);
            insert.setString(2, name);
            insert.setString(3, deposit);
            insert.setString(4, years);
            insert.setString(5, saving);

            insert.executeUpdate();

            JOptionPane.showMessageDialog(null, "Record added");

            txt_name.setText("");
            txt_number.setText("");
            txt_years.setText("");
            txt_savings.setSelectedIndex(0);
            txt_number.requestFocus();
            updatetable();


        }
    }
    private void button_editActionPerformed(ActionEvent e) throws SQLException, ClassNotFoundException {

        String name, number, years, deposit, saving;

        name = txt_name.getText();
        number = txt_number.getText();
        years = txt_years.getText();
        deposit = txt_deposit.getText();
        saving = txt_savings.getSelectedItem() + "";

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/savings","root","");

        insert = con.prepareStatement("update savingstable set custno=?,custname=?,cdep=?,nyears=?,savtype=? where custno =?");

        insert.setString(1, number);
        insert.setString(2, name);
        insert.setString(3, deposit);
        insert.setString(4, years);
        insert.setString(5, saving);

        insert.setString(6, this.number);

        insert.executeUpdate();

        JOptionPane.showMessageDialog(null, "Record edited");

        txt_name.setText("");
        txt_number.setText("");
        txt_years.setText("");
        txt_deposit.setText("");
        txt_savings.setSelectedIndex(0);
        txt_number.requestFocus();

        updatetable();
    }

    private void button_deleteActionPerformed(ActionEvent e) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/savings","root","");

        int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?", "Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){

            insert = con.prepareStatement("delete from savingstable where custno =?");

            insert.setString(1, this.number);

        }

        insert.execute();

        JOptionPane.showMessageDialog(null, "Record deleted");

        txt_name.setText("");
        txt_number.setText("");
        txt_years.setText("");
        txt_deposit.setText("");
        txt_savings.setSelectedIndex(0);
        txt_number.requestFocus();

        updatetable();
    }

    private void table_MouseClicked(MouseEvent e) {

        DefaultTableModel df = (DefaultTableModel)table_principal.getModel();

        int index1 = table_principal.getSelectedRow();

        txt_number.setText(df.getValueAt(index1,0).toString());
        this.number = txt_number.getText();

        txt_name.setText(df.getValueAt(index1,1).toString());
        txt_deposit.setText(df.getValueAt(index1,2).toString());
        txt_years.setText(df.getValueAt(index1,3).toString());
        switch (df.getValueAt(index1,4).toString()){
            case "Savings-Deluxe":
                txt_savings.setSelectedIndex(0);
                break;
            case "Savings-Regular":
                txt_savings.setSelectedIndex(1);
                break;
        }

    }

    public void updatetable() throws ClassNotFoundException, SQLException {

        int c;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/savings","root","");

        insert = con.prepareStatement("Select * from savingstable");

        ResultSet rs = insert.executeQuery();

        ResultSetMetaData Res = rs.getMetaData();
        c = Res.getColumnCount();
        DefaultTableModel df = (DefaultTableModel) table_principal.getModel();
        df.setRowCount(0);

        while(rs.next()) {
            Vector v2 = new Vector();

            for(int a =1;a<=c;a++){
                v2.add(rs.getString("custno"));
                v2.add(rs.getString("custname"));
                v2.add(rs.getString("cdep"));
                v2.add(rs.getString("nyears"));
                v2.add(rs.getString("savtype"));

            }
            df.addRow(v2);
        }
    }
    public void setTableModel(){
        String[] cols = {"Number", "Name", "Deposit", "Years", "Type of Saving"};
        String[][] data = {{"d1", "d1.1"},{"d2", "d2.1"},{"d3", "d3.1"},{"d4", "d2.4"},{"d5", "d5.1"}};
        DefaultTableModel model = new DefaultTableModel(data, cols);
        table_principal.setModel(model);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Main frame =  new Main();

        frame.setTableModel();
        frame.updatetable();

        frame.setVisible(true);
    }
}
