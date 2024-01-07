package erp.snippets.dialog.credential;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.io.IOException;

public class CredentialDialogController extends Dialog<Credential> {

    private static final String FXML_PATH = "credential-dialog.fxml";

    @FXML
    private TextField     fieldUsername;
    @FXML
    private PasswordField fieldPassword;

    public CredentialDialogController() {
        configure();
    }

    private void configure() {
        setDialogPane(loadLayout());
        setResultConverter(createConverter());
        setFocus();
    }

    private Callback<ButtonType, Credential> createConverter() {
        return button -> {
            Credential credential = Credential
                .builder()
                .build();

            if (button.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                credential.setUsername(fieldUsername.getText());
                credential.setPassword(fieldPassword.getText());
            }

            return credential;
        };
    }

    private DialogPane loadLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FXML_PATH));
            loader.setController(this);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFocus() {
        Platform.runLater(() -> fieldUsername.requestFocus());
    }
}
