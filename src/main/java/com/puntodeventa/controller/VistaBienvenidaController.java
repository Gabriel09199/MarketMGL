package com.puntodeventa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.puntodeventa.config.StageManager;
import com.puntodeventa.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

@Controller
public class VistaBienvenidaController implements Initializable {
	
    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    void iniciarSesionSuperAdmin(ActionEvent event) {
    	stageManager.switchScene(FxmlView.LOGIN_SUPER_ADMIN);
    }
    
    @FXML
    void iniciarSesionAdmin(ActionEvent event) {
    	stageManager.switchScene(FxmlView.LOGIN_ADMIN);
    }

    @FXML
    void iniciarSesionEmpleado(ActionEvent event) {
    	stageManager.switchScene(FxmlView.LOGIN_EMPLEADO);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
