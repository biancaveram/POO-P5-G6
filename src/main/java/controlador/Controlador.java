/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package controlador;

import administradores.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import modelo.*;
import vista.Vista;

/**
 * Controlador principal del Sistema de Registro de Llamadas ECU 911.
 * Coordina la Vista, los administradores en memoria y los modelos del dominio.
 */
public class Controlador {

    private final Vista vista;
    private final AdmiOperador admiOperador;
    private final AdmiCiudadano admiCiudadano;
    private final AdmiLlamadasE admiLlamadas;
    private final AdmiNotas admiNotas;

    private final List<Operador> operadores = new ArrayList<>();
    private final List<Ciudadano> ciudadanos = new ArrayList<>();
    private final List<LlamadaEmergencia> llamadas = new ArrayList<>();
    private final List<NotaSeguimiento> notas = new ArrayList<>();

    private final DateTimeFormatter fmtFechaHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Construye el controlador e inicializa los componentes del sistema.
     */
    public Controlador() {
        this.vista = new Vista();
        this.admiOperador = new AdmiOperador();
        this.admiCiudadano = new AdmiCiudadano();
        this.admiLlamadas = new AdmiLlamadasE();
        this.admiNotas = new AdmiNotas();
        cargarDatosIniciales();
    }

    /**
     * Punto de entrada de la aplicacion ECU 911.
     */
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        controlador.iniciar();
    }

    /**
     * Carga al inicio 1 operador, 1 ciudadano, 1 llamada y 1 nota de seguimiento.
     */
    private void cargarDatosIniciales() {
        Operador opInicial = new Operador(
                "Laura",
                "Mendoza",
                Turno.MATUTINO,
                "l.mendoza@ecu911.gob.ec"
        );
        operadores.add(opInicial);

        Ciudadano ciudInicial = new Ciudadano("0912345678", "Marcos Andres", "Villacis Torres");
        ciudadanos.add(ciudInicial);

        LlamadaEmergencia llamadaInicial = new LlamadaEmergencia(
                TipoEmergencia.TRANSITO,
                "Guayaquil",
                "Urdesa",
                "Colision leve sin heridos en avenida principal.",
                ciudInicial,
                opInicial
        );
        llamadas.add(llamadaInicial);

        NotaSeguimiento notaInicial = new NotaSeguimiento(
                "Se asigna unidad de control de transito al sector.",
                llamadaInicial,
                opInicial
        );
        notas.add(notaInicial);
    }

    /**
     * Ejecuta el bucle principal del sistema.
     */
    public void iniciar() {
        boolean ejecutando = true;
        while (ejecutando) {
            int opcion = vista.mostrarMenuPrincipal();
            switch (opcion) {
                case 1 -> gestionLlamadas();
                case 2 -> gestionOperadores();
                case 3 -> gestionCiudadanos();
                case 4 -> gestionNotas();
                case 5 -> {
                    vista.mostrarMensaje("\nSaliendo del Sistema ECU 911. ¡Hasta pronto!");
                    ejecutando = false;
                }
                default -> vista.mostrarMensaje("Opcion no valida.");
            }
        }
        vista.cerrar();
    }

    private void gestionLlamadas() {
        boolean volver = false;
        while (!volver) {
            int op = vista.mostrarMenuLlamadas();
            switch (op) {
                case 1 -> listarLlamadasRecientes();
                case 2 -> registrarLlamada();
                case 3 -> actualizarEstadoLlamada();
                case 4 -> anularLlamada();
                case 5 -> volver = true;
                default -> { }
            }
        }
    }

    private void listarLlamadasRecientes() {
        vista.mostrarEncabezado("[Menu Principal > 1. Llamadas > 1.1. Lista de llamadas]");
        if (llamadas.isEmpty()) {
            vista.mostrarMensaje("No hay llamadas registradas.");
        } else {
            vista.mostrarMensaje("Se encontraron " + llamadas.size() + " llamadas registradas.");
            vista.mostrarMensaje("CODIGO | FECHA Y HORA | CIUDADANO | TIPO | CIUDAD | ESTADO");
            for (int i = llamadas.size() - 1; i >= 0; i--) {
                LlamadaEmergencia ll = llamadas.get(i);
                String nomCiudadano = (ll.getCiudadano() != null) ? ll.getCiudadano().getNombreCompleto() : "N/A";
                String fechaStr = (ll.getFechaHora() != null) ? ll.getFechaHora().format(fmtFechaHora) : "N/A";
                vista.mostrarMensaje(
                        ll.getCodigoLlamada() + " | "
                        + fechaStr + " | "
                        + nomCiudadano + " | "
                        + ll.getTipoEmergencia() + " | "
                        + ll.getCiudad() + " | "
                        + ll.getEstado()
                );
            }
        }
        vista.pausarEnter("Presione ENTER para regresar al Menu de Llamadas...");
    }

    private void registrarLlamada() {
        vista.mostrarEncabezado("[Menu Principal > 1. Llamadas > 1.2. Registrar llamada]");
        if (operadores.isEmpty()) {
            vista.mostrarMensaje("No hay operadores registrados. Registre uno primero en la opcion 2.");
            vista.pausarEnter("Presione ENTER para regresar...");
            return;
        }

        vista.mostrarMensaje("Seleccione el operador que atiende la llamada:");
        for (int i = 0; i < operadores.size(); i++) {
            Operador o = operadores.get(i);
            vista.mostrarMensaje("[" + (i + 1) + "] " + o.getNombreCompleto() + " (" + o.getCodigoOp() + ")");
        }
        int idxOp = vista.leerEnteroEnRango("Seleccione el numero de operador: ", 1, operadores.size()) - 1;
        Operador operadorSeleccionado = operadores.get(idxOp);

        vista.mostrarMensaje("\nDATOS DEL CIUDADANO");
        String nombres = vista.leerTextoObligatorio("1. Nombres: ", "Nombres");
        String apellidos = vista.leerTextoObligatorio("2. Apellidos: ", "Apellidos");
        String cedula = vista.leerCedulaValida("3. Cedula de identidad (10 digitos sin guiones): ");

        Ciudadano ciudadano = buscarCiudadanoPorCedula(cedula);
        if (ciudadano != null) {
            vista.mostrarMensaje("Ciudadano ya registrado en el sistema: " + ciudadano.getNombreCompleto()
                    + ". Se utilizaran los datos existentes.");
        } else {
            ciudadano = new Ciudadano(cedula, nombres, apellidos);
            ciudadanos.add(ciudadano);
        }

        vista.mostrarMensaje("\nDATOS DE LA EMERGENCIA");
        vista.mostrarMensaje("4. Tipo de emergencia:");
        vista.mostrarMensaje("[1] Energia");
        vista.mostrarMensaje("[2] Civil");
        vista.mostrarMensaje("[3] Transito");
        vista.mostrarMensaje("[4] Medio ambiente");
        vista.mostrarMensaje("[5] Otros");
        int opTipo = vista.leerEnteroEnRango("Seleccione una opcion (1-5): ", 1, 5);
        TipoEmergencia tipo = mapearTipoEmergencia(opTipo);

        String ciudad = vista.leerTextoObligatorio("5. Ciudad: ", "Ciudad");
        String barrio = vista.leerTextoObligatorio("6. Barrio: ", "Barrio");
        String detalle = vista.leerTextoObligatorio("7. Detalle: ", "Detalle");

        LlamadaEmergencia nuevaLlamada = new LlamadaEmergencia(
                tipo,
                ciudad,
                barrio,
                detalle,
                ciudadano,
                operadorSeleccionado
        );
        llamadas.add(nuevaLlamada);

        String fechaHoraStr = (nuevaLlamada.getFechaHora() != null)
                ? nuevaLlamada.getFechaHora().format(fmtFechaHora)
                : LocalDateTime.now().format(fmtFechaHora);

        vista.mostrarMensaje("\nPROCESANDO REGISTRO...");
        vista.mostrarMensaje("¡Exito! La llamada ha sido registrada correctamente:");
        vista.mostrarMensaje("-> Codigo: " + nuevaLlamada.getCodigoLlamada());
        vista.mostrarMensaje("-> Fecha y hora: " + fechaHoraStr);
        vista.mostrarMensaje("-> Ciudadano: " + ciudadano.getNombreCompleto() + " (C.I. " + ciudadano.getCedula() + ")");
        vista.mostrarMensaje("-> Emergencia: " + tipo + " | " + ciudad + ", " + barrio);
        vista.mostrarMensaje("-> Operador: " + operadorSeleccionado.getNombreCompleto());
        vista.mostrarMensaje("-> Estado: " + nuevaLlamada.getEstado());
        vista.pausarEnter("Presione ENTER para regresar al Menu de Llamadas...");
    }

    private void actualizarEstadoLlamada() {
        vista.mostrarEncabezado("[Menu Principal > 1. Llamadas > 1.3. Actualizar estado]");
        List<LlamadaEmergencia> pendientes = new ArrayList<>();
        for (LlamadaEmergencia ll : llamadas) {
            if (ll.getEstado() != Estado.ATENDIDA) {
                pendientes.add(ll);
            }
        }

        if (pendientes.isEmpty()) {
            vista.mostrarMensaje("No hay llamadas pendientes por actualizar.");
            vista.pausarEnter("Presione ENTER para regresar al Menu de Llamadas...");
            return;
        }

        vista.mostrarMensaje("Llamadas pendientes:");
        vista.mostrarMensaje("CODIGO | CIUDADANO | TIPO | ESTADO ACTUAL");
        for (LlamadaEmergencia ll : pendientes) {
            String ciud = (ll.getCiudadano() != null) ? ll.getCiudadano().getNombreCompleto() : "N/A";
            vista.mostrarMensaje(ll.getCodigoLlamada() + " | " + ciud + " | " + ll.getTipoEmergencia() + " | " + ll.getEstado());
        }

        String codigo = vista.leerTextoObligatorio("> Ingrese el codigo de la llamada: ", "Codigo de llamada").toUpperCase();
        LlamadaEmergencia objetivo = buscarLlamadaPorCodigo(codigo);
        if (objetivo == null || objetivo.getEstado() == Estado.ATENDIDA) {
            vista.mostrarMensaje("No se encontro una llamada pendiente con el codigo " + codigo + ".");
            vista.pausarEnter("Presione ENTER para regresar...");
            return;
        }

        Estado actual = objetivo.getEstado();
        Estado siguiente = (actual == Estado.REGISTRADA) ? Estado.EN_ATENCION : Estado.ATENDIDA;

        vista.mostrarMensaje("El estado actual es \"" + actual + "\". El nuevo estado sera \"" + siguiente + "\".");
        if (vista.confirmarSN("¿Confirmar el cambio?")) {
            objetivo.actualizarEstado();
            vista.mostrarMensaje("¡Exito! La llamada " + objetivo.getCodigoLlamada() + " ahora esta \"" + objetivo.getEstado() + "\".");
        } else {
            vista.mostrarMensaje("Cambio cancelado.");
        }
        vista.pausarEnter("Presione ENTER para regresar al Menu de Llamadas...");
    }

    private void anularLlamada() {
        vista.mostrarEncabezado("[Menu Principal > 1. Llamadas > 1.4. Anular llamada]");
        List<LlamadaEmergencia> anulables = new ArrayList<>();

        for (LlamadaEmergencia ll : llamadas) {
            if (ll.puedeAnularse()) {
                anulables.add(ll);
            }
        }

        if (anulables.isEmpty()) {
            vista.mostrarMensaje("No hay llamadas en estado REGISTRADA registradas hoy para anular.");
            vista.pausarEnter("Presione ENTER para regresar al Menu de Llamadas...");
            return;
        }

        vista.mostrarMensaje("Llamadas que se pueden anular (Registradas hoy):");
        vista.mostrarMensaje("CODIGO | FECHA Y HORA | CIUDADANO | TIPO");
        for (LlamadaEmergencia ll : anulables) {
            String ciud = (ll.getCiudadano() != null) ? ll.getCiudadano().getNombreCompleto() : "N/A";
            String fechaStr = (ll.getFechaHora() != null) ? ll.getFechaHora().format(fmtFechaHora) : "N/A";
            vista.mostrarMensaje(ll.getCodigoLlamada() + " | " + fechaStr + " | " + ciud + " | " + ll.getTipoEmergencia());
        }

        String codigo = vista.leerTextoObligatorio("> Ingrese el codigo de la llamada que desea anular: ", "Codigo").toUpperCase();
        LlamadaEmergencia seleccionada = null;
        for (LlamadaEmergencia ll : anulables) {
            if (ll.getCodigoLlamada().equalsIgnoreCase(codigo)) {
                seleccionada = ll;
                break;
            }
        }

        if (seleccionada == null) {
            vista.mostrarMensaje("El codigo ingresado no corresponde a una llamada anulable de hoy.");
            vista.pausarEnter("Presione ENTER para regresar...");
            return;
        }

        vista.mostrarMensaje("¡ADVERTENCIA: ESTA ACCION NO SE PUEDE DESHACER!");
        vista.mostrarMensaje("[Llamada seleccionada]: " + seleccionada.getCodigoLlamada()
                + " (" + seleccionada.getTipoEmergencia() + " en " + seleccionada.getCiudad() + ")");

        if (vista.confirmarSN("¿Confirmar anulacion?")) {
            llamadas.remove(seleccionada);
            vista.mostrarMensaje("¡Exito! La llamada " + seleccionada.getCodigoLlamada() + " ha sido anulada del sistema.");
        } else {
            vista.mostrarMensaje("Anulacion cancelada.");
        }
        vista.pausarEnter("Presione ENTER para regresar al Menu de Llamadas...");
    }

    private void gestionOperadores() {
        boolean volver = false;
        while (!volver) {
            vista.mostrarEncabezado("[Menu Principal > 2. Administrar operadores]");
            vista.mostrarMensaje("Listado actual de operadores registrados:");
            vista.mostrarMensaje("CODIGO | NOMBRE COMPLETO | TURNO | LLAMADAS ATENDIDAS");
            for (Operador op : operadores) {
                int cantLlamadas = contarLlamadasDeOperador(op);
                vista.mostrarMensaje(
                        op.getCodigoOp() + " | "
                        + op.getNombreCompleto() + " | "
                        + op.getTurno() + " | "
                        + cantLlamadas
                );
            }

            int accion = vista.mostrarMenuOperadores();
            if (accion == 1) {
                vista.mostrarEncabezado("[Administrar operadores > Agregar operador]");
                String nombres = vista.leerTextoObligatorio("1. Nombres: ", "Nombres");
                String apellidos = vista.leerTextoObligatorio("2. Apellidos: ", "Apellidos");
                vista.mostrarMensaje("3. Turno:\n[1] Matutino\n[2] Vespertino\n[3] Nocturno");
                int opTurno = vista.leerEnteroEnRango("Seleccione una opcion (1-3): ", 1, 3);
                Turno turno = (opTurno == 1) ? Turno.MATUTINO : (opTurno == 2) ? Turno.VESPERTINO : Turno.NOCTURNO;
                String email = vista.leerTextoOpcional("4. Email (opcional, ENTER para omitir): ");

                Operador nuevo = new Operador(nombres, apellidos, turno, email);
                operadores.add(nuevo);
                vista.mostrarMensaje("¡Exito! El operador " + nuevo.getNombreCompleto()
                        + " ha sido registrado con el codigo " + nuevo.getCodigoOp() + ".");
                vista.pausarEnter("Presione ENTER para actualizar y volver a Administrar operadores...");
            } else if (accion == 2) {
                vista.mostrarEncabezado("[Administrar operadores > Eliminar operador]");
                String codigo = vista.leerTextoObligatorio("> Ingrese el codigo del operador que desea eliminar: ", "Codigo").toUpperCase();
                Operador target = null;
                for (Operador op : operadores) {
                    if (op.getCodigoOp().equalsIgnoreCase(codigo)) {
                        target = op;
                        break;
                    }
                }
                if (target == null) {
                    vista.mostrarMensaje("No se encontro un operador con codigo " + codigo + ".");
                } else {
                    int cant = contarLlamadasDeOperador(target);
                    if (cant > 0) {
                        vista.mostrarMensaje("¡No se puede eliminar! El operador " + target.getNombreCompleto()
                                + " tiene " + cant + " llamadas registradas.");
                    } else if (vista.confirmarSN("¿Esta seguro de que desea eliminar a " + target.getNombreCompleto() + "?")) {
                        operadores.remove(target);
                        vista.mostrarMensaje("¡Exito! El registro del operador ha sido removido.");
                    } else {
                        vista.mostrarMensaje("Eliminacion cancelada.");
                    }
                }
                vista.pausarEnter("Presione ENTER para actualizar y volver a Administrar operadores...");
            } else {
                volver = true;
            }
        }
    }

    private void gestionCiudadanos() {
        boolean volver = false;
        while (!volver) {
            vista.mostrarEncabezado("[Menu Principal > 3. Administrar ciudadanos]");
            vista.mostrarMensaje("Listado de ciudadanos registrados:");
            vista.mostrarMensaje("CEDULA | NOMBRE COMPLETO | TOTAL LLAMADAS");
            for (Ciudadano c : ciudadanos) {
                vista.mostrarMensaje(c.getCedula() + " | " + c.getNombreCompleto() + " | " + contarLlamadasDeCiudadano(c));
            }

            int accion = vista.mostrarMenuCiudadanos();
            if (accion == 1) {
                String cedula = vista.leerCedulaValida("> Ingrese la cedula del ciudadano (10 digitos): ");
                Ciudadano c = buscarCiudadanoPorCedula(cedula);
                if (c == null) {
                    vista.mostrarMensaje("No existe un ciudadano registrado con la cedula " + cedula + ".");
                } else {
                    vista.mostrarMensaje("\nHISTORIAL DE LLAMADAS " + c.getNombreCompleto() + " (C.I. " + c.getCedula() + ")");
                    vista.mostrarMensaje("CODIGO | FECHA Y HORA | TIPO | BARRIO | ESTADO");
                    for (LlamadaEmergencia ll : llamadas) {
                        if (ll.getCiudadano() != null && c.getCedula().equals(ll.getCiudadano().getCedula())) {
                            String fechaStr = (ll.getFechaHora() != null) ? ll.getFechaHora().format(fmtFechaHora) : "N/A";
                            vista.mostrarMensaje(
                                    ll.getCodigoLlamada() + " | "
                                    + fechaStr + " | "
                                    + ll.getTipoEmergencia() + " | "
                                    + ll.getBarrio() + " | "
                                    + ll.getEstado()
                            );
                        }
                    }
                }
                vista.pausarEnter("Presione ENTER para regresar a Administrar ciudadanos...");
            } else {
                volver = true;
            }
        }
    }

    private void gestionNotas() {
        vista.mostrarEncabezado("[Menu Principal > 4. Notas de seguimiento]");
        String codigoLl = vista.leerTextoObligatorio("> Ingrese el codigo de la llamada: ", "Codigo de llamada").toUpperCase();
        LlamadaEmergencia llamada = buscarLlamadaPorCodigo(codigoLl);
        if (llamada == null) {
            vista.mostrarMensaje("No se encontro ninguna llamada con el codigo " + codigoLl + ".");
            vista.pausarEnter("Presione ENTER para regresar al Menu Principal...");
            return;
        }

        boolean volver = false;
        while (!volver) {
            vista.mostrarMensaje("\nNotas de la llamada " + codigoLl + " (Ordenadas desde la mas reciente):");
            vista.mostrarMensaje("FECHA Y HORA | OPERADOR | NOTA");
            List<NotaSeguimiento> notasLlamada = new ArrayList<>();
            for (NotaSeguimiento n : notas) {
                if (n.getLlamada() != null && codigoLl.equalsIgnoreCase(n.getLlamada().getCodigoLlamada())) {
                    notasLlamada.add(n);
                }
            }
            for (int i = notasLlamada.size() - 1; i >= 0; i--) {
                NotaSeguimiento n = notasLlamada.get(i);
                String nomOp = (n.getOperador() != null) ? n.getOperador().getNombreCompleto() : "N/A";
                String fechaStr = (n.getFechaHora() != null) ? n.getFechaHora().format(fmtFechaHora) : "N/A";
                vista.mostrarMensaje(fechaStr + " | " + nomOp + " | " + n.getNota());
            }

            int accion = vista.mostrarMenuNotas();
            if (accion == 1) {
                if (operadores.isEmpty()) {
                    vista.mostrarMensaje("No hay operadores disponibles.");
                    continue;
                }
                vista.mostrarMensaje("\n[Notas de seguimiento > Anadir nota]");
                vista.mostrarMensaje("Seleccione el operador que registra la nota:");
                for (int i = 0; i < operadores.size(); i++) {
                    vista.mostrarMensaje("[" + (i + 1) + "] " + operadores.get(i).getNombreCompleto());
                }
                int idxOp = vista.leerEnteroEnRango("Seleccione el numero de operador: ", 1, operadores.size()) - 1;
                Operador op = operadores.get(idxOp);

                String fechaHoraNota = LocalDateTime.now().format(fmtFechaHora);
                vista.mostrarMensaje("Fecha y hora actuales del registro: " + fechaHoraNota);
                String textoNota = vista.leerTextoObligatorio("> Ingrese el texto de la nota: ", "Texto de la nota");

                NotaSeguimiento nuevaNota = new NotaSeguimiento(textoNota, llamada, op);
                notas.add(nuevaNota);

                String fechaGuardada = (nuevaNota.getFechaHora() != null)
                        ? nuevaNota.getFechaHora().format(fmtFechaHora)
                        : fechaHoraNota;

                vista.mostrarMensaje("GUARDANDO NOTA...");
                vista.mostrarMensaje("¡Exito! La nota ha sido guardada en la llamada " + codigoLl + ":");
                vista.mostrarMensaje("-> Registro: " + fechaGuardada);
                vista.mostrarMensaje("-> Operador: " + op.getNombreCompleto());
                vista.pausarEnter("Presione ENTER para actualizar y volver a Notas de seguimiento...");
            } else {
                volver = true;
            }
        }
    }

    private TipoEmergencia mapearTipoEmergencia(int op) {
        return switch (op) {
            case 1 -> TipoEmergencia.ENERGIA;
            case 2 -> TipoEmergencia.CIVIL;
            case 3 -> TipoEmergencia.TRANSITO;
            case 4 -> TipoEmergencia.MEDIO_AMBIENTE;
            default -> TipoEmergencia.OTROS;
        };
    }

    private Ciudadano buscarCiudadanoPorCedula(String cedula) {
        for (Ciudadano c : ciudadanos) {
            if (cedula.equals(c.getCedula())) {
                return c;
            }
        }
        return null;
    }

    private LlamadaEmergencia buscarLlamadaPorCodigo(String codigo) {
        for (LlamadaEmergencia ll : llamadas) {
            if (codigo.equalsIgnoreCase(ll.getCodigoLlamada())) {
                return ll;
            }
        }
        return null;
    }

    private int contarLlamadasDeOperador(Operador op) {
        int total = 0;
        for (LlamadaEmergencia ll : llamadas) {
            if (ll.getOperador() != null && op.getCodigoOp().equalsIgnoreCase(ll.getOperador().getCodigoOp())) {
                total++;
            }
        }
        return total;
    }

    private int contarLlamadasDeCiudadano(Ciudadano c) {
        int total = 0;
        for (LlamadaEmergencia ll : llamadas) {
            if (ll.getCiudadano() != null && c.getCedula().equals(ll.getCiudadano().getCedula())) {
                total++;
            }
        }
        return total;
    }
}