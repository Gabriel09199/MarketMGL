package com.puntodeventa.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.puntodeventa.config.StageManager;
import com.puntodeventa.estructure.TipoUsuario;
import com.puntodeventa.estructure.Usuario;
import com.puntodeventa.service.UsuarioService;
import com.puntodeventa.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class LoginEmpleadoController implements Initializable {
	
    @FXML
    private TextField txtCedula;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private Button btnIngresar;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
        
	@FXML
    private void iniciarSesion(ActionEvent event) throws IOException{
    	try
    	{
    		Usuario currentUsuario = usuarioService.autenticar(getCedula(), getContrasenia(), TipoUsuario.EMPLEADO);
			if(currentUsuario != null)
			{	    		
				usuarioService.setUsuario(currentUsuario);
				stageManager.switchScene(FxmlView.MENU_EMPLEADO);
			}
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Autenticación correcta");
				alert.setHeaderText(null);
				alert.setContentText("La contraseña es incorrecta");
				alert.showAndWait();
			}
		}
    	catch (Exception e) 
    	{
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("¡Error!");
			alert.setHeaderText(null);
			alert.setContentText("Error: " + e.getMessage());
			alert.showAndWait();
		}
    }
	
	@FXML
    void volverVistaBienvenida(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Salir");
		alert.setHeaderText("¿Desea devolverse a la anterior vista?");
		alert.setContentText(null);
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK)
		{
			stageManager.switchScene(FxmlView.LOGIN_VISTA_BIENVENIDA);
		}
    }
	
	public String getCedula() {
		return txtCedula.getText();
	}
	
	public String getContrasenia() {
		return txtContrasenia.getText();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
