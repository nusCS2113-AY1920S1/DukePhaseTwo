package compal.ui;

import compal.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@@author jaedonkey
public class UiManager implements Ui {
    private UiUtil uiUtil;

    public UiManager(UiUtil uiUtil) {
        this.uiUtil = uiUtil;
    }


    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            TabPane tabReference = (TabPane) ap.getChildren().get(2);
            uiUtil.setTabWindow(tabReference);

            //Create MainWindow Pane
            VBox root = new VBox();
            ScrollPane mainPane = new ScrollPane();
            mainPane.setContent(root);

            Tab mainTab = new Tab();
            mainTab.setText("Main Window");
            mainTab.setContent(mainPane);
            tabReference.getTabs().add(0, mainTab);

            uiUtil.setMainWindow(mainPane);
            Scene s1 = new Scene(ap);
            primaryStage.setScene(s1);
            primaryStage.setTitle("COMPal");
            primaryStage.setOpacity(1);
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

            Label date = (Label) ap.getChildren().get(4);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date d = new Date();

            date.setText("Today's Date:" + formatter.format(d));
            fxmlLoader.<MainWindow>getController();

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
