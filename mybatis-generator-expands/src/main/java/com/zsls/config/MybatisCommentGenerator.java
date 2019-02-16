package com.zsls.config;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

public class MybatisCommentGenerator implements CommentGenerator
{
	private Properties properties;
	private Properties systemPro;
	private boolean suppressDate;
	private boolean suppressAllComments;
	private String beginningDelimiter;
	private String endingDelimiter;
	private boolean forceAnnotation;
	private String currentDateStr;

	public MybatisCommentGenerator() {
		this.beginningDelimiter = "";
		this.endingDelimiter = "";
		this.properties = new Properties();
		this.systemPro = System.getProperties();
		this.suppressDate = false;
		this.suppressAllComments = false;
		this.currentDateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	@Override
	public void addClassComment(final InnerClass innerClass, final IntrospectedTable introspectedTable) {
		if (this.suppressAllComments) {
			return;
		}
		final StringBuilder sb = new StringBuilder();
		innerClass.addJavaDocLine("/**111111111111");
		sb.append(" * ");
		sb.append(introspectedTable.getFullyQualifiedTable());
		sb.append(" ");
		sb.append(this.getDateString());
		innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
		innerClass.addJavaDocLine(" */");
	}

	@Override
	public void addClassComment(final InnerClass innerClass, final IntrospectedTable introspectedTable, final boolean markAsDoNotDelete) {
		if (this.suppressAllComments) {
			return;
		}
		final StringBuilder sb = new StringBuilder();
		innerClass.addJavaDocLine("/**222222222222");
		sb.append(" * ");
		sb.append(" * ");
		sb.append(introspectedTable.getFullyQualifiedTable());
		innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
		sb.setLength(0);
		sb.append(" * @author ");
		sb.append(this.systemPro.getProperty("user.name"));
		sb.append(" ");
		sb.append(this.currentDateStr);
		innerClass.addJavaDocLine(" */");
	}

	@Override
	public void addComment(final XmlElement xmlElement) {
	}

	@Override
	public void addConfigurationProperties(final Properties properties) {
		final String beginningDelimiter = properties.getProperty("beginningDelimiter");
		if (StringUtility.stringHasValue(beginningDelimiter)) {
			this.beginningDelimiter = beginningDelimiter;
		}
		final String endingDelimiter = properties.getProperty("endingDelimiter");
		if (StringUtility.stringHasValue(endingDelimiter)) {
			this.endingDelimiter = endingDelimiter;
		}
		final String forceAnnotation = properties.getProperty("forceAnnotation");
		if (StringUtility.stringHasValue(forceAnnotation)) {
			this.forceAnnotation = forceAnnotation.equalsIgnoreCase("TRUE");
		}
		this.properties.putAll(properties);
		this.suppressDate = StringUtility.isTrue(properties.getProperty("suppressDate"));
		this.suppressAllComments = StringUtility.isTrue(properties.getProperty("suppressAllComments"));
	}

	protected String getDateString() {
		String result = null;
		if (!this.suppressDate) {
			result = this.currentDateStr;
		}
		return result;
	}

	protected void addJavadocTag(final JavaElement javaElement, final boolean markAsDoNotDelete) {
		javaElement.addJavaDocLine(" *");
		final StringBuilder sb = new StringBuilder();
		sb.append(" * ");
		sb.append("@mbg.generated");
		if (markAsDoNotDelete) {
			sb.append(" do_not_delete_during_merge");
		}
		final String s = this.getDateString();
		if (s != null) {
			sb.append(' ');
			sb.append(s);
		}
		javaElement.addJavaDocLine(sb.toString());
	}

	@Override
	public void addEnumComment(final InnerEnum innerEnum, final IntrospectedTable introspectedTable) {
		if (this.suppressAllComments) {
			return;
		}
		final StringBuilder sb = new StringBuilder();
		innerEnum.addJavaDocLine("/**");
		sb.append(" * ");
		sb.append(introspectedTable.getFullyQualifiedTable());
		innerEnum.addJavaDocLine(sb.toString().replace("\n", " "));
		innerEnum.addJavaDocLine(" */");
	}

	@Override
	public void addFieldComment(final Field field, final IntrospectedTable introspectedTable) {
		if (this.suppressAllComments) {
			return;
		}
		final StringBuilder sb = new StringBuilder();
		field.addJavaDocLine("/** test1");
		sb.append(" * ");
		sb.append(introspectedTable.getFullyQualifiedTable());
		field.addJavaDocLine(sb.toString().replace("\n", " "));
		field.addJavaDocLine(" */");
	}

	@Override
	public void addFieldComment(final Field field, final IntrospectedTable introspectedTable, final IntrospectedColumn introspectedColumn) {
		if (this.suppressAllComments) {
			return;
		}
		final StringBuilder sb = new StringBuilder();
		sb.append("@ApiModelProperty(value = \"");
		sb.append(introspectedColumn.getRemarks().trim());
		sb.append("\")");
		field.addJavaDocLine(sb.toString());
		if (field.isTransient()) {
			field.addAnnotation("@Transient");
		}
		for (final IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
			if (introspectedColumn == column) {
				field.addAnnotation("@Id");
				break;
			}
		}
		String column2 = introspectedColumn.getActualColumnName();
		if (StringUtility.stringContainsSpace(column2) || introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled()) {
			column2 = introspectedColumn.getContext().getBeginningDelimiter() + column2 + introspectedColumn.getContext().getEndingDelimiter();
		}
		if (!column2.equals(introspectedColumn.getJavaProperty())) {
			field.addAnnotation("@Column(name = \"" + this.getDelimiterName(column2) + "\")");
		}
		else if (!StringUtility.stringHasValue(this.beginningDelimiter) && !StringUtility.stringHasValue(this.endingDelimiter)) {
			if (this.forceAnnotation) {
				field.addAnnotation("@Column(name = \"" + this.getDelimiterName(column2) + "\")");
			}
		}
		else {
			field.addAnnotation("@Column(name = \"" + this.getDelimiterName(column2) + "\")");
		}
		if (introspectedColumn.isIdentity()) {
			if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
				field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
			}
			else {
				field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
			}
		}
		else if (introspectedColumn.isSequenceColumn()) {
			final String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
			final String sql = MessageFormat.format(introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement(), tableName, tableName.toUpperCase());
			field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY, generator = \"" + sql + "\")");
		}
	}

	@Override
	public void addGeneralMethodComment(final Method method, final IntrospectedTable introspectedTable) {
		if (this.suppressAllComments) {
			return;
		}
	}

	@Override
	public void addJavaFileComment(final CompilationUnit compilationUnit) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		compilationUnit.addFileCommentLine("/*");
		compilationUnit.addFileCommentLine("*");
		compilationUnit.addFileCommentLine("* " + compilationUnit.getType().getShortName() + ".java");
		compilationUnit.addFileCommentLine("* Copyright(C) 2017-2020 ");
		compilationUnit.addFileCommentLine("* @date " + sdf.format(new Date()) + "");
		compilationUnit.addFileCommentLine("*/");
	}
	@Override
	public void addModelClassComment(final TopLevelClass topLevelClass, final IntrospectedTable introspectedTable) {
	}
	@Override
	public void addRootComment(final XmlElement xmlElement) {
	}

	@Override
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
			Set<FullyQualifiedJavaType> set) {

	}

	@Override
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

	}

	@Override
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

	}

	@Override
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

	}

	@Override
	public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable,
			Set<FullyQualifiedJavaType> set) {

	}

	@Override
	public void addSetterComment(final Method method, final IntrospectedTable introspectedTable, final IntrospectedColumn introspectedColumn) {
		if (this.suppressAllComments) {
			return;
		}
		method.addJavaDocLine("/**");
		final StringBuilder sb = new StringBuilder();
		sb.append(" * ");
		sb.append(introspectedColumn.getRemarks().trim());
		method.addJavaDocLine(sb.toString().replace("\n", " "));
		final Parameter parm = method.getParameters().get(0);
		sb.setLength(0);
		sb.append(" * @param ");
		sb.append(parm.getName());
		sb.append(" ");
		sb.append(introspectedColumn.getRemarks().trim());
		method.addJavaDocLine(sb.toString().replace("\n", " "));
		method.addJavaDocLine(" */");
	}

	@Override
	public void addGetterComment(final Method method, final IntrospectedTable introspectedTable, final IntrospectedColumn introspectedColumn) {
		if (this.suppressAllComments) {
			return;
		}
		method.addJavaDocLine("/**");
		final StringBuilder sb = new StringBuilder();
		sb.append(" * ");
		sb.append(introspectedColumn.getRemarks().trim());
		method.addJavaDocLine(sb.toString().replace("\n", " "));
		sb.setLength(0);
		sb.append(" * @return ");
		sb.append(introspectedColumn.getActualColumnName());
		sb.append(" ");
		sb.append(introspectedColumn.getRemarks().trim());
		method.addJavaDocLine(sb.toString().replace("\n", " "));
		method.addJavaDocLine(" */");
	}

	public String getDelimiterName(final String name) {
		final StringBuilder nameBuilder = new StringBuilder();
		nameBuilder.append(this.beginningDelimiter);
		nameBuilder.append(name);
		nameBuilder.append(this.endingDelimiter);
		return nameBuilder.toString();
	}
}
