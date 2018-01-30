package es.cic.curso.grupo4.ejercicio027.UI;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.context.ContextLoader;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import es.cic.curso.grupo4.ejercicio027.dominio.Conector;
import es.cic.curso.grupo4.ejercicio027.dominio.ConectorDTO;
import es.cic.curso.grupo4.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo4.ejercicio027.dominio.EjecucionDTO;
import es.cic.curso.grupo4.ejercicio027.dominio.Flujo;
import es.cic.curso.grupo4.ejercicio027.dominio.FlujoDTO;
import es.cic.curso.grupo4.ejercicio027.servicio.ConectorServicio;
import es.cic.curso.grupo4.ejercicio027.servicio.EjecucionServicio;
import es.cic.curso.grupo4.ejercicio027.servicio.FlujoServicio;

@Theme("mytheme")
public class MyUI extends UI
{
	private static final long serialVersionUID = 6311851224449216807L;
	
	//Grid
	private Grid maestroConectores;
	private Grid maestroHistorico;
	private Grid maestroFlujos;
	
	//Servicio
	protected ConectorServicio conectorServicio;
	private EjecucionServicio ejecucionServicio;
	protected FlujoServicio flujoServicio;
	
	//Form
	private NuevaForm nuevaForm;
	private HistoricoForm historicoForm;
	private LoginForm loginForm;
	private FlujoForm flujoForm;
	
	//TabSheet & Layouts
	private TabSheet tabSheet;
	private HorizontalLayout base;
	private VerticalLayout fondo;
	private HorizontalLayout cabecera;
	private VerticalLayout layoutTab;
	
	Conector c;
	Flujo f;
	
	Button ejecutar ;
	Button modificar ;
	Button verHistorico;
	Button cambiaHabilitar = new Button("Deshabilitar");
	VerticalLayout cajaHabilitar;
	VerticalLayout cajaEjecutar;
	VerticalLayout cajaModificar;
	VerticalLayout cajaHabilitados;
	HorizontalLayout cajaBotones;
	VerticalLayout cajaVerHistorico;
	ComboBox habilitado;
	Notification alerta;
	
	final static String SERVER = "10.60.11.90";
	
	@Override
	protected void init(VaadinRequest request)
	{
		conectorServicio = ContextLoader.getCurrentWebApplicationContext().getBean(ConectorServicio.class);
		ejecucionServicio = ContextLoader.getCurrentWebApplicationContext().getBean(EjecucionServicio.class);
		flujoServicio = ContextLoader.getCurrentWebApplicationContext().getBean(FlujoServicio.class);
		
		if(conectorServicio.listaConectores().isEmpty()){
			cargarBD();
		}

		fondo = new VerticalLayout();
		cabecera = new HorizontalLayout();
		base = new HorizontalLayout();
	
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
		cabecera.setMargin(true);
		cabecera.setWidth("100%");
		
		Label titulo = new Label("Integracion de sistemas");
		titulo.setStyleName("h1", true);
		cabecera.addComponents(titulo);

		
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/		

		tabSheet = new TabSheet();
		tabSheet.setHeight(100.0f, Unit.PERCENTAGE);
		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		tabSheet.addSelectedTabChangeListener(e->{
			cargarGridAlCambiar();
		});

/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
		
		// TAB - CONECTORES
		maestroConectores = new Grid();
		maestroConectores.setColumns("id", "tipo", "nombre", "descripcion", "origen", "destino", "habilitado");
		maestroConectores.setSizeFull();
		maestroConectores.setFrozenColumnCount(1);
		maestroConectores.setSelectionMode(SelectionMode.SINGLE);
		
		habilitado = new ComboBox("Seleccion");
		habilitado.addItem("Todos");
		habilitado.addItem("Habilitados");
		habilitado.setValue("Habilitados");
		habilitado.addItem("Deshabilitados");
		habilitado.setNullSelectionAllowed(false);
		cambiaHabilitar = new Button("Deshabilitar");
		cambiaHabilitar.setIcon(FontAwesome.TOGGLE_ON);
		habilitado.addValueChangeListener(e->{
			if(habilitado.getValue().toString() == "Habilitados"){
				cargaGridConectoresHabilitados();
			}else{
				if(habilitado.getValue().toString() == "Todos"){
					cargaGridConectores();
				}else{
					cargaGridConectoresDeshabilitados();
				}
			}
			cargaGridConectores();
			cambiaHabilitar.setVisible(false);
		});
		cajaHabilitar = new VerticalLayout();
		cajaHabilitar.setMargin(true);
		cajaHabilitar.addComponent(cambiaHabilitar);
		cajaBotones = new HorizontalLayout();
		cajaEjecutar = new VerticalLayout();
		cajaHabilitados = new VerticalLayout();
		cajaHabilitados.setMargin(true);
		cajaModificar = new VerticalLayout();
		cajaVerHistorico = new VerticalLayout();
		
		cambiaHabilitar.addClickListener(e->{
			cambiaHabilitarClick(c);
		});
		
		ejecutar = new Button("Ejecutar");
		ejecutar.setIcon(FontAwesome.ROCKET);
		modificar = new Button("Modificar");
		modificar.setIcon(FontAwesome.REFRESH);
		verHistorico = new Button("Ver Historico");
		verHistorico.setIcon(FontAwesome.SEARCH);
		
		
		modificar.addClickListener(e->{
			nuevaForm.setConector(c);
			nuevaForm.resetEdicion();
			modificar.setVisible(false);
			cambiaHabilitar.setVisible(false);
			ejecutar.setVisible(false);
			verHistorico.setVisible(false);
			loginForm.setVisible(false);
			nuevaForm.actualizar.setVisible(true);
			nuevaForm.resetEdicion();
			nuevaForm.resetVisualizacion(false);
    		if(nuevaForm.selectTipo.getValue().toString().equals("Rest")){
    			nuevaForm.origen.setVisible(false);
    		}else{
    			nuevaForm.origen.setVisible(true);
    		}
		});

		cajaHabilitar.addComponent(cambiaHabilitar);
		modificar.setVisible(false);
		
		cajaEjecutar.addComponent(ejecutar);
		cajaModificar.addComponent(modificar);
		cajaHabilitados.addComponent(habilitado);
		cajaVerHistorico.addComponent(verHistorico);
		cajaVerHistorico.setMargin(true);
		cajaEjecutar.setMargin(true);
		cajaModificar.setMargin(true);
		cajaVerHistorico.setSpacing(true);
		cajaEjecutar.setSpacing(true);
		cajaModificar.setSpacing(true);
		
		ejecutar.addClickListener(e ->{
			try {
				getTipoConector();
			} catch (IOException e1) {
			
				
			}
		});
		
		verHistorico.addClickListener(e -> {
			tabSheet.setSelectedTab(1);
			String nombre = c.getNombre();
			cargaGridHistoricoFiltradoConector(nombre,convierteADTO(ejecucionServicio.listaEjecuciones()));
		});
		
		maestroConectores.addSelectionListener(e -> {
			c = null;
			if (!e.getSelected().isEmpty()) {
				c = (Conector) e.getSelected().iterator().next();
				cambiaHabilitarSelection(c);
				nuevaForm.resetVisualizacion(false);
				nuevaForm.setConector(c);
				nuevaForm.resetEdicion();
				ejecutar.setVisible(true);
				modificar.setVisible(true);
				cambiaHabilitar.setVisible(true);
				verHistorico.setVisible(true);
				loginForm.setVisible(false);
				nuevaForm.resetVisualizacion(true);
				maestroConectores.setSizeFull();
			}else{
				nuevaForm.resetInicio();
				modificar.setVisible(false);
				cambiaHabilitar.setVisible(false);
				ejecutar.setVisible(false);
				verHistorico.setVisible(false);
				loginForm.setVisible(false);
			}
		});
		nuevaForm = new NuevaForm(this);
		maestroConectores.setSizeFull();
		
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/		
		
		// TAB - FLUJO
		maestroFlujos = new Grid();
		maestroFlujos.setColumns("nombre", "conectores", "habilitado");
		maestroFlujos.setSizeFull();
		maestroFlujos.setFrozenColumnCount(1);
		maestroFlujos.setSelectionMode(SelectionMode.SINGLE);
	
		maestroFlujos.addSelectionListener(e -> {
			f = null;
			if (!e.getSelected().isEmpty()) {
				f = (Flujo) e.getSelected().iterator().next();
				cambiaHabilitarFlujoSelection(f);
				flujoForm.resetVisualizacion(false);
				flujoForm.setFlujo(f);
				flujoForm.resetEdicion();
				flujoForm.mostrarBotonesPaso();
				flujoForm.ejecutar.setVisible(true);
				flujoForm.maestroConectoresDeFlujo.setVisible(false);
				flujoForm.borrarConector.setVisible(false);	
				flujoForm.conectoresLibres.setVisible(false);
				flujoForm.confirmar.setVisible(false);
				flujoForm.cambiaHabilitar.setVisible(true);
				flujoForm.resetVisualizacion(true);
				maestroFlujos.setSizeFull();
				flujoForm.borrarConector.setVisible(false);
			}else{
				flujoForm.resetInicio();
				flujoForm.aniadePaso.setVisible(false);
				flujoForm.eliminaPaso.setVisible(false);
				flujoForm.cambiaHabilitar.setVisible(false);
				flujoForm.ejecutar.setVisible(false);
				flujoForm.maestroConectoresDeFlujo.setVisible(false);

				flujoForm.ocultarBotonesPaso();
			}
		});
		flujoForm = new FlujoForm(this);
		maestroFlujos.setSizeFull();

/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
		
		// TAB - HISTORICO
		maestroHistorico = new Grid();
		maestroHistorico.setColumns("tipoConector","nombreConector","descripcionConector", "fecha", "correcta");
		maestroHistorico.setSizeFull();
		maestroHistorico.setFrozenColumnCount(1);
		maestroHistorico.setSelectionMode(SelectionMode.SINGLE);
		historicoForm = new HistoricoForm(this);
		loginForm = new LoginForm(this);
		maestroHistorico.setSizeFull();

/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
			
		//ADD COMPONENTS & SET
		layoutTab = getTabConectores();
		tabSheet.addTab(layoutTab, "Conectores");
		layoutTab = getTabHistorico();
		tabSheet.addTab(layoutTab, "Historico");
		layoutTab = getTabFlujos();
		tabSheet.addTab(layoutTab, "Flujos");
		
        base.addComponents(tabSheet);
        
        base.setMargin(true);
        base.setSpacing(true);
        base.setWidth("100%");
        
		cargaGridConectoresHabilitados();
		fondo.addComponents(cabecera, base);
        setContent(fondo);
	}
	
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/

	//GET TAB - CONECTORES
	private VerticalLayout getTabConectores()
	{
		VerticalLayout layoutVertical= new VerticalLayout();
		//No añado loginForm hasta que no se ejecute(solo si es de tipo ftp)
		//Añado un boton Ejecutar. Tampoco será visible hasta que no sea seleccionado ningun conector
		cajaEjecutar.addComponent(loginForm);
		
		cajaBotones.addComponents(nuevaForm, cajaModificar, cajaEjecutar, cajaHabilitar,cajaVerHistorico);
		
		layoutVertical.addComponents(cajaHabilitados,maestroConectores,cajaBotones);
		ejecutar.setVisible(false);
		modificar.setVisible(false);
		cambiaHabilitar.setVisible(false);
		verHistorico.setVisible(false);
		loginForm.setVisible(false);
		return layoutVertical;
	}

/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/

	//GET TAB - HISTORICO
	private VerticalLayout getTabHistorico()
	{
		VerticalLayout layoutVertical= new VerticalLayout();
		layoutVertical.addComponents(maestroHistorico,historicoForm);
		return layoutVertical;
	}
	
	private VerticalLayout getTabFlujos(){
		VerticalLayout layoutVertical = new VerticalLayout();
		layoutVertical.addComponents(maestroFlujos, flujoForm);
		return layoutVertical;
	}

/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/

	//METODOS DE CARGA
	public void cargarBD()
	{		
		conectorServicio.agregaConector(new Conector("Fichero","ConectorFichero","Conector para Fichero",
				"/home/curso/Escritorio/origen.txt","/home/curso/Escritorio/destino.txt"));
		conectorServicio.agregaConector(new Conector("FTP","ConectorFTP","Conector para FTP",
				"/home/curso/Escritorio/origen.txt","/upload/grupo4/destinoFTP.txt"));
		conectorServicio.agregaConector(new Conector("Rest","ConectorRest","Conector para Rest",
				"Sin Origen","www.google.com"));
		flujoServicio.agregaFlujo(new Flujo("PruebaFlujo","0;1;"));
	}
	
	public void cargaGridConectores()
	{
		if("Habilitados"==habilitado.getValue()){
			cargaGridConectoresHabilitados();
		}else if("Deshabilitados"==habilitado.getValue()){
			cargaGridConectoresDeshabilitados();
		}else{
			maestroConectores.setContainerDataSource(
					new BeanItemContainer<>(Conector.class,conectorServicio.listaConectores())
					);
		}
	}
	
	public int peticionREST(String enlace) throws IOException{
		URL url = new URL("http://"+enlace);
		
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		int codigo = connection.getResponseCode();

		connection.disconnect();
	
		return codigo;
		
	}


	public void cargaGridConectoresHabilitados()
	{
		List<Conector> lista = conectorServicio.listaConectores();
		List<Conector> listaHabilitados = new ArrayList<>();
		for(Conector c: lista){
			if(c.isHabilitado() == true){
				listaHabilitados.add(c);
				
			}
		}
		habilitado.setValue("Habilitados");
		maestroConectores.setContainerDataSource(
				new BeanItemContainer<>(Conector.class, listaHabilitados)
				);
	}
	
	public void cargaGridConectoresDeshabilitados()
	{
		List<Conector> lista = conectorServicio.listaConectores();
		List<Conector> listaDeshabilitados = new ArrayList<>();
		for(Conector c: lista){
			if(c.isHabilitado() == false){
				listaDeshabilitados.add(c);
				
			}
		}
		habilitado.setValue("Deshabilitados");
		maestroConectores.setContainerDataSource(
				new BeanItemContainer<>(Conector.class, listaDeshabilitados)
				);
	}
	
	//CARGA TODAS LAS EJECUCIONES
	public void cargaGridHistorico()
	{
		maestroHistorico.setContainerDataSource(
				new BeanItemContainer<>(EjecucionDTO.class, convierteADTO(ejecucionServicio.listaEjecuciones()))
				);
	}
	
	//CARGA TODAS LOS FLUJOS
	public void cargaGridFlujos()
	{
		maestroFlujos.setContainerDataSource(
				new BeanItemContainer<>(Flujo.class, flujoServicio.listaFlujos())
				);
	}
	
	
	/////////////// Conversor DTOs ///////////////
	
	public EjecucionDTO conversorEjecuciones(Ejecucion e){
		EjecucionDTO eDTO = new EjecucionDTO();
		eDTO.setId(e.getId());
		eDTO.setFecha(e.getFecha());
		eDTO.setNombreConector(e.getConector().getNombre());
		eDTO.setDescripcionConector(e.getConector().getDescripcion());
		eDTO.setTipoConector(e.getConector().getTipo());
		eDTO.setCorrecta(e.isCorrecta());
		return eDTO;
	}
	
	public FlujoDTO conversorFlujos(Flujo f){
		FlujoDTO fDTO = new FlujoDTO();
		fDTO.setId(f.getId());
		fDTO.setNombre(f.getNombre());
		fDTO.setConectores(f.getConectores());
		fDTO.setHabilitado(f.isHabilitado());
		return fDTO;
	}

	public ConectorDTO conversorConectores(Conector c){
		ConectorDTO cDTO = new ConectorDTO();
		cDTO.setId(c.getId());
		cDTO.setTipo(c.getTipo());
		cDTO.setNombre(c.getNombre());
		cDTO.setDescripcion(c.getDescripcion());
		cDTO.setOrigen(c.getOrigen());
		cDTO.setDestino(c.getDestino());
		cDTO.setHabilitado(c.isHabilitado());
		return cDTO;
	}
	
	///////////////////////////////////////////
	
	public void cargaGridHistoricoFiltrado()
	{
		List<EjecucionDTO> listaFiltrada = convierteADTO(ejecucionServicio.listaEjecuciones());

		String tipoConector;
		if(historicoForm.selectTipo.getValue()!=null){
			tipoConector = historicoForm.selectTipo.getValue().toString();
			listaFiltrada = cargaGridHistoricoFiltradoTipo(tipoConector, listaFiltrada);
		}
		
		String nombreConector;
		if(historicoForm.selectConector.getValue()!=null){
			nombreConector = historicoForm.selectConector.getValue().toString();
			listaFiltrada = cargaGridHistoricoFiltradoConector(nombreConector, listaFiltrada);

		}
		
		String correcta;
		if(historicoForm.selectCorrecta.getValue()!=null){
			correcta = historicoForm.selectCorrecta.getValue().toString();
			listaFiltrada = cargaGridHistoricoFiltradoCorrecta(correcta, listaFiltrada);
		}

		Date fechaInicio;
		if(historicoForm.fechaInicio.getValue()!=null){
			fechaInicio = (Date) historicoForm.fechaInicio.getValue();
			listaFiltrada = cargaGridHistoricoFiltradoInicio(fechaInicio, listaFiltrada);
		}
		
		Date fechaFinal;
		if(historicoForm.fechaFinal.getValue()!=null){
			fechaFinal  = (Date) historicoForm.fechaFinal.getValue();
			listaFiltrada = cargaGridHistoricoFiltradoFinal(fechaFinal, listaFiltrada);
		}
		
		maestroHistorico.setContainerDataSource(
				new BeanItemContainer<>(EjecucionDTO.class, listaFiltrada)
				);
	}
	
	public List<EjecucionDTO> cargaGridHistoricoFiltradoTipo(String tipoConector, List<EjecucionDTO> listaDTO)
	{
		List<EjecucionDTO> lista = new ArrayList<>();
		for(EjecucionDTO e: listaDTO){
			if(e.getTipoConector().equalsIgnoreCase(tipoConector)){
				lista.add(e);
			}
		}
	    historicoForm.selectConector.setValue(tipoConector);
		
		return lista;
	}
	
	public List<EjecucionDTO> cargaGridHistoricoFiltradoConector(String nombreConector, List<EjecucionDTO> listaDTO)
	{
		List<EjecucionDTO> lista = new ArrayList<>();
		for(EjecucionDTO e: listaDTO){
			if(e.getNombreConector().equalsIgnoreCase(nombreConector)){
				lista.add(e);
			}
		}
	    historicoForm.selectConector.setValue(nombreConector);
		
		return lista;
	}
	
	public List<EjecucionDTO> cargaGridHistoricoFiltradoCorrecta(String correcta, List<EjecucionDTO> listaDTO)
	{
		List<EjecucionDTO> lista = new ArrayList<>();
		boolean correctaBoolean=false;
		for(EjecucionDTO e: listaDTO)
		{
			if(correcta.equals("SI"))
			{
				correctaBoolean=true;
			}else{
				correctaBoolean=false;
			}
			if((correctaBoolean && e.isCorrecta()) || (!correctaBoolean && !e.isCorrecta())){
				lista.add(e);
			}
		}
	    historicoForm.selectCorrecta.setValue(correcta);
		return lista;
	}

	public List<EjecucionDTO> cargaGridHistoricoFiltradoInicio(Date fechaInicio, List<EjecucionDTO> listaDTO)
	{
		List<EjecucionDTO> lista = new ArrayList<>();
		for(EjecucionDTO e: listaDTO){
			if(e.getFecha().after(fechaInicio)){
				lista.add(e);
			}
		}
	    historicoForm.fechaInicio.setValue(fechaInicio);
		
		return lista;
	}
	
	public List<EjecucionDTO> cargaGridHistoricoFiltradoFinal(Date fechaFinal, List<EjecucionDTO>  listaDTO)
	{
		List<EjecucionDTO> lista = new ArrayList<>();
		for(EjecucionDTO e: listaDTO){
			if(e.getFecha().before(fechaFinal)){
				lista.add(e);
			}
		}
 	    historicoForm.fechaFinal.setValue(fechaFinal);
		
		return lista;
	}
	
	public List<EjecucionDTO> convierteADTO(List<Ejecucion> listaEjecuciones){
		List<EjecucionDTO> listaDTO = new ArrayList<>();
		for(Ejecucion e: listaEjecuciones){
			EjecucionDTO ejecucionDTO = new EjecucionDTO();
			ejecucionDTO = conversorEjecuciones(e);
			listaDTO.add(ejecucionDTO);
		}
		return listaDTO;
	}

/***************************************************************************************************************************************/
/***************************************************************************************************************************************/
/***************************************************************************************************************************************/

	public void habilitaConector(Conector c){
		c.setHabilitado(true);
		actualizaConector(c);
	}
	
	public void deshabilitaConector(Conector c){
		c.setHabilitado(false);
		actualizaConector(c);
	}
	
	public Conector creaConector(Conector conector) {
		conectorServicio.agregaConector(conector);
		limpiaListaConectores();
		return conectorServicio.obtenConector(conector.getId());
	}
	
	public void actualizaConector(Conector conector) {
		conectorServicio.modificaConector(conector);
		limpiaListaConectores();
	}
	
	public void borraConector(Long id) {
		conectorServicio.eliminaConector(id);
		limpiaListaConectores();
	}
	
	public void limpiaListaConectores()	{
	    historicoForm.selectConector.removeAllItems();
		for(Conector c : conectorServicio.listaConectores()){
			historicoForm.selectConector.addItem(c.getNombre());
		}
	}

	public Flujo creaFlujo(Flujo flujo) {
		flujoServicio.agregaFlujo(flujo);
		return flujoServicio.obtenFlujo(flujo.getId());
	}
	
	public void actualizaFlujo(Flujo flujo) {
		flujoServicio.modificaFlujo(flujo);
	}
	
	public void borraFlujo(Long id) {
		flujoServicio.eliminaFlujo(id);
	}
	
	public void habilitaFlujo(Flujo f){
		f.setHabilitado(true);
		actualizaFlujo(f);
	}
	
	public void deshabilitaFlujo(Flujo f){
		f.setHabilitado(false);
		actualizaFlujo(f);
	}
	
	///////// metodo para comprobar el tipo de conector a la hora de ejecutar////////////////	
	public void getTipoConector() throws IOException
	{
		if(c.getTipo() == "FTP"){
			loginForm.setVisible(true);
			loginForm.mostrar();
		}else if(c.getTipo().equalsIgnoreCase("Rest")){
			loginForm.setVisible(false);
			ejecutaTareaRest(c);
		}else{
			if(c.getTipo() == "Fichero"){
				loginForm.setVisible(false);
				ejecutaTareaFichero(c);
			}else{

				ejecutaTareaRest(c);
			}
		}
	}
	
	public void getTipoConector(Conector conector) throws IOException
	{
		if(conector.getTipo() == "FTP"){
			ejecutaTareaFTP(conector, "anonymous", "");
		}else if(conector.getTipo().equalsIgnoreCase("Rest")){
			loginForm.setVisible(false);
			ejecutaTareaRest(conector);
		}else{
			if(conector.getTipo() == "Fichero"){
				loginForm.setVisible(false);
				ejecutaTareaFichero(conector);
			}else{
				ejecutaTareaRest(conector);
			}
		}
	}
	///////////////////////////////////////////////////////////////////////////////
	
	
	///////////////////////Metodos de ejecucion//////////////////////////////////////
	public void ejecutarFlujo() throws IOException{
		String s = flujoForm.flujo.getConectores();
		List<Conector> lista = escribeLista(s);
		for(Conector conector:lista){
			getTipoConector(conector);
		}
	}

	public void ejecutaTareaFTP(Conector c, String usuario, String contrasena){
		try{
        int port = 21;
        String user = usuario;
        String pass = contrasena;
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(SERVER, port);
	    ftpClient.login(user, pass);
	    ftpClient.enterLocalPassiveMode();
	    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	    FileInputStream file = new FileInputStream(c.getOrigen());
	    ftpClient.storeFile(c.getDestino(), file);
	    file.close();
	    ftpClient.logout();
	    generarEjecucionCorrecta(c);
	    notificar("Credenciales correctas");
		
		}catch(IOException e){
			generarEjecucionIncorrecta(c);
			notificar("Alerta!", "Ejecucion "+ c.getNombre() +" fallida");
		}
	}
	
	public void ejecutaTareaRest(Conector c){
	
		String url = c.getDestino();
		int codigo;
		try {
			codigo = peticionREST(url);
		
			if(codigo == 200){
				generarEjecucionCorrecta(c);
				notificar("Ejecución realizada con éxito");
			}else{
				generarEjecucionIncorrecta(c);
				notificar("Alerta!","Ejecución fallida");
			}
		} catch (IOException e) {
			notificar("Alerta!","Ejecución con excepción, la url no es correcta");
		}
	}
	
	public void ocultarLogingForm(){
		loginForm.setVisible(false);
	}

	public void generarEjecucionCorrecta(Conector c) {
		Ejecucion ejecucion;
		ejecucion = new Ejecucion();
		ejecucion.setConector(c);
		Timestamp fecha = Timestamp.valueOf(LocalDateTime.now());
		ejecucion.setFecha(fecha);
		ejecucion.setCorrecta(true);
		ejecucionServicio.agregaEjecucion(ejecucion);
		cargaGridHistoricoFiltrado();
	}
	
	public void generarEjecucionIncorrecta(Conector c) {
		Ejecucion ejecucion;
		ejecucion = new Ejecucion();
		ejecucion.setConector(c);
		Timestamp fecha = Timestamp.valueOf(LocalDateTime.now());
		ejecucion.setFecha(fecha);
		ejecucion.setCorrecta(false);
		ejecucionServicio.agregaEjecucion(ejecucion);
		cargaGridHistoricoFiltrado();
	}
	
	public void ejecutaTareaFichero(Conector c){
			try{
				String cadena;
				List<String> listaEscribir = new ArrayList<>();
				FileReader fileReader;
				fileReader = new FileReader(c.getOrigen());
				
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
					while((cadena = bufferedReader.readLine()) != null){
						listaEscribir.add(cadena);
					}
	
			bufferedReader.close();
			PrintWriter printWriter = new PrintWriter(c.getDestino(), "UTF-8");
				for(int i = 0; i<listaEscribir.size();i++){
					printWriter.println(listaEscribir.get(i));
				}
			printWriter.close();
			
			generarEjecucionCorrecta(c);
			notificar("Ejecucion "+ c.getNombre() +" realizada");

		}catch(IOException e){
			generarEjecucionIncorrecta(c);
			notificar("Alerta!", "Ejecucion "+ c.getNombre() +" fallida");
			
		}
	}
	/////////////////////METODOS DE NOTIFICACION//////////////////////////////////
	public void notificar(String titulo){
		alerta = new Notification(titulo);
		alerta.setDelayMsec(1000);
		alerta.show(Page.getCurrent());
	}
	
	public void notificar(String titulo, String contenido){
		alerta = new Notification(titulo, contenido);
		alerta.setDelayMsec(1000);
		alerta.show(Page.getCurrent());
	}
	
	/////////////////////METODOS DE COMPROBACION//////////////////////////////////
	public void cambiaHabilitarClick(Conector c){
		if(c.isHabilitado()){
			deshabilitaConector(c);
		}else{
			habilitaConector(c);
			;
		}			
		cargaGridConectores();
	}
	
	public void cambiaHabilitarSelection(Conector c){
		if(c.isHabilitado()){
			cambiaHabilitar.setCaption("Deshabilitar");
			cambiaHabilitar.setIcon(FontAwesome.TOGGLE_ON);
			ejecutar.setEnabled(true);
		}else{
			cambiaHabilitar.setCaption("Habilitar");
			cambiaHabilitar.setIcon(FontAwesome.TOGGLE_OFF);
			ejecutar.setEnabled(false);
		}
	}
	
	public void cambiaHabilitarFlujoSelection(Flujo f){
		if(f.isHabilitado()){
			flujoForm.cambiaHabilitar.setCaption("Deshabilitar");
			flujoForm.cambiaHabilitar.setIcon(FontAwesome.TOGGLE_ON);
			flujoForm.ejecutar.setEnabled(true);
		}else{
			flujoForm.cambiaHabilitar.setCaption("Habilitar");
			flujoForm.cambiaHabilitar.setIcon(FontAwesome.TOGGLE_OFF);
			flujoForm.ejecutar.setEnabled(false);
		}
	}
	
	/////////////////////CONECTORES LIBRES/////////////////////
	public List<Conector> getConectoresLibres(){
		List<Conector> lista = conectorServicio.listaConectores(); 
		List<Conector> listaDefinitiva = new ArrayList<Conector>();
		listaDefinitiva.clear();
		for(Conector conector: lista){
			if(conector.isHabilitado()){
				listaDefinitiva.add(conector);
			}
		}
		return listaDefinitiva;
	}
	
	///////////////////////LISTA DE CONECTORES//////////////////////////
	
	public String escribeConectores(List<Conector> lista){
		String pasos = "";
		for(Conector c: lista){
			Long id = c.getId();
			pasos += id.toString()+";";
		}
		return pasos;
	}
	
	public List<Conector> escribeLista(String s){
		if(s.equals("")){
			return null;
		}else{
			List<Conector> listaConectores = new ArrayList<Conector>();
			listaConectores.clear();
			String[] listaIds = s.split(";");
			Conector c;
			for(String string :listaIds){
				Long id = Long.parseLong(string);
				c = conectorServicio.obtenConector(id);
				listaConectores.add(c);
			}
			return listaConectores;
		}
	}
	
//////////////////////////METODOS DE CONECTORES//////////////////////////////////
	
		public void anadirConectorAFlujo(Conector c){
			Flujo f = flujoForm.flujo;
			Long id = c.getId();
			String ids = flujoForm.flujo.getConectores();
			ids += id.toString()+";";
			f.setConectores(ids);
			flujoServicio.modificaFlujo(f);
			cargaGridFlujos();
		}
		
		public void borrarConectorDeFlujo(Conector c){
			Flujo f = flujoForm.flujo;
			Long id = c.getId();
			String ids = flujoForm.flujo.getConectores();
			List<Conector> lista = escribeLista(ids);
			for(Conector conector: lista){
				if(id == conector.getId()){
					lista.remove(conector);
				}
			}
			ids = escribeConectores(lista);
			f.setConectores(ids);
			flujoServicio.modificaFlujo(f);
		}
		
		public void setConectores(){
			List<Conector> lista = new ArrayList<Conector>();
			lista.clear();
			lista = getConectoresLibres();
			String nombreConector = "";
			for(Conector conector:lista){
				nombreConector= conector.getNombre();
				flujoForm.conectoresLibres.addItem(nombreConector);
			}
		}
		
		public void cargaGridConectoresDeFlujo(){
				List<Conector> listaConectoresFlujo = escribeLista(flujoForm.flujo.getConectores());
				for(int i= 0; i < listaConectoresFlujo.size(); i++){
					listaConectoresFlujo.get(i).setId(Long.parseLong(Integer.toString(i)));
				}
				flujoForm.maestroConectoresDeFlujo.setContainerDataSource(
						new BeanItemContainer<>(Conector.class, listaConectoresFlujo)
						);
		}
		
		public void cargarGridAlCambiar(){
				cargaGridConectores();
				cargaGridHistoricoFiltrado();
				cargaGridFlujos();
		}
		
		public boolean validarEliminaPaso(){
			if(escribeLista(flujoForm.flujo.getConectores()) != null){
				return true;
			}
			return false;
		}
	
	
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 3976043250405856261L;
    }
}