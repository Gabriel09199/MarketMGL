package com.puntodeventa.view;

import java.util.ResourceBundle;

public enum FxmlView {

    LOGIN_VISTA_BIENVENIDA {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("vistaBienvenida.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/vistasLogin/VistaBienvenida.fxml";
        }
    }, 
    LOGIN_SUPER_ADMIN {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/vistasLogin/LoginSuperAdmin.fxml";
        }
    },
    LOGIN_ADMIN {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/vistasLogin/LoginAdmin.fxml";
        }
    },
    LOGIN_EMPLEADO {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/vistasLogin/LoginEmpleado.fxml";
        }
    },
    MENU_SUPER_ADMIN {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("menu.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/menus/MenuSuperAdmin.fxml";
        }
    },
    
    MENU_ADMIN {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("menu.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/menus/MenuAdmin.fxml";
        }
    },
    
    MENU_EMPLEADO {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("menu.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/menus/MenuEmpleado.fxml";
        }
    },
    
    REGISTRAR_VENTA {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("venta.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/ventas/RegistrarVenta.fxml";
        }
    },
    
    CLIENTES {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("cliente.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/clientes/Clientes.fxml";
        }
    },
	
    USUARIOS {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("usuario.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/usuarios/Usuarios.fxml";
        }
    },
    
    VENTAS {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("venta.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/ventas/Ventas.fxml";
        }
    }, 
    
    PRODUCTOS {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("producto.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/productos/Productos.fxml";
        }
    },
    
    PROVEEDORES {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("proveedor.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/proveedores/Proveedores.fxml";
        }
    };
    
    public abstract String getTitle();
    public abstract String getFxmlFile();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
