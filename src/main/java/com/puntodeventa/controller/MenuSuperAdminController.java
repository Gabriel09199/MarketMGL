package com.puntodeventa.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.puntodeventa.config.StageManager;
import com.puntodeventa.service.UsuarioService;
import com.puntodeventa.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

@Service
public class MenuSuperAdminController implements Initializable
{
	@FXML
    private Label lblNombreUsuario;

    @FXML
    private Label lblTipoUsuario;

    @FXML
    private Label lblCedula;
    
    @FXML
    private Label lblCorreoElectronico;
    
    @Autowired
    private UsuarioService usuarioService;
    
	@Lazy
    @Autowired
    private StageManager stageManager;
	
    @FXML
    void cerrarSesion(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Cerrar Sesión");
		alert.setHeaderText("¿Desea cerrar sesión?");
		alert.setContentText("Si cierra sesión lo llevará a la vista de bienvenido");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK)
		{
			stageManager.switchScene(FxmlView.LOGIN_VISTA_BIENVENIDA);
			usuarioService.setUsuario(null);
		}
    }

    @FXML
    void ingresarVistaCaja(ActionEvent event) {
    	stageManager.switchScene(FxmlView.REGISTRAR_VENTA);
    }

    @FXML
    void ingresarVistaClientes(ActionEvent event) {
    	stageManager.switchScene(FxmlView.CLIENTES);
    }

    @FXML
    void ingresarVistaProductos(ActionEvent event) {
    	stageManager.switchScene(FxmlView.PRODUCTOS);
    }

    @FXML
    void ingresarVistaProveedores(ActionEvent event) {
    	stageManager.switchScene(FxmlView.PROVEEDORES);
    }

    @FXML
    void ingresarVistaReportes(ActionEvent event) {

    }

    @FXML
    void ingresarVistaUsuarios(ActionEvent event) {
    	stageManager.switchScene(FxmlView.USUARIOS);
    }

    @FXML
    void ingresarVistaVentas(ActionEvent event) {
    	stageManager.switchScene(FxmlView.VENTAS);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inicializarDatos();
	}
	
	private void inicializarDatos()
	{
		String nombreCompleto = usuarioService.getUsuario().getNombresApellidos();
		
		String nombreTipo = usuarioService.getUsuario().getFKTipoUsuario().getNombreEstado();
		int cedula = usuarioService.getUsuario().getCedula();
		String correoElectronico = usuarioService.getUsuario().getCorreoElectronico();
		
		lblNombreUsuario.setText(nombreCompleto);
		lblTipoUsuario.setText(nombreTipo);
		lblCedula.setText("Cédula: " +cedula);
		lblCorreoElectronico.setText(correoElectronico);
	}

}