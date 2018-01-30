package es.cic.curso.grupo3.ejercicio027.frontend;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import es.cic.curso.grupo3.ejercicio027.domain.Evento;
import es.cic.curso.grupo3.ejercicio027.domain.Nivel;
import es.cic.curso.grupo3.ejercicio027.domain.Origen;
import es.cic.curso.grupo3.ejercicio027.domain.Tipo;
import es.cic.curso.grupo3.ejercicio027.domain.Usuario;
import es.cic.curso.grupo3.ejercicio027.service.EventoService;
import es.cic.curso.grupo3.ejercicio027.service.NivelService;
import es.cic.curso.grupo3.ejercicio027.service.OrigenService;
import es.cic.curso.grupo3.ejercicio027.service.TipoService;
import es.cic.curso.grupo3.ejercicio027.service.UsuarioService;

public class CapaEvento extends VerticalLayout{

	private static final long serialVersionUID = 511825084914845254L;
	
	private Controles controles = new Controles();
	private EventoForm eventoForm = new EventoForm();
	private ComboBox filtroTipos = eventoForm.getFiltroTipos();
	private ComboBox filtroNiveles = eventoForm.getFiltroNiveles();
	private ComboBox filtroUsuarios = eventoForm.getFiltroUsuarios();
	private ComboBox filtroOrigenes = eventoForm.getFiltroOrigenes();
	private Grid gridEventos = eventoForm.getGridEventos();
	private Label labelEvento = eventoForm.getLabelEvento();
	private ComboBox selectTipo = eventoForm.getSelectTipo();
	private ComboBox selectNivel = eventoForm.getSelectNivel();
	private ComboBox selectUsuario = eventoForm.getSelectUsuario();
	private ComboBox selectOrigen = eventoForm.getSelectOrigen();
	private TextField campoDescripcion = eventoForm.getCampoDescripcion();
	private Button botonCrear = eventoForm.getBotonCrear();
	private Button botonAceptar = eventoForm.getBotonAceptar();
	private Button botonModificar = eventoForm.getBotonModificar();
	private Button botonBorrar = eventoForm.getBotonBorrar();
	private Button botonCancelar = eventoForm.getBotonCancelar();
	private EventoService eventoService = ContextLoader.getCurrentWebApplicationContext().getBean(EventoService.class);
	private TipoService tipoService = ContextLoader.getCurrentWebApplicationContext().getBean(TipoService.class);
	private NivelService nivelService = ContextLoader.getCurrentWebApplicationContext().getBean(NivelService.class);
	private UsuarioService usuarioService = ContextLoader.getCurrentWebApplicationContext().getBean(UsuarioService.class);
	private OrigenService origenService = ContextLoader.getCurrentWebApplicationContext().getBean(OrigenService.class);
	private Long eventoId;
	private Evento evento;
	private Window ventanaConfirm;
	private Label labelConfirmar = controles.crearLabel("Confirmar borrado");
	private HorizontalLayout barraConfirmar = controles.crearCabecera(labelConfirmar, "barraazul");
	private Button botonAceptarBorrado = controles.crearButton("Aceptar");
	private Button botonCancelarBorrado = controles.crearButton("Cancelar");
	private HorizontalLayout barraBotonesVentanaConfirmar = controles.crearHorizontalLayoutSinEstilo();
	private VerticalLayout layoutVentana = controles.crearVerticalLayout();
	private FormLayout formLayout = eventoForm.getFormLayout();
	
	public CapaEvento() {
		super();
		barraBotonesVentanaConfirmar.setSpacing(true);
		barraBotonesVentanaConfirmar.addComponents(botonAceptarBorrado, botonCancelarBorrado);
		layoutVentana.addComponents(barraConfirmar, barraBotonesVentanaConfirmar);
	}

	@SuppressWarnings("static-access")
	public VerticalLayout creaCapa(MyUI containerMaestro){
		VerticalLayout layout = controles.crearVerticalLayout();
		
		filtroTipos.setFilteringMode(FilteringMode.CONTAINS);

				
		cargarGridEventos();
		cargarTipos();
		cargarNiveles();
		cargarUsuariosActivos();
		cargarOrigenesActivos();
		
		ocultarForm();
		setBotonesInicio();
		
		gridEventos.addSelectionListener(e -> {
			Evento eventoActual = null;
			if (!e.getSelected().isEmpty()) {
				mostrarForm();
				eventoActual = (Evento) e.getSelected().iterator().next();
				Usuario usuario = eventoActual.getUsuarioObj();
				Origen origen = eventoActual.getOrigenObj();
				if((!usuario.isAlta())||(!origen.isAlta())){
					Notification.show("Este evento no se puede modificar porque su usuario o el origen está dado de baja");
		 		}else{
		 			mostrarForm();
		 			setBotonesModificarBorrar();
					eventoId = eventoActual.getId();
					setEvento(eventoActual);
					selectTipo.setValue(eventoActual.getTipo());
					selectNivel.setValue(eventoActual.getNivel());
					selectUsuario.setValue(eventoActual.getUsuario());
					selectOrigen.setValue(eventoActual.getOrigen());
					campoDescripcion.setValue(eventoActual.getDescripcion());
					botonAceptar.setVisible(false);
					botonModificar.setVisible(true);
					botonBorrar.setVisible(true);
					labelEvento.setValue("Id = " + eventoActual.getId().toString());
				}
				
				gridEventos.setSizeFull();
				
			}
		});
		
		botonCrear.addClickListener(e -> accionNuevoEvento());
		
		botonAceptar.addClickListener(e -> {
			if (validarFormulario()) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter ltf = DateTimeFormatter.ofPattern("HH:mm:ss");
				
				LocalDateTime localDateTime = LocalDateTime.now();
				
				String nombreTipo = selectTipo.getValue().toString();
				Tipo tipo = tipoService.obtenerTipoPorNombre(nombreTipo);
				
				String nombreNivel = selectNivel.getValue().toString();
				Nivel nivel = nivelService.obtenerNivelPorNombre(nombreNivel);
				
				String nombreUsuario = selectUsuario.getValue().toString();
				Usuario usuario = usuarioService.obtenerUsuarioPorNombre(nombreUsuario);
				
				String nombreOrigen = selectOrigen.getValue().toString();
				Origen origen = origenService.obtenerOrigenPorNombre(nombreOrigen);
				
				String descripcion = campoDescripcion.getValue().toString();
			
				Date fecha = Date.valueOf(localDateTime.format(dtf));
				Time hora = Time.valueOf(localDateTime.format(ltf));

				evento = new Evento();
				evento.setFecha(fecha);
				evento.setHora(hora);
				evento.setOrigen(origen);
				evento.setUsuario(usuario);
				evento.setDescripcion(descripcion);
				evento.setNivel(nivel);
				evento.setTipo(tipo);
				evento.setAlta(true);
				
				Evento nuevoEVento = eventoService.aniadirEvento(evento);
				Notification.show("Creado Evento ID = " + nuevoEVento.getId());
				vaciarCampos();
				ocultarForm();
				setBotonesInicio();
				cargarGridEventos();
			} else {
				Notification.show("Ha de completar todos los campos");
			}
		});
		
		botonModificar.addClickListener(e -> {
			if (validarFormulario()) {
				
				evento = eventoService.obtenerEvento(eventoId);
				
				String nombreTipo = selectTipo.getValue().toString();
				Tipo tipo = tipoService.obtenerTipoPorNombre(nombreTipo);
				evento.setTipo(tipo);
				
				String nombreNivel = selectNivel.getValue().toString();
				Nivel nivel = nivelService.obtenerNivelPorNombre(nombreNivel);
				evento.setNivel(nivel);
				
				String nombreUsuario = selectUsuario.getValue().toString();
				Usuario usuario = usuarioService.obtenerUsuarioPorNombre(nombreUsuario);
				evento.setUsuario(usuario);
				
				String nombreOrigen = selectOrigen.getValue().toString();
				Origen origen = origenService.obtenerOrigenPorNombre(nombreOrigen);
				evento.setOrigen(origen);
				
				String descripcion = campoDescripcion.getValue().toString();
				evento.setDescripcion(descripcion);

				eventoService.actualizarEvento(evento);
				Notification.show("Modificado Evento ID = " + evento.getId());
				vaciarCampos();
				ocultarForm();
				setBotonesInicio();
				cargarGridEventos();
			} else {
				Notification.show("Ha de completar todos los campos");
			}
		});
		
		botonBorrar.addClickListener(e ->	{
			
			ventanaConfirm = crearVentanaConfirmar();
			containerMaestro.getCurrent().addWindow(ventanaConfirm);
			
			botonAceptarBorrado.addClickListener(aceptar ->
			{
				eventoService.borrarEvento(eventoId);
				ventanaConfirm.close();
				Notification.show("Se ha borrado el evento correctamente");
				vaciarCampos();
				ocultarForm();
				setBotonesInicio();
				cargarGridEventos();
				
			});
			
			botonCancelarBorrado.addClickListener(cancelar ->
			{
				ventanaConfirm.close();
				vaciarCampos();
				ocultarForm();
				setBotonesInicio();
				cargarGridEventos();
				Notification.show("Se ha cancelado la operación de borrado");
			});
			
		});
		
		botonCancelar.addClickListener(e ->
		{
			vaciarCampos();
			ocultarForm();
			setBotonesInicio();
			cargarGridEventos();
		});
		
		layout.addComponent(eventoForm);
		return layout;
	}
	
	public void cargarGridEventos() {
		gridEventos.setContainerDataSource(
				new BeanItemContainer<>(Evento.class, eventoService.obtenerEventos())
				);
		gridEventos.setHeightByRows(gridEventos.getContainerDataSource().size());
		gridEventos.setHeightMode(HeightMode.ROW);
	}
	
	public void cargarTipos() {
		List<Tipo> listaTipos = tipoService.obtenerListaTipos();
		for (Tipo tipoLista: listaTipos) {
			selectTipo.addItem(tipoLista.getNombre());
			filtroTipos.addItem(tipoLista.getNombre());
		}
	}
	
	public void cargarNiveles() {
		List<Nivel> listaNiveles = nivelService.obtenerListaNiveles();
		for (Nivel nivelLista: listaNiveles) {
			selectNivel.addItem(nivelLista.getNombre());
			filtroNiveles.addItem(nivelLista.getNombre());
		}
	}
	
	public void cargarUsuariosActivos() {
		List<Usuario> listaUsuariosActivos = usuarioService.listaUsuariosActivos();
		for (Usuario usuarioLista: listaUsuariosActivos) {
			selectUsuario.addItem(usuarioLista.getNombre());
			filtroUsuarios.addItem(usuarioLista.getNombre());
		}
	}
	
	public void cargarOrigenesActivos() {
		List<Origen> listaOrigenesActivos = origenService.listaOrigenesActivos();
		for (Origen origenLista: listaOrigenesActivos) {
			selectOrigen.addItem(origenLista.getNombre());
			filtroOrigenes.addItem(origenLista.getNombre());
		}
	}
	
	public void setEvento(Evento evento) {
		this.evento = evento;
		if (evento != null) {
			BeanFieldGroup.bindFieldsUnbuffered(evento, this);
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Evento(), this);
		}
	}
	
	public boolean validarFormulario() {
		if ( (selectTipo.getValue() == null) || (selectNivel.getValue() == null) ||
				(selectUsuario.getValue() == null) || (selectOrigen.getValue() == null) ||
				(campoDescripcion.getValue().isEmpty()) 
				) {
			return false;
		}
		return true;
	}
	
	public void vaciarCampos() {
		setEvento(null);
		labelEvento.setValue("Evento nuevo");
		selectTipo.setValue(null);
		selectNivel.setValue(null);
		selectUsuario.setValue(null);
		selectOrigen.setValue(null);
		campoDescripcion.setValue("");
	}
	
	public Window crearVentanaConfirmar() {
		ventanaConfirm = new Window("Confirmar borrado");
		ventanaConfirm.setWidth(460, Unit.PIXELS);
		ventanaConfirm.setContent(layoutVentana);
		
		ventanaConfirm.setModal(true);
		ventanaConfirm.setDraggable(false);
		ventanaConfirm.setResizable(false);
		ventanaConfirm.setClosable(false);
		ventanaConfirm.center();
		
		return ventanaConfirm;
	}
	
	public void setBotonesInicio() {
		botonCrear.setVisible(true);
		botonCrear.setEnabled(true);
		botonAceptar.setVisible(false);
		botonModificar.setEnabled(false);
		botonBorrar.setEnabled(false);
		botonCancelar.setEnabled(false);
	}
	
	public void setBotonesModificarBorrar() {
		botonCrear.setVisible(true);
		botonCrear.setEnabled(false);
		botonAceptar.setVisible(false);
		botonModificar.setEnabled(true);
		botonBorrar.setEnabled(true);
		botonCancelar.setEnabled(true);
	}
	
	public void setBotonesNuevoEvento() {
		botonCrear.setVisible(false);
		botonAceptar.setVisible(true);
		botonAceptar.setEnabled(true);
		botonModificar.setEnabled(false);
		botonBorrar.setEnabled(false);
		botonCancelar.setEnabled(true);
	}
	
	public void ocultarForm() {
		formLayout.setVisible(false);
	}
	
	public void mostrarForm() {
		formLayout.setVisible(true);
	}
	
	public void accionNuevoEvento() {
		mostrarForm();
		setBotonesNuevoEvento();
	}
	
}
