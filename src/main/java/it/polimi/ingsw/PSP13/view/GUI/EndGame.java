package it.polimi.ingsw.PSP13.view.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class EndGame {

    @FXML
    private ImageView trumpet1, trumpet2;
    @FXML
    private Button yesButton;
    @FXML
    private Label label, label1;
    @FXML
    private ImageView gif;

    private boolean choice = false;
    private GuiInput guiInput;

    /**
     * initializes all the messages and images with the lost status
     */
    public void initialize() {
        label1.setText("DEFEAT\nYOU LOST..");
        label1.setTextAlignment(TextAlignment.CENTER);
        label1.setMaxWidth(Double.MAX_VALUE);
        label1.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
    }

    /**
     * initializes the message and images with the win status
     */
    public void win() {
        label1.setText("CONGRATULATIONS\n   YOU WON!");
        File file = new File("resources/endgame_victorytrumpets.png");
        Image image = new Image(file.toURI().toString());
        trumpet1.setImage(image);
        trumpet1.setScaleX(-1);
        trumpet2.setImage(image);
        File file1 = new File("resources/confetti.gif");
        Image image1 = new Image(file1.toURI().toString());
        gif.setImage(image1);
    }

    /**
     * the user selects if he wants to have a rematch
     * @param e the event related to the user clicking to "yes" or "no" button
     * @throws IOException if the communication with server fails
     */
    @FXML
    public void playAgain(ActionEvent e) throws IOException {
        if(e.getSource() == yesButton) {
            choice = true;
            guiInput.getController().notifyPlayAgain("yes");
        }
        else
        {
            guiInput.getController().notifyPlayAgain("no");
            System.exit(0);
        }



        backToLobbySceneChange();
    }

    /**
     * once the user choose what to do he will be put back to the lobby scene
     * @throws IOException
     */
    public void backToLobbySceneChange() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:./resources/lobby.fxml"));

        AnchorPane pane = loader.<AnchorPane>load();
        Scene lobby = new Scene(pane);
        lobby.getStylesheets().add("lobby.css");

        Stage stage = (Stage) (trumpet1.getScene().getWindow());
        stage.setScene(lobby);

        Lobby lobby1 = loader.<Lobby>getController();

        guiInput.setGodDispatcher(null);

        lobby1.setGuiInput(guiInput);
        guiInput.setLoginController(lobby1);
        guiInput.setMap(null);

        if(choice) {
            lobby1.rematch();
        }
    }



    public GuiInput getGuiInput() {
        return guiInput;
    }

    public void setGuiInput(GuiInput guiInput) {
        this.guiInput = guiInput;
    }


}
