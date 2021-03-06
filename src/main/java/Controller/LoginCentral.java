package Controller;

import Model.BucketCentralOfficer;
import Model.CentralOfficer;
import Service.CentralOfficerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoginCentral {
    private CentralOfficerData centraldata;
    private BucketCentralOfficer centralList;
    @FXML TextField Userbtn;
    @FXML PasswordField Passwordbtn;
    @FXML Button backhomecentralbtn;
    @FXML Button Loginbtn;
    private CentralOfficer nowCentral;

    public void setNowCentral(CentralOfficer nowCentral){
        this.nowCentral = nowCentral;
    }

    public void initialize(){
        centralList = new BucketCentralOfficer();
        centraldata = new CentralOfficerData("data","CentralOfficer.csv");
        centralList = centraldata.getCentralList();
    }

    @FXML public void LoginBtnOnAction(ActionEvent event) throws IOException {
        if(Userbtn.getText().equals("") || Passwordbtn.getText().equals("") ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setContentText("Please fill all information.");
            alert.showAndWait();}
        else{
            if(centralList.checkPassword(Userbtn.getText(),Passwordbtn.getText()) && centralList.checkStatus(Userbtn.getText())) {
                nowCentral = centralList.getAcc(Userbtn.getText());
                String time = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(Calendar.getInstance().getTime());
                nowCentral.setTime(time);
                centraldata.setCentralList(centralList);
                Button c = (Button) event.getSource();
                Stage stage_CentralloginPage = (Stage) c.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CentralHome.fxml"));
                stage_CentralloginPage.setScene(new Scene(loader.load(), 882, 390));

                stage_CentralloginPage.show();
            }
            else if (!centralList.checkStatus(Userbtn.getText())){
                centralList.getAcc(Userbtn.getText()).setAttempt(centralList.getAcc(Userbtn.getText()).getAttempt()+1);
                centraldata.setCentralList(centralList);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("");
                alert.setTitle("Please contact admin");
                alert.setContentText("Your user has been ban");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setContentText("INCORRECT USERNAME OR PASSWORD");
                alert.showAndWait();
            }
        }

    }

    @FXML public void backhomecentralbtnonaction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage_ProfilePage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Homepage.fxml"));
        stage_ProfilePage.setScene(new Scene(loader.load(), 882, 390));
        stage_ProfilePage.show();}
}
