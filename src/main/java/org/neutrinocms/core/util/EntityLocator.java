package org.neutrinocms.core.util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class EntityLocator{
	private Logger logger = Logger.getLogger(EntityLocator.class);
	
    @Autowired
    private ApplicationContext context;
    private Map<String, Object> entities;

    public Object getEntity(String entityName) throws ClassNotFoundException {
    	checkEntities();
    	Object result = entities.get(entityName.toUpperCase());
        if (result == null) {
        	throw new ClassNotFoundException("class '" + entityName + "' not found !");
        }
    	logger.debug("getEntity -> Entity (" + entityName.toUpperCase() + ") is null ? = " + (result == null));
        return result;
        
    }

    private void checkEntities() {
        if (entities == null) {
        	entities = new HashMap<String, Object>();
            Map<String, Object> beans = context.getBeansWithAnnotation(Entity.class);
            for (Map.Entry<String, Object> bean : beans.entrySet()) {
            	logger.debug("checkEntities -> " + bean.getKey().toUpperCase() + " added to entities locator");
            	entities.put(bean.getKey().toUpperCase(),  bean.getValue());
            }
        }
    }

	public Map<String, Object> getEntities() {
		checkEntities();
		return entities;
	}
	
    
}

