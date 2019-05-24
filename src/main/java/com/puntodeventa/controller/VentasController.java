package com.puntodeventa.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.puntodeventa.config.StageManager;
import com.puntodeventa.estructure.TipoUsuario;
import com.puntodeventa.estructure.Usuario;
import com.puntodeventa.estructure.Venta;
import com.puntodeventa.service.TipoUsuarioService;
import com.puntodeventa.service.UsuarioService;
import com.puntodeventa.service.VentaService;
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
public class VentasController implements Initializable {

    @FXML
    private TextField txtBusqueda;

    @FXML
    private TableView<Venta> tvVentas;

    @FXML
    private TableColumn<Venta, Integer> tcIdVenta;

    @FXML
    private TableColumn<Venta, Date> tcFechaVenta;

    @FXML
    private TableColumn<Venta, Integer> tcPrecioVenta;

    @FXML
    private TableColumn<Venta, String> tcEstado;

    @FXML
    private TableColumn<Venta, String> tcCedulaUsuario;

    @FXML
    private TableColumn<Venta, String> tcTipoUsuario;

    @FXML
    private TableColumn<Venta, String> tcCedulaCliente;
    
    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private TipoUsuarioService tipoUsuarioService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    private ObservableList<Venta> ventasList = FXCollections.observableArrayList();
    private FilteredList<Venta> filteredData;

    @Lazy
    @Autowired
	private UsuarioService usuarioService;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setPropiedadesVentas();
		cargarDatosVentas();
		filtraBusqueda();
	}
	
	private void setPropiedadesVentas()
	{
		tcIdVenta.setCellValueFactory(new PropertyValueFactory<>("idVenta"));
		tcFechaVenta.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
		tcPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioTotalVenta"));
		tcEstado.setCellValueFactory(cellData -> 
				new ReadOnlyStringWrapper(cellData.getValue().getNameEstado()));
		tcCedulaUsuario.setCellValueFactory(cellData -> 
				new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getFKUsuario().getCedula())));
		tcCedulaCliente.setCellValueFactory(cellData -> 
				new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getCedulaCliente())));
		tcTipoUsuario.setCellValueFactory(cellData -> 
				new ReadOnlyStringWrapper(cellData.getValue().getFKUsuario().getFKTipoUsuario().getNombreEstado()));
		
		tcEstado.setCellFactory(ChoiceBoxTableCell.forTableColumn(Venta.ACTIVO, Venta.INACTIVO));
		tcEstado.setOnEditCommit(data -> {
			Venta venta = data.getRowValue();
			if(!venta.getNameEstado().equals(data.getNewValue()))
			{
				if(venta.getNameEstado().equals(Venta.ACTIVO))
				{
					venta.setEstado(Short.parseShort("0"));
				}
				else
				{
					venta.setEstado(Short.parseShort("1"));
				}
				
				ventaService.modificarVenta(venta.getIdVenta(), venta);
			}
		});
	}
    
    private void cargarDatosVentas()
	{
    	ventasList.clear();
    	ventasList.addAll(ventaService.listarVentas());
		tvVentas.setItems(ventasList);
	}

	public void filtraBusqueda() {		
		filteredData = new FilteredList<>(ventasList, usuario -> true);
		txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(venta -> {
		        if(newValue == null || newValue.isEmpty())
		        {
		        	return true;
		        }
		        else if(String.valueOf(venta.getIdVenta()).toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(venta.getFechaVenta().toString().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(String.valueOf(venta.getPrecioTotalVenta()).toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }  
		        else if(venta.getNameEstado().contains(newValue))
		        {
		        	return true;
		        }  
		        else if(String.valueOf(venta.getFKUsuario().getCedula()).toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(venta.getFKUsuario().getFKTipoUsuario().getNombreEstado().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(venta.getCedulaCliente().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        
		        
		        return false;
		    });
		});
		
		SortedList sort = new SortedList(filteredData);
		sort.comparatorProperty().bind(tvVentas.comparatorProperty());
		tvVentas.setItems(sort);
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