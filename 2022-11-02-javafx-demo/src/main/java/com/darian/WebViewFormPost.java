package com.darian;


import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.*;
import javafx.stage.Stage;
import org.w3c.dom.*;
import org.w3c.dom.html.*;

/**
 * JavaFX WebView 代码提交
 */
public class WebViewFormPost extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start(Stage stage) {
        final TextField fxUsername = new TextField();
        fxUsername.setPrefColumnCount(20);
        final TextField fxPassword = new PasswordField();
        final BooleanProperty loginAttempted = new SimpleBooleanProperty(false);
        final WebView webView = new WebView();
        webView.setPrefWidth(1000);
        final WebEngine engine = webView.getEngine();
        engine.documentProperty().addListener(new ChangeListener<Document>() {
            @Override
            public void changed(ObservableValue<? extends Document> ov, Document oldDoc, Document doc) {
                if (doc != null && !loginAttempted.get()) {
                    if (doc.getElementsByTagName("form").getLength() > 0) {
                        HTMLFormElement form = (HTMLFormElement) doc.getElementsByTagName("form").item(0);
                        if ("/oam/server/sso/auth_cred_submit".equals(form.getAttribute("action"))) {
                            HTMLInputElement username = null;
                            HTMLInputElement password = null;
                            NodeList nodes = form.getElementsByTagName("input");
                            for (int i = 0; i < nodes.getLength(); i++) {
                                HTMLInputElement input = (HTMLInputElement) nodes.item(i);
                                switch (input.getName()) {
                                    case "ssousername":
                                        username = input;
                                        break;
                                    case "password":
                                        password = input;
                                        break;
                                }
                            }
                            if (username != null && password != null) {
                                loginAttempted.set(true);
                                username.setValue(fxUsername.getText());
                                password.setValue(fxPassword.getText());
                                form.submit();
                            }
                        }
                    }
                }

            }

        });

        engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {
            @Override
            public void changed(ObservableValue<? extends Throwable> ov, Throwable oldException, Throwable exception) {
                System.out.println("Load Exception: " + exception);
            }
        });

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.addRow(0, new Label("Username: "), fxUsername);
        inputGrid.addRow(0, new Label("Password: "), fxPassword);
        Button fxLoginButton = new Button("Login to Oracle Forums");

        fxLoginButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (notEmpty(fxPassword.getText()) && notEmpty(fxPassword.getText())) {
                    loginAttempted.set(false);
                    engine.load("https://forums.oracle.com/community/developer/english/java/javafx/login.jspa");
                }
            }
        });

        fxLoginButton.setDefaultButton(true);
        ProgressIndicator fxLoadProgress = new ProgressIndicator(0);
        fxLoadProgress.progressProperty().bind(webView.getEngine().getLoadWorker().progressProperty());
        fxLoadProgress.visibleProperty().bind(webView.getEngine().getLoadWorker().runningProperty());
        HBox loginPane = new HBox(10);
        loginPane.getChildren().setAll(
                fxLoginButton, fxLoadProgress
        );

        final VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        layout.getChildren().addAll(
                new Label("Enter your Oracle Web Account credentials"), inputGrid, loginPane, webView
        );
        VBox.setVgrow(webView, Priority.ALWAYS);
        stage.setScene(new Scene(layout));
        stage.show();
        fxUsername.requestFocus();
    }

    private boolean notEmpty(String s) {
        return s != null && !"".equals(s);
    }

}
