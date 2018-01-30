package es.cic.curso.grupo1.ejercicio027.dto;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import es.cic.curso.grupo1.ejercicio027.dominio.VariableNumero;
import es.cic.curso.grupo1.ejercicio027.repository.TareaRepository;
import es.cic.curso.grupo1.ejercicio027.repository.TareaRepositoryImpl;

public class ConverterVariableNumImpl implements Converter<VariableDTO, VariableNumero>, Serializable{
	
	@Autowired
	private TareaRepository tareaRepository;
	
	@Override
	public VariableDTO converToDTO (VariableNumero entity) {
		tareaRepository = new TareaRepositoryImpl();
		VariableDTO variableDTO = new VariableDTO();
		variableDTO.setIdVariable(entity.getId());
		variableDTO.setNombre(entity.getNombreVarNum());
		variableDTO.setTipo("Numero");
		variableDTO.setTarea(entity.getTarea());

		return variableDTO;
	}

	@Override
	public VariableNumero converToENTITY(VariableDTO dto) {
		VariableNumero variable = new VariableNumero();
		variable.setId(dto.getIdVariable());
		variable.setNombreVarNum(dto.getNombre());
		variable.setTarea(dto.getTarea());
		return variable;
	}
}