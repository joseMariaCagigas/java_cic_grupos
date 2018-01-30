package es.cic.curso.grupo1.ejercicio027.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableTexto;
import es.cic.curso.grupo1.ejercicio027.repository.VariableTextoRepository;

@Service
@Transactional
public class VariableTextoServiceImpl implements VariableTextoService {
	
	
	@Autowired
	private VariableTextoRepository varRepository;
	
	@Override
	public List<VariableTexto> getVarTex(){
		return varRepository.list();
	}
	
	@Override
	public VariableTexto buscarVarTex(long varId){
		return varRepository.read(varId);
	}

	@Override
	public void borrarVarTex (long varId){
		varRepository.delete(varId);
	}
	
	@Override
	public VariableTexto nuevaVarTex(Tarea tarea, String nombre, String valor){
		VariableTexto var = new VariableTexto();
		
		var.setNombreVarTex(nombre);
		var.setTarea(tarea);
		var.setValorVarTex(valor);
		
		varRepository.add(var);
		
		return var;
	}
	
	@Override
	public VariableTexto modificarTarea(long varId, String nombre, String valor){
		VariableTexto nuevaVar = varRepository.read(varId);
		
		nuevaVar.setNombreVarTex(nombre);
		nuevaVar.setValorVarTex(valor);
		
		varRepository.add(nuevaVar);
		
		return nuevaVar;
	}

	@Override
	public VariableTexto add(VariableTexto t) {
		return varRepository.add(t);
	}

	@Override
	public VariableTexto update(VariableTexto t) {
		return varRepository.update(t);
	}
}