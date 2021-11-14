package Start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Stage stage;
    public static boolean gameState = false;

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        showStartPage();
    }

    public void showStartPage() throws Exception{
        // XML Loading using FXMLLoader

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Startpage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        StartPage controller = loader.getController();
        //controller.init();
        controller.setMain(this);


        // Set the primary stage
        stage.setTitle("Start Page");
        stage.setScene(new Scene(root, 630, 482));
        stage.show();

    }


    public void startHumanVsHuman6x6() throws Exception{
        // XML Loading using FXMLLoader


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HumanVsHuman6x6.fxml"));
        Parent root = loader.load();

        // Loading the controller
        HumanVsHuman6x6 controller = loader.getController();
        controller.init();
        controller.setMain(this);



        // Set the primary stage
        stage.setTitle("Human Vs Human 6x6");
        stage.setScene(new Scene(root, 630, 482));
        stage.show();
        controller.drawBoard();


    }


    public void startHumanVsAI6x6() throws Exception{
        // XML Loading using FXMLLoader


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HumanVsAI6x6.fxml"));
        Parent root = loader.load();

        // Loading the controller
        HumanVsAI6x6 controller = loader.getController();
        controller.init();
        controller.setMain(this);



        // Set the primary stage
        stage.setTitle("Human Vs AI 6x6");
        stage.setScene(new Scene(root, 630, 482));
        stage.show();
        controller.drawBoard();


    }

}



