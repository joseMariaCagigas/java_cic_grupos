package es.cic.curso.grupo3.ejercicio027.frontend;

import java.util.List;
import javax.servlet.annotation.WebServlet;
import org.springframework.web.context.ContextLoader;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import es.cic.curso.grupo3.ejercicio027.domain.Evento;
import es.cic.curso.grupo3.ejercicio027.frontend.admin.PestaniasAdmin;
import es.cic.curso.grupo3.ejercicio027.service.CargaService;
import es.cic.curso.grupo3.ejercicio027.service.EventoService;



/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */


@Theme("mytheme")
public class MyUI extends UI {

	private static final long serialVersionUID = -559467523308030806L;
	private Controles controles = new Controles();
	private EventoService eventoService;
	private List<Evento> listaEventos;
	private CargaService cargaService;
	private VerticalLayout layout;
	private TabSheet pestanias = new TabSheet();
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {

		pestanias.setHeight(100.f, Unit.PERCENTAGE);
		pestanias.addStyleName(ValoTheme.TABSHEET_FRAMED);
		pestanias.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

		eventoService = ContextLoader.getCurrentWebApplicationContext().getBean(EventoService.class);
		cargaService = ContextLoader.getCurrentWebApplicationContext().getBean(CargaService.class);
		
		layout = controles.crearVerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		listaEventos = eventoService.obtenerEventos();
		
		if (listaEventos.isEmpty()) {
			cargaService.cargarEventos();
		}
		
		CapaEvento capaEventoClass = new CapaEvento();
		VerticalLayout capaEvento = capaEventoClass.creaCapa(this);
		
		pestanias.addTab(capaEvento, "Eventos");
		
		PestaniasAdmin pestaniasAdminClass = new PestaniasAdmin();
		VerticalLayout pestaniasAdmin = pestaniasAdminClass.creaCapa();
		
		pestanias.addTab(pestaniasAdmin, "Administración");
		
        layout.addComponents(pestanias);
        setContent(layout);
    }


	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

		private static final long serialVersionUID = 5747624197253709025L;
    }
}
 