package erp.snippets.dialog.credential;

import javafx.application.Application;
import javafx.stage.Stage;

public class CredentialMainClass extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        new CredentialDialogController()
            .showAndWait()
            .ifPresent(System.out::println);
    }
}
