package com.puntodeventa.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.puntodeventa.config.StageManager;
import com.puntodeventa.estructure.Cliente;
import com.puntodeventa.estructure.TipoUsuario;
import com.puntodeventa.estructure.Usuario;
import com.puntodeventa.service.TipoUsuarioService;
import com.puntodeventa.service.UsuarioService;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

@Service
public class UsuariosController implements Initializable{

    @FXML
    private TextField txtPrimerNombre;

    @FXML
    private TextField txtSegundoNombre;

    @FXML
    private TextField txtPrimerApellido;

    @FXML
    private TextField txtSegundoApellido;

    @FXML
    private RadioButton rbHombre;

    @FXML
    private RadioButton rbMujer;
    
    @FXML
    private RadioButton rbActivo;

    @FXML
    private RadioButton rbInactivo;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtConfirmarContrasenia;

    @FXML
    private ComboBox<TipoUsuario> cbTipoUsuario;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCancelar;
    
    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private TextField txtBusqueda;

    @FXML
    private TableView<Usuario> tvUsuarios;

    @FXML
    private TableColumn<Usuario, Integer> tcCedula;

    @FXML
    private TableColumn<Usuario, String> tcPrimerNombre;

    @FXML
    private TableColumn<Usuario, String> tcSegundoNombre;

    @FXML
    private TableColumn<Usuario, String> tcPrimerApellido;

    @FXML
    private TableColumn<Usuario, String> tcSegundoApellido;

    @FXML
    private TableColumn<Usuario, String> tcGenero;

    @FXML
    private TableColumn<Usuario, String> tcTipo;

    @FXML
    private TableColumn<Usuario, String> tcEstado;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private TipoUsuarioService tipoUsuarioService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    private ObservableList<Usuario> usuariosList = FXCollections.observableArrayList();
    private FilteredList<Usuario> filteredData;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	setPropiedadesUsuarios();
    	cargarDatosUsuarios();
    	filtraBusqueda();
    	eventoActualizarCliente();
    	setVisibleBotones(false);
	}

    private void setPropiedadesUsuarios()
	{
		tcCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
		tcPrimerNombre.setCellValueFactory(new PropertyValueFactory<>("primerNombre"));
		tcSegundoNombre.setCellValueFactory(new PropertyValueFactory<>("segundoNombre"));
		tcPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
		tcSegundoApellido.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
		tcGenero.setCellValueFactory(cellData -> 
				new ReadOnlyStringWrapper(cellData.getValue().getNameGenero()));
		tcTipo.setCellValueFactory(cellData -> 
				new ReadOnlyStringWrapper(cellData.getValue().getFKTipoUsuario().getNombreEstado()));
		tcEstado.setCellValueFactory(cellData -> 
				new ReadOnlyStringWrapper(cellData.getValue().getNameEstado()));
		
		if(usuarioService.getUsuario().getFKTipoUsuario().getNombreEstado().equals(TipoUsuario.SUPER_ADMINISTRADOR))
    	{
			cbTipoUsuario.getItems().addAll(tipoUsuarioService.listarUsuarios());
    	}
    	else
    	{
    		cbTipoUsuario.getItems().addAll(tipoUsuarioService.listarUsuariosAdministrador());
    	}
		
		
		cbTipoUsuario.getSelectionModel().select(1);
	}
    
    private void cargarDatosUsuarios()
	{
    	usuariosList.clear();
    	if(usuarioService.getUsuario().getFKTipoUsuario().getNombreEstado().equals(TipoUsuario.SUPER_ADMINISTRADOR))
    	{
    		usuariosList.addAll(usuarioService.listarUsuarios());
    	}
    	else
    	{
    		usuariosList.addAll(usuarioService.listarUsuariosAdministrador());
    	}
    	
		tvUsuarios.setItems(usuariosList);
	}

	public void filtraBusqueda() {		
		filteredData = new FilteredList<>(usuariosList, usuario -> true);
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
		        else if(cliente.getNameGenero().contains(newValue))
		        {
		        	return true;
		        } 
		        else if(cliente.getFKTipoUsuario().getNombreEstado().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        else if(cliente.getNameEstado().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }
		        
		        return false;
		    });
		});
		
		SortedList sort = new SortedList(filteredData);
		sort.comparatorProperty().bind(tvUsuarios.comparatorProperty());
		tvUsuarios.setItems(sort);
    }
    
    @FXML
    void actualizarUsuario(ActionEvent event) {
    	try
		{
			validarCampos();
			System.out.println("aqui");
			int cedula = Integer.parseInt(txtCedula.getText());
			
			Usuario usuario = usuarioService.existeUsuarioPorCedula(cedula);
			String primerNombre = txtPrimerNombre.getText();
			String segundoNombre = txtSegundoNombre.getText();
			String primerApellido = txtPrimerApellido.getText();
			String segundoApellido = txtSegundoApellido.getText();
			int telefono = Integer.parseInt(txtTelefono.getText());
			String correo = txtCorreo.getText();
			String contrasenia = txtContrasenia.getText();
			Short genero = rbHombre.isSelected() ? Short.parseShort("1"):Short.parseShort("2");
			char estado = rbActivo.isSelected() ? '1':'0';
			LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
			TipoUsuario tipoUsuario = cbTipoUsuario.getValue();
			
			usuario.setPrimerNombre(primerNombre);
			usuario.setSegundoNombre(segundoNombre);
			usuario.setPrimerApellido(primerApellido);
			usuario.setSegundoApellido(segundoApellido);
			usuario.setTelefono(telefono);
			usuario.setCorreoElectronico(correo);
			usuario.setContrasenia(contrasenia);
			usuario.setGenero(genero);
			usuario.setEstado(estado);
			usuario.setFechaNacimiento(fechaNacimiento);
			usuario.setFKTipoUsuario(tipoUsuario);
			System.out.println(usuario);
			
			usuarioService.modificarUsuario(usuario.getIdUsuario(), usuario);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Usuario");
			alert.setHeaderText(null);
			alert.setContentText("Se ha actualizado el usuario existosamente.");
			alert.show();
			vaciarCajasTexto();
			cargarDatosUsuarios();
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
    void cancelarActualizarUsuario(ActionEvent event) {
    	vaciarCajasTexto();
    	setVisibleBotones(false);
    }

    @FXML
    void registrarUsuario(ActionEvent event) {
    	try
		{
			validarCampos();
			System.out.println("aqui");
			int cedula = Integer.parseInt(txtCedula.getText());
			String primerNombre = txtPrimerNombre.getText();
			String segundoNombre = txtSegundoNombre.getText();
			String primerApellido = txtPrimerApellido.getText();
			String segundoApellido = txtSegundoApellido.getText();
			int telefono = Integer.parseInt(txtTelefono.getText());
			String correo = txtCorreo.getText();
			String contrasenia = txtContrasenia.getText();
			Short genero = rbHombre.isSelected() ? Short.parseShort("1"):Short.parseShort("2");
			char estado = rbActivo.isSelected() ? '1':'0';
			LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
			TipoUsuario tipoUsuario = cbTipoUsuario.getValue();
			
			Usuario usuario = new Usuario(cedula, contrasenia, primerNombre, segundoNombre, primerApellido, 
											segundoApellido, telefono, genero, fechaNacimiento, correo, 
											estado, tipoUsuario);
			usuarioService.crearUsuarios(usuario);
			System.out.println(usuario);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Usuario");
			alert.setHeaderText(null);
			alert.setContentText("Se ha actualizado el usuario existosamente.");
			alert.show();
			vaciarCajasTexto();
			cargarDatosUsuarios();
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
    
    public void eventoActualizarCliente()
	{
		tvUsuarios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuario>() {

			@Override
			public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario newValue) {
				Usuario usuario = (Usuario) newValue;
	            if (usuario != null)
	            {
	            	txtCedula.setText(usuario.getCedula() + "");
	                txtPrimerNombre.setText(usuario.getPrimerNombre());
	                txtPrimerApellido.setText(usuario.getPrimerApellido());
	                txtSegundoApellido.setText(usuario.getSegundoApellido());
	                txtPrimerNombre.setText(usuario.getPrimerNombre());
	                txtTelefono.setText(usuario.getTelefono() + "");
	                txtCorreo.setText(usuario.getCorreoElectronico());
	                txtContrasenia.setText(usuario.getContrasenia());
	                txtConfirmarContrasenia.setText(usuario.getContrasenia());
	                
	                if(usuario.getSegundoNombre() != null)
	                {
	                	txtSegundoNombre.setText(usuario.getSegundoNombre());
	                }
	                
	                if(usuario.getNameGenero().equals("Hombre"))
	                {
	                	rbHombre.setSelected(true);
	                }
	                else
	                {
	                	rbMujer.setSelected(true);
	                }
	                
	                if(usuario.getEstado() == '1')
	                {
	                	rbActivo.setSelected(true);
	                }
	                else
	                {
	                	rbInactivo.setSelected(true);
	                }
	                
	                cbTipoUsuario.setValue(usuario.getFKTipoUsuario());
	                dpFechaNacimiento.setValue(usuario.getFechaNacimiento());
	                
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
		txtCedula.setEditable(!value);
	}

    public void validarCampos() throws Exception
	{
    	Calendar cal= Calendar.getInstance();
    	int year= cal.get(Calendar.YEAR);
    	
		if(txtPrimerNombre.getText().trim().equals("") || txtPrimerApellido.getText().trim().equals("") || 
				txtSegundoApellido.getText().trim().equals("") || txtCedula.getText().trim().equals("") || 
				txtTelefono.getText().trim().equals("") || txtCorreo.getText().trim().equals("") ||
				txtContrasenia.getText().trim().equals("") || txtConfirmarContrasenia.getText().trim().equals(""))
		{
			throw new Exception("No se ha podido agregar el cliente porque hay campos vacios");
		}
		else if(isInteger(txtPrimerNombre.getText().trim()) || isInteger(txtSegundoNombre.getText().trim()) ||
					isInteger(txtPrimerApellido.getText().trim()) || isInteger(txtSegundoApellido.getText().trim()) ||
					isInteger(txtCorreo.getText()) )
		{
			throw new Exception("Los nombres o apellidos o el correo no admiten valor numéricos");
		}
		else if(!validarCorreo(txtCorreo.getText()))
		{
			throw new Exception("El correo es invalido");
		}
		else if(!isInteger(txtCedula.getText()))
		{
			throw new Exception("La cédula solo admite valor numéricos");
		}
		else if(!isInteger(txtTelefono.getText()))
		{
			throw new Exception("El telefono solo admite valor numéricos");
		}
		else if(!txtContrasenia.getText().equals(txtConfirmarContrasenia.getText()))
		{
			throw new Exception("Las contraseñas no coinciden");
		}
		else if(dpFechaNacimiento.getValue().getYear() > year - 18)
		{
			throw new Exception("El usuario debe tener más de 18 años");
		}
	}
    
    public boolean validarCorreo(String correo)
    {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
 
 
        Matcher mather = pattern.matcher(correo);
        
        return mather.find();
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
		txtCorreo.setText("");
		txtContrasenia.setText("");
		txtConfirmarContrasenia.setText("");
		rbHombre.setSelected(true);
		rbActivo.setSelected(true);
		dpFechaNacimiento.setValue(null);
	}

    @FXML
    void devolverVistaMenu(ActionEvent event) {
    	stageManager.switchScene(FxmlView.MENU_SUPER_ADMIN);
    }

}