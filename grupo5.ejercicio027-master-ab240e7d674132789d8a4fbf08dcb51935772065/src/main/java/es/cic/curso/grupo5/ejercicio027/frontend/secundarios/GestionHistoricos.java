package es.cic.curso.grupo5.ejercicio027.frontend.secundarios;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.web.context.ContextLoader;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Historico;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;
import es.cic.curso.grupo5.ejercicio027.backend.dto.HistoricoConverter;
import es.cic.curso.grupo5.ejercicio027.backend.dto.HistoricoDTO;
import es.cic.curso.grupo5.ejercicio027.backend.service.UsuarioService;
import es.cic.curso.grupo5.ejercicio027.frontend.principal.MyUI;

public class GestionHistoricos  extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6683850118394414599L;

	private NativeButton aniadirHistorico;
	Grid gridHistorico;
	private HistoricoForm detalleHistorico;
 
	@SuppressWarnings("unused")
	private MyUI padre;

	private HistoricoConverter conv = new HistoricoConverter();
	private UsuarioService usuarioService;
	private List<HistoricoDTO> listaHistoricos = new ArrayList<>();;
	
	public GestionHistoricos(MyUI padre){
		this.padre = padre;
		usuarioService = ContextLoader.getCurrentWebApplicationContext().getBean(UsuarioService.class);	
	
		aniadirHistorico = new NativeButton("Añadir Registro");
		aniadirHistorico.setIcon(FontAwesome.PLUS);
		
		gridHistorico = new Grid();
		gridHistorico.setWidth(820, Unit.PIXELS);	
		gridHistorico.setColumns("usuario","operacion","hora","permitido");	
		gridHistorico.setFrozenColumnCount(1);
		gridHistorico.setSelectionMode(SelectionMode.NONE);	
		
		detalleHistorico = new HistoricoForm(this);

		aniadirHistorico.addClickListener(e->{	
			aniadirHistorico.setVisible(false);
			detalleHistorico.actualizarUsuarios();
			detalleHistorico.actualizarOp();
			aniadirHistorico();
		});
		
		cargaHistoricos(null);
		
		addComponents(gridHistorico,aniadirHistorico,detalleHistorico);		
	}
	private void aniadirHistorico() {	
		
		detalleHistorico.setVisible(true);
		Historico h = new Historico("","",null,false);
		detalleHistorico.setHistorico(h);
		gridHistorico.setContainerDataSource(
				new BeanItemContainer<>(HistoricoDTO.class, listaHistoricos)
				);
	}


	public void cargaHistoricos(Historico historico){	
		
		List<Usuario> u = usuarioService.listarUsuario();
		aniadirHistorico.setVisible(true);
		detalleHistorico.setVisible(false);
		
		if(historico!=null){
			
			for(Usuario user: u){
				
				if(historico.getUsuario().getNombre().equals(user.getNombre())){
					HistoricoDTO d = new HistoricoDTO();
					d = conv.entityToDto(historico, user);
					listaHistoricos.add(d);

				}
				 
			}
		}
		
		List<HistoricoDTO> lista  = new ArrayList<>();
		lista = listaHistoricos; 	
		lista.sort(Comparator.comparing(HistoricoDTO::getHora).reversed());

		gridHistorico.setContainerDataSource(
				new BeanItemContainer<>(HistoricoDTO.class, lista)
				);
		detalleHistorico.setHistorico(null);

	}
}