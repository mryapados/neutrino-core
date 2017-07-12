package org.neutrinocms.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.neutrinocms.core.model.independant.MapTemplate;
import org.neutrinocms.core.model.independant.Position;

public class PositionDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	private String name;
	
	public PositionDto() {

	}

	private List<MapTemplateDto> mapTemplateDtos;
	
	public PositionDto(Integer id, String name, List<MapTemplateDto> mapTemplateDtos) {
		this.id = id;
		this.name = name;
		this.mapTemplateDtos = mapTemplateDtos;
	}

	public static PositionDto from(Position position) {
		List<MapTemplateDto> mapTemplateDtos = new ArrayList<>();
		for (MapTemplate mapTemplate : position.getMapTemplates()) {
			mapTemplateDtos.add(MapTemplateDto.from(mapTemplate));
		}
		return new PositionDto(position.getId(), position.getName(), mapTemplateDtos);
	}
	
	public static PositionDto fromWithoutMapTemplate(Position position) {
		return new PositionDto(position.getId(), position.getName(), new ArrayList<>());
	}

//	public static Position to(PositionDto positionDto){
//		List<MapTemplate> mapTemplates = new ArrayList<>();
//		for (MapTemplateDto mapTemplateDto : positionDto.getMapTemplateDtos()) {
//			mapTemplates.add(MapTemplateDto.to(mapTemplateDto, positionDto));
//		}
//		return new Position(positionDto.getId(), positionDto.getName(), mapTemplates);
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MapTemplateDto> getMapTemplateDtos() {
		return mapTemplateDtos;
	}

	public void setMapTemplateDtos(List<MapTemplateDto> mapTemplateDtos) {
		this.mapTemplateDtos = mapTemplateDtos;
	}






	
}
