package Controllers;

import features.RWJsonUser;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SetupCon {

    public TextField txtBoxFN;
    public TextField txtBoxLN;
    public ChoiceBox comboGrade;
    public TextField txtBoxISD;

    public void FinishedClicked(ActionEvent actionEvent) {

        RWJsonUser.firstName = txtBoxFN.getText();
        RWJsonUser.lastName = txtBoxLN.getText();
        //RWJsonUser.sGrade = comboGrade.getSelectionModel().toString();
        RWJsonUser.sIsd = txtBoxISD.getText();
        RWJsonUser.getComputerName(false);
        RWJsonUser.getOSVersion();
        RWJsonUser.rootPathMaker();
        RWJsonUser.jsonPathMaker("User");
        RWJsonUser.setupCom = "true";

        RWJsonUser.WriteToJson();
        RWJsonUser.ReadToJson();

    }

    public void buildSaveSys() {

        copyFiles("ProjectPlanner.sqlite");
        copyFiles("Collections.sqlite");
    }

    public void copyFiles(String fileName) {

        //Path source = null;


        Object OS = RWJsonUser.osName;
        String fullPath;
        if (OS.equals("Windows 10") || OS.equals("Windows 8") || OS.equals("Windows 7")) {
            fullPath = "C:\\Test\\TA\\Data\\"+fileName;

        } else {

            String paths = System.getProperty("user.home");
            //System.out.println(paths);

            fullPath = paths + "/TA/Data/"+fileName;
            //System.out.println(full);
        }


        Path destination = Paths.get(fullPath); //"C:\\Test\\TA\\Data\\" + fileName

	    /*try {
	    	source = Paths.get(PanelSetup.class.getResource("/mainFolder/resources/" + fileName).toURI());
	    	Files.copy(source, destination); //Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}*/

        try (InputStream stream = getClass().getResourceAsStream("/src/resources/" + fileName)) { //Fsdfsd
            Files.copy(stream, destination);
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}
