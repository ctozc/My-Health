package com.zc.controller;

import com.jfoenix.controls.JFXButton;
import com.zc.dao.*;
import com.zc.entity.*;
import com.zc.utils.ExcelWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserC {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    public AnchorPane root;
    public Text editTip;
    public Text exportTip;
    public TextField weight;
    public TextField temperature;
    public TextField bloodPressure;
    public TextField deleteRecordId;
    public TextArea textualNote;
    @FXML
    private TableColumn<User, String> userInfoUserName,userInfoFirstName,userInfoLastName;
    @FXML
    private TableColumn<Record, String> recordId,recordWeight,recordTemperature, recordBloodPressure, recordTextualNote, createTime;
    @FXML
    private TableView userInfoTable;
    @FXML
    private TableView recordTable;

    public void getUserInfo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/user_info.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("User Info");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void updateInfo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/update_info.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Update Info");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void init(){
        UserDao userDao =(UserDao)context.getBean("userDao");
        User user = userDao.selectByUsername((String)StageManage.CONTROLLER.get("contextName"));
        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(user);
        userInfoTable.setItems(users);
        userInfoUserName.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        userInfoFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        userInfoLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
    }

    public void exist() throws IOException {
        StageManage.CONTROLLER.remove("contextName");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/login.fxml"));
        Parent root2 = fxmlLoader.load();
        Stage stage =(Stage) root.getScene().getWindow();
        stage.setScene(new Scene(root2));
    }

    public void recordAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/record_add.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("record management");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void recordQuery() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/record_query.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("record management");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private JFXButton cpw_edit;
    @FXML
    private TextField cpw_first_name,cpw_last_name;

    public void cpw_edit(){
        UserDao userDao =(UserDao)context.getBean("userDao");//获得bean
        User user = userDao.selectByUsername((String)StageManage.CONTROLLER.get("contextName"));
        user.setFirstName(cpw_first_name.getText());
        user.setLastName(cpw_last_name.getText());
        userDao.update(user, user.getUsername());
        editTip.setText("Update success, pls close page");
    }

    public void recordInit(){
        UserDao userDao =(UserDao)context.getBean("userDao");
        RecordDao recordDao =(RecordDao)context.getBean("recordDao");
        User user = userDao.selectByUsername((String)StageManage.CONTROLLER.get("contextName"));
        List<Record> recordsByUserId = recordDao.batchQueryByUserId(user.getId());
        ObservableList<Record> records = FXCollections.observableArrayList();
        for (int i = 0; i < recordsByUserId.size(); i++) {
            records.add(recordsByUserId.get(i));
        }
        recordTable.setItems(records);
        recordId.setCellValueFactory(new PropertyValueFactory<Record, String>("id"));
        recordWeight.setCellValueFactory(new PropertyValueFactory<Record, String>("weight"));
        recordTemperature.setCellValueFactory(new PropertyValueFactory<Record, String>("temperature"));
        recordBloodPressure.setCellValueFactory(new PropertyValueFactory<Record, String>("bloodPressure"));
        recordTextualNote.setCellValueFactory(new PropertyValueFactory<Record, String>("textualNote"));
        createTime.setCellValueFactory(new PropertyValueFactory<Record, String>("createTime"));

        // edit record files
        recordWeight.setCellFactory(TextFieldTableCell.forTableColumn());
        recordWeight.setOnEditCommit(
            new EventHandler<CellEditEvent<Record, String>>() {
                @Override
                public void handle(CellEditEvent<Record, String> t) {
                    Record record = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    record.setWeight(t.getNewValue());
                    recordDao.update(record, record.getId());
                }
            }
        );
        recordTemperature.setCellFactory(TextFieldTableCell.forTableColumn());
        recordTemperature.setOnEditCommit(
            new EventHandler<CellEditEvent<Record, String>>() {
                @Override
                public void handle(CellEditEvent<Record, String> t) {
                    Record record = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    record.setTemperature(t.getNewValue());
                    recordDao.update(record, record.getId());
                }
            }
        );
        recordBloodPressure.setCellFactory(TextFieldTableCell.forTableColumn());
        recordBloodPressure.setOnEditCommit(
            new EventHandler<CellEditEvent<Record, String>>() {
                @Override
                public void handle(CellEditEvent<Record, String> t) {
                    Record record = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    record.setBloodPressure(t.getNewValue());
                    recordDao.update(record, record.getId());
                }
            }
        );
        recordTextualNote.setCellFactory(TextFieldTableCell.forTableColumn());
        recordTextualNote.setOnEditCommit(
            new EventHandler<CellEditEvent<Record, String>>() {
                @Override
                public void handle(CellEditEvent<Record, String> t) {
                    Record record = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    record.setTextualNote(t.getNewValue());
                    recordDao.update(record, record.getId());
                }
            }
        );
    }

    public void insertRecord() throws IOException {
        if (weight.getText() == "" && temperature.getText() == "" &&
                bloodPressure.getText() == "" & textualNote.getText() == "") {
            editTip.setText("pls input at least one filed");
        } else {
            RecordDao recordDao =(RecordDao)context.getBean("recordDao");
            UserDao userDao =(UserDao) context.getBean("userDao");
            User user = userDao.selectByUsername((String) StageManage.CONTROLLER.get("contextName"));
            Record record = new Record();
            record.setUserId(user.getId());
            record.setWeight(weight.getText());
            record.setBloodPressure(temperature.getText());
            record.setTemperature(bloodPressure.getText());
            record.setTextualNote(textualNote.getText());
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            record.setCreateTime(sdf.format(date));
            recordDao.insert(record);
            editTip.setText("create success, pls close page");
        }
    }

    public void deleteRecord() throws Exception{
        RecordDao recordDao =(RecordDao)context.getBean("recordDao");
        recordDao.delete(Long.valueOf(deleteRecordId.getText()));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/record_query.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("record management");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void exportRecords() throws Exception{
        UserDao userDao =(UserDao)context.getBean("userDao");
        RecordDao recordDao =(RecordDao)context.getBean("recordDao");
        User user = userDao.selectByUsername((String)StageManage.CONTROLLER.get("contextName"));
        List<Record> records = recordDao.batchQueryByUserId(user.getId());
        Workbook workbook = ExcelWriter.exportData(records);
        FileOutputStream fileOut = null;
        try {
            String exportFilePath = "./records.xlsx";
            File exportFile = new File(exportFilePath);
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }
            fileOut = new FileOutputStream(exportFilePath);
            workbook.write(fileOut);
            fileOut.flush();
            exportTip.setText("Exports success, pls check file in current directory");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (null != fileOut) {
                    fileOut.close();
                }
                if (null != workbook) {
                    workbook.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
