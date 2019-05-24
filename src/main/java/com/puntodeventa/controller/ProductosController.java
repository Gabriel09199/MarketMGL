package com.puntodeventa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.puntodeventa.config.StageManager;
import com.puntodeventa.estructure.Producto;
import com.puntodeventa.estructure.TipoUsuario;
import com.puntodeventa.estructure.Venta;
import com.puntodeventa.service.ProductoService;
import com.puntodeventa.service.UsuarioService;
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
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

@Service
public class ProductosController implements Initializable {

    @FXML
    private TextField txtBusqueda;

    @FXML
    private TableView<Producto> tvProductos;

    @FXML
    private TableColumn<Producto, String> tcCategoria;

    @FXML
    private TableColumn<Producto, Integer> tcIdProducto;

    @FXML
    private TableColumn<Producto, String> tcNombreProducto;

    @FXML
    private TableColumn<Producto, String> tcDescripcion;

    @FXML
    private TableColumn<Producto, Integer> tcCosto;

    @FXML
    private TableColumn<Producto, Integer> tcValorUnitario;

    @FXML
    private TableColumn<Producto, String> tcNombreCompania;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    @Autowired
    private ProductoService productoService;
    
    @Lazy
    @Autowired
	private UsuarioService usuarioService;
    
    private ObservableList<Producto> productosList = FXCollections.observableArrayList();
    private FilteredList<Producto> filteredData;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		setPropiedadesProductos();
    	cargarDatosProductos();
    	filtraBusqueda();
	}
	
	private void setPropiedadesProductos()
	{
		tcCategoria.setCellValueFactory(cellData -> 
				new ReadOnlyStringWrapper(cellData.getValue().getFKCategoria().getNombreCategoria()));
		tcIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
		tcNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
		tcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		tcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
		tcValorUnitario.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
		tcNombreCompania.setCellValueFactory(cellData -> 
				new ReadOnlyStringWrapper(cellData.getValue().getFKProveedor().getNombreCompania()));
	}
    
    private void cargarDatosProductos()
	{
    	productosList.clear();
    	productosList.addAll(productoService.listarProductos());
		tvProductos.setItems(productosList);
	}

	public void filtraBusqueda() {		
		filteredData = new FilteredList<>(productosList, usuario -> true);
		txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(producto -> {
		        if(newValue == null || newValue.isEmpty())
		        {
		        	return true;
		        }
		        else if(producto.getFKCategoria().getNombreCategoria().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(String.valueOf(producto.getIdProducto()).toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(producto.getNombreProducto().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(producto.getDescripcion().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(String.valueOf(producto.getCosto()).toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(String.valueOf(producto.getValorUnitario()).toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(producto.getFKProveedor().getNombreCompania().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        
		        return false;
		    });
		});
		
		SortedList sort = new SortedList(filteredData);
		sort.comparatorProperty().bind(tvProductos.comparatorProperty());
		tvProductos.setItems(sort);
    }
	
	@FXML
    void devolverVistaMenu(ActionEvent event) {
    	if(usuarioService.getUsuario().getFKTipoUsuario().getNombreEstado().equals(TipoUsuario.EMPLEADO))
    	{
    		stageManager.switchScene(FxmlView.MENU_EMPLEADO);
    	}
    	else
    	{
    		stageManager.switchScene(FxmlView.MENU_SUPER_ADMIN);
    	}
    	
    }

}