package es.cic.curso.grupo1.ejercicio027.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.cic.curso.grupo1.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo1.ejercicio027.dominio.Registro;
import es.cic.curso.grupo1.ejercicio027.service.EjecucionService;

public class ConverterEjecucion {

	
	public List<EjecucionDTO> convertDTO(Ejecucion e,List<Registro> regs){
		EjecucionDTO resultado = new EjecucionDTO();
		List<EjecucionDTO> lista = new ArrayList<>();
		if(!regs.isEmpty()){
		resultado.setCampo(regs.get(0).getCampo());
		resultado.setValor(regs.get(0).getValor());
		resultado.setEjecucion("Ejecucion "+e.getId());
		resultado.setFecha(e.getFecha());
		lista.add(resultado);
		}
		if(regs.size()>1){
		for(int i=1;i< regs.size();i++){
			resultado = new EjecucionDTO();
			resultado.setCampo(regs.get(i).getCampo());
			resultado.setValor(regs.get(i).getValor());
			lista.add(resultado);
		}
		}
		return lista;
	}
}
