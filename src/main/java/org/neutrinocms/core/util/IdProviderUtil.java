package org.neutrinocms.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.neutrinocms.core.constant.CacheConst;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.exception.UtilException;
import org.neutrinocms.core.model.IdProvider;
import org.neutrinocms.core.model.notranslation.NoTranslation;
import org.neutrinocms.core.model.translation.Translation;
import org.neutrinocms.core.service.NTObjectService;
import org.neutrinocms.core.service.TObjectService;
import org.neutrinocms.core.specification.IdProviderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.BindTag;



@Component
public class IdProviderUtil {
	
	final private String ALLJOINS = ".allJoins";
	
	private Logger logger = Logger.getLogger(BindTag.class);
	
	@Autowired
	private EntityLocator entityLocator;
	
	@Autowired
	private ServiceLocator serviceLocator;

	@Autowired
	private TObjectService tObjectService ;
	
	@Autowired
	private NTObjectService nTObjectService ;
	
	public Class<?> getEntity(String type) throws ClassNotFoundException {
		return entityLocator.getEntity(type).getClass();
	}
	
	public IdProvider getTranslationObject(Integer id) throws UtilException{
		try {
			return tObjectService.findOne(id);
		} catch (ServiceException e) {
			throw new UtilException(e);
		}
	}
	public IdProvider getNoTranslationObject(Integer id) throws UtilException{
		try {
			return nTObjectService.findOne(id);
		} catch (ServiceException e) {
			throw new UtilException(e);
		}
	}

	private IdProvider getConcreteObject(Class<?> entity, Integer id) throws UtilException{
		logger.debug("Enter in getObject : entity = " + entity + "; id = " + id);
		try {
			Class<?> params[] = {Integer.class};
			Object paramsObj[] = {id};
			Object service = serviceLocator.getService(entity.getSimpleName());
			Class<?> clazz = service.getClass();
			Method findOne = clazz.getMethod("findOne", params);
			return (IdProvider) findOne.invoke(service, paramsObj);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			throw new UtilException(e);
		}
	}
	
	public IdProvider getObject(Class<?> entity, Integer id) throws UtilException{
		if (entity == Translation.class){
			return getTranslationObject(id);
		} else if (entity == Translation.class){
			return getNoTranslationObject(id);
		} else {
			return getConcreteObject(entity, id);
		}
	}
	
	private Field getField(Class<?> classObject, String fieldName) throws NoSuchFieldException {
		logger.debug("Enter in getField : classObject = " + classObject + "; fieldName = " + fieldName);
		try {
			return classObject.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			Class<?> superClass = classObject.getSuperclass();
			if (superClass != null){
				return getField(classObject.getSuperclass(), fieldName);
			} else {
				throw e;
			}
		}
	}
	
	private Object getFieldValue(Object object, Field field) throws UtilException {
		logger.debug("Enter in getFieldValue : object = " + object + "; field = " + field);
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("Failed to get value from field", e);
			throw new UtilException("Erreur getFieldValue", e);
		}
	}
	
	private Class<?> getClass(String type) throws UtilException{
		if (type.equals(Translation.class.getSimpleName())){
			return Translation.class;
		} else if (type.equals(NoTranslation.class.getSimpleName())){
			return NoTranslation.class;
		} else {
			try {
				return entityLocator.getEntity(type).getClass();
			} catch (ClassNotFoundException e) {
				throw new UtilException(e);
			}
		}
	}
	
	@Cacheable(value = CacheConst.IDPROVIDERFIEDDVALUE, condition = "#cache")
	public Object getIdProviderFieldValue(String type, int beanId, String field, boolean cache) throws UtilException{
		logger.debug("Enter in getIdProviderFieldValue");
		try {
			Class<?> clazz = getClass(type);
			Object object = getObject(clazz, beanId);
			return getFieldValue(object, getField(clazz, field));
		} catch (NoSuchFieldException e) {
			throw new UtilException(e);
		}
	}
	
	@Cacheable(value = CacheConst.IDPROVIDERFIEDDVALUE, condition = "#cache")
	public Object getIdProviderFieldValue(IdProvider object, String field, boolean cache) throws UtilException{
		logger.debug("Enter in getIdProviderFieldValue");
		try {
			Class<?> clazz = object.getClass();
			return getFieldValue(object, getField(clazz, field));
		} catch (NoSuchFieldException e) {
			throw new UtilException(e);
		}
	}
	
	private IdProvider copyFields(IdProvider entity, IdProvider newEntity, Class<?> clazz) throws IllegalAccessException {
		logger.debug("Enter in copyFields : entity = " + entity + "; newEntity = " + newEntity + "; clazz = " + clazz);
	    List<Field> fields = new ArrayList<>();
	    for (Field field : clazz.getDeclaredFields()) {
	        fields.add(field);
	    }
	    for (Field field : fields) {
	        field.setAccessible(true);
	        field.set(newEntity, field.get(entity));
	    }
	    return newEntity;
	}
	public IdProvider copy(IdProvider entity) throws IllegalAccessException, InstantiationException {
		logger.debug("Enter in entity : entity = " + entity);
	    Class<?> clazz = entity.getClass();
	    IdProvider newEntity = (IdProvider) entity.getClass().newInstance();

	    while (clazz != null) {
	        copyFields(entity, newEntity, clazz);
	        clazz = clazz.getSuperclass();
	    }

	    return newEntity;
	}


	
	
	public IdProvider getFullObject(Class<?> entity, Integer id) throws ServiceException{
		return getFullObject(entity, id, null);
	}
	public IdProvider getFullObject(Class<?> entity, Integer id, Specification<IdProvider> spec) throws ServiceException{
		return getFullObject(entity, id, spec, EntityGraphType.FETCH, entity.getSimpleName() + ALLJOINS);
	}

	private IdProvider getFullObject(Class<?> entity, Integer id, Specification<IdProvider> spec, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException{
		logger.debug("Enter in getFullObject : entity = " + entity + "; id = " + id + "; spec = " + spec + "; entityGraphType = " + entityGraphType + "; entityGraphName = " + entityGraphName);
		try {
			List<Class<?>> classes = new ArrayList<>();
			classes.add(Specification.class);
			if (entityGraphType != null) classes.add(EntityGraphType.class);
			if (entityGraphName != null) classes.add(String.class);
			Class<?> params[] = new Class<?>[classes.size()];
			classes.toArray(params);
			
			List<Object> objects = new ArrayList<>();
			Specification<IdProvider> specId = Specifications.where(spec).and(IdProviderSpecification.idEqualsTo(id));
			
			objects.add(specId);
			if (entityGraphType != null) objects.add(entityGraphType);
			if (entityGraphName != null) objects.add(entityGraphName);
			Object paramsObj[] = new Object[objects.size()];
			objects.toArray(paramsObj);
						
			String StringParamsObj = "";
			for (Object object : paramsObj) {
				StringParamsObj += object.getClass().getName() + " = " + object.toString() + "; ";
			}
			logger.debug("getDatas -> paramsObj " + StringParamsObj);	
			
			logger.debug("getDatas -> Look for " + entity.getSimpleName());			
			Object service = serviceLocator.getService(entity.getSimpleName());
			logger.debug("getDatas -> Entity found " + entity.getSimpleName());		
			logger.debug("getDatas -> Service found " + service.getClass().getSimpleName());
			
			Class<?> clazz = service.getClass();
			Method findOne;
			try {
				findOne = clazz.getMethod("findOne", params);
			} catch (NoSuchMethodException e) {
				logger.error("getData -> NoSuchMethodException", e);
				throw new ServiceException("Error getData", e);
			}
			
			try {
				return (IdProvider) findOne.invoke(service, paramsObj);
			} catch (InvocationTargetException e) {
				if (entityGraphName == null) throw e;
				return getFullObject(entity, id, spec, null, null);
			}
			
		} catch (ClassNotFoundException e) {
			logger.error("getData -> ClassNotFoundException", e);
			throw new ServiceException("Error getList", e);
		} catch (SecurityException e) {
			logger.error("getData -> SecurityException", e);
			throw new ServiceException("Error getList", e);
		} catch (IllegalAccessException e) {
			logger.error("getData -> IllegalAccessException", e);
			throw new ServiceException("Error getList", e);
		} catch (IllegalArgumentException e) {
			logger.error("getData -> IllegalArgumentException", e);
			throw new ServiceException("Error getList", e);
		} catch (InvocationTargetException e) {
			logger.error("getData -> InvocationTargetException", e);
			throw new ServiceException("Error getList", e);
		}
	}
	
	public Page<IdProvider> getFullObjects(Class<?> entity, Pageable pageable) throws ServiceException{
		return getFullObjects(entity, pageable, null);
	}
	public Page<IdProvider> getFullObjects(Class<?> entity, Pageable pageable, Specification<IdProvider> spec) throws ServiceException{
		return getFullObjects(entity, pageable, spec, EntityGraphType.FETCH, entity.getSimpleName() + ALLJOINS);
	}

	private Page<IdProvider> getFullObjects(Class<?> entity, Pageable pageable, Specification<IdProvider> spec, EntityGraphType entityGraphType, String entityGraphName) throws ServiceException{
		logger.debug("Enter in getFullObjects : entity = " + entity + "; pageable = " + pageable + "; spec = " + spec + "; entityGraphType = " + entityGraphType + "; entityGraphName = " + entityGraphName);
		try {

			List<Class<?>> classes = new ArrayList<>();
			if (spec != null) classes.add(Specification.class);
			classes.add(Pageable.class);
			if (entityGraphType != null) classes.add(EntityGraphType.class);
			if (entityGraphName != null) classes.add(String.class);
			Class<?> params[] = new Class<?>[classes.size()];
			classes.toArray(params);
			
			List<Object> objects = new ArrayList<>();
			if (spec != null) objects.add(spec);
			objects.add(pageable);
			if (entityGraphType != null) objects.add(entityGraphType);
			if (entityGraphName != null) objects.add(entityGraphName);
			Object paramsObj[] = new Object[objects.size()];
			objects.toArray(paramsObj);
			
			String StringParamsObj = "";
			for (Object object : paramsObj) {
				StringParamsObj += object.getClass().getName() + " = " + object.toString() + "; ";
			}
			logger.debug("getDatas -> paramsObj " + StringParamsObj);	
			
			logger.debug("getDatas -> Look for " + entity.getSimpleName());			
			Object service = serviceLocator.getService(entity.getSimpleName());
			logger.debug("getDatas -> Entity found " + entity.getSimpleName());		
			logger.debug("getDatas -> Service found " + service.getClass().getSimpleName());		

			Class<?> clazz = Class.forName(service.getClass().getName());
			Method findAll;
			try {
				findAll = clazz.getMethod("findAll", params);
				
				Parameter parameters[] = findAll.getParameters();
				String parameterString = "";
				for (Parameter parameter : parameters) {
					parameterString += parameter.getName() + " = " + parameter.getType().getName() + "; ";
				}
				
				logger.debug("getDatas -> findAll parameters : " + parameterString);			
				
				
			} catch (NoSuchMethodException e) {
				logger.error("getDatas -> NoSuchMethodException", e);
				throw new ServiceException("Error getDatas", e);
			}
			
			try {
				return (Page<IdProvider>) findAll.invoke(service, paramsObj);
			} catch (InvocationTargetException e) {
				//e.printStackTrace();
				if (entityGraphName == null) throw e;
				return getFullObjects(entity, pageable, spec, null, null);
			}
			
			
		} catch (ClassNotFoundException e) {
			logger.error("getDatas -> ClassNotFoundException", e);
			throw new ServiceException("Error getDatas", e);
		} catch (SecurityException e) {
			logger.error("getDatas -> SecurityException", e);
			throw new ServiceException("Error getDatas", e);
		} catch (IllegalAccessException e) {
			logger.error("getDatas -> IllegalAccessException", e);
			throw new ServiceException("Error getDatas", e);
		} catch (IllegalArgumentException e) {
			logger.error("getDatas -> IllegalArgumentException", e);
			throw new ServiceException("Error getDatas", e);
		} catch (InvocationTargetException e) {
			logger.error("getDatas -> InvocationTargetException", e);
			throw new ServiceException("Error getDatas", e);
		}
	}
	
}
