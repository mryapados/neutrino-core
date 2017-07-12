package org.neutrinocms.core.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.neutrinocms.core.model.independant.Folder;

public class FolderDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	private String name;
	
	@NotNull
	private List<String> serverName;

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.SIMPLE_TEXT)
	private String path;
	
	
	public FolderDto() {

	}

	public FolderDto(Integer id, String name, List<String> serverName, String path) {
		this.id = id;
		this.name = name;
		this.serverName = serverName;
		this.path = path;
	}

	public static FolderDto from(Folder folder) {
		return new FolderDto(folder.getId(), folder.getName(), folder.getServerName(), folder.getPath());
	}
	
	public static Folder to(FolderDto folderDto){
		return new Folder(folderDto.getId(), folderDto.getName(), folderDto.getServerName(), folderDto.getPath());
	}

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

	public List<String> getServerName() {
		return serverName;
	}

	public void setServerName(List<String> serverName) {
		this.serverName = serverName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	
	

	






	
}
