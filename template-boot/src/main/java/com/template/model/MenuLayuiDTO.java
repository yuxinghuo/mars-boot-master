package com.template.model;

import com.template.system.util.tree.TreeAnnotation;
import com.template.system.util.tree.TreeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor(staticName = "of")
public class MenuLayuiDTO {

	@TreeAnnotation(TreeEnum.ID)
	private Integer menuId;// 节点id
	@TreeAnnotation(TreeEnum.PARENT_ID)
	private Integer parentId;// 父菜单ID，一级菜单为0
	private String title;// 名称
	private String icon;// 图标
	private String href;// 链接
	private String target;// 跳转方式
	private Boolean spread = false;// 是否展开
	@TreeAnnotation(TreeEnum.SUB_LIST)
	private List<MenuLayuiDTO> children;
}
