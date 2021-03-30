package com.xin.mybatis.generator.plugins.custom;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * 自定义生成 xml
 * 可以扩展很多方法
 */
public class CustomAbstractXmlElementGenerator extends AbstractXmlElementGenerator {

	@Override
	public void addElements(XmlElement parentElement) {
		// 增加baseQuery
		XmlElement sql = new XmlElement("sql");
		sql.addAttribute(new Attribute("id", "baseQuery"));
		//在这里添加where条件
        XmlElement selectTrimElement = new XmlElement("trim"); //设置trim标签
        selectTrimElement.addAttribute(new Attribute("prefix", "WHERE"));  
        selectTrimElement.addAttribute(new Attribute("prefixOverrides", "AND | OR")); //添加where和and
		StringBuilder sb = new StringBuilder();
		for(IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
			XmlElement selectNotNullElement = new XmlElement("if"); //$NON-NLS-1$
			sb.setLength(0);
			sb.append("null != ");
			sb.append(introspectedColumn.getJavaProperty());
			selectNotNullElement.addAttribute(new Attribute("test", sb.toString()));
			sb.setLength(0);
			// 添加and
			sb.append(" and ");
			// 添加别名t
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			// 添加等号
			sb.append(" = ");
			sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            selectNotNullElement.addElement(new TextElement(sb.toString()));
            selectTrimElement.addElement(selectNotNullElement);
		}
		sql.addElement(selectTrimElement);
		parentElement.addElement(sql);
		
		// 公用select
		sb.setLength(0);
		sb.append("select ");
		sb.append(" * ");
		sb.append("from ");
		sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
		TextElement selectText = new TextElement(sb.toString());
		
		// 公用include
		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "baseQuery"));
		
		// 增加find
		XmlElement find = new XmlElement("select");
		find.addAttribute(new Attribute("id", "selectByPrimaryKeySelective"));
		find.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		find.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
		find.addElement(selectText);
		find.addElement(include);
		parentElement.addElement(find);
		
		// 增加list
		XmlElement list = new XmlElement("select");
		list.addAttribute(new Attribute("id", "selectByPrimaryKeySelectiveList"));
		list.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		list.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
		list.addElement(selectText);
		list.addElement(include);
		parentElement.addElement(list);

	}

}
