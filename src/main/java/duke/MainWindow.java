package duke;

import duke.command.Command;
import duke.command.ExitCommand;
import duke.command.BackupCommand;
import duke.command.FilterCommand;
import duke.dukeexception.DukeException;
import duke.task.FilterList;
import duke.task.Reminders;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tooltip;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private ListView<Task> listT;
    @FXML
    private Label labelSelectedTask;
    @FXML
    private Button btnDone;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private VBox vboxUpdate;
    @FXML
    private ComboBox<String> cbupdateType;
    @FXML
    private TextField tfnewDesc;
    @FXML
    private TextField tfnewDateTime;
    @FXML
    private ComboBox<String> cbtaskType;
    @FXML
    private TabPane tpTabs;
    @FXML
    SingleSelectionModel<Tab> selectedTab;


    private Duke duke;
    private Tooltip toolTip;

    private int refreshType = 0;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TIMER_DELAY = 500;
    private static final int VBOX_WIDTH = 200;
    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/myUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/myBot.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    //@@author talesrune-reused
    /**
     * Setting up Duke GUI.
     * @param d The object of Duke.
     */
    public void setDuke(Duke d) {
        duke = d;
        updateGui();
        setVboxWidth(false);
        setButtonsVisibility(true);
        selectedTab =  tpTabs.getSelectionModel();
        toolTip = new Tooltip();
        toolTip.setText("");
        listT.setTooltip(toolTip);
        toolTip.setShowDelay(Duration.millis(75.0));
        toolTip.setShowDuration(Duration.millis(0.0));

        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(Ui.showWelcomeGui(), dukeImage)
        );

        TaskList items = duke.getTaskList();
        Reminders remind = new Reminders();
        dialogContainer.getChildren().add(
                //DialogBox.getDukeDialog("Upcoming Reminders: \n" + items.getList(), dukeImage)
                DialogBox.getDukeDialog(remind.getReminders(3, items).getList(), dukeImage)
        );
    }

    Timer timer = new Timer();
    TimerTask exitDuke = new TimerTask() {
        public void run() {
            System.exit(ZERO);
        }
    };

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        try {
            Command cmd = duke.getCommand(input);
            if (cmd instanceof ExitCommand) {
                duke.saveState(cmd);
                response = Ui.showByeGui();
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage)
                );
                timer.schedule(exitDuke, new Date(System.currentTimeMillis() + TIMER_DELAY));
            }  else if (cmd instanceof BackupCommand) {
                duke.saveState(cmd);
                response = Ui.showBackupMessageGui();
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog(response, dukeImage)
                );
            } else {
                if (cmd instanceof FilterCommand) {
                    refreshType = 1;
                }
                response = duke.executeCommand(cmd);
                dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
                );
                updateGui();
            }
        } catch (DukeException e) {
            response = Ui.showErrorMsgGui(e.getMessage());
            logr.log(Level.WARNING, response, e);

            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        } catch (Exception e) {
            response = Ui.showErrorMsgGui("     New error, please read console:")
                    +  Ui.showErrorMsgGui("     Duke will continue as per normal.");
            logr.log(Level.WARNING, response, e);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            e.printStackTrace();
        }
        userInput.clear();
    }

    //@@author talesrune
    @FXML
    protected void handleUserEvent(String input) {
        String response;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        try {
            Command cmd = duke.getCommand(input);
            response =  duke.executeCommand(cmd);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );

        } catch (DukeException e) {
            response = Ui.showErrorMsgGui(e.getMessage());
            logr.log(Level.WARNING, response, e);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        } catch (Exception e) {
            response = Ui.showErrorMsgGui("     New error, please read console:")
                    +  Ui.showErrorMsgGui("     Duke will continue as per normal.");
            logr.log(Level.WARNING, response, e);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            e.printStackTrace();
        }
        userInput.clear();
    }

    @FXML
    private void onMouseClick_ListView(MouseEvent mouseEvent) {
        labelSelectedTask.setText("Selected Task: " + listT.getSelectionModel().getSelectedItem());
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        toolTip.setText("Notes: " + taskObj.getNotes());
        Node node = (Node) mouseEvent.getSource();
        toolTip.show(node, mouseEvent.getScreenX() + 120, mouseEvent.getScreenY());


        if (taskObj.isDone()) {
            btnDone.setDisable(true);
        } else {
            btnDone.setDisable(false);
        }
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    private void onMouseClickTabs() {
        String str = selectedTab.getSelectedItem().getText();
        if (str.toLowerCase().equals("all")) {
            handleUserEvent("list");
        } else {
            handleUserEvent("filter " + str.toLowerCase());
            refreshType = 1;
        }
        updateGui();
    }


    @FXML
    private void onMouseClickDone() {
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + ONE;
        handleUserEvent("done " + itemNumber);
        updateGui();
    }

    @FXML
    private void onMouseClickDelete() {
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + ONE;
        handleUserEvent("delete " + itemNumber);
        updateGui();
    }

    @FXML
    private void onMouseClick_DeleteNotes() {
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + ONE;
        handleUserEvent("notes " + itemNumber + " /delete");
        updateGui();
    }

    @FXML
    private void onMouseClickUpdate() {
        setVboxWidth(true);
        setButtonsVisibility(false);
        cleanUp();
        cbupdateType.getItems().addAll(
                "Description",
                "Date/Time",
                "Type of Task"

        );
        cbtaskType.getItems().addAll(
                "Todo",
                "Deadline",
                "Fixed Duration",
                "Repeat"
        );
    }

    @FXML
    private void onMouseClickOK() {
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + ONE;
        if (cbupdateType.getSelectionModel().getSelectedItem().equals("Description")) {
            handleUserEvent("update " + itemNumber + " /desc " + tfnewDesc.getText().trim());
        } else if (cbupdateType.getSelectionModel().getSelectedItem().equals("Date/Time")) {
            handleUserEvent("update " + itemNumber + " /date " + tfnewDateTime.getText().trim());
        } else if (cbupdateType.getSelectionModel().getSelectedItem().equals("Type of Task")) {
            String typeStr = "";
            if (cbtaskType.getSelectionModel().getSelectedItem().equals("Todo")) {
                typeStr = "todo";
            } else  if (cbtaskType.getSelectionModel().getSelectedItem().equals("Deadline")) {
                typeStr = "deadline";
            } else  if (cbtaskType.getSelectionModel().getSelectedItem().equals("Fixed Duration")) {
                typeStr = "fixedduration";
            } else  if (cbtaskType.getSelectionModel().getSelectedItem().equals("Repeat")) {
                typeStr = "repeat";
            }
            handleUserEvent("update " + itemNumber + " /type " + typeStr);
        }
        updateGui();
        setVboxWidth(false);
        setButtonsVisibility(true);
    }

    @FXML
    private void onMouseClickCancel() {
        setVboxWidth(false);
        setButtonsVisibility(true);
    }

    @FXML
    private void cleanUp() {
        cbupdateType.getItems().clear();
        cbtaskType.getItems().clear();
        tfnewDateTime.clear();
        tfnewDesc.clear();
    }

    @FXML
    private void setVboxWidth(boolean isEnabled) {
        if (isEnabled) {
            vboxUpdate.setPrefWidth(VBOX_WIDTH);
            vboxUpdate.setVisible(true);
        } else {
            vboxUpdate.setPrefWidth(ZERO);
            vboxUpdate.setVisible(false);
        }
    }

    @FXML
    private void setButtonsVisibility(boolean isVisible) {
        if (isVisible) {
            btnOK.setVisible(false);
            btnCancel.setVisible(false);
            btnDone.setVisible(true);
            btnUpdate.setVisible(true);
            btnDelete.setVisible(true);
        } else {
            btnOK.setVisible(true);
            btnCancel.setVisible(true);
            btnDone.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
        }
    }

    @FXML
    private void updateGui() {
        listViewRefresh();
        setDisableButtons();
        labelSelectedTask.setText("Selected Task: ");
    }

    @FXML
    protected void listViewRefresh() {
        listT.getItems().clear();
        TaskList items = duke.getTaskList();
        FilterList filterList = duke.getFilterList();
        if (refreshType == 0) {
            if (selectedTab != null) {
                selectedTab.selectFirst();
            }
            for (int i = ZERO; i < items.size(); i++) {
                listT.getItems().add(items.get(i));
            }
        } else {
            for (int i = ZERO; i < filterList.size(); i++) {
                listT.getItems().add(filterList.get(i));
            }
            selectedTab.select(filterList.getFilterIndex());
        }
        refreshType = 0;

    }

    @FXML
    private void setDisableButtons() {
        btnDone.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    @FXML
    private void exitProgram() {
        String input = "bye";
        String response;
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        try {
            Command cmd = duke.getCommand(input);
            duke.saveState(cmd);
            response = Ui.showByeGui();
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            timer.schedule(exitDuke, new Date(System.currentTimeMillis() + TIMER_DELAY));
        } catch (DukeException e) {
            response = Ui.showErrorMsgGui(e.getMessage());
            logr.log(Level.WARNING, response, e);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
        } catch (Exception e) {
            response = Ui.showErrorMsgGui("     New error, please read console:")
                    +  Ui.showErrorMsgGui("     Duke will continue as per normal.");
            logr.log(Level.WARNING, response, e);
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            e.printStackTrace();
        }
        userInput.clear();
    }

    /**
     * Creates a new window to allow the user to add a new task via user friendly interface.
     */
    @FXML
    public void createAddWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/AddWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            Stage stage = new Stage();
            stage.setScene(scene);
            fxmlLoader.<AddWindow>getController().setAddWindow(duke, this);
            stage.show();
        } catch (IOException e) {
            logr.log(Level.SEVERE, "Unable to load add window", e);
            e.printStackTrace();
        }
    }

    /**
     * Creates a new window to allow the user to add or update notes of existing task via user friendly interface.
     */
    @FXML
    public void createAddNotesWindow() {
        Task taskObj = listT.getSelectionModel().getSelectedItem();
        TaskList items = duke.getTaskList();
        int itemNumber = items.getIndex(taskObj) + ONE;
        String notesDesc = items.get(itemNumber - ONE).getNotes();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/AddNotesWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            Stage stage = new Stage();
            stage.setScene(scene);
            fxmlLoader.<AddNotesWindow>getController().setAddNotesWindow(this, itemNumber, notesDesc);
            stage.show();
        } catch (IOException e) {
            logr.log(Level.SEVERE, "Unable to load add notes window", e);
            e.printStackTrace();
        }
    }

    /**
     * Creates a new window to allow the user to view commands under help via user friendly interface.
     */
    @FXML
    public void createHelpWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/HelpWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            Stage stage = new Stage();
            stage.setScene(scene);
            fxmlLoader.<HelpWindow>getController().setHelpWindow(duke, this);
            stage.show();
        } catch (IOException e) {
            logr.log(Level.SEVERE, "Unable to load help window", e);
            e.printStackTrace();
        }
    }

}