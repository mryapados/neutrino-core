package org.neutrinocms.core.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import org.neutrinocms.core.bo.annotation.BOField.SortType;
import org.neutrinocms.core.bo.annotation.BOField.ValueType;

public class NField implements Serializable {

	private static final long serialVersionUID = 1L;

	private ValueType type;
	private ValueType ofType;
	private String name;
	private Class<?> clazz;
	private String className;
	private String ofClassName;
	private boolean inList;
	private boolean inView;
	private boolean editable;
	private SortType sortBy;
	private int sortPriority;
	private boolean defaultField;
	private int displayOrder;
	private String tabName;
	private String groupName;
	private List<String> enumDatas;
	private Field field;
	private String defaultValue;
	
	
	private String reverseJoin;
	private Boolean reverseIsCollection;
	
	public NField(Field field, ValueType type, ValueType ofType, String name, Class<?> clazz, String className, String ofClassName, boolean inList, boolean inView, boolean editable, SortType sortBy, int sortPriority, boolean defaultField, int displayOrder, String tabName, String groupName, List<String> enumDatas, String defaultValue) {
		super();
		
		this.field = field;
		this.type = type;
		this.ofType = ofType;
		this.clazz = clazz;
		this.name = name;
		this.className = className;
		this.ofClassName = ofClassName;
		this.inList = inList;
		this.inView = inView;
		this.editable = editable;
		this.sortBy = sortBy;
		this.sortPriority = sortPriority;
		this.defaultField = defaultField;
		this.displayOrder = displayOrder;
		this.tabName = tabName;
		this.groupName = groupName;
		this.enumDatas = enumDatas;
		this.defaultValue = defaultValue;
		
		this.reverseJoin = null;
		this.reverseIsCollection = true;
	}
	
	public ValueType getType() {
		return type;
	}
	public void setType(ValueType type) {
		this.type = type;
	}
	public ValueType getOfType() {
		return ofType;
	}
	public void setOfType(ValueType ofType) {
		this.ofType = ofType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getOfClassName() {
		return ofClassName;
	}
	public void setOfClassName(String ofClassName) {
		this.ofClassName = ofClassName;
	}
	public boolean isInList() {
		return inList;
	}
	public void setInList(boolean inList) {
		this.inList = inList;
	}
	public boolean isInView() {
		return inView;
	}
	public void setInView(boolean inView) {
		this.inView = inView;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public SortType getSortBy() {
		return sortBy;
	}
	public void setSortBy(SortType sortBy) {
		this.sortBy = sortBy;
	}
	public int getSortPriority() {
		return sortPriority;
	}
	public void setSortPriority(int sortPriority) {
		this.sortPriority = sortPriority;
	}
	public boolean isDefaultField() {
		return defaultField;
	}
	public void setDefaultField(boolean defaultField) {
		this.defaultField = defaultField;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<String> getEnumDatas() {
		return enumDatas;
	}
	public void setEnumDatas(List<String> enumDatas) {
		this.enumDatas = enumDatas;
	}
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getReverseJoin() {
		return reverseJoin;
	}
	public void setReverseJoin(String reverseJoin) {
		this.reverseJoin = reverseJoin;
	}

	public Boolean getReverseIsCollection() {
		return reverseIsCollection;
	}

	public void setReverseIsCollection(Boolean reverseIsCollection) {
		this.reverseIsCollection = reverseIsCollection;
	}

	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}

	@Override
	public String toString() {
		return "NField [type=" + type + ", ofType=" + ofType + ", name=" + name + ", clazz=" + clazz + ", className="
				+ className + ", ofClassName=" + ofClassName + ", inList=" + inList + ", inView=" + inView
				+ ", editable=" + editable + ", sortBy=" + sortBy + ", sortPriority=" + sortPriority + ", defaultField="
				+ defaultField + ", displayOrder=" + displayOrder + ", tabName=" + tabName + ", groupName=" + groupName
				+ ", enumDatas=" + enumDatas + ", field=" + field + ", defaultValue=" + defaultValue + ", reverseJoin="
				+ reverseJoin + ", reverseIsCollection=" + reverseIsCollection + "]";
	}


	
}