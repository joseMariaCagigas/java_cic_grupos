package es.cic.curso.grupo3.ejercicio027.frontend;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class EventoForm extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9028863066469227604L;
	
	private Controles controles = new Controles();
	private Label labelTitulo = controles.crearLabel("Log de Actividades");
	private HorizontalLayout barraTitulo = controles.crearCabecera(labelTitulo, "barraazul");
	private ComboBox filtroTipos = controles.crearComboBox("Tipos");
	private ComboBox filtroNiveles = controles.crearComboBox("Niveles");
	private ComboBox filtroUsuarios = controles.crearComboBox("Usuarios");
	private ComboBox filtroOrigenes = controles.crearComboBox("Orígenes");
	private HorizontalLayout barraFiltros = controles.crearHorizontalLayoutSinEstilo();
	private Grid gridEventos = controles.crearGrid();
	private Label labelEvento = controles.crearLabel();
	private ComboBox selectTipo = controles.crearComboBox("Tipo de envento: ");
	private ComboBox selectNivel = controles.crearComboBox("Nivel de envento: ");
	private ComboBox selectUsuario = controles.crearComboBox("Usuario: ");
	private ComboBox selectOrigen = controles.crearComboBox("Origen: ");
	private TextField campoDescripcion = controles.crearTextField("Descripción: ");
	private Button botonCrear = controles.crearButton("Crear");
	private Button botonAceptar = controles.crearButton("Aceptar");
	private Button botonModificar = controles.crearButton("Modificar");
	private Button botonBorrar = controles.crearButton("Borrar");
	private Button botonCancelar = controles.crearButton("Cancelar");
	private HorizontalLayout barraBotones = controles.crearHorizontalLayoutSinEstilo();
	private VerticalLayout columnaGridEventos = controles.crearVerticalLayout();
	private FormLayout formLayout = controles.crearFormLayout();
	
	public EventoForm() {
		super();
		VerticalLayout layout = controles.crearVerticalLayout();
		layout.setSpacing(true);
		
		labelEvento.setCaption("Evento actual: ");
		
		barraFiltros.setSpacing(true);
		barraFiltros.addComponents(filtroTipos, filtroNiveles, filtroUsuarios, filtroOrigenes);
		
		gridEventos.setColumns("tipo", "nivel", "usuario", "origen", "descripcion", "fecha", "hora");
		gridEventos.setFrozenColumnCount(1);
		gridEventos.setWidth(100, Unit.PERCENTAGE);
		gridEventos.setCaption("Listado de Eventos");
		gridEventos.getDefaultHeaderRow().getCell("descripcion").setText("Descripción");
		
		columnaGridEventos.addComponent(gridEventos);
	
		formLayout.addComponents(labelEvento, selectTipo, selectNivel, selectUsuario, selectOrigen, campoDescripcion);
		
		barraBotones.setSpacing(true);
		barraBotones.addComponents(botonCrear, botonAceptar, botonModificar, botonBorrar, botonCancelar);
		
		layout.addComponents(barraTitulo, barraFiltros, columnaGridEventos, formLayout, barraBotones);

		addComponents(layout);
	}

	public Controles getControles() {
		return controles;
	}

	public Label getLabelTitulo() {
		return labelTitulo;
	}

	public HorizontalLayout getBarraTitulo() {
		return barraTitulo;
	}

	public ComboBox getFiltroTipos() {
		return filtroTipos;
	}

	public ComboBox getFiltroNiveles() {
		return filtroNiveles;
	}

	public ComboBox getFiltroUsuarios() {
		return filtroUsuarios;
	}

	public ComboBox getFiltroOrigenes() {
		return filtroOrigenes;
	}

	public HorizontalLayout getBarraFiltros() {
		return barraFiltros;
	}

	public Grid getGridEventos() {
		return gridEventos;
	}

	public Label getLabelEvento() {
		return labelEvento;
	}

	public ComboBox getSelectTipo() {
		return selectTipo;
	}

	public ComboBox getSelectNivel() {
		return selectNivel;
	}

	public ComboBox getSelectUsuario() {
		return selectUsuario;
	}

	public ComboBox getSelectOrigen() {
		return selectOrigen;
	}

	public TextField getCampoDescripcion() {
		return campoDescripcion;
	}

	public Button getBotonCrear() {
		return botonCrear;
	}

	public Button getBotonAceptar() {
		return botonAceptar;
	}

	public Button getBotonModificar() {
		return botonModificar;
	}

	public Button getBotonBorrar() {
		return botonBorrar;
	}

	public Button getBotonCancelar() {
		return botonCancelar;
	}

	public HorizontalLayout getBarraBotones() {
		return barraBotones;
	}

	public VerticalLayout getColumnaGridEventos() {
		return columnaGridEventos;
	}

	public FormLayout getFormLayout() {
		return formLayout;
	}
		
}
