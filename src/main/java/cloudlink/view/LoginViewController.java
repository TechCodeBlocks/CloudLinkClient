package cloudlink.view;

import cloudlink.utility.HTTPClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.reflect.annotation.ExceptionProxy;

import java.util.regex.Pattern;

public class LoginViewController {
    @FXML
    private TextField userIDField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginStatus;

    private Stage dialogStage;

    @FXML
    private void initialize(){

    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleLoginPressed(){
        if(isInputValid()){
            if(isUserValid()){
                dialogStage.close();
            }else{
                loginStatus.setText("User id or password is incorrect, please try again");
                userIDField.setText("");
                passwordField.setText("");

            }


        }else{
            loginStatus.setText("Invalid input. Your username or password isn't formatted");
            userIDField.setText("");
            passwordField.setText("");
        }


    }

    private boolean isInputValid() {
        if (userIDField.getText().length() < 3) {
            return false;
        } else {
            try {
                Integer.parseInt(userIDField.getText());

            } catch (Exception e) {
                System.out.println("Error parsing");
                return false;
            }
        }
        if (passwordField.getText().length() > 5) {

            String passwordText = passwordField.getText();
            System.out.println(passwordText);
            boolean characterLCReq = false;
            boolean characterUCReq = false;
            boolean numberReq = false;
            for (char letter : passwordText.toCharArray()) {

                if (Pattern.matches("[a-z]", Character.toString(letter))) {
                    System.out.println("Found lowercase letter");
                    characterLCReq = true;

                } else if (Pattern.matches("[A-Z]", Character.toString(letter))) {
                    System.out.println("Found uppercase");
                    characterUCReq = true;
                } else {
                    try{
                        Integer.parseInt(Character.toString(letter));
                        numberReq = true;
                    }catch (Exception e){

                    }
                }


            }
            if (characterLCReq && characterUCReq && numberReq) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    private boolean isUserValid(){
        //Use string.hashcode() to get password hash to use. This will be implemented later.
        return HTTPClient.verifyUser(userIDField.getText(), passwordField.getText());
    }

}
