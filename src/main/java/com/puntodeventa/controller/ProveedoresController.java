package com.puntodeventa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.puntodeventa.config.StageManager;
import com.puntodeventa.estructure.Producto;
import com.puntodeventa.estructure.Proveedor;
import com.puntodeventa.service.ProductoService;
import com.puntodeventa.service.ProveedorService;
import com.puntodeventa.view.FxmlView;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Service
public class ProveedoresController implements Initializable {

    @FXML
    private TextField txtBusqueda;

    @FXML
    private TableView<Proveedor> tvProveedores;

    @FXML
    private TableColumn<Proveedor, Integer> tcIdProveedor;

    @FXML
    private TableColumn<Proveedor, String> tcCompania;

    @FXML
    private TableColumn<Proveedor, String> tcEncargado;

    @FXML
    private TableColumn<Proveedor, String> tcCorreo;

    @FXML
    private TableColumn<Proveedor, String> tcDireccion;

    @FXML
    private TableColumn<Proveedor, Integer> tcTelefono;
    
    @Autowired
    private ProveedorService proveedorService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    private ObservableList<Proveedor> proveedoresList = FXCollections.observableArrayList();
    private FilteredList<Proveedor> filteredData;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setPropiedadesProveedor();
    	cargarDatosProveedor();
    	filtraBusqueda();
	}
	
	private void setPropiedadesProveedor()
	{
		tcIdProveedor.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
		tcCompania.setCellValueFactory(new PropertyValueFactory<>("nombreCompania"));
		tcEncargado.setCellValueFactory(cellData -> 
				new ReadOnlyStringWrapper(cellData.getValue().getNombresApellidos()));
		tcCorreo.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
		tcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
	}
    
    private void cargarDatosProveedor()
	{
    	proveedoresList.clear();
    	proveedoresList.addAll(proveedorService.listarProveedores());
		tvProveedores.setItems(proveedoresList);
	}

	public void filtraBusqueda() {		
		filteredData = new FilteredList<>(proveedoresList, usuario -> true);
		txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(proveedor -> {
		        if(newValue == null || newValue.isEmpty())
		        {
		        	return true;
		        }
		        else if(String.valueOf(proveedor.getIdProveedor()).toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(proveedor.getNombreCompania().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(proveedor.getNombresApellidos().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(proveedor.getCorreoElectronico().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(proveedor.getDireccion().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(String.valueOf(proveedor.getTelefono()).toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		       
		        
		        return false;
		    });
		});
		
		SortedList sort = new SortedList(filteredData);
		sort.comparatorProperty().bind(tvProveedores.comparatorProperty());
		tvProveedores.setItems(sort);
    }

	@FXML
    void devolverVistaMenu(ActionEvent event) {
    	stageManager.switchScene(FxmlView.MENU_SUPER_ADMIN);
    }
}
