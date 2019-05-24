package com.puntodeventa.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.puntodeventa.config.StageManager;
import com.puntodeventa.estructure.Categoria;
import com.puntodeventa.estructure.Cliente;
import com.puntodeventa.estructure.Producto;
import com.puntodeventa.estructure.TipoUsuario;
import com.puntodeventa.estructure.Usuario;
import com.puntodeventa.estructure.Venta;
import com.puntodeventa.estructure.VentaActual;
import com.puntodeventa.service.ClienteService;
import com.puntodeventa.service.ProductoService;
import com.puntodeventa.service.UsuarioService;
import com.puntodeventa.service.VentaActualService;
import com.puntodeventa.service.VentaService;
import com.puntodeventa.view.FxmlView;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTableCellBuilder;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.util.Callback;

@Service
public class RegistrarVentaController implements Initializable{

	@FXML
    private BorderPane vistaRegistrarVenta;
	
    @FXML
    private TextField txtTotalVenta;

    @FXML
    private TextField txtBusqueda;

    @FXML
    private RadioButton rbCategoria;

    @FXML
    private ToggleGroup bgBusqueda;

    @FXML
    private RadioButton rbNombreProducto;
    
    @FXML
    private TableView<Producto> tvProductosTienda;

    @FXML
    private TableColumn<Producto, String> tcCategoriaTienda;

    @FXML
    private TableColumn<Producto, String> tcNombreProductoTienda;

    @FXML
    private TableColumn<Producto, Integer> tcCostoProductoTienda;

    @FXML
    private TableView<VentaActual> tvProductosCarrito;

    @FXML
    private TableColumn<VentaActual, String> tcNombreProductoCarrito;

    @FXML
    private TableColumn<VentaActual, String> tcCantidadCarrito;

    @FXML
    private TableColumn<VentaActual, String> tcCostoProductoCarrito;

    @FXML
    private TableColumn<VentaActual, Integer> tcCostoTotalCarrito;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private VentaActualService ventaActualService;
    
    @Autowired
    private VentaService ventaService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    private ObservableList<Producto> productosTiendaList = FXCollections.observableArrayList();
    private ObservableList<VentaActual> productosCarritoList = FXCollections.observableArrayList();
    private FilteredList<Producto> filteredData;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtTotalVenta.setEditable(false);
		txtTotalVenta.setText("$0");
		setPropiedadesProductosTienda();
		setPropiedadesProductosCarrito();
		
		cargarDatosProductos();
		filtraBusqueda();
		
		tvProductosCarrito.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tvProductosTienda.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		tcCantidadCarrito.setOnEditCommit(data -> {
			if(!isInteger(data.getNewValue()) && !isInteger(data.getOldValue()))
			{
				VentaActual ventaNueva = data.getRowValue();		    
				ventaNueva.setCantidad(Short.parseShort(data.getOldValue()));
			}
			else
			{
				VentaActual ventaNueva = data.getRowValue();		    
				ventaNueva.setCantidad(Short.parseShort(data.getNewValue()));
				int precioVenta = ventaNueva.getCantidad() * ventaNueva.getFKProducto().getCosto();
				ventaNueva.setPrecioVenta(precioVenta);
				productosCarritoList.set(data.getTablePosition().getRow(), ventaNueva);
				tvProductosCarrito.setItems(productosCarritoList);
				txtTotalVenta.setText("$" + totalVenta());
			}
		});
	}
	
    @FXML
    void realizarVenta(ActionEvent event) 
    {
    	if(productosCarritoList.size() < 1)
    	{
    		Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("Warning");
			alert2.setHeaderText(null);
			alert2.setContentText("No se puede realizar una venta si no hay productos en el carrito.");

			alert2.showAndWait();
    	}
    	else
    	{
    		TextInputDialog dialog = new TextInputDialog("");
        	dialog.setTitle("Registrar venta");
        	dialog.setHeaderText(null);
        	dialog.setContentText("Ingrese la cédula del cliente:");
        	
        	Optional<String> result = dialog.showAndWait();
    		if(result.isPresent() && isInteger(result.get().trim()))
    		{
    	    	Cliente cliente = clienteService.existeClientePorCedula(Integer.parseInt(result.get()));
    	    	
    			if(result.get().equals("0"))
    			{
    				int precioTotalVenta = 0;
    				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    		    	Date fechaVenta = new Date();
    		    	Usuario usuario = usuarioService.getUsuario();
    		    	Short estado = Short.parseShort("1");
    		    	
    		    	for(VentaActual ventaActual: productosCarritoList)
    		    	{
    		    		precioTotalVenta += ventaActual.getPrecioVenta();
    		    	}
    		    	
    		    	Venta nuevaVenta = ventaService.crearVenta(new Venta(fechaVenta, precioTotalVenta, estado, usuario));
    		    	for(VentaActual ventaActual: productosCarritoList)
    		    	{
    		    		ventaActual.setFKVenta(nuevaVenta);
    		    		VentaActual nuevaVentaActual = ventaActualService.crearVenta(ventaActual);
    		    		ventaActual.setIdVentaActual(nuevaVentaActual.getIdVentaActual());
    		    	}
    		    	
    		    	productosCarritoList.clear();
    		    	
    		    	Alert alert2 = new Alert(AlertType.INFORMATION);
    				alert2.setTitle("Venta Exitosa");
    				alert2.setHeaderText(null);
    				alert2.setContentText("Se ha realizado la venta exitosamente.");

    				alert2.showAndWait();
    			}
    			else if(cliente != null)
    			{
    				Alert alert = new Alert(AlertType.CONFIRMATION);
    				alert.setTitle("Confirmar Cliente");
    				alert.setHeaderText("¿Este es el cliente?");
    				alert.setContentText(cliente.getPrimerNombre() + " " + cliente.getSegundoNombre() + " " +
    										cliente.getPrimerApellido() + " " + cliente.getSegundoApellido() + 
    										"\nCédula: " + cliente.getCedula());


    				Optional<ButtonType> result2 = alert.showAndWait();
    				if (result2.get() == ButtonType.OK)
    				{
    					int precioTotalVenta = 0;
    					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    			    	Date fechaVenta = new Date();
    			    	Usuario usuario = usuarioService.getUsuario();
    			    	Short estado = Short.parseShort("1");
    			    	
    			    	for(VentaActual ventaActual: productosCarritoList)
    			    	{
    			    		precioTotalVenta += ventaActual.getPrecioVenta();
    			    	}
    			    	
    			    	Venta nuevaVenta = ventaService.crearVenta(new Venta(fechaVenta, precioTotalVenta, estado, cliente, usuario));
    			    	for(VentaActual ventaActual: productosCarritoList)
    			    	{
    			    		ventaActual.setFKVenta(nuevaVenta);
    			    		VentaActual nuevaVentaActual = ventaActualService.crearVenta(ventaActual);
    			    		System.out.println(ventaActual);
    			    		ventaActual.setIdVentaActual(nuevaVentaActual.getIdVentaActual());
    			    	}
    			    	
    			    	productosCarritoList.clear();
    			    	
    			    	Alert alert2 = new Alert(AlertType.INFORMATION);
    					alert2.setTitle("Venta Exitosa");
    					alert2.setHeaderText(null);
    					alert2.setContentText("Se ha realizado la venta exitosamente.");

    					alert2.showAndWait();
    				}
    				else 
    				{
    					Alert alert2 = new Alert(AlertType.WARNING);
    					alert2.setTitle("Warning");
    					alert2.setHeaderText(null);
    					alert2.setContentText("No se ha realizado la venta");

    					alert2.showAndWait();
    				}
    			}
    			else
    			{
    				Alert alert = new Alert(AlertType.ERROR);
    				alert.setTitle("Error cliente");
    				alert.setHeaderText(null);
    				alert.setContentText("El cliente con esa cédula no existe.");

    				alert.showAndWait();
    			}
    		}
    		else
    		{
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("¡Error!");
    			alert.setHeaderText(null);
    			alert.setContentText("¡Solo se permiten números!");

    			alert.showAndWait();
    		}
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
	
	private void cargarDatosProductos()
	{
		productosTiendaList.clear();
		productosTiendaList.addAll(productoService.listarProductos());
		tvProductosTienda.setItems(productosTiendaList);
	}
	
	private void setPropiedadesProductosTienda()
	{
		tcCategoriaTienda.setCellValueFactory(cellData -> 
					new ReadOnlyStringWrapper(cellData.getValue().getFKCategoria().getNombreCategoria()));
		tcNombreProductoTienda.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
		tcCostoProductoTienda.setCellValueFactory(new PropertyValueFactory<>("costo"));
	}
	
	private void setPropiedadesProductosCarrito()
	{
		tvProductosCarrito.setEditable(true);
		tcNombreProductoCarrito.setCellValueFactory(cellData -> 
					new ReadOnlyStringWrapper(cellData.getValue().getFKProducto().getNombreProducto()));
		tcCantidadCarrito.setCellValueFactory(cellData -> 
		new ReadOnlyStringWrapper(cellData.getValue().getCantidad() + ""));
		tcCostoProductoCarrito.setCellValueFactory(cellData -> 
					new ReadOnlyStringWrapper(cellData.getValue().getFKProducto().getCosto() + ""));
		tcCostoTotalCarrito.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
		
		tcCantidadCarrito.setCellFactory(TextFieldTableCell.forTableColumn());
		
	}
	
	@FXML
    void añadirProductoCarrito(ActionEvent event)
	{
		List<Producto> productos = tvProductosTienda.getSelectionModel().getSelectedItems();
    	
    	for(Producto productoActual: productos)
    	{
    		if(existeProductoCarrito(productoActual.getNombreProducto()))
    		{
    			Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Añadir producto al carrito");
    			alert.setHeaderText("Error al añadir producto al carro");
    			alert.setContentText("No se ha podido añadir el producto " + productoActual.getNombreProducto() + 
    									" porque este mismo ya se encuentra en el carrito");
    			alert.show();
    		}
    		else
    		{
    			VentaActual ventaActual = new VentaActual();
        		ventaActual.setCantidad((short) 1);
        		ventaActual.setFKProducto(productoActual);
        		ventaActual.setPrecioVenta(productoActual.getCosto());
        		
        		productosCarritoList.add(ventaActual);
    		}
    	}
    	
    	txtTotalVenta.setText("$" + totalVenta());
    	tvProductosCarrito.setItems(productosCarritoList);
    }
	
	public int totalVenta()
	{
		int total = 0;
		for(VentaActual ventaActual: productosCarritoList)
	    {
	    	total += ventaActual.getPrecioVenta();
	    }
		
		return total;
	}
	
	private boolean existeProductoCarrito(String nombreProducto)
	{
		for(int i = 0; i < tvProductosCarrito.getItems().size(); i++)
		{
			if(tvProductosCarrito.getItems().get(i).getFKProducto().getNombreProducto().equals(nombreProducto))
			{
				return true;
			}
		}
		
		return false;
	}

	@FXML
    void sacarVentaCarrito(ActionEvent event)
	{
		ObservableList<VentaActual> nuevaLista = FXCollections.observableArrayList();
		
		for(int i = 0; i < tvProductosCarrito.getItems().size(); i++)
		{
			if(!tvProductosCarrito.getSelectionModel().isSelected(i))
			{
				nuevaLista.add(tvProductosCarrito.getItems().get(i));
			}
		}
    	
    	productosCarritoList = nuevaLista;
    	txtTotalVenta.setText("$" + totalVenta());
    	tvProductosCarrito.setItems(productosCarritoList);
   }

    public void filtraBusqueda() {	
		filteredData = new FilteredList<>(productosTiendaList, producto -> true);
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
		        else if(producto.getNombreProducto().toLowerCase().contains(newValue.toLowerCase()))
		        {
		        	return true;
		        }  
		        
		        return false;
		    });
		});
		
		SortedList sort = new SortedList(filteredData);
		sort.comparatorProperty().bind(tvProductosTienda.comparatorProperty());
		tvProductosTienda.setItems(sort);
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
