package server;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class RobotController {
    private float actualTouchX = 0;
    private float actualTouchY = 0;
    private byte wheelCount = 0;
    private int scrollSensitivity = 5;
    private float mouseSensitivity = 1;

    private Robot robot;

    public RobotController() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void setMouseSensitivity(float mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
    }

    public void setScrollSensitivity(int scrollSensitivity) {
        this.scrollSensitivity = scrollSensitivity;
    }

    public void receiveInput(byte[] input) throws IOException {
        actualTouchX = input[0] * mouseSensitivity;
        actualTouchY = input[1] * mouseSensitivity;
        boolean leftClick = input[2] == 1;
        boolean rightClick = input[3] == 1;
        byte key = input[4];
        byte scrollWheel = (byte) (input[5] > 0 ? 1 : input[5] < 0 ? -1 : 0);

        Point point = MouseInfo.getPointerInfo().getLocation();
        robot.mouseMove(point.x + ((int) actualTouchX), point.y + ((int) actualTouchY));


        wheelCount += scrollWheel;
        if (wheelCount > scrollSensitivity) {
            robot.mouseWheel(scrollWheel);
            wheelCount = 0;
        }
        if (wheelCount < -scrollSensitivity) {
            robot.mouseWheel(scrollWheel);
            wheelCount = 0;
        }

        if (key != 0) {
            if (key < 0) {
                if (key == -1) {
                    robot.keyPress(KeyEvent.VK_WINDOWS);
                    robot.keyRelease(KeyEvent.VK_WINDOWS);
                } else if (key == -3)
                    Runtime.getRuntime().exec("volumeControl up");
                else if (key == -4)
                    Runtime.getRuntime().exec("volumeControl down");

            } else {
                robot.keyPress(key);
                robot.keyRelease(key);
            }
        }

        if (leftClick) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    robot.mousePress(MouseEvent.BUTTON1_MASK);
                    robot.mouseRelease(MouseEvent.BUTTON1_MASK);
                }
            }).start();
        }

        if (rightClick) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    robot.mousePress(MouseEvent.BUTTON3_MASK);
                    robot.mouseRelease(MouseEvent.BUTTON3_MASK);
                }
            }).start();
        }

    }
}
