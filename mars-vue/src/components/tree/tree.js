// 组织机构名称优化卡顿
export function tree(res) {
	var data = [];
	var child=[];
	for (var i = 0; i < res.result.length; i++) {
		let temp = res.result[i]
		data.push({
			key: temp.key,
			value: temp.value,
			title: temp.title,
			isLeaf:temp.isLeaf,
			parentId: temp.parentId,
			children: []
		})
		if (temp.children) {
			for (var j = 0; j < temp.children.length; j++) {
				let temp2 = temp.children[j];
				data[i].children.push({
					key: temp2.key,
					value: temp2.value,
					title: temp2.title,
					isLeaf:temp2.isLeaf,
					parentId: temp2.parentId,
					children: []
				})
				if (temp2.children) {
					for (var k = 0; k < temp2.children.length; k++) {
						let temp3 = temp2.children[k];
						data[i].children[j].children.push({
							key: temp3.key,
							value: temp3.value,
							title: temp3.title,
							isLeaf:temp3.isLeaf,
							parentId: temp3.parentId,
						})
						if (temp3.children) {
							child.push({
								key: temp3.key,
								value: temp3.value,
								title: temp3.title,
								isLeaf:temp3.isLeaf,
								parentId: temp3.parentId,
								children: temp3.children
							})
						}
					}
				}
			}
		}
	
	}
	return {
		data:data,
		child:child
	}
}