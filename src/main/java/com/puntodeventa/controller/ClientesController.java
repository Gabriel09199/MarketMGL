package com.puntodeventa.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.puntodeventa.config.StageManager;
import com.puntodeventa.estructure.Cliente;
import com.puntodeventa.estructure.Producto;
import com.puntodeventa.service.ClienteService;
import com.puntodeventa.view.FxmlView;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

@Service
public class ClientesController implements Initializable{

	@FXML
    private TextField txtCedula;

    @FXML
    private TextField txtPrimerNombre;

    @FXML
    private TextField txtSegundoNombre;

    @FXML
    private TextField txtPrimerApellido;

    @FXML
    private TextField txtSegundoApellido;

    @FXML
    private TextField txtTelefono;

    @FXML
    private ToggleGroup bgGenero;
    
    @FXML
    private RadioButton rbHombre;

    @FXML
    private RadioButton rbMujer;
    
    @FXML
    private Button btnRegistrar;
    
    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TableView<Cliente> tvClientes;

    @FXML
    private TableColumn<Cliente, Integer> tcCedula;

    @FXML
    private TableColumn<Cliente, String> tcPrimerNombre;

    @FXML
    private TableColumn<Cliente, String> tcSegundoNombre;

    @FXML
    private TableColumn<Cliente, String> tcPrimerApellido;

    @FXML
    private TableColumn<Cliente, String> tcSegundoApellido;

    @FXML
    private TableColumn<Cliente, String> tcGenero;

    @FXML
    private TableColumn<Cliente, Integer> tcTelefono;
    
    @FXML
    private TextField txtBusqueda;
    
    @Autowired
    private ClienteService clienteService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    private ObservableList<Cliente> clientesList = FXCollections.observableArrayList();
    private FilteredList<Cliente> filteredData;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setPropiedadesClientes();
		cargarDatosClientes();
		filtraBusqueda();
		eventoActualizarCliente();
		setVisibleBotones(false);
	}
	
	private void setPropiedadesClientes()
	{
		tcCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
		tcPrimerNombre.setCellValueFactory(new PropertyValueFactory<>("primerNombre"));
		tcSegundoNombre.setCellValueFactory(new PropertyValueFactory<>("segundoNombre"));
		tcPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
		tcSegundoApellido.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
		tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		tcGenero.setCellValueFactory(cellData -> 
				new ReadOnlyStringWrapper(cellData.getValue().getNameGenero()));
	}
	
	private void cargarDatosClientes()
	{
		clientesList.clear();
		clientesList.addAll(clienteService.listarClientes());
		tvClientes.setItems(clientesList);
	}

	public void filtraBusqueda() {		
		filteredData = new FilteredList<>(clientesList, cliente -> true);
		txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(cliente -> {
		        if(newValue == null || newValue.isEmpty())
		        {
		        	return true;
		        }
		        else if(String.valueOf(cliente.getCedula()).toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(cliente.getNombresApellidos().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(String.valueOf(cliente.getTelefono()).toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }  
		        else if(cliente.getNameGenero().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }  
		        
		        return false;
		    });
		});
		
		SortedList sort = new SortedList(filteredData);
		sort.comparatorProperty().bind(tvClientes.comparatorProperty());
		tvClientes.setItems(sort);
    }
	
	@FXML
	void limpiarCajasTexto(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Cliente");
		alert.setHeaderText("¿Desea limpiar las cajas de texto?");
		alert.setContentText("Si le da OK se perderá toda la información escrita.");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK)
		{
			vaciarCajasTexto();
		}
	}
	
	private void vaciarCajasTexto()
	{
		txtCedula.setText("");
		txtPrimerNombre.setText("");
		txtSegundoNombre.setText("");
		txtPrimerApellido.setText("");
		txtSegundoApellido.setText("");
		txtTelefono.setText("");
		rbHombre.setSelected(true);
	}
	
	@FXML
    void registrarCliente(ActionEvent event) {
		
		try
		{
			validarCampos();
			int cedula = Integer.parseInt(txtCedula.getText());
			String primerNombre = txtPrimerNombre.getText();
			String segundoNombre = txtSegundoNombre.getText();
			String primerApellido = txtPrimerApellido.getText();
			String segundoApellido = txtSegundoApellido.getText();
			int telefono = Integer.parseInt(txtTelefono.getText());
			Short genero = rbHombre.isSelected() ? Short.parseShort("1"):Short.parseShort("2");
			Cliente cliente = new Cliente(cedula, primerNombre, segundoNombre, primerApellido, segundoApellido, telefono, genero);
			clienteService.crearClientes(cliente);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Cliente");
			alert.setHeaderText(null);
			alert.setContentText("Se ha registrado el cliente existosamente.");
			alert.show();
			vaciarCajasTexto();
			cargarDatosClientes();
			filtraBusqueda();
		}
		catch (Exception e)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Hay un error al registrar un cliente.");
			alert.setContentText(e.getMessage());
			alert.show();
		}
	}
	
	@FXML
    void actualizarCliente(ActionEvent event)
	{
		try
		{
			validarCampos();
			int cedula = Integer.parseInt(txtCedula.getText());
			
			Cliente cliente = clienteService.existeClientePorCedula(cedula);
			System.out.println(cliente);
			String primerNombre = txtPrimerNombre.getText();
			String segundoNombre = txtSegundoNombre.getText();
			String primerApellido = txtPrimerApellido.getText();
			String segundoApellido = txtSegundoApellido.getText();
			int telefono = Integer.parseInt(txtTelefono.getText());
			Short genero = rbHombre.isSelected() ? Short.parseShort("1"):Short.parseShort("2");
			
			cliente.setPrimerNombre(primerNombre);
			cliente.setSegundoNombre(segundoNombre);
			cliente.setPrimerApellido(primerApellido);
			cliente.setSegundoApellido(segundoApellido);
			cliente.setTelefono(telefono);
			cliente.setGenero(genero);
			System.out.println(cliente);
			
			clienteService.modificarCliente(cliente.getIdCliente(), cliente);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Cliente");
			alert.setHeaderText(null);
			alert.setContentText("Se ha actualizado el cliente existosamente.");
			alert.show();
			vaciarCajasTexto();
			cargarDatosClientes();
			filtraBusqueda();
			setVisibleBotones(false);
		}
		catch (Exception e)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Hay un error al actualizar el cliente.");
			alert.setContentText(e.getMessage());
			alert.show();
		}
    }

    @FXML
    void cancelarActualizarCliente(ActionEvent event)
    {
    	vaciarCajasTexto();
    	setVisibleBotones(false);
    }
	
	public void eventoActualizarCliente()
	{
		tvClientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cliente>() {

			@Override
			public void changed(ObservableValue<? extends Cliente> observable, Cliente oldValue, Cliente newValue) {
				Cliente cliente = (Cliente) newValue;
	            if (cliente != null)
	            {
	            	txtCedula.setText(cliente.getCedula() + "");
	                txtPrimerNombre.setText(cliente.getPrimerNombre());
	                
	                txtPrimerApellido.setText(cliente.getPrimerApellido());
	                txtSegundoApellido.setText(cliente.getSegundoApellido());
	                txtPrimerNombre.setText(cliente.getPrimerNombre());
	                txtTelefono.setText(cliente.getTelefono() + "");
	                
	                if(cliente.getSegundoNombre() != null)
	                {
	                	txtSegundoNombre.setText(cliente.getSegundoNombre());
	                }
	                
	                if(cliente.getNameGenero().equals("Hombre"))
	                {
	                	rbHombre.setSelected(true);
	                }
	                else
	                {
	                	rbMujer.setSelected(true);
	                }
	                
	                setVisibleBotones(true);
	            }
			}
		});
	}
	
	private void setVisibleBotones(boolean value)
	{
		btnRegistrar.setVisible(!value);
		btnActualizar.setVisible(value);
		btnCancelar.setVisible(value);
	}

	public void validarCampos() throws Exception
	{
		if(txtPrimerNombre.getText().trim().equals("") || txtPrimerApellido.getText().trim().equals("") || 
				txtSegundoApellido.getText().trim().equals("") || txtCedula.getText().trim().equals("") || 
				txtTelefono.getText().trim().equals(""))
		{
			throw new Exception("No se ha podido agregar el cliente porque hay campos vacios");
		}
		else if(isInteger(txtPrimerNombre.getText().trim()) || isInteger(txtSegundoNombre.getText().trim()) ||
					isInteger(txtPrimerApellido.getText().trim()) || isInteger(txtSegundoApellido.getText().trim()))
		{
			throw new Exception("Los nombres o apellidos no admiten valor numéricos");
		}
		else if(!isInteger(txtCedula.getText()))
		{
			throw new Exception("La cédula solo admite valor numéricos");
		}
		else if(!isInteger(txtTelefono.getText()))
		{
			throw new Exception("El telefono solo admite valor numéricos");
		}
	}
	
	public boolean isInteger(String numero){
        try{
            Integer.parseInt(numero);
            return true;
        }catch(NumberFormatException e){
        	e.printStackTrace();
            return false;
        }
    }
	
	@FXML
    void devolverVistaMenu(ActionEvent event) {
    	stageManager.switchScene(FxmlView.MENU_SUPER_ADMIN);
    }

}
