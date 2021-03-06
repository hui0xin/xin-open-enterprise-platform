package com.xin.mybatis.generator.plugins;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

public class CustomJavaMapperMethodGenerator extends AbstractJavaMapperMethodGenerator {

	@Override
	public void addInterfaceElements(Interface interfaze) {
		
		addInterfaceFind(interfaze);
		addInterfaceList(interfaze);
//		addInterfacePageList(interfaze);
	}
	
	private void addInterfaceFind(Interface interfaze) {
		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 添加Lsit的包
		importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
		// 创建方法对象
		Method method = new Method();
		// 设置该方法为public
		method.setVisibility(JavaVisibility.PUBLIC);
		// 设置返回类型是List
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		// 方法对象设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名称为我们在IntrospectedTable类中初始化的 “selectByObject”
		method.setName("find");

		// 设置参数类型是对象
		FullyQualifiedJavaType parameterType;
		parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		// import参数类型对象
		importedTypes.add(parameterType);
		// 为方法添加参数，变量名称record
		method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$
		//
		addMapperAnnotations(interfaze, method);
		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
		if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable)) {
			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	private void addInterfaceList(Interface interfaze) {
		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 添加Lsit的包
		importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
		// 创建方法对象
		Method method = new Method();
		// 设置该方法为public
		method.setVisibility(JavaVisibility.PUBLIC);
		// 设置返回类型是List
		FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
		FullyQualifiedJavaType listType;
		// 设置List的类型是实体类的对象
		listType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		importedTypes.add(listType);
		// 返回类型对象设置为List
		returnType.addTypeArgument(listType);
		// 方法对象设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名称为我们在IntrospectedTable类中初始化的 “selectByObject”
		method.setName("list");

		// 设置参数类型是对象
		FullyQualifiedJavaType parameterType;
		parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		// import参数类型对象
		importedTypes.add(parameterType);
		// 为方法添加参数，变量名称record
		method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$
		//
		addMapperAnnotations(interfaze, method);
		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
		if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable)) {
			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}
	
	private void addInterfacePageList(Interface interfaze) {
		// 先创建import对象
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		// 添加Page的包
		importedTypes.add(new FullyQualifiedJavaType("com.github.pagehelper.Page"));
		// 创建方法对象
		Method method = new Method();
		// 设置该方法为public
		method.setVisibility(JavaVisibility.PUBLIC);
		// 设置返回类型是List
		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("Page");
		FullyQualifiedJavaType listType;
		// 设置List的类型是实体类的对象
		listType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		importedTypes.add(listType);
		// 返回类型对象设置为List
		returnType.addTypeArgument(listType);
		// 方法对象设置返回类型对象
		method.setReturnType(returnType);
		// 设置方法名称为我们在IntrospectedTable类中初始化的 “selectByObject”
		method.setName("pageList");

		// 设置参数类型是对象
		FullyQualifiedJavaType parameterType;
		parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		// import参数类型对象
		importedTypes.add(parameterType);
		// 为方法添加参数，变量名称record
		method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$
		//
		addMapperAnnotations(interfaze, method);
		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
		if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable)) {
			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	public void addMapperAnnotations(Interface interfaze, Method method) {
	}
}
