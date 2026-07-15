package poliairlines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class VentanaPrincipal extends JFrame {

    private List<Vuelo> vuelosDisponibles;
    private JComboBox<Vuelo> comboVuelos;
    private JComboBox<Integer> comboPasajeros; // Nuevo: Para elegir la cantidad de personas

    private JToggleButton btnNacionales;
    private JToggleButton btnInternacionales;
    private ButtonGroup grupoTipoVuelo;

    private JLabel lblOrigen, lblDestino, lblFecha, lblPrecio;

    private JPanel panelMapaAsientos;

    // Nuevo: Ahora usamos una Lista para guardar varios asientos en lugar de uno solo
    private List<Asiento> asientosSeleccionados;

    private JTextField txtCedula, txtNombre, txtApellido, txtEmail, txtTelefono;
    private JTabbedPane pestanas;

    public VentanaPrincipal() {
        vuelosDisponibles = new ArrayList<>();
        asientosSeleccionados = new ArrayList<>();
        inicializarDatosCSV();
        cargarReservasPrevias();

        setTitle("Poli Airlines - Sistema de Reservas");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        pestanas = new JTabbedPane();

        // ==========================================
        // PESTAÑA 1: SELECCIÓN Y DETALLES DE VUELO
        // ==========================================
        JPanel panelVuelos = new JPanel(new BorderLayout(15, 15));
        panelVuelos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Aumentamos a 5 filas para acomodar el selector de cantidad de pasajeros
        JPanel panelSeleccionVuelo = new JPanel(new GridLayout(5, 1, 5, 8));

        panelSeleccionVuelo.add(new JLabel("1. Elija la categoría del vuelo:"));
        JPanel panelBotonesTipo = new JPanel(new GridLayout(1, 2, 10, 0));
        btnNacionales = new JToggleButton("Vuelos Nacionales");
        btnInternacionales = new JToggleButton("Vuelos Internacionales");

        grupoTipoVuelo = new ButtonGroup();
        grupoTipoVuelo.add(btnNacionales);
        grupoTipoVuelo.add(btnInternacionales);

        panelBotonesTipo.add(btnNacionales);
        panelBotonesTipo.add(btnInternacionales);
        panelSeleccionVuelo.add(panelBotonesTipo);

        comboVuelos = new JComboBox<>();
        panelSeleccionVuelo.add(comboVuelos);

        // Nuevo componente para elegir la cantidad de boletos
        panelSeleccionVuelo.add(new JLabel("2. Cantidad de pasajeros (Asientos a reservar):"));
        comboPasajeros = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        panelSeleccionVuelo.add(comboPasajeros);

        JPanel panelDetallesVuelo = new JPanel(new GridLayout(4, 2, 5, 10));
        panelDetallesVuelo.setBorder(BorderFactory.createTitledBorder("Detalles del Trayecto y Tarifa (Por persona)"));

        panelDetallesVuelo.add(new JLabel("Origen:"));
        lblOrigen = new JLabel("-");
        panelDetallesVuelo.add(lblOrigen);

        panelDetallesVuelo.add(new JLabel("Destino:"));
        lblDestino = new JLabel("-");
        panelDetallesVuelo.add(lblDestino);

        panelDetallesVuelo.add(new JLabel("Fecha y Hora:"));
        lblFecha = new JLabel("-");
        panelDetallesVuelo.add(lblFecha);

        panelDetallesVuelo.add(new JLabel("Precio Estimado:"));
        lblPrecio = new JLabel("-");
        panelDetallesVuelo.add(lblPrecio);

        panelVuelos.add(panelSeleccionVuelo, BorderLayout.NORTH);
        panelVuelos.add(panelDetallesVuelo, BorderLayout.CENTER);

        // ==========================================
        // PESTAÑA 2: SELECCIÓN DE ASIENTOS (VISUAL)
        // ==========================================
        JPanel panelAsientos = new JPanel(new BorderLayout(10, 10));
        panelAsientos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTituloAsientos = new JLabel("Mapa de Asientos (Verde = Libre, Rojo = Ocupado)", SwingConstants.CENTER);
        panelAsientos.add(lblTituloAsientos, BorderLayout.NORTH);

        panelMapaAsientos = new JPanel();
        panelAsientos.add(new JScrollPane(panelMapaAsientos), BorderLayout.CENTER);

        // ==========================================
        // PESTAÑA 3: DATOS DEL PASAJERO Y CONFIRMACIÓN
        // ==========================================
        JPanel panelReserva = new JPanel(new BorderLayout(10, 10));
        panelReserva.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 10));
        panelFormulario.add(new JLabel("Datos del Titular de la Reserva", SwingConstants.LEFT));
        panelFormulario.add(new JLabel("")); // Espacio vacío

        panelFormulario.add(new JLabel("Cédula / ID:"));
        txtCedula = new JTextField();
        panelFormulario.add(txtCedula);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        JButton btnReservar = new JButton("Confirmar y Registrar Reserva");
        btnReservar.setPreferredSize(new Dimension(100, 40));
        btnReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarReserva();
            }
        });

        panelReserva.add(panelFormulario, BorderLayout.CENTER);
        panelReserva.add(btnReservar, BorderLayout.SOUTH);

        pestanas.addTab("1. Selección de Vuelo", panelVuelos);
        pestanas.addTab("2. Selección de Asiento", panelAsientos);
        pestanas.addTab("3. Datos del Pasajero", panelReserva);

        add(pestanas);

        // Eventos
        ActionListener filtroTipoListener = e -> actualizarFiltroVuelos();
        btnNacionales.addActionListener(filtroTipoListener);
        btnInternacionales.addActionListener(filtroTipoListener);

        comboVuelos.addActionListener(e -> {
            actualizarDetallesVuelo();
            actualizarMapaAsientos();
        });

        // Si el usuario cambia la cantidad de pasajeros, reiniciamos el mapa de asientos
        comboPasajeros.addActionListener(e -> {
            actualizarDetallesVuelo();
            actualizarMapaAsientos();
        });

        actualizarFiltroVuelos();
    }

    private void actualizarFiltroVuelos() {
        comboVuelos.removeAllItems();

        if (btnNacionales.isSelected()) {
            for (Vuelo v : vuelosDisponibles) {
                if (v instanceof VueloNacional) {
                    comboVuelos.addItem(v);
                }
            }
        } else if (btnInternacionales.isSelected()) {
            for (Vuelo v : vuelosDisponibles) {
                if (v instanceof VueloInternacional) {
                    comboVuelos.addItem(v);
                }
            }
        }

        comboVuelos.setSelectedIndex(-1);
        actualizarDetallesVuelo();
        actualizarMapaAsientos();
    }

    private void actualizarDetallesVuelo() {
        Vuelo vueloSeleccionado = (Vuelo) comboVuelos.getSelectedItem();
        if (vueloSeleccionado != null) {
            lblOrigen.setText(vueloSeleccionado.getOrigen());
            lblDestino.setText(vueloSeleccionado.getDestino());

            java.time.format.DateTimeFormatter formatoVisual = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
            lblFecha.setText(vueloSeleccionado.getFechaHora().format(formatoVisual));

            // Calculamos el precio mínimo (Económica) multiplicado por los pasajeros
            int cantidadPasajeros = (Integer) comboPasajeros.getSelectedItem();
            double precioBaseMinimo = vueloSeleccionado.calcularPrecioBase() * cantidadPasajeros;

            // Aclaramos que es un precio "Desde"
            lblPrecio.setText("Desde $" + precioBaseMinimo + " (Económica)");
        } else {
            lblOrigen.setText("-");
            lblDestino.setText("-");
            lblFecha.setText("-");
            lblPrecio.setText("-");
        }
    }

    private void actualizarMapaAsientos() {
        panelMapaAsientos.removeAll();
        asientosSeleccionados.clear();

        Vuelo vueloSel = (Vuelo) comboVuelos.getSelectedItem();

        if (vueloSel != null) {
            List<Asiento> todosLosAsientos = vueloSel.getListaAsientos();

            // Usamos BoxLayout para apilar los paneles uno debajo del otro de forma vertical
            panelMapaAsientos.setLayout(new BoxLayout(panelMapaAsientos, BoxLayout.Y_AXIS));

            // Creamos los tres sub-paneles para cada sección
            JPanel panelPrimera = crearPanelSeccion("Primera Clase", 2);
            JPanel panelEjecutiva = crearPanelSeccion("Clase Ejecutiva", 3);
            JPanel panelEconomica = crearPanelSeccion("Clase Económica", 5);

            int index = 0;

            // Llenamos la Primera Clase
            for (int f = 0; f < 2; f++) {
                index = agregarFilaAsientos(panelPrimera, todosLosAsientos, index, vueloSel);
            }

            // Llenamos la Clase Ejecutiva
            for (int f = 0; f < 3; f++) {
                index = agregarFilaAsientos(panelEjecutiva, todosLosAsientos, index, vueloSel);
            }

            // Llenamos la Clase Económica
            for (int f = 0; f < 5; f++) {
                index = agregarFilaAsientos(panelEconomica, todosLosAsientos, index, vueloSel);
            }

            // Añadimos los sub-paneles al panel principal con un pequeño espacio entre ellos
            panelMapaAsientos.add(panelPrimera);
            panelMapaAsientos.add(Box.createRigidArea(new Dimension(0, 10)));
            panelMapaAsientos.add(panelEjecutiva);
            panelMapaAsientos.add(Box.createRigidArea(new Dimension(0, 10)));
            panelMapaAsientos.add(panelEconomica);
        }

        panelMapaAsientos.revalidate();
        panelMapaAsientos.repaint();
    }

    private JPanel crearPanelSeccion(String titulo, int filas) {
        JPanel panel = new JPanel(new GridLayout(filas, 5, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        return panel;
    }

    private int agregarFilaAsientos(JPanel panel, List<Asiento> lista, int index, Vuelo vueloSel) {
        panel.add(crearBotonAsiento(lista.get(index++), vueloSel));
        panel.add(crearBotonAsiento(lista.get(index++), vueloSel));
        panel.add(new JLabel(" ", SwingConstants.CENTER));
        panel.add(crearBotonAsiento(lista.get(index++), vueloSel));
        panel.add(crearBotonAsiento(lista.get(index++), vueloSel));
        return index;
    }

    // Método actualizado para controlar el límite de selecciones
    private JToggleButton crearBotonAsiento(Asiento asiento, Vuelo vueloSel) {
        // 1. Calculamos el precio de ESTE asiento antes de crear el botón
        double precioAsiento = vueloSel.calcularPrecioAsiento(asiento);

        // 2. Usamos etiquetas HTML para dividir el texto en dos líneas (Asiento arriba, Precio abajo)
        String textoBoton = "<html><center><b>" + asiento.getNumeroAsiento() + "</b><br>$" + precioAsiento + "</center></html>";

        JToggleButton btn = new JToggleButton(textoBoton);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (asiento.getEstaOcupado()) {
            btn.setEnabled(false);
            btn.setBackground(new Color(255, 120, 120)); // Rojo
            btn.setToolTipText("Ocupado");
        } else {
            btn.setBackground(new Color(150, 255, 150)); // Verde
            btn.setToolTipText("Clase: " + asiento.getClaseAsiento());

            btn.addItemListener(e -> {
                int cantidadPermitida = (Integer) comboPasajeros.getSelectedItem();

                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (asientosSeleccionados.size() < cantidadPermitida) {
                        btn.setBackground(new Color(100, 200, 255)); // Azul
                        asientosSeleccionados.add(asiento);
                    } else {
                        btn.setSelected(false);
                        JOptionPane.showMessageDialog(this,
                                "Solo puedes seleccionar " + cantidadPermitida + " asiento(s).",
                                "Límite alcanzado",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    btn.setBackground(new Color(150, 255, 150)); // Vuelve a verde
                    asientosSeleccionados.remove(asiento);
                }
            });
        }
        return btn;
    }

    private void procesarReserva() {
        try {
            if (txtCedula.getText().isEmpty() || txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty()) {
                throw new ReservaInvalidaException("Todos los campos personales son obligatorios.");
            }

            Vuelo vueloSel = (Vuelo) comboVuelos.getSelectedItem();
            int cantidadEsperada = (Integer) comboPasajeros.getSelectedItem();

            if (vueloSel == null) {
                throw new ReservaInvalidaException("Debe seleccionar un vuelo.");
            }

            if (asientosSeleccionados.size() != cantidadEsperada) {
                throw new ReservaInvalidaException("Debe seleccionar exactamente " + cantidadEsperada + " asiento(s) en el mapa.");
            }

            // ¡NUEVO! Volvemos a leer el CSV en tiempo real justo antes de procesar
            // para evitar que dos personas reserven a la vez (Control de concurrencia RNF-04)
            cargarReservasPrevias();

            Pasajero pasajero = new Pasajero(txtCedula.getText(), txtNombre.getText(), txtApellido.getText(), txtEmail.getText(), txtTelefono.getText());
            pasajero.registrarPasajero();

            double precioTotal = 0.0;
            StringBuilder resumenAsientos = new StringBuilder();

            for (Asiento asientoSel : asientosSeleccionados) {
                // Esta validación ahora está respaldada por la recarga del CSV
                if (asientoSel.getEstaOcupado()) {
                    throw new ReservaInvalidaException("¡Alguien más acaba de tomar el asiento " + asientoSel.getNumeroAsiento() + "!. Por favor elija otro.");
                }

                Reserva nuevaReserva = new Reserva();
                nuevaReserva.crearReserva(pasajero, vueloSel, asientoSel); // Si falla, salta directamente al catch
                nuevaReserva.confirmarPago();
                guardarReservaCSV(nuevaReserva);

                precioTotal += vueloSel.calcularPrecioAsiento(asientoSel);
                resumenAsientos.append(asientoSel.getNumeroAsiento()).append(" ");
            }

            JOptionPane.showMessageDialog(this,
                    "Reserva creada con éxito para " + cantidadEsperada + " pasajero(s).\n" +
                            "Asientos asignados: " + resumenAsientos.toString() + "\n" +
                            "Precio total a pagar: $" + precioTotal,
                    "Transacción Exitosa", JOptionPane.INFORMATION_MESSAGE);

            txtCedula.setText(""); txtNombre.setText(""); txtApellido.setText(""); txtEmail.setText(""); txtTelefono.setText("");

            grupoTipoVuelo.clearSelection();
            comboPasajeros.setSelectedIndex(0);
            actualizarFiltroVuelos();
            pestanas.setSelectedIndex(0);

        } catch (ReservaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, "Error de Reserva: " + ex.getMotivoError(), "Error", JOptionPane.ERROR_MESSAGE);
            // Si el asiento fue tomado, forzamos al mapa a redibujarse para que se ponga rojo
            actualizarMapaAsientos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarDatosCSV() {
        File fileNacional = new File("vuelosNacionales.csv");
        File fileInter = new File("vuelosInternacionales.csv");

        try {
            if (!fileNacional.exists()) {
                FileWriter fw = new FileWriter(fileNacional);
                fw.write("VN01,Quito,Guayaquil,2026-08-10T08:00,12.5,5.0\n");
                fw.write("VN02,Cuenca,Quito,2026-08-12T14:30,10.0,2.5\n");
                fw.close();
            }

            if (!fileInter.exists()) {
                FileWriter fw = new FileWriter(fileInter);
                fw.write("VI01,Quito,Bogota,2026-08-15T09:00,45.0\n");
                fw.write("VI02,Guayaquil,Miami,2026-08-20T22:00,60.0\n");
                fw.close();
            }

            BufferedReader br = new BufferedReader(new FileReader(fileNacional));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                vuelosDisponibles.add(new VueloNacional(datos[0], datos[1], datos[2], LocalDateTime.parse(datos[3]), Double.parseDouble(datos[4]), Double.parseDouble(datos[5])));
            }
            br.close();

            br = new BufferedReader(new FileReader(fileInter));
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                vuelosDisponibles.add(new VueloInternacional(datos[0], datos[1], datos[2], LocalDateTime.parse(datos[3]), Double.parseDouble(datos[4])));
            }
            br.close();

        } catch (IOException e) {
            System.out.println("Error manejando archivos de inicialización: " + e.getMessage());
        }
    }

    private void cargarReservasPrevias() {
        File fileReservas = new File("reservas.csv");
        if (!fileReservas.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(fileReservas))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                for (Vuelo v : vuelosDisponibles) {
                    if (v.getCodigoVuelo() != null && v.getCodigoVuelo().equals(datos[6])) {
                        Asiento a = v.buscarAsiento(datos[7]);
                        if (a != null) a.ocuparAsiento();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo reservas: " + e.getMessage());
        }
    }

    private void guardarReservaCSV(Reserva reserva) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reservas.csv", true))) {
            Pasajero p = reserva.getPasajeroAsociado();
            String linea = reserva.getCodigoReserva() + "," + p.getIdPasajero() + "," + p.getNombre() + "," +
                    p.getApellido() + "," + p.getEmail() + "," + p.getTelefono() + "," +
                    reserva.getVueloAsociado().getCodigoVuelo() + "," + reserva.getAsientoAsignado().getNumeroAsiento() + "," +
                    reserva.getEstadoReserva();
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la reserva en el CSV.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}