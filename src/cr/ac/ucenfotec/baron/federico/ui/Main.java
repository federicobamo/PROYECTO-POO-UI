package cr.ac.ucenfotec.baron.federico.ui;

import cr.ac.ucenfotec.baron.federico.bl.entities.*;
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
            out.println("3. Crear una subasta");
            out.println("4. Listar subastas");
            out.println("5. Crear una oferta");
            out.println("6. Listar ofertas");
            out.println("7. Cancelar subastas");
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

                if (!service.hayUsuarios()) {

                    out.println("No hay usuarios registrados para registrar una substa");

                } else {

                    registrarSubasta();
                }
                break;

            case (4):

                if (!service.haySubastas()) {

                    out.println("No hay subastas registrados");

                } else {

                    listarSubasta();

                }

                break;

            case (5):
                if (!service.haySubastas()) {

                    out.println("No hay subastas registrados para crear una oferta");

                } else {

                    registrarOfertas();
                }
                break;

            case (6):

                if (!service.hayOfertas()) {

                    out.println("No hay ofertas registrados");

                } else {

                    listarOfertas();
                }

                break;


            case (7):

                if (!service.haySubastas()) {

                    out.println("No hay subastas registrados para crear una oferta");

                } else {

                    cancelarSubastas();
                }

                break;

            case (0):

                out.println("Has salido del sistema");

                break;
        }
    }

    /**
     * Metodo para registrar los usuarios de la aplicacion y clasificarlos en 1 de los 3 tipos ( vendedor, coleccionsita, moderador)
     *
     * @throws IOException the io exception
     */
    public static void registrarUsuarios() throws IOException { // Metodo para registrar Usuarios

        String nombre;
        int id;
        String contrasena;
        String correoElectronico;
        LocalDate fechaNacimiento;

        TipoUsuario tipo = null;
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
        out.println("1. Moderador ");
        out.println("2. Vendedor");
        out.println("3. Coleccionista");

        menuTipo = Integer.parseInt(in.readLine());

        switch (menuTipo) {

            case (1): // Datos Extra Moderador

                tipo = TipoUsuario.MODERADOR;
                puntuacion = 0;
                direccion = "";
                service.registrarUsuarios(nombre, id, contrasena, correoElectronico, puntuacion, direccion, tipo, fechaNacimiento);

                break;

            case (2): // Datos Extra Vendedor

                out.println("Puntuación: (1 al 5) ");
                puntuacion = Double.parseDouble(in.readLine());
                out.println("Dirección: ");
                direccion = in.readLine();
                tipo = TipoUsuario.VENDEDOR;
                service.registrarUsuarios(nombre, id, contrasena, correoElectronico, puntuacion, direccion, tipo, fechaNacimiento);


                break;


            case (3): //Datos Extra Coleccionista

                out.println("Puntuación: (1 al 5) ");
                puntuacion = Double.parseDouble(in.readLine());
                out.println("Dirección: ");
                direccion = in.readLine();
                tipo = TipoUsuario.COLECCIONISTA;
                out.println("----- Lista de intereses ----- ");
                out.println("¿Quiere agregar objetos de interes a su lista? ");
                out.println("1.Si 2.NO");
                opcionInteres = Integer.parseInt(in.readLine());

                Usuario nuevoUsuario = service.registrarUsuarios(nombre, id, contrasena, correoElectronico, puntuacion, direccion, tipo, fechaNacimiento);

                while (opcionInteres == 1) {

                    String objetoInteres;

                    out.println("Ingrese un objeto de su interes: ");
                    objetoInteres = in.readLine();

                    nuevoUsuario.getListaIntereses().add(objetoInteres);

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

                    service.registrarObjetosColeccionista(nuevoUsuario, nombreObjeto, descripcion, estado, fechaCompra);
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
            out.println("Tipo: " + service.listarUsuarios().get(i).getTipo());

            out.println("-------------------");

        }

        out.println("Ingrese el numero del usuario que va a crear la subasta: ");
        opcionCreador = Integer.parseInt(in.readLine());
        creador = service.listarUsuarios().get(opcionCreador - 1);

        out.println("----- Colección Personal -----");
        if (creador.getTipo() == TipoUsuario.COLECCIONISTA) {

            for (int i = 0; i < creador.getListaObjetos().size(); i++) {
                out.println("Objeto #" + (i + 1) + ": " + creador.getListaObjetos().get(i).getNombreObjeto());
            }

            out.println("Seleccione el objeto a subastar: ");
            int opcionObjeto = Integer.parseInt(in.readLine());
            Objeto objetoSeleccionado = creador.getListaObjetos().get(opcionObjeto - 1);

            out.println("----- Configuración de la subasta ---------");

            out.println("Fecha y hora de vencimiento de la subasta (AAAA-MM-DDTHH:MM): ");
            fechaVencimiento = LocalDateTime.parse(in.readLine());
            out.println("Ingrese el precio minimo de la subasta");
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
                out.println("Ingrese el precio minimo de la subasta");
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

        for (int i = 0; i < service.listarSubastas().size(); i++) { // recorre el array par mostrar las subastas

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

            out.println("Precio minimo en dolares: " + service.listarSubastas().get(i).getPrecioMinimo());
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
                out.println("Correo Electronico: " + service.listarUsuarios().get(i).getCorreoElectronico());
                out.println("Tipo: " + service.listarUsuarios().get(i).getTipo());
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

                        out.println("Su oferta debe ser mayor al valor actual minimo");
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

        for (int u = 0; u < service.listarOfertas().size(); u++) {

            for (int i = 0; i < service.listarSubastas().size(); i++) {

                service.subastaVencio(service.listarSubastas().get(i));

                for (int j = 0; j < service.listarSubastas().get(i).getListaOfertas().size(); j++) {

                    out.println("Ofertante: " + service.listarSubastas().get(i).getListaOfertas().get(j).getColeccionista().getNombre());
                    out.println("Objeto: " + service.listarSubastas().get(i).getListaObjetos().get(0).getNombreObjeto());
                    out.println("Precio: " + service.listarSubastas().get(i).getListaOfertas().get(j).getprecioOferta());
                }
            }
            out.println("Estado de la subasta: " + service.listarSubastas().get(u).getEstado());
            out.println("Tiempo restante de la subasta: " + service.listarSubastas().get(u).calcularTiempoRestante());
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
            out.println("Correo Electronico: " + service.listarUsuarios().get(i).getCorreoElectronico());
            out.println("Tipo: " + service.listarUsuarios().get(i).getTipo());
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

                for (int j = 0; j < service.listarSubastas().get(i).getListaObjetos().size(); j++) { // ciclo for que recorre la lista de los objetos

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
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {

        mostrarMenu();

    }

}