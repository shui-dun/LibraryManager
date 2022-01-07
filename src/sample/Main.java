package sample;

import com.sun.javafx.stage.StageHelper;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 图书馆管理系统
 *
 * @author 黄小可 学号：18130500197
 */
public class Main extends Application
{
    private static String ID;// 记录当前用户的ID

    public static String getID()
    {
        return ID;
    }

    public static void setID(String ID)
    {
        Main.ID = ID;
    }

    public static void main(String[] args) throws Exception { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        showStage(primaryStage, "LoginPage");
    }

    //    打开新舞台
    public static void showStage(Stage stage, String fxmlName) throws Exception
    {
        String url = "/sample/view/" + fxmlName + ".fxml";
        AnchorPane root = FXMLLoader.load(Main.class.getResource(url));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(fxmlName);
        stage.getIcons().add(new Image("/icon/book.png"));
        stage.centerOnScreen();
        stage.show();
    }

    //    关闭旧舞台，打开新舞台
    public static void showStage(String fxmlName) throws Exception
    {
        Stage stage = StageHelper.getStages().get(0);
        stage.close();
        showStage(stage, fxmlName);
    }

    //    提示标签渐淡效果
    public static void promptFade(Label label)
    {
        label.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(label);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setDuration(Duration.millis(3000));
        fadeTransition.play();
        fadeTransition.statusProperty().addListener(observable ->
        {
            if (fadeTransition.getStatus() == Animation.Status.STOPPED)
                label.setVisible(false);
        });
    }

}
