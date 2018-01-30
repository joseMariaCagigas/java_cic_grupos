package es.cic.curso.grupo1.ejercicio027.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo1.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo1.ejercicio027.dominio.Registro;
import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableNumero;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableTexto;
import es.cic.curso.grupo1.ejercicio027.repository.EjecucionRepository;
import es.cic.curso.grupo1.ejercicio027.repository.RegistroRepository;
import es.cic.curso.grupo1.ejercicio027.repository.TareaRepository;
import es.cic.curso.grupo1.ejercicio027.repository.VariableNumeroRepository;
import es.cic.curso.grupo1.ejercicio027.repository.VariableTextoRepository;

@Service
@Transactional
public class TareaServiceImpl implements TareaService {
	
	@Autowired
	private TareaRepository tareaRepository;
	@Autowired
	private VariableNumeroRepository varNRepository;
	@Autowired
	private VariableTextoRepository varTRepository;
	@Autowired
	private EjecucionRepository ejeRepository;
	@Autowired
	private RegistroRepository regRepository;
	
	@Override
	public List<Tarea> getTareas(){
		return tareaRepository.list();
	}
	
	@Override
	public Tarea buscarTarea(long tareaId){
		return tareaRepository.read(tareaId);
	}
	
	@Override
	public Tarea buscarTarea(String codigo){
		Tarea resultado = new Tarea();
		for (Tarea t : tareaRepository.list()){
			if (t.getCodigo().equalsIgnoreCase(codigo)){
				resultado = t;
			}
		}
		return resultado;
	}

	@Override
	public void borrarTarea(long tareaId){
		for (Registro r : regRepository.list()){
			if (r.getEjecucion().getTarea().getId() == tareaId){
				regRepository.delete(r);
			}
		}
		for (Ejecucion e : ejeRepository.list()){
			if (e.getTarea().getId() == tareaId){
				ejeRepository.delete(e);
			}
		}
		for (VariableNumero vN : varNRepository.list()){
			if (vN.getTarea().getId() == tareaId){
				varNRepository.delete(vN.getId());
			}
		}
		for (VariableTexto vT : varTRepository.list()){
			if (vT.getTarea().getId() == tareaId){
				varTRepository.delete(vT.getId());
			}
		}
		tareaRepository.delete(tareaId);
	}
	
	@Override
	public Tarea nuevaTarea(String codigo, String descripcion){
		Tarea nuevaTarea = new Tarea();
		
		nuevaTarea.setCodigo(codigo);
		nuevaTarea.setDescripcion(descripcion);
		
		tareaRepository.add(nuevaTarea);
		
		return nuevaTarea;
	}
	
	@Override
	public Tarea modificarTareaNom(long tareaId, String codigo){
		Tarea nuevaTarea = tareaRepository.read(tareaId);
		
		nuevaTarea.setCodigo(codigo);
		
		tareaRepository.update(nuevaTarea);
		
		return nuevaTarea;
	}
	
	@Override
	public Tarea modificarTareaDesc(long tareaId, String descripcion){
		Tarea nuevaTarea = tareaRepository.read(tareaId);
		
		nuevaTarea.setDescripcion(descripcion);
		
		tareaRepository.update(nuevaTarea);
		
		return nuevaTarea;
	}

	@Override
	public Tarea add(Tarea t) {
		return tareaRepository.add(t);
	}
	
	@Override
	public boolean verificarNombre(String codTarea){
		boolean resultado = true;
		
		for (Tarea t : tareaRepository.list()){
			if (t.getCodigo().equalsIgnoreCase(codTarea)){
				resultado = false;
			}
		}
		return resultado;
	}

	@Override
	public Tarea update(Tarea t) {
		return tareaRepository.update(t);
	}
	
	@Override
 	public synchronized List<Tarea> findAll(String filtro){
 		List<Tarea> lista = new ArrayList<>();
 		for(Tarea t : getTareas()){
 			try{
 				boolean pasoFiltro = (filtro==null||filtro.isEmpty())
 						||t.getCodigo().toLowerCase().contains(filtro.toLowerCase());
 				if(pasoFiltro){
 					lista.add(t.clone());
 				}
 			}catch(CloneNotSupportedException ex) {
 				Logger.getLogger(TareaServiceImpl.class.getName()).log(null, ex);
 			}
 		}
 		Collections.sort(lista, new Comparator<Tarea>() {
 
 			@Override
 			public int compare(Tarea o1, Tarea o2) {
 				return (int) (o2.getId() - o1.getId());
 			}});
 		return lista;
	}	
}