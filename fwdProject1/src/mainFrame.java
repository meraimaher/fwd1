import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.ArrayList;


public class mainFrame {
    private JTable table1;
    private JButton createNewInoviceButton;
    private JButton deleteInoviceButton;
    private JPanel frame1;
    private JTextField dateTxt;
    private JTextField customerTxt;
    private JTable table2;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton loadFileButton;
    private JLabel totalLbl;
    private JLabel invoiceID;

    Object [][] dataForTable1 = {};
    Object [][] dataForTable2 = {};
    String selectTableID = "0";
    Double totalOfInovice = 0.0;
    String coustomerName = "";
    String invoiceDate = "";

    public  mainFrame() {
        createTables();
        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openReader();
                createTables();
            }
        });

        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectTableID = table1.getValueAt(table1.getSelectedRow(), 0).toString();
                coustomerName = table1.getValueAt(table1.getSelectedRow(), 2).toString();
                invoiceDate = table1.getValueAt(table1.getSelectedRow(), 1).toString();
                invoiceID.setText(selectTableID);
                customerTxt.setText(coustomerName);
                dateTxt.setText(invoiceDate);
                addDataToTable2();
            }
        });
    }

    private void createTables() {
        table1.setModel(new DefaultTableModel(
                dataForTable1,
                new  String[]{"No","Date","Customer","Total"}
        ));

        table2.setModel(new DefaultTableModel(
                dataForTable2,
                new  String[]{"No","Item Name","Item Price","Count","Item Total"}
        ));
    }

    private void addDataToTable2() {
        dataForTable2 = new Object[][]{};
        totalOfInovice = 0.0;
        String file = "/Users/Merai/Documents/Merai/fwdProject1/src/inovicesDetails.csv";
        BufferedReader reader = null;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                System.out.println();
                System.out.printf(row[0]);
                System.out.println();
                System.out.printf(selectTableID);
                if (Integer.parseInt(selectTableID) == Integer.parseInt(row[0])) {
                    dataForTable2 = appendValue(dataForTable2,Arrays.copyOfRange(row, 1, row.length));
                    totalOfInovice = totalOfInovice + Double.parseDouble(row[5]);
                }
            }
            totalLbl.setText(totalOfInovice.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        table2.setModel(new DefaultTableModel(
                dataForTable2,
                new  String[]{"No","Item Name","Item Price","Count","Item Total"}
        ));
    }


    public static void main (String[] args) {
        JFrame frame = new JFrame("frame1");
        frame.setContentPane(new mainFrame().frame1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(2000,1000);
        frame.pack();
        frame.setVisible(true);
    }

    private void openReader() {
        String file = "/Users/Merai/Documents/Merai/fwdProject1/src/invoices.csv";
        BufferedReader reader = null;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                dataForTable1 = appendValue(dataForTable1,row);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Object[][] appendValue(Object[][] obj, Object newObj) {

        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray(new Object[0][]);

    }

}
