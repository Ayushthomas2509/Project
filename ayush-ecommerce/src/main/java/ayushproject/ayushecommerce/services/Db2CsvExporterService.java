package ayushproject.ayushecommerce.services;


import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;


@Service
public class Db2CsvExporterService {

    public void convert1() {
        String jdbcURL = "jdbc:mysql://localhost:3306/BootcampProjects";
        String username = "root";
        String password = "igdefault";

        String csvFilePath = "data-export.csv";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String sql = "SELECT * FROM orders";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

            // write header line containing column names
            fileWriter.write("order_id, amount_paid, address_id  ,customer_id ,date_create ,date_updated , order_status");

            while (result.next()) {
                Integer order_id = result.getInt("order_id");
                Integer amount_paid = result.getInt("amount_paid");
                Integer address_id  = result.getInt("address_id");
                Integer customer_id = result.getInt("customer_id");
                Timestamp date_create = result.getTimestamp("date_create");
                Timestamp date_updated = result.getTimestamp("date_updated");
                String order_status = result.getString("order_status");

//                if (comment == null) {
//                    comment = "";   // write empty value for null
//                } else {
//                    comment = "\"" + comment + "\""; // escape double quotes
//                }

                String line = String.format("%d,%d,%d,%d,%s,%s,%s",
                        order_id, amount_paid, address_id  ,customer_id ,date_create ,date_updated , order_status);

                fileWriter.newLine();
                fileWriter.write(line);
            }

            statement.close();
            fileWriter.close();

        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }
    public void convert2() {
        String jdbcURL = "jdbc:mysql://localhost:3306/BootcampProjects";
        String username = "root";
        String password = "igdefault";

        String csvFilePath = "Reviews-export.csv";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String sql = "SELECT * FROM orders";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

            // write header line containing column names
            fileWriter.write("order_id, amount_paid, address_id  ,customer_id ,date_create ,date_updated , order_status");

            while (result.next()) {
                Integer order_id = result.getInt("order_id");
                Integer amount_paid = result.getInt("amount_paid");
                Integer address_id  = result.getInt("address_id");
                Integer customer_id = result.getInt("customer_id");
                Timestamp date_create = result.getTimestamp("date_create");
                Timestamp date_updated = result.getTimestamp("date_updated");
                String order_status = result.getString("order_status");

//                if (comment == null) {
//                    comment = "";   // write empty value for null
//                } else {
//                    comment = "\"" + comment + "\""; // escape double quotes
//                }

                String line = String.format("%d,%d,%d,%d,%s,%s,%s",
                        order_id, amount_paid, address_id  ,customer_id ,date_create ,date_updated , order_status);

                fileWriter.newLine();
                fileWriter.write(line);
            }

            statement.close();
            fileWriter.close();

        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }
    public void convert3() {
        String jdbcURL = "jdbc:mysql://localhost:3306/BootcampProjects";
        String username = "root";
        String password = "igdefault";

        String csvFilePath = "Reviews-export.csv";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String sql = "SELECT * FROM orders";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

            // write header line containing column names
            fileWriter.write("order_id, amount_paid, address_id  ,customer_id ,date_create ,date_updated , order_status");

            while (result.next()) {
                Integer order_id = result.getInt("order_id");
                Integer amount_paid = result.getInt("amount_paid");
                Integer address_id  = result.getInt("address_id");
                Integer customer_id = result.getInt("customer_id");
                Timestamp date_create = result.getTimestamp("date_create");
                Timestamp date_updated = result.getTimestamp("date_updated");
                String order_status = result.getString("order_status");

//                if (comment == null) {
//                    comment = "";   // write empty value for null
//                } else {
//                    comment = "\"" + comment + "\""; // escape double quotes
//                }

                String line = String.format("%d,%d,%d,%d,%s,%s,%s",
                        order_id, amount_paid, address_id  ,customer_id ,date_create ,date_updated , order_status);

                fileWriter.newLine();
                fileWriter.write(line);
            }

            statement.close();
            fileWriter.close();

        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }
}
