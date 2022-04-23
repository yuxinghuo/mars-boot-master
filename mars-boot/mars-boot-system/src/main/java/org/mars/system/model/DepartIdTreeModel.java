package org.mars.system.model;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

import org.mars.base.util.tree.TreeAnnotation;
import org.mars.base.util.tree.TreeEnum;
import org.mars.system.entity.SysDepart;

import java.io.Serializable;
import java.util.List;

@Data
public class DepartIdTreeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@TreeAnnotation(TreeEnum.ID)
	private String key;
	private String value;
	private String title;

	@TreeAnnotation(TreeEnum.PARENT_ID)
	private String parentId;

	private boolean isLeaf;

	@TreeAnnotation(TreeEnum.SUB_LIST)
	private List<DepartIdTreeModel> children;

	public DepartIdTreeModel() {
	}

	public DepartIdTreeModel(SysDepart dept) {
		this.key = dept.getId();
		this.parentId = dept.getParentId();
		this.value = dept.getId();
		this.title = dept.getDepartName();
	}
	public Boolean getIsLeaf(){
		return CollUtil.isEmpty(children);
	}
}
