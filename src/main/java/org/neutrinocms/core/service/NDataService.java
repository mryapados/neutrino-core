package org.neutrinocms.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.bean.NDataValue;
import org.neutrinocms.core.dao.NDataDao;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.independant.MapTemplate;
import org.neutrinocms.core.model.independant.NData;
import org.neutrinocms.core.model.independant.NSchema;
import org.neutrinocms.core.model.independant.NSchema.ScopeType;
import org.neutrinocms.core.model.independant.NType.ValueType;
import org.neutrinocms.core.model.notranslation.NoTranslation;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;



@Service
@Scope(value = "singleton")
public class NDataService extends BaseService<NData>{

	private Logger logger = Logger.getLogger(NDataService.class);

	@Autowired
	private NDataDao nDataDao;

	public List<NData> findAllForTemplate(Template template) throws ServiceException {
		try {
			return nDataDao.findAllForTemplate(template);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findAllForTemplate NData", e);
		}
	}
	public List<NData> findAllForMapTemplate(MapTemplate mapTemplate) throws ServiceException {
		try {
			return nDataDao.findAllForMapTemplate(mapTemplate);
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findAllForMapTemplate NData", e);
		}
	}
	
	public NDataValue getNDataValue(NData nData) throws ServiceException {
		try {
			ValueType nTypeValueType = nData.getVarType();
			
			switch (nData.getVarType()) {
			case INTEGER:
				return new NDataValue(nTypeValueType, nData.getvInteger());
			case FLOAT:
				return new NDataValue(nTypeValueType, nData.getvFloat());
			case DOUBLE:
				return new NDataValue(nTypeValueType, nData.getvDouble());
			case DATETIME:
				return new NDataValue(nTypeValueType, nData.getvDateTime());
			case VARCHAR50:
				return new NDataValue(nTypeValueType, nData.getvVarchar50());
			case VARCHAR255:
				return new NDataValue(nTypeValueType, nData.getvVarchar255());
			case TEXT:
				return new NDataValue(nTypeValueType, nData.getvText());
			case IMAGE:
				return new NDataValue(nTypeValueType, nData.getvPathFile());
			case HTML:
				return new NDataValue(nTypeValueType, nData.getvHtml());
			case FILE:
				return new NDataValue(nTypeValueType, nData.getvPathFile());
			case TOBJECT:
				Translation dataTObject = nData.getvTObject();
				System.out.println("ndata object = " + dataTObject.getName());
				return  new NDataValue(nTypeValueType, dataTObject, dataTObject.getObjectType().toUpperCase());
			case NTOBJECT:
				NoTranslation dataNTObject = nData.getvNTObject();
				System.out.println("ndata object = " + dataNTObject.getName());
				return new NDataValue(nTypeValueType, dataNTObject, dataNTObject.getObjectType().toUpperCase());
			case OBJECT:
				NData dataObject = nData.getvObject();
//				if (dataObject.getVarType() != ValueType.OBJECT){
//					return new NDataValue(nTypeValueType, dataObject);
//				} else {
					return getNDataValue(dataObject);
//				}
				
				
				
			case COLLECTION:
				System.out.println("collection");
				List<NData> nDatas = nData.getDatas();
				SortedMap<Integer, Object> objects = new TreeMap<>();
				for (NData data : nDatas) {
					//System.out.println(data.getOrdered() + " ; " + getNDataValue(data));
					objects.put(data.getOrdered(), getNDataValue(data));
				}
				return new NDataValue(nTypeValueType, objects.values());
			default:
				throw new ServiceException("getNDataValue No Type");
			}
			
			
		} catch (PersistenceException e) {
			throw new ServiceException("erreur getNDataValue NData", e);
		}
	}
		
	public Map<String, Object> getNDatas(MapTemplate mapTemplate) throws ServiceException{
		Template block = mapTemplate.getBlock();
		Map<String, Object> mapNDatas = new HashMap<>(); 
		NSchema nSchema =  block.getSchema();
		List<NData> nDatas = null;
		if (nSchema != null){
			if (nSchema.getScope() == ScopeType.ALL){
				nDatas = findAllForTemplate(block);
			} else if (nSchema.getScope() == ScopeType.ONE){
				nDatas = findAllForMapTemplate(mapTemplate);
			}
			for (NData nData : nDatas) {
				mapNDatas.put(nData.getPropertyName(), getNDataValue(nData));
			}
		}
		return mapNDatas;
	}
	
	public Map<String, Object> getNDatas(Template template) throws ServiceException{
		Map<String, Object> mapNDatas = new HashMap<>(); 
		NSchema nSchema =  template.getSchema();
		List<NData> nDatas = null;
		if (nSchema != null){
			if (nSchema.getScope() == ScopeType.ALL){
				nDatas = findAllForTemplate(template);
			} else if (nSchema.getScope() == ScopeType.ONE){
				throw new ServiceException("NDatas Scope ONE is for a mapTemplate");
			}
			for (NData nData : nDatas) {
				mapNDatas.put(nData.getPropertyName(), getNDataValue(nData));
			}
		}
		return mapNDatas;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}



}