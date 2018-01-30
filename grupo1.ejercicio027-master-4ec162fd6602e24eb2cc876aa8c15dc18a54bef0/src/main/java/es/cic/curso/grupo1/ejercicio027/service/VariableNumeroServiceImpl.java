package es.cic.curso.grupo1.ejercicio027.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableNumero;
import es.cic.curso.grupo1.ejercicio027.repository.VariableNumeroRepository;

@Service
@Transactional
public class VariableNumeroServiceImpl implements VariableNumeroService {
	
	@Autowired
	private VariableNumeroRepository varRepository;
	
	@Override
	public List<VariableNumero> getVarNums(){
		return varRepository.list();
	}
	
	@Override
	public VariableNumero buscarVarNum(long varId){
		return varRepository.read(varId);
	}

	@Override
	public void borrarVarNum (long varId){
		varRepository.delete(varId);
	}
	
	@Override
	public VariableNumero nuevaVarNum(Tarea tarea, String nombre, double valor){
		VariableNumero var = new VariableNumero();
		
		var.setNombreVarNum(nombre);
		var.setTarea(tarea);
		var.setValorVarNum(valor);
		
		varRepository.add(var);
		
		return var;
	}
	
	@Override
	public VariableNumero modificarTarea(long varId, String nombre, double valor){
		VariableNumero nuevaVar = varRepository.read(varId);
		
		nuevaVar.setNombreVarNum(nombre);
		nuevaVar.setValorVarNum(valor);
		
		varRepository.add(nuevaVar);
		
		return nuevaVar;
	}

	@Override
	public VariableNumero add(VariableNumero n) {
		return varRepository.add(n);
	}

	@Override
	public VariableNumero update(VariableNumero v) {
		return varRepository.update(v);
	}
}