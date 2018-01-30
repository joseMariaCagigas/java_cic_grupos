package es.cic.curso.grupo1.ejercicio027.dto;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import es.cic.curso.grupo1.ejercicio027.dominio.VariableTexto;
import es.cic.curso.grupo1.ejercicio027.repository.TareaRepository;

public class ConverterVariableTexImpl implements Converter<VariableDTO, VariableTexto>, Serializable{
	
	@Autowired
	TareaRepository tareaRepository;
	
	@Override
	public VariableDTO converToDTO (VariableTexto entity) {
		VariableDTO variableDTO = new VariableDTO();
		
		variableDTO.setNombre(entity.getNombreVarTex());
		variableDTO.setTipo("Texto");
		variableDTO.setTarea(entity.getTarea());
		variableDTO.setIdVariable(entity.getId());

		return variableDTO;
	}

	@Override
	public VariableTexto converToENTITY(VariableDTO dto) {
		VariableTexto variable = new VariableTexto();

		variable.setNombreVarTex(dto.getNombre());
		variable.setId(dto.getIdVariable());
		variable.setTarea(dto.getTarea());
		return variable;
	}
}