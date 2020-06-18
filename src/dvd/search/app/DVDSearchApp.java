// David Perry
// 30010019
// 18 June 2020
// Deployment of a Movie Search Application

package dvd.search.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DVDSearchApp extends JFrame {

    JTextField txtTitle;
    JButton butSearch;
    JList list;
    JScrollPane scrollResults;
    ArrayList<String> dvdList;

    final String mainClass = DVDSearchApp.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//    final String mainDir = mainClass.substring(0, mainClass.indexOf("build"));

    public DVDSearchApp() {

        setTitle("DVD Search App");
        setLayout(null);

        DefaultListModel dlm = new DefaultListModel();
        list = new JList(dlm);
        dvdList = new ArrayList();
        txtTitle = new JTextField();
        txtTitle.setBounds(30, 30, 150, 25);
        butSearch = new JButton("Title Search");
        butSearch.setBounds(200, 30, 120, 25);
        scrollResults = new JScrollPane(list);
        scrollResults.setBounds(30, 85, 290, 150);

        String url = "jdbc:mysql://localhost:3306/dvd_search"; // 3306 is default port
        String user = "root";
        String password = "root"; // you set password when MySQL is installed
        Connection con = null; // JDBC connection
        Statement stmt = null; // SQL statement object
        String query; // SQL query string
        ResultSet result = null; // results after SQL execution

        try {
            con = DriverManager.getConnection(url, user, password); // connect to MySQL
            stmt = con.createStatement();
            query = ("SELECT * FROM movies");
            result = stmt.executeQuery(query); // execute the SQL query

            while (result.next()) { // loop until the end of the results
                String title = result.getString("title");
                dvdList.add(title);
                System.out.println(title);
            }
////            FileInputStream fileInputStream = new FileInputStream(new File(mainDir + "\\TITLES.xls"));
//            FileInputStream fileInputStream = new FileInputStream(new File("TITLES.xls"));
//            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
//            HSSFSheet worksheet = workbook.getSheet("Sheet1");
//            
//            for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
//                HSSFRow row = worksheet.getRow(i);
//                HSSFCell title = row.getCell(0);
//                dvdList.add(title.getStringCellValue());
//            }

        } catch (Exception e) {
            txtTitle.setText("Failed to Build DVD List");
            System.out.println(e);
        }

        butSearch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    dlm.removeAllElements();

                    for (String curVal : dvdList) {
                        String tempVal = curVal.toLowerCase();
                        if (tempVal.contains(txtTitle.getText().toLowerCase())) {
                            dlm.addElement(curVal);
                        }
                    }
                } catch (Exception e) {
                    txtTitle.setText("Something Went Wrong!");
                    System.out.println(e);
                }
            }
        });

        add(txtTitle);
        add(butSearch);
        add(scrollResults);

        setSize(360, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DVDSearchApp();
    }
}
