package org.neutrinocms.core.service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.neutrinocms.core.bo.annotation.BlockMapping;
import org.neutrinocms.core.bo.annotation.ElementMapping;
import org.neutrinocms.core.bo.annotation.TemplateController;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.translation.Lang;
import org.neutrinocms.core.model.translation.Page;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class TemplateControllerExecutor {

	private Logger logger = Logger.getLogger(TemplateControllerExecutor.class);
	
	private class TemplateControllerBean implements Serializable{
		private static final long serialVersionUID = 1L;
		private Object object;
		private Method method;
		private List<Class<?>> parameters;
		public TemplateControllerBean(Object object, Method method, List<Class<?>> parameters) {
			super();
			this.object = object;
			this.method = method;
			this.parameters = parameters;
		}
		public Object getObject() {
			return object;
		}
		public Method getMethod() {
			return method;
		}
		public List<Class<?>> getParameters() {
			return parameters;
		}
	}
	
    @Autowired
    private ApplicationContext context;
    private Map<String, TemplateControllerBean> templateControllers;
    
    public ModelMap execute(String controllerName, Page page, Translation model, Translation activeObject, Template template, Folder folder, Lang lang, Map<String, String> params, PageContext pageContext) throws ServiceException{
    	logger.debug("Enter in execute : controllerName = " + controllerName);
    	if (controllerName == null) return null;
    	
        checkTemplateControllers();
        String lookingFor = controllerName.toUpperCase();
        TemplateControllerBean templateControllerBean = templateControllers.get(lookingFor);
        if (templateControllerBean == null) {
        	return null;
        }

		try {
			Object paramsObj[] = mkParameters(templateControllerBean.getParameters(), page, model, activeObject, template, folder, lang, params, pageContext);
			return (ModelMap) templateControllerBean.getMethod().invoke(templateControllerBean.getObject(), paramsObj);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ServiceException("Error execute", e);//TODO TemplateControllerException
		}
        
    }
    
    private Object[] mkParameters(List<Class<?>> parameters, Page page, Translation model, Translation activeObject, Template template, Folder folder, Lang lang, Map<String, String> params, PageContext pageContext){
    	logger.debug("Enter in mkParameters : parameters = " + parameters);	
    	Boolean firstTranslation = true;
		List<Object> objects = new ArrayList<>();
		for (Class<?> parameter : parameters) {
			if (parameter == Page.class){
				objects.add(page);
			} else if (firstTranslation && parameter == Translation.class){
				objects.add(model);
				firstTranslation = false;
			} else if (!firstTranslation && parameter == Translation.class){
				objects.add(activeObject);
			} else if (parameter == Template.class){
				objects.add(template);
			} else if (parameter == Folder.class){
				objects.add(folder);
			} else if (parameter == Lang.class){
				objects.add(lang);
			} else if (parameter == Map.class){
				objects.add(params);
			} else if (parameter == PageContext.class){
				objects.add(pageContext);
			}
		}
		Object paramsObj[] = new Object[objects.size()];
		objects.toArray(paramsObj);
		return paramsObj;
    }
    
    

    private void addTemplateControllers(Method method, Object bean, String[] templateControllerNames) {
		for (String templateControllerName : templateControllerNames) {
			templateControllerName = templateControllerName.toUpperCase();
    		if (templateControllers.containsKey(templateControllerName)){
    			logger.warn("Ambiguous block controller name '" + templateControllerName + "' found !");
    		}
    		String key = templateControllerName.toUpperCase();
			List<Class<?>> classes = new ArrayList<>();
    		Parameter[] parameters = method.getParameters();
    		for (Parameter parameter : parameters) {
    			classes.add(parameter.getType());
			}
    		templateControllers.put(key, new TemplateControllerBean(bean, method, classes));
		}
    }
    
    
    private void checkTemplateControllers() {
    	logger.debug("Enter in 'checkTemplateControllers'");	
        if (templateControllers == null) {
            templateControllers = new HashMap<String, TemplateControllerBean>();
            Map<String, Object> beans = context.getBeansWithAnnotation(TemplateController.class);
            for (Map.Entry<String, Object> bean : beans.entrySet()) {
            	
            	logger.debug("Bean found '" +  bean.getValue().getClass().getName() + "'");
            	for (Method method : bean.getValue().getClass().getDeclaredMethods())
            	{
            		BlockMapping blockMapping = method.getAnnotation(BlockMapping.class);
        	    	if (blockMapping != null){
        	    		logger.debug("blockMapping found on method '" +  method.getName() + "'");
        	    		String[] templateControllerNames = blockMapping.value();
        	    		addTemplateControllers(method, bean.getValue(), templateControllerNames);
        	    	}
        	    	
            		ElementMapping elementMapping = method.getAnnotation(ElementMapping.class);
        	    	if (elementMapping != null){
        	    		logger.debug("blockMapping found on method '" +  method.getName() + "'");
        	    		String[] templateControllerNames = elementMapping.value();
        	    		addTemplateControllers(method, bean.getValue(), templateControllerNames);
        	    	}
        	    	
            	}
            }
        }
    }   
}

