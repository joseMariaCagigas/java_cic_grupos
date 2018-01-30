package es.cic.curso.grupo2.ejercicio027.ui;

import javax.servlet.annotation.WebServlet;

import org.springframework.web.context.ContextLoader;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import es.cic.curso.grupo2.ejercicio027.servicio.ServicioCampo;
import es.cic.curso.grupo2.ejercicio027.servicio.ServicioPlantilla;

@Theme("mytheme")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;

	Navigator navegador;
	
	private ServicioCampo campoService;
	private ServicioPlantilla plantillaService;
	
	
	
	@Override
	protected void init(VaadinRequest request) {
		campoService=ContextLoader.getCurrentWebApplicationContext().getBean(ServicioCampo.class);
		plantillaService=ContextLoader.getCurrentWebApplicationContext().getBean(ServicioPlantilla.class);
		
		getPage().setTitle("Gestor de Plantillas");
		
		navegador = new Navigator(this,this);
		
		navegador.addView("", new VistaPlantilla(navegador, campoService,plantillaService));
	}
	
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = -2502393197016663089L;
	}

}
