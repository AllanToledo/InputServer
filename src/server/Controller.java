package server;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Controller {
    @FXML
    Label mouseSensitivityText;

    @FXML
    Label scrollSensitivityText;

    @FXML
    Slider mouseSensitivity;

    @FXML
    Slider scrollSensitivity;

    @FXML
    Label ipLabel;

    public void initialize() {

        mouseSensitivity.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                float sensitivityValue = (float) round((newValue.floatValue() / 100), 2);
                App.robotController.setMouseSensitivity(sensitivityValue);
                mouseSensitivityText.setText(String.valueOf(sensitivityValue));
            }
        });

        scrollSensitivity.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int sensitivityValue = newValue.intValue();
                App.robotController.setScrollSensitivity(sensitivityValue);
                scrollSensitivityText.setText(String.valueOf(sensitivityValue));
            }
        });

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ipLabel.setText(String.format("Your IP is %s", inetAddress.getHostAddress()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
