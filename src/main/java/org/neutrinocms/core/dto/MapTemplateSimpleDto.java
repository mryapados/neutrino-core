package org.neutrinocms.core.dto;

import java.io.Serializable;

public class MapTemplateSimpleDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer modelId;
	private Integer blockId;
	private Integer positionId;
	private Integer ordered;

	public MapTemplateSimpleDto() {

	}

	public MapTemplateSimpleDto(Integer modelId, Integer blockId, Integer positionId, Integer ordered) {
		super();
		this.modelId = modelId;
		this.blockId = blockId;
		this.positionId = positionId;
		this.ordered = ordered;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getOrdered() {
		return ordered;
	}

	public void setOrdered(Integer ordered) {
		this.ordered = ordered;
	}

	@Override
	public String toString() {
		return "MapTemplateSimpleDto [modelId=" + modelId + ", blockId=" + blockId + ", positionId=" + positionId + ", ordered=" + ordered + "]";
	}

	


	
	
}
