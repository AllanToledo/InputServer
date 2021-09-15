package server;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends javafx.application.Application {

    public static Server server;
    public static RobotController robotController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("serverScreen.fxml"));
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        robotController = new RobotController();
        server = new Server(robotController);
        server.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
