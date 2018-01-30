package es.cic.curso.grupo1.ejercicio027.dto;

public interface Converter<DTO,ENTITY>{

	public DTO converToDTO(ENTITY entity);
	public ENTITY converToENTITY(DTO dto);
}