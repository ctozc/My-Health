package com.zc.controller;

import com.zc.dao.UserDao;
import com.zc.entity.User;
import com.zc.utils.MD5;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoginC {
    public Text loginTip;
    public Text registerTip;
    public SplitPane root;
    public TextField username;
    public TextField password;
    private final ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

    public void login() throws Exception {
        String getPassword = "";
        UserDao userDao = (UserDao)context.getBean("userDao");
        User user = userDao.selectByUsername(username.getText());
        if (user != null) {
            getPassword = user.getPassword();
        }
        String pwd = MD5.md5(password.getText());
        if (pwd.equals(getPassword)) {
            StageManage.CONTROLLER.put("contextName", username.getText());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/user.fxml"));
            Parent root2 = fxmlLoader.load();
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(root2));
        }
        loginTip.setText("Wrong username or password!");
    }

    public void register() throws Exception {
        UserDao userDao = (UserDao)context.getBean("userDao");
        User user = new User();
        user.setUsername(username.getText());
        String pwd = MD5.md5(password.getText());
        user.setPassword(pwd);
        userDao.insert(user);
        StageManage.CONTROLLER.put("contextName", username.getText());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/login.fxml"));
        Parent root2 = fxmlLoader.load();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(root2));
        registerTip.setText("Cannot register, pls try again!");
    }
}
