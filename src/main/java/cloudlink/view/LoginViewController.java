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

    /**
     * JavaFX event listener for button press, no great complexity.
     */
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

    /**
     * @return
     * Validates user input. User IDs must be numeric and be more than 3 digits.
     * Passwords must be at least 5 characters and contain both uppercase, lowercase and numeric characters.
     * Returns a boolean for whether these verifications are passed.
     */
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

    /**
     * @return
     * Checks user details against those which are stored in the cloud.
     * Only if user details are correct, will return true.
     * Designed such that hashing can be used in the future.
     */
    private boolean isUserValid(){
        return HTTPClient.verifyUser(userIDField.getText(), passwordField.getText());
    }

}
