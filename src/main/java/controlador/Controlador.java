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
        admiOperador.agregarOperador("Laura", "Mendoza", Turno.MATUTINO, "l.mendoza@ecu911.gob.ec");
        Operador opInicial = admiOperador.buscarOperador("OP-01");

        admiCiudadano.agregarCiudadano("0912345678", "Marcos Andres", "Villacis Torres");
        Ciudadano ciudInicial = admiCiudadano.buscarCiudadano("0912345678");

        LlamadaEmergencia llamadaInicial = new LlamadaEmergencia(
                TipoEmergencia.TRANSITO,
                "Guayaquil",
                "Urdesa",
                "Colision leve sin heridos en avenida principal.",
                ciudInicial,
                opInicial
        );
        admiLlamadas.registrarLlamada(llamadaInicial);

        NotaSeguimiento notaInicial = new NotaSeguimiento(
                "Se asigna unidad de control de transito al sector.",
                llamadaInicial,
                opInicial
        );
        admiNotas.agregarNota(notaInicial);
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
        ArrayList<LlamadaEmergencia> lista= admiLlamadas.listaLlamadas();
        if (lista.isEmpty()) {
            vista.mostrarMensaje("No hay llamadas registradas.");
        } else {
            vista.mostrarMensaje("Se encontraron " + lista.size() + " llamadas registradas.");
            vista.mostrarMensaje("CODIGO | FECHA Y HORA | CIUDADANO | TIPO | CIUDAD | ESTADO");
            for (int i = lista.size() - 1; i >= 0; i--) {
                LlamadaEmergencia ll = lista.get(i);
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
        ArrayList<Operador> listaOp = admiOperador.listaOperadores();
        if (listaOp.isEmpty()) {
            vista.mostrarMensaje("No hay operadores registrados. Registre uno primero en la opcion 2.");
            vista.pausarEnter("Presione ENTER para regresar...");
            return;
        }

        vista.mostrarMensaje("Seleccione el operador que atiende la llamada:");
        for (int i = 0; i < listaOp.size(); i++) {
            Operador o = listaOp.get(i);
            vista.mostrarMensaje("[" + (i + 1) + "] " + o.getNombreCompleto() + " (" + o.getCodigoOp() + ")");
        }
        int idxOp = vista.leerEnteroEnRango("Seleccione el numero de operador: ", 1, listaOp.size()) - 1;
        Operador operadorSeleccionado = listaOp.get(idxOp);

        vista.mostrarMensaje("\nDATOS DEL CIUDADANO");
        String nombres = vista.leerTextoObligatorio("1. Nombres: ", "Nombres");
        String apellidos = vista.leerTextoObligatorio("2. Apellidos: ", "Apellidos");
        String cedula = vista.leerCedulaValida("3. Cedula de identidad (10 digitos sin guiones): ");

        Ciudadano ciudadano = admiCiudadano.buscarCiudadano(cedula);
        if (ciudadano != null) {
            vista.mostrarMensaje("Ciudadano ya registrado en el sistema: " + ciudadano.getNombreCompleto()
                    + ". Se utilizaran los datos existentes.");
        } else {
            admiCiudadano.agregarCiudadano(cedula, nombres, apellidos);
            ciudadano = admiCiudadano.buscarCiudadano(cedula);
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
        admiLlamadas.registrarLlamada(nuevaLlamada);

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
        for (LlamadaEmergencia ll : admiLlamadas.listaLlamadas()) {
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
        LlamadaEmergencia objetivo = admiLlamadas.buscarLlamada(codigo);
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

        for (LlamadaEmergencia ll : admiLlamadas.listaLlamadas()) {
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
            if (admiLlamadas.anularLlamada(seleccionada.getCodigoLlamada())) {
                vista.mostrarMensaje("Exito! La llamada " + seleccionada.getCodigoLlamada()
                        + " ha sido anulada del sistema.");
            }
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
            for (Operador op : admiOperador.listaOperadores()) {
                int atendidas = admiLlamadas.contadorLlamadasAtendidas(op);
                vista.mostrarMensaje(
                        op.getCodigoOp() + " | "
                        + op.getNombreCompleto() + " | "
                        + op.getTurno() + " | "
                        + atendidas
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

                admiOperador.agregarOperador(nombres, apellidos, turno, email);
                vista.mostrarMensaje("Exito! El operador " + nombres + " " + apellidos + " ha sido registrado.");
                vista.pausarEnter("Presione ENTER para actualizar y volver a Administrar operadores...");
            } else if (accion == 2) {
                vista.mostrarEncabezado("[Administrar operadores > Eliminar operador]");
                String codigo = vista.leerTextoObligatorio("> Ingrese el codigo del operador que desea eliminar: ", "Codigo").toUpperCase();
                Operador target = admiOperador.buscarOperador(codigo);
                if (target == null) {
                    vista.mostrarMensaje("No se encontro un operador con codigo " + codigo + ".");
                } else if (admiLlamadas.tieneLlamadasRegistradas(target)) {
                    vista.mostrarMensaje("No se puede eliminar! El operador " + target.getNombreCompleto()
                            + " tiene llamadas registradas.");
                } else if (vista.confirmarSN("Esta seguro de que desea eliminar a " + target.getNombreCompleto() + "?")) {
                    if (admiOperador.eliminarOperador(codigo)) {
                        vista.mostrarMensaje("Exito! El registro del operador ha sido removido.");
                    } else {
                        vista.mostrarMensaje("No se pudo eliminar el operador.");
                    }
                } else {
                    vista.mostrarMensaje("Eliminacion cancelada.");
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
           for (Ciudadano c : admiCiudadano.listaCiudadanos()) {
                int total = admiLlamadas.historialLlamadas(c).size();
                vista.mostrarMensaje(c.getCedula() + " | " + c.getNombreCompleto() + " | " + total);
            }

            int accion = vista.mostrarMenuCiudadanos();
            if (accion == 1) {
                String cedula = vista.leerCedulaValida("> Ingrese la cedula del ciudadano (10 digitos): ");
                Ciudadano c = admiCiudadano.buscarCiudadano(cedula);
                if (c == null) {
                    vista.mostrarMensaje("No existe un ciudadano registrado con la cedula " + cedula + ".");
                } else {
                    vista.mostrarMensaje("\nHISTORIAL DE LLAMADAS " + c.getNombreCompleto() + " (C.I. " + c.getCedula() + ")");
                    vista.mostrarMensaje("CODIGO | FECHA Y HORA | TIPO | BARRIO | ESTADO");
                    for (LlamadaEmergencia ll : admiLlamadas.historialLlamadas(c)) {
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
        LlamadaEmergencia llamada = admiLlamadas.buscarLlamada(codigoLl);
        if (llamada == null) {
            vista.mostrarMensaje("No se encontro ninguna llamada con el codigo " + codigoLl + ".");
            vista.pausarEnter("Presione ENTER para regresar al Menu Principal...");
            return;
        }

       boolean volver = false;
        while (!volver) {
            vista.mostrarMensaje("\nNotas de la llamada " + codigoLl + " (Ordenadas desde la mas reciente):");
            vista.mostrarMensaje("FECHA Y HORA | OPERADOR | NOTA");
            for (NotaSeguimiento n : admiNotas.notaDeLlamada(llamada)) {
                String nomOp = (n.getOperador() != null) ? n.getOperador().getNombreCompleto() : "N/A";
                String fechaStr = (n.getFechaHora() != null) ? n.getFechaHora().format(fmtFechaHora) : "N/A";
                vista.mostrarMensaje(fechaStr + " | " + nomOp + " | " + n.getNota());
            }

            int accion = vista.mostrarMenuNotas();
            if (accion == 1) {
                ArrayList<Operador> listaOp = admiOperador.listaOperadores();
                if (listaOp.isEmpty()) {
                    vista.mostrarMensaje("No hay operadores disponibles.");
                    continue;
                }
                vista.mostrarMensaje("\n[Notas de seguimiento > Anadir nota]");
                vista.mostrarMensaje("Seleccione el operador que registra la nota:");
                for (int i = 0; i < listaOp.size(); i++) {
                    vista.mostrarMensaje("[" + (i + 1) + "] " + listaOp.get(i).getNombreCompleto());
                }
                int idxOp = vista.leerEnteroEnRango("Seleccione el numero de operador: ", 1, listaOp.size()) - 1;
                Operador op = listaOp.get(idxOp);

                String fechaHoraNota = LocalDateTime.now().format(fmtFechaHora);
                vista.mostrarMensaje("Fecha y hora actuales del registro: " + fechaHoraNota);
                String textoNota = vista.leerTextoObligatorio("> Ingrese el texto de la nota: ", "Texto de la nota");

                NotaSeguimiento nuevaNota = new NotaSeguimiento(textoNota, llamada, op);
                admiNotas.agregarNota(nuevaNota);

                String fechaGuardada = (nuevaNota.getFechaHora() != null)
                        ? nuevaNota.getFechaHora().format(fmtFechaHora)
                        : fechaHoraNota;

                vista.mostrarMensaje("GUARDANDO NOTA...");
                vista.mostrarMensaje("Exito! La nota ha sido guardada en la llamada " + codigoLl + ":");
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
}