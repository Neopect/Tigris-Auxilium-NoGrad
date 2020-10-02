package Controllers;

import features.SqliteConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HomeworkCon {
    public Button btn1;
    public TableView ppTable;
    public TextField tfID;
    public TextField tdTitle;
    public TextField tfAuthor;
    public TextField tfYear;
    public TextField tfPages;
    public TableView tbBooks;
    public TableColumn colID;
    public TableColumn colTitle;
    public TableColumn colAuthor;
    public TableColumn colYear;
    public TableColumn colPages;

    Connection connection = null;

    public void initialize() {
        SqliteConnection sqlConn = new SqliteConnection();
        connection = sqlConn.dbConnector("ProjectPlanner");
    }

    public void btn1Action(ActionEvent actionEvent) {
        try {
            String query = "select * from ProjectInfo"; //If you want all use * "select * from "
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            System.out.println(rs);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void insertAction(ActionEvent actionEvent) {
    }

    public void updateAction(ActionEvent actionEvent) {
    }

    public void deleteAction(ActionEvent actionEvent) {
    }
}
