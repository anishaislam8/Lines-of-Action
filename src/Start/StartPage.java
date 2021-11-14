package Start;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class StartPage {
    Main main;
    public void setMain(Main main){
        this.main = main;
    }

    @FXML
    private Button humanVsHuman, humanVsAI;

    public void onClickHumanVsHuman(){
        try {
            main.startHumanVsHuman6x6();
        }catch (Exception e){
            System.out.println("Failed to start Human vs Human");
        }

    }
    public void onClickHumanVsAI(){
        try {
            main.startHumanVsAI6x6();
        }catch (Exception e){
            System.out.println("Failed to start Human vs AI");
        }

    }



}
