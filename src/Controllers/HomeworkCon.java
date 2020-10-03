package Controllers;

import features.Books;
import features.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class HomeworkCon {
    public Button btn1;
    public TextField tfID;
    public TextField tfTitle;
    public TextField tfAuthor;
    public TextField tfYear;
    public TextField tfPages;
    public TableView<Books> tbBooks;
    public TableColumn<Books, Integer> colID;
    public TableColumn<Books, String> colTitle;
    public TableColumn<Books, String> colAuthor;
    public TableColumn<Books, Integer> colYear;
    public TableColumn<Books, Integer> colPages;
    public Button btnInsert;
    public Button btnUpdate;
    public Button btnDelete;

    //Connection connection = null;

    public void initialize() {
        //SqliteConnection sqlConn = new SqliteConnection();
        //connection = sqlConn.dbConnector("ProjectPlanner");

        showBooks();
    }

    public void btn1Action(ActionEvent actionEvent) {
        /*try {
            String query = "select * from ProjectInfo"; //If you want all use * "select * from "
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            System.out.println(rs);
        }catch(Exception e) {
            e.printStackTrace();
        }*/
    }

    public void insertAction(ActionEvent actionEvent) {
        insertRecord();
    }

    public void updateAction(ActionEvent actionEvent) {
        updateRecord();
    }

    public void deleteAction(ActionEvent actionEvent) {
        deleteRecord();
    }

    public Connection getConnection() {
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://www.neopect.heliohost.org:3306/neopect_test", "neopect_neo", "TyPass01");
            //conn = DriverManager.getConnection("jdbc:sqlite:C:/Test/TA/Data/ProjectPlanner.sqlite");
            return conn;
        }catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public ObservableList<Books> getBooksList(){
        ObservableList<Books> bookList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM books";
        Statement st = null; //Use prepare statement for repetitive scripts
        ResultSet rs = null;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Books books;
            while(rs.next()) {
                books = new Books(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("year"), rs.getInt("pages"));
                bookList.add(books);
            }

        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(st !=null) {
                try {
                    st.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }



        return bookList;
    }

    public void showBooks() {
        ObservableList<Books> list = getBooksList();

        colID.setCellValueFactory(new PropertyValueFactory<Books, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Books, Integer>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<Books, Integer>("pages"));

        tbBooks.setItems(list);

    }

    private void insertRecord() {
        String query = "INSERT INTO books VALUES ("+ tfID.getText() + ",'" + tfTitle.getText() + "','" + tfAuthor.getText() + "'," + tfYear.getText() + "," + tfPages.getText() + ")";
        executeQuery(query);
        showBooks();
    }

    private void updateRecord() {
        String query = "UPDATE books SET title = '" + tfTitle.getText() + "', author = '" + tfAuthor.getText() + "', year = " + tfYear.getText() + ", pages = " + tfPages.getText() + " WHERE id = " + tfID.getText() + "";
        executeQuery(query);
        showBooks();
    }

    private void deleteRecord() {
        String query = "DELETE FROM books WHERE id =" + tfID.getText() + "";
        executeQuery(query);
        showBooks();
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st = null;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();



        }finally {
            /*if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }*/
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public void onMouseClickedPlanner(MouseEvent mouseEvent) {
        Books book = tbBooks.getSelectionModel().getSelectedItem();
        System.out.println("id: " + book.getId() + "  " + "title: " + book.getId());
        tfID.setText(String.valueOf(book.getId()));
        tfTitle.setText(book.getTitle());
        tfAuthor.setText(book.getAuthor());
        tfYear.setText(String.valueOf(book.getYear()));
        tfPages.setText(String.valueOf(book.getPages()));

    }
}
