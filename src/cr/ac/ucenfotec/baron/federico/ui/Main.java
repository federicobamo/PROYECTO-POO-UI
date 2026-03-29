package cr.ac.ucenfotec.baron.federico.ui;

import cr.ac.ucenfotec.baron.federico.bl.entities.*;
import cr.ac.ucenfotec.baron.federico.bl.entities.usuario.Coleccionista;
import cr.ac.ucenfotec.baron.federico.bl.entities.usuario.Moderador;
import cr.ac.ucenfotec.baron.federico.bl.entities.usuario.Usuario;
import cr.ac.ucenfotec.baron.federico.bl.entities.usuario.Vendedor;
import cr.ac.ucenfotec.baron.federico.bl.logic.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Clase principal que contiene el menú de consola del sistema de subastas.
 * Maneja la interacción con el usuario a través de opciones de menú.
 *
 * @author Baron Federico
 */
public class Main {

    /**
     * la entrada
     */
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    /**
     * la salida
     */
    static PrintStream out = System.out;
    /**
     * el servicio
     */
    static Service service = new Service();

    /**
     * Meetodo que muestra el menu de inicio con todas los opciones requeridas para el funcionamiento
     *
     * @throws IOException la io exception
     */
    public static void mostrarMenu() throws IOException {

        int opcionMenu = -1;

        do {
            out.println("----- Vamos a Vender y Comprar -----");
            out.println("1. Registrar Usuarios");
            out.println("2. Listar Usuarios");
            out.println("3. Iniciar Sesión");
            out.println("4. Crear una subasta");
            out.println("5. Listar subastas");
            out.println("6. Crear una oferta");
            out.println("7. Listar ofertas");
            out.println("8. Cancelar subastas");
            out.println("9. Listar Ordenes");
            out.println("0. Salir");
            out.println("------------------------------------------");
            out.println("Digite una opción: ");
            opcionMenu = Integer.parseInt(in.readLine());
            procesarMenu(opcionMenu);

        } while (opcionMenu != 0);
    }

    /**
     * Metodo para procesar el menu, se lo muestra al usuario y guarda su repuesta
     *
     * @param opcionMenu la opcion menu
     * @throws IOException the io exception
     */
    public static void procesarMenu(int opcionMenu) throws IOException {

        switch (opcionMenu) {

            case (1):

                registrarUsuarios();

                break;

            case (2):

                if (!service.hayUsuarios()) {

                    out.println("No hay usuarios registrados");

                } else {

                    listarUsuarios();

                }

                break;

            case (3):

                inicioSesion();


                break;

            case (4):

                if (!service.hayUsuarios()) {

                    out.println("No hay usuarios registrados para registrar una subasta");

                } else {

                    registrarSubasta();
                }
                break;

            case (5):

                if (!service.haySubastas()) {
                    out.println("No hay subastas registrados");
                } else {
                    listarSubasta();
                }

                break;

            case (6):
                if (!service.haySubastas()) {

                    out.println("No hay subastas registrados para crear una oferta");

                } else {

                    registrarOfertas();
                }
                break;

            case (7):

                if (!service.hayOfertas()) {
                    out.println("No hay ofertas registrados");
                } else {
                    listarOfertas();
                }
                break;
            case (8):

                if (!service.haySubastas()) {
                    out.println("No hay subastas registrados");
                } else {
                    cancelarSubastas();
                }

                break;

            case (9):

                listarOrdenes();
                break;

            case (0):

                out.println("Has salido del sistema");

                break;
        }
    }

    public static void registrarModerador() throws IOException {

        String nombre;
        int id;
        String contrasena;
        String correoElectronico;
        LocalDate fechaNacimiento;

        out.println("------- Registro Moderador -------");
        out.println("Nombre: ");
        nombre = in.readLine();
        out.println("Identificación: ");
        id = Integer.parseInt(in.readLine());
        out.println("Correo Electronico: ");
        correoElectronico = in.readLine();
        out.println("Contraseña: ");
        contrasena = in.readLine();
        out.println("Fecha Nacimiento: (AAAA-MM-DD)");
        fechaNacimiento = LocalDate.parse(in.readLine());

        service.registrarModerador(nombre, id, contrasena, correoElectronico, fechaNacimiento);
    }

    public static void registrarUsuarios() throws IOException { // Metodo para registrar Usuarios

        String nombre;
        int id;
        String contrasena;
        String correoElectronico;
        LocalDate fechaNacimiento;

        int menuTipo;
        double puntuacion = 0;
        String direccion = "";
        int opcionInteres;
        int opcionObjetos;

        String nombreObjeto;
        String descripcion;
        EstadoObjeto estado = null;
        LocalDate fechaCompra;
        int menuEstadoObjeto;

        out.println("------- Registro Usuario -------");
        out.println("Nombre: ");
        nombre = in.readLine();
        out.println("Identificación: ");
        id = Integer.parseInt(in.readLine());
        out.println("Correo Electronico: ");
        correoElectronico = in.readLine();
        out.println("Contraseña: ");
        contrasena = in.readLine();
        out.println("Fecha Nacimiento: (AAAA-MM-DD)");
        fechaNacimiento = LocalDate.parse(in.readLine());
        out.println("Tipo: ");
        out.println("1. Vendedor ");
        out.println("2. Coleccionista");

        menuTipo = Integer.parseInt(in.readLine());

        switch (menuTipo) {

            case (1): // Datos Extra vendedor

                out.println("Puntuación: (1 al 5) ");
                puntuacion = Double.parseDouble(in.readLine());
                out.println("Dirección: ");
                direccion = in.readLine();

                service.registrarVendedor(nombre, id, contrasena, correoElectronico, fechaNacimiento, puntuacion, direccion);

                break;

            case (2): // Datos Extra coleccionista

                out.println("Puntuación: (1 al 5) ");
                puntuacion = Double.parseDouble(in.readLine());
                out.println("Dirección: ");
                direccion = in.readLine();

                out.println("----- Lista de intereses ----- ");
                out.println("¿Quiere agregar objetos de interes a su lista? ");
                out.println("1.Si 2.NO");
                opcionInteres = Integer.parseInt(in.readLine());

                Coleccionista nuevoColeccionista = service.registrarColeccionista(nombre, id, contrasena, correoElectronico, fechaNacimiento, puntuacion, direccion);

                while (opcionInteres == 1) {

                    String objetoInteres;

                    out.println("Ingrese un objeto de su interes: ");
                    objetoInteres = in.readLine();

                    nuevoColeccionista.getListaIntereses().add(objetoInteres);

                    out.println("¿Agregar otro? 1.Si 2.No");
                    opcionInteres = Integer.parseInt(in.readLine());

                }

                out.println("----- lista de objetos ----- ");
                out.println("¿Quiere agregar objetos para luego vender? ");
                out.println("1.Si 2.NO");
                opcionObjetos = Integer.parseInt(in.readLine());

                while (opcionObjetos == 1) {

                    out.println("Nombre: ");
                    nombreObjeto = in.readLine();
                    out.println("Descripción: ");
                    descripcion = in.readLine();
                    out.println("Estado: ");
                    out.println("1. Nuevo: ");
                    out.println("2. Usado: ");
                    out.println("3. Antiguo sin abrir: ");
                    menuEstadoObjeto = Integer.parseInt(in.readLine());

                    switch (menuEstadoObjeto) {

                        case (1): // Nuevo

                            estado = EstadoObjeto.NUEVO;

                            break;

                        case (2): // Usado

                            estado = EstadoObjeto.USADO;

                            break;

                        case (3): // Antiguo sin usar

                            estado = EstadoObjeto.ANTIGUO_SIN_ABRIR;

                            break;

                    }

                    out.println("Ingresa la fecha de fabricación del objeto (AAAA-MM-DD): ");
                    fechaCompra = LocalDate.parse(in.readLine());
                    out.println("---------------------------------");

                    out.println("¿Agregar otro? 1.Si 2.No");
                    opcionObjetos = Integer.parseInt(in.readLine());

                    service.registrarObjetosColeccionista(nuevoColeccionista, nombreObjeto, descripcion, estado, fechaCompra);
                }
                break;
        }
    }
    /**
     * metodo para listar los usuarios ingresados previamente
     */
    public static void listarUsuarios() { // Metodo para listar Usuarios

        for (Usuario u : service.listarUsuarios()) {
            out.println(u);
        }
    }
    /***
     * Metodo para inicar sesión, solicita al usuario un correo y una contraaseña
     * para poder compararla con la aldgun usuario registrado
     * @throws IOException
     */
    public static void inicioSesion() throws IOException {

        out.println("----- Inicio Sesión -----");

        out.println("Correo Electronico: ");
        String correoElectronico = in.readLine();

        out.println("Contraseña: ");
        String contrasena = in.readLine();

        Usuario usuarioLogueado = service.inicarSesion(correoElectronico, contrasena);

        if (usuarioLogueado == null) {

            out.println("Error en los credenciales");
            out.println("Intente nuevamente");
            return;

        } else {
            out.println("Bienvenido");
        }
    }

    /**
     * metodo para registrar una subasta, por medio de un usurio previamente registrado
     *
     * @throws IOException the io exception
     */
    public static void registrarSubasta() throws IOException {

        LocalDateTime fechaVencimiento;
        Usuario creador;
        double precioMinimo;
        int opcionCreador;
        String nombreObjeto;
        String descripcion;
        EstadoObjeto estado = null;
        LocalDate fechaCompra;
        int menuEstadoObjeto;

        out.println("------ Usuarios Registrados -----");

        for (int i = 0; i < service.listarUsuarios().size(); i++) { // recorre el array par mostrar los usuarios registrados

            out.println("Usuario #" + (i + 1));
            out.println("Nombre: " + service.listarUsuarios().get(i).getNombre());
            out.println("ID: " + service.listarUsuarios().get(i).getId());
            out.println("Edad: " + service.listarUsuarios().get(i).calcularEdad());
            out.println("Correo Electronico: " + service.listarUsuarios().get(i).getCorreoElectronico());
            if (service.listarUsuarios().get(i) instanceof Moderador) {
                out.println("Tipo: Moderador");
            } else if (service.listarUsuarios().get(i) instanceof Vendedor) {
                out.println("Tipo: Vendedor");
            } else if (service.listarUsuarios().get(i) instanceof Coleccionista) {
                out.println("Tipo: Coleccionista");

            }
            out.println("-------------------");
        }

        out.println("Ingrese el numero del usuario que va a crear la subasta: ");
        opcionCreador = Integer.parseInt(in.readLine());
        creador = service.listarUsuarios().get(opcionCreador - 1);


        if (creador instanceof Coleccionista) {
            Coleccionista coleccionista = (Coleccionista) creador; // cast

            out.println("----- Colección Personal -----");

            for (int i = 0; i < coleccionista.getListaObjetos().size(); i++) {
                out.println("Objeto #" + (i + 1) + ": " + coleccionista.getListaObjetos().get(i).getNombreObjeto());
            }

            out.println("Seleccione el numoer del objeto a subastar: ");
            int opcionObjeto = Integer.parseInt(in.readLine());
            Objeto objetoSeleccionado = coleccionista.getListaObjetos().get(opcionObjeto - 1);

            out.println("----- Configuración de la subasta ---------");

            out.println("Fecha y hora de vencimiento de la subasta (AAAA-MM-DDTHH:MM): ");
            fechaVencimiento = LocalDateTime.parse(in.readLine());
            out.println("Ingrese el precio minimo de la subasta: (Dolares)");
            precioMinimo = Double.parseDouble(in.readLine());
            out.println(" ");
            out.println("Subasta creada con exito");
            out.println("---------------------------------");
            Subasta subasta = service.registroSubastas(fechaVencimiento, creador, precioMinimo);
            subasta.getListaObjetos().add(objetoSeleccionado);

        } else {

            if (service.puedeCrearSubasta(creador) == true) {

                // Crear un objeto para subastar
                out.println("------- Registro del objeto -----");
                out.println("Nombre: ");
                nombreObjeto = in.readLine();
                out.println("Descripción: ");
                descripcion = in.readLine();
                out.println("Estado: ");
                out.println("1. Nuevo: ");
                out.println("2. Usado: ");
                out.println("3. Antiguo sin abrir: ");

                menuEstadoObjeto = Integer.parseInt(in.readLine());

                switch (menuEstadoObjeto) {

                    case (1): // Nuevo

                        estado = EstadoObjeto.NUEVO;

                        break;

                    case (2): // Usado
                        estado = EstadoObjeto.USADO;

                        break;

                    case (3): // Antiguo sin usar

                        estado = EstadoObjeto.ANTIGUO_SIN_ABRIR;

                        break;
                }

                out.println("Ingresa la fecha de fabricación del objeto (AAAA-MM-DD): ");
                fechaCompra = LocalDate.parse(in.readLine());
                out.println("---------------------------------");

                out.println(" ");

                out.println("----- Configuración de la subasta ---------");

                out.println("Fecha y hora de vencimiento de la subasta (AAAA-MM-DDTHH:MM): ");
                fechaVencimiento = LocalDateTime.parse(in.readLine());
                out.println("Ingrese el precio minimo de la subasta (Dolares): ");
                precioMinimo = Double.parseDouble(in.readLine());
                out.println(" ");
                out.println("Subasta creada con exito");
                out.println("---------------------------------");

                Subasta subasta = service.registroSubastas(fechaVencimiento, creador, precioMinimo);
                service.registrarObjeto(subasta, nombreObjeto, descripcion, estado, fechaCompra);

            } else {

                out.println("------------------------------------");
                out.println("El usario no puede crear una subasta");
                out.println("debe ser Coleccionista o Vendedor");
                out.println("------------------------------------");
                return;
            }
        }
    }
    /**
     * Metodo para listar las subastas, que se craeron anteriormente
     */
    public static void listarSubasta() {

        for (int i = 0; i < service.listarSubastas().size(); i++) { // recorre el array para mostrar las subastas

            out.println("----- Subastas publicadas -----");
            out.println("Subasta #" + (i + 1));
            out.println("Nombre del creador: " + service.listarSubastas().get(i).getCreador().getNombre());

            for (int j = 0; j < service.listarSubastas().get(i).getListaObjetos().size(); j++) { // ciclo for que recorre la lista de los objetos

                out.println("Objeto: " + service.listarSubastas().get(i).getListaObjetos().get(j).getNombreObjeto());
                out.println("Descripción: " + service.listarSubastas().get(i).getListaObjetos().get(j).getDescripcion());
                out.println("Antigüedad: " + service.listarSubastas().get(i).getListaObjetos().get(j).calcularAntiguedad());

            }

            out.println("Precio minimo: " + service.listarSubastas().get(i).getPrecioMinimo());
            service.subastaVencio(service.listarSubastas().get(i)); // llama al metodo que verifica si la subasta sigue activa
            out.println("Estado de la subasta: " + service.listarSubastas().get(i).getEstado());
            out.println("Tiempo restante de la subasta: " + service.listarSubastas().get(i).calcularTiempoRestante());
            out.println("-------------------");
        }
    }
    /**
     * Metodo para registrar ofertas de los usuarios, por los ojetos antes listados
     *
     * @throws IOException the io exception
     */
    public static void registrarOfertas() throws IOException {

        int seleccionoSubasta;
        Subasta subasta;
        int seleccionOfertante;
        Usuario usuario;

        for (int i = 0; i < service.listarSubastas().size(); i++) { // recorre el array para mostrar las subastas

            out.println("----- Subastas publicadas -----");
            out.println("Subasta #" + (i + 1));
            out.println("Nombre del creador: " + service.listarSubastas().get(i).getCreador().getNombre());

            for (int j = 0; j < service.listarSubastas().get(i).getListaObjetos().size(); j++) { // ciclo for que recorre la lista de los objetos

                out.println("Objeto: " + service.listarSubastas().get(i).getListaObjetos().get(j).getNombreObjeto());
                out.println("Descripción: " + service.listarSubastas().get(i).getListaObjetos().get(j).getDescripcion());
                out.println("Antigüedad: " + service.listarSubastas().get(i).getListaObjetos().get(j).calcularAntiguedad());
                out.println("Estado de la subasta: " + service.listarSubastas().get(i).getEstado());
            }
            out.println("Precio minimo: (Dolares) " + service.listarSubastas().get(i).getPrecioMinimo());
            out.println("-------------------");
        }

        out.println("Ingrese el numero de subasta a la que quiere ofertar ");
        seleccionoSubasta = Integer.parseInt(in.readLine());
        subasta = service.listarSubastas().get(seleccionoSubasta - 1);

        if (!service.subastaActiva(subasta)) {
            out.println("La subasta ya no está disponible");
            return;

        } else {

            out.println(" ");
            out.println("----- Usuarios ------");
            for (int i = 0; i < service.listarUsuarios().size(); i++) { // recorre el array par mostrar los usuarios registrados

                out.println("Usuario #" + (i + 1));
                out.println("Nombre: " + service.listarUsuarios().get(i).getNombre());
                out.println("ID: " + service.listarUsuarios().get(i).getId());
                out.println("Edad: " + service.listarUsuarios().get(i).calcularEdad() + " años");
                out.println("Correo Electronico: " + service.listarUsuarios().get(i).getCorreoElectronico());
                if (service.listarUsuarios().get(i) instanceof Moderador) {
                    out.println("Tipo: Moderadore");
                } else if (service.listarUsuarios().get(i) instanceof Vendedor) {
                    out.println("Tipo: Vendedor");
                } else if (service.listarUsuarios().get(i) instanceof Coleccionista) {
                    out.println("Tipo: Coleccionista");
                }
                out.println("-------------------");
            }

            out.println("Ingrese el numero del usuario que va a ofertar: ");
            seleccionOfertante = Integer.parseInt(in.readLine());
            usuario = service.listarUsuarios().get(seleccionOfertante - 1);

            if (service.puedeParcipar(subasta, usuario) == true) {

                out.println("Este usuario no puede participar de la subasta");
                return;

            } else {

                if (service.puedeOfertar(usuario) == true) {

                    double precioOferta;

                    out.println("Realice una oferta: ");
                    precioOferta = Double.parseDouble(in.readLine());

                    if (service.ofertaValida(precioOferta, subasta.getPrecioMinimo()) == true) {

                        service.registrarOfertas(subasta, usuario, precioOferta);
                        out.println("Su oferta fue procesada exitosamente: ");

                    } else {

                        out.println("Su oferta debe ser mayor al valor minimo actual");
                        return;
                    }

                } else {

                    out.println("------------------------------------");
                    out.println("El usario no puede oferta en subastas");
                    out.println("debe ser Coleccionista");
                    out.println("------------------------------------");
                }
                return;
            }
        }
    }

    /**
     * Listar las ofertas recibidas por los usuarios para las diferentes subastas
     */
    public static void listarOfertas() {

        out.println("------- LISTA OFERTAS -------");

        for (int i = 0; i < service.listarSubastas().size(); i++) {

            service.subastaVencio(service.listarSubastas().get(i));

            for (int j = 0; j < service.listarSubastas().get(i).getListaOfertas().size(); j++) {

                out.println("Ofertante: " + service.listarSubastas().get(i).getListaOfertas().get(j).getColeccionista().getNombre());
                out.println("Objeto: " + service.listarSubastas().get(i).getListaObjetos().get(0).getNombreObjeto());
                out.println("Precio: " + service.listarSubastas().get(i).getListaOfertas().get(j).getprecioOferta());
            }
            out.println("Estado de la subasta: " + service.listarSubastas().get(i).getEstado());
            out.println("Tiempo restante de la subasta: " + service.listarSubastas().get(i).calcularTiempoRestante());
            out.println("-------------------");
        }
    }

    /**
     * Meotodo para que un moderador pueda cancelar alguna subasta
     */
    public static void cancelarSubastas() throws IOException {

        int usuarioModerador;
        int seleccionSubastaCancelar;


        out.println("----- Usuarios ------");
        for (int i = 0; i < service.listarUsuarios().size(); i++) { // recorre el array par mostrar los usuarios registrados

            out.println("Usuario #" + (i + 1));
            out.println("Nombre: " + service.listarUsuarios().get(i).getNombre());
            out.println("ID: " + service.listarUsuarios().get(i).getId());
            out.println("Edad: " + service.listarUsuarios().get(i).calcularEdad() + " años");
            out.println("Correo Electronico: " + service.listarUsuarios().get(i).getCorreoElectronico());
            if (service.listarUsuarios().get(i) instanceof Moderador) {
                out.println("Tipo: Moderadore");
            } else if (service.listarUsuarios().get(i) instanceof Vendedor) {
                out.println("Tipo: Vendedor");
            } else if (service.listarUsuarios().get(i) instanceof Coleccionista) {
                out.println("Tipo: Coleccionista");
            }
            out.println("-------------------");

        }
        out.println("Seleccione el numero del usario que va a cancelar la subasta; ");
        usuarioModerador = Integer.parseInt(in.readLine());

        Usuario moderador = service.listarUsuarios().get(usuarioModerador - 1);

        if (service.puedeCancelarSubasta(moderador) == true) {

            for (int i = 0; i < service.listarSubastas().size(); i++) { // recorre el array para mostrar las subastas

                out.println("----- Subastas publicadas -----");
                out.println("Subasta #" + (i + 1));
                out.println("Nombre del creador: " + service.listarSubastas().get(i).getCreador().getNombre());

                for (int j = 0; j < service.listarSubastas().get(i).getListaObjetos().size(); j++) { // ciclo for que recorre la lista de los objetos de la subasta

                    out.println("Objeto: " + service.listarSubastas().get(i).getListaObjetos().get(j).getNombreObjeto());
                    out.println("Descripción: " + service.listarSubastas().get(i).getListaObjetos().get(j).getDescripcion());
                    out.println("Antigüedad: " + service.listarSubastas().get(i).getListaObjetos().get(j).calcularAntiguedad());
                    out.println("Precio minimo en dolares: " + service.listarSubastas().get(i).getPrecioMinimo());
                    out.println("-------------------");

                }

            }

            out.println("Ingrese el numero de subasta que quiere cancelar: ");
            seleccionSubastaCancelar = Integer.parseInt(in.readLine());
            Subasta subasta = service.listarSubastas().get(seleccionSubastaCancelar - 1);

            service.CancelarSubasta(subasta);
            out.println("Subasta cancelada exitosamente");

        } else {

            out.println("El usuario tiene que ser tipo moderador para cancelar una subasta");

        }
    }
    /***
     * Metodo para listar las ordenes que están guardadas
     */
    public static void listarOrdenes() {

        for (OrdenAdjudicacion o : service.listarOrdenes()) {
            out.println(o);
        }
    }
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {

        if (!service.existeModerador()) {
            out.println("No existe un moderador registrado");
            out.println("Registre un moderador");

            registrarModerador();
        }

        mostrarMenu();

    }
}


