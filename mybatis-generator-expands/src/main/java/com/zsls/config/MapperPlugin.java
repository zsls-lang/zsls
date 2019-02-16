package com.zsls.config;


import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.JDBCConnectionFactory;
import org.mybatis.generator.internal.util.StringUtility;
import tk.mybatis.mapper.generator.FalseMethodPlugin;
import tk.mybatis.mapper.generator.MapperCommentGenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * 通用Mapper生成器插件
 *
 * @author wdong
 */
public class MapperPlugin extends FalseMethodPlugin {
	private Set<String> mappers                   = new HashSet<String>();
	private boolean     caseSensitive             = false;
	private boolean     useMapperCommentGenerator = true;
	//开始的分隔符，例如mysql为`，sqlserver为[
	private String      beginningDelimiter        = "";
	//结束的分隔符，例如mysql为`，sqlserver为]
	private String      endingDelimiter           = "";
	//数据库模式
	private String                        schema;
	//注释生成器
	private CommentGeneratorConfiguration commentCfg;
	//强制生成注解
	private boolean                       forceAnnotation;

	//注释生成器类
	private String commentGeneratorType;

	public String getDelimiterName(String name) {
		StringBuilder nameBuilder = new StringBuilder();
		if (StringUtility.stringHasValue(schema)) {
			nameBuilder.append(schema);
			nameBuilder.append(".");
		}
		nameBuilder.append(beginningDelimiter);
		nameBuilder.append(name);
		nameBuilder.append(endingDelimiter);
		return nameBuilder.toString();
	}

	/**
	 * 生成的Mapper接口
	 *
	 * @param interfaze
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		//获取实体类
		FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		//import接口
		for (String mapper : mappers) {
			interfaze.addImportedType(new FullyQualifiedJavaType(mapper));
			interfaze.addSuperInterface(new FullyQualifiedJavaType(mapper + "<" + entityType.getShortName() + ">"));
		}
		//import实体类
		interfaze.addImportedType(entityType);
		return true;
	}

	/**
	 * 处理实体类的包和@Table注解
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 */
	private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		//引入JPA注解
		topLevelClass.addImportedType("javax.persistence.*");
		String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
		//如果包含空格，或者需要分隔符，需要完善
		if (StringUtility.stringContainsSpace(tableName)) {
			tableName = context.getBeginningDelimiter()
					+ tableName
					+ context.getEndingDelimiter();
		}
		//是否忽略大小写，对于区分大小写的数据库，会有用
		if (caseSensitive && !topLevelClass.getType().getShortName().equals(tableName)) {
			topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
		} else if (!topLevelClass.getType().getShortName().equalsIgnoreCase(tableName)) {
			topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
		} else if (StringUtility.stringHasValue(schema)
				|| StringUtility.stringHasValue(beginningDelimiter)
				|| StringUtility.stringHasValue(endingDelimiter)) {
			topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
		} else if (forceAnnotation) {
			topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
		}
	}

	/**
	 * 生成基础实体类
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		addModelClassComment(topLevelClass, introspectedTable);
		processEntityClass(topLevelClass, introspectedTable);

		return true;
	}

	/**
	 * 生成实体类注解KEY对象
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		processEntityClass(topLevelClass, introspectedTable);

		return true;
	}

	/**
	 * 生成带BLOB字段的对象
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		processEntityClass(topLevelClass, introspectedTable);
		return false;
	}


	@Override
	public void setContext(Context context) {
		super.setContext(context);
		//设置默认的注释生成器
		useMapperCommentGenerator = !"FALSE".equalsIgnoreCase(context.getProperty("useMapperCommentGenerator"));
		if (useMapperCommentGenerator) {
			commentCfg = new CommentGeneratorConfiguration();
			commentGeneratorType =  context.getCommentGeneratorConfiguration().getConfigurationType();
			//设置自定义的注释生成器
			if (commentGeneratorType!=null) {
				commentCfg.setConfigurationType(commentGeneratorType);
			}
			else
			{
				commentCfg.setConfigurationType(MapperCommentGenerator.class.getCanonicalName());
			}
			context.setCommentGeneratorConfiguration(commentCfg);
		}
		//支持oracle获取注释#114
		context.getJdbcConnectionConfiguration().addProperty("remarksReporting", "true");
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		String mappers = this.properties.getProperty("mappers");
		if (StringUtility.stringHasValue(mappers)) {
			for (String mapper : mappers.split(",")) {
				this.mappers.add(mapper);
			}
		} else {
			throw new RuntimeException("Mapper插件缺少必要的mappers属性!");
		}


		String caseSensitive = this.properties.getProperty("caseSensitive");
		if (StringUtility.stringHasValue(caseSensitive)) {
			this.caseSensitive = caseSensitive.equalsIgnoreCase("TRUE");
		}
		String forceAnnotation = this.properties.getProperty("forceAnnotation");
		if (StringUtility.stringHasValue(forceAnnotation)) {
			if (useMapperCommentGenerator) {
				commentCfg.addProperty("forceAnnotation", forceAnnotation);
			}
			this.forceAnnotation = forceAnnotation.equalsIgnoreCase("TRUE");
		}
		String beginningDelimiter = this.properties.getProperty("beginningDelimiter");
		if (StringUtility.stringHasValue(beginningDelimiter)) {
			this.beginningDelimiter = beginningDelimiter;
		}
		String endingDelimiter = this.properties.getProperty("endingDelimiter");
		if (StringUtility.stringHasValue(endingDelimiter)) {
			this.endingDelimiter = endingDelimiter;
		}
		String schema = this.properties.getProperty("schema");
		if (StringUtility.stringHasValue(schema)) {
			this.schema = schema;
		}

		if (useMapperCommentGenerator) {
			commentCfg.addProperty("beginningDelimiter", this.beginningDelimiter);
			commentCfg.addProperty("endingDelimiter", this.endingDelimiter);

		}
	}


	/***
	 * 为模型类添加注释
	 * @param topLevelClass
	 * @param introspectedTable
	 */
	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModel;");
		topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModelProperty;");
		topLevelClass.addJavaDocLine("");
		topLevelClass.addJavaDocLine("");
		String remarks = getRemaks(introspectedTable);
		StringBuilder sb = new StringBuilder();
		sb.append("@ApiModel(value = \"");
		sb.append(remarks);
		sb.append("\", description = \"");
		sb.append(remarks);
		sb.append("\")");
		topLevelClass.addJavaDocLine(sb.toString());
	}

	private String getRemaks(IntrospectedTable introspectedTable){
		String remarks = "";

		FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
		try {
			JDBCConnectionFactory jdbcConnectionFactory = new JDBCConnectionFactory(context.getJdbcConnectionConfiguration());
			Connection connection = jdbcConnectionFactory.getConnection();
			String sql = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_NAME='"+
					table.getIntrospectedTableName()+"'";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
//			ResultSet rs = connection.getMetaData().getTables(table.getIntrospectedCatalog(),
//					table.getIntrospectedSchema(), table.getIntrospectedTableName(), new String[] { "TABLE" });
			if (null != rs && rs.next()) {
//				remarks = rs.getString("REMARKS");
				remarks = rs.getString("TABLE_COMMENT");
			}
			closeConnection(connection, rs,statement);
		} catch (SQLException e) {}

		return remarks;
	}

	private void closeConnection(Connection connection, ResultSet rs,Statement statement) {

		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {}
		}
		if (null != statement) {
			try {
				statement.close();
			} catch (SQLException e) {}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {}
		}

	}
	//=====================================================================
	private String servicePack;
	private String serviceImplPack;
	private String project;
	private String pojoUrl;
	private FullyQualifiedJavaType autowired;
	private FullyQualifiedJavaType service;
	private FullyQualifiedJavaType slf4jLogger;
	private FullyQualifiedJavaType slf4jLoggerFactory;
	private FullyQualifiedJavaType serviceType;
	private FullyQualifiedJavaType daoType;
	private FullyQualifiedJavaType interfaceType;
	private FullyQualifiedJavaType pojoType;
	private FullyQualifiedJavaType listType;
	private FullyQualifiedJavaType baseServiceImplPackage;
	private FullyQualifiedJavaType baseServiceImpl;
	private FullyQualifiedJavaType baseServicePackage;
	private FullyQualifiedJavaType baseService;
	private FullyQualifiedJavaType controller;
	private FullyQualifiedJavaType controllerType;
	private FullyQualifiedJavaType baseController;
	private FullyQualifiedJavaType baseControllerPackage;
	private String controllerPack;
	private String controllerProject;

	private boolean enableCreateService = false;
	private boolean enableCreateController = false;

	/**
	 * 读取配置文件
	 */
	@Override
	public boolean validate(List<String> warnings) {
		this.servicePack = properties.getProperty("targetPackage");
		this.serviceImplPack = this.servicePack+".impl";
		this.project = properties.getProperty("targetProject");
		this.pojoUrl = context.getJavaModelGeneratorConfiguration().getTargetPackage();

		autowired = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
		service = new FullyQualifiedJavaType("org.springframework.stereotype.Service");
		controller=new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController");
		slf4jLogger = new FullyQualifiedJavaType("org.slf4j.Logger");
		slf4jLoggerFactory = new FullyQualifiedJavaType("org.slf4j.LoggerFactory");


		this.controllerPack = properties.getProperty("controllerTargetPackage");
		this.controllerProject = properties.getProperty("controllerTargetProject");




		String enableCreateService = properties.getProperty("enableCreateService");
		if (StringUtility.stringHasValue(enableCreateService)){
			this.enableCreateService = StringUtility.isTrue(enableCreateService);
		}

		String enableCreateController = properties.getProperty("enableCreateController");
		if (StringUtility.stringHasValue(enableCreateController)){
			this.enableCreateController = StringUtility.isTrue(enableCreateController);
		}

		return true;
	}

	/**
	 *
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {


		List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
		// 取Service名称【com.coolead.service.PetService】
		String table = introspectedTable.getBaseRecordType();
		String tableName = table.replaceAll(this.pojoUrl + ".", "");
		if(enableCreateService) {
			String baseServiceImpl = properties.getProperty("baseServiceImpl");
			String baseServiceImplPackage = properties.getProperty("baseServiceImplPackage");
			String baseService = properties.getProperty("baseService");
			String baseServicePackage = properties.getProperty("baseServicePackage");

			if(StringUtility.stringHasValue(baseServiceImplPackage)) {
				this.baseServiceImplPackage = new FullyQualifiedJavaType(baseServiceImplPackage);
			}
			if(StringUtility.stringHasValue(baseServiceImpl)) {
				this.baseServiceImpl = new FullyQualifiedJavaType(baseServiceImpl+"<"+tableName+">");
			}

			if(StringUtility.stringHasValue(baseServicePackage)) {
				this.baseServicePackage = new FullyQualifiedJavaType(baseServicePackage);
			}

			if(StringUtility.stringHasValue(baseService)) {
				this.baseService = new FullyQualifiedJavaType(baseService+"<"+tableName+">");
			}

			interfaceType = new FullyQualifiedJavaType(servicePack + ".I" + tableName + "Service");

			// 【com.coolead.mapper.PetMapper】
			daoType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());

			// 【com.coolead.service.impl.PetServiceImpl】logger.info(toLowerCase(daoType.getShortName()));
			serviceType = new FullyQualifiedJavaType(serviceImplPack + "." + tableName + "ServiceImpl");
			// 【com.coolead.domain.Pet】
			pojoType = new FullyQualifiedJavaType(pojoUrl + "." + tableName);
			listType = new FullyQualifiedJavaType("java.util.List");
			Interface interface1 = new Interface(interfaceType);
			TopLevelClass topLevelClass = new TopLevelClass(serviceType);

			// 导入必须的类
			addImport(interface1, topLevelClass);
			// 接口
			addService(interface1, introspectedTable, tableName, files);
			// 实现类
			addServiceImpl(topLevelClass, introspectedTable, tableName, files);
//			addLogger(topLevelClass);
		}
		if(enableCreateController){

			String baseController = properties.getProperty("baseController");
			String baseControllerPackage = properties.getProperty("baseControllerPackage");

			if(StringUtility.stringHasValue(baseControllerPackage)) {
				this.baseControllerPackage = new FullyQualifiedJavaType(baseControllerPackage);
			}
			if(StringUtility.stringHasValue(baseController)) {
				this.baseController = new FullyQualifiedJavaType(baseController);
			}

			controllerType = new FullyQualifiedJavaType(controllerPack + "." + tableName + "Controller");
			TopLevelClass topLevelClass = new TopLevelClass(controllerType);

			//add import 导入必须的类
			addImport(topLevelClass);

			//controller类
			addController(topLevelClass, introspectedTable, tableName, files);
		}
		return files;
	}

	/**
	 * import must class
	 */
	private void addImport(Interface interfaces, TopLevelClass topLevelClass) {
		interfaces.addImportedType(pojoType);

//		interfaces.addImportedType(pojoCriteriaType);
//		interfaces.addImportedType(listType);
		topLevelClass.addImportedType(daoType);
		topLevelClass.addImportedType(interfaceType);
		topLevelClass.addImportedType(pojoType);

//		topLevelClass.addImportedType(pojoCriteriaType);
//		topLevelClass.addImportedType(listType);
		topLevelClass.addImportedType(slf4jLogger);
		topLevelClass.addImportedType(slf4jLoggerFactory);
		topLevelClass.addImportedType(service);
		topLevelClass.addImportedType(autowired);
	}

	/**
	 * import must class
	 */
	private void addImport(TopLevelClass topLevelClass) {
		topLevelClass.addImportedType(controller);
		topLevelClass.addImportedType(autowired);
	}

	/**
	 * add interface
	 *
	 * @param tableName
	 * @param files
	 */
	protected void addService(Interface interface1,IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {

		interface1.setVisibility(JavaVisibility.PUBLIC);
		if(baseService!=null) {
			interface1.addSuperInterface(baseService);
			interface1.addImportedType(baseServicePackage);
		}
		//此外报错[已修2016-03-22，增加:"context.getJavaFormatter()"]
		GeneratedJavaFile file = new GeneratedJavaFile(interface1, project,context.getJavaFormatter());

		files.add(file);
	}

	/**
	 * add implements class
	 *
	 * @param introspectedTable
	 * @param tableName
	 * @param files
	 */
	protected void addServiceImpl(TopLevelClass topLevelClass,IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		// set implements interface
		topLevelClass.addSuperInterface(interfaceType);
		if(baseServiceImpl!=null) {
			topLevelClass.setSuperClass(baseServiceImpl);
			topLevelClass.addImportedType(baseServiceImplPackage);
		}
		topLevelClass.addAnnotation("@Service");
		topLevelClass.addImportedType(service);

		// add import dao
		addField(topLevelClass, tableName);
//		// add method
//		topLevelClass.addMethod(countByExample(introspectedTable, tableName));
//		topLevelClass.addMethod(selectByPrimaryKey(introspectedTable, tableName));
//		topLevelClass.addMethod(selectByExample(introspectedTable, tableName));
//
//		/**
//		 * type:  pojo 1 ;key 2 ;example 3 ;pojo+example 4
//		 */
//		if (enableDeleteByPrimaryKey) {
//			topLevelClass.addMethod(getOtherInteger("deleteByPrimaryKey", introspectedTable, tableName, 2));
//		}
//
//		if (enableUpdateByPrimaryKeySelective) {
//			topLevelClass.addMethod(getOtherInteger("updateByPrimaryKeySelective", introspectedTable, tableName, 1));
//		}
//
//		if (enableUpdateByPrimaryKey) {
//			topLevelClass.addMethod(getOtherInteger("updateByPrimaryKey", introspectedTable, tableName, 1));
//		}
//
//		if (enableDeleteByExample) {
//			topLevelClass.addMethod(getOtherInteger("deleteByExample", introspectedTable, tableName, 3));
//		}
//
//		if (enableUpdateByExampleSelective) {
//			topLevelClass.addMethod(getOtherInteger("updateByExampleSelective", introspectedTable, tableName, 4));
//		}
//
//		if (enableUpdateByExample) {
//			topLevelClass.addMethod(getOtherInteger("updateByExample", introspectedTable, tableName, 4));
//		}
//
//		if (enableInsert) {
//			topLevelClass.addMethod(getOtherInsertboolean("insert", introspectedTable, tableName));
//		}
//
//		if (enableInsertSelective) {
//			topLevelClass.addMethod(getOtherInsertboolean("insertSelective", introspectedTable, tableName));
//		}
		//此外报错[已修2016-03-22，增加:",context.getJavaFormatter()"]
		GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, project, context.getJavaFormatter());
		files.add(file);
	}

	/**
	 * @Description addController
	 * @Date 18:58 2019/2/15
	 * @Param [topLevelClass, introspectedTable, tableName, files]
	 * @return void
	 */
	protected void addController(TopLevelClass topLevelClass,IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);

		if(baseController!=null) {
			topLevelClass.setSuperClass(baseController);
			topLevelClass.addImportedType(baseControllerPackage);
		}

		topLevelClass.addAnnotation("@RestController");


		// add import service
		addControllerField(topLevelClass, tableName);
//		// add method

		GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, controllerProject, context.getJavaFormatter());
		files.add(file);
	}



	/**
	 * 添加字段
	 *
	 * @param topLevelClass
	 */
	protected void addField(TopLevelClass topLevelClass, String tableName) {
		// add dao
		Field field = new Field();
		field.setName(toLowerCase(daoType.getShortName())); // set var name
		topLevelClass.addImportedType(daoType);
		field.setType(daoType); // type
		field.setVisibility(JavaVisibility.PRIVATE);
		field.addAnnotation("@Autowired");
		topLevelClass.addField(field);
	}

	/**
	 * 添加字段
	 *
	 * @param topLevelClass
	 */
	protected void addControllerField(TopLevelClass topLevelClass, String tableName) {
		// add dao
		Field field = new Field();
		field.setName(toLowerCase(serviceType.getShortName())); // set var name
		topLevelClass.addImportedType(serviceType);
		field.setType(serviceType); // type
		field.setVisibility(JavaVisibility.PRIVATE);
		field.addAnnotation("@Autowired");
		topLevelClass.addField(field);
	}



	/**
	 * import logger
	 */
	private void addLogger(TopLevelClass topLevelClass) {
		Field field = new Field();
		field.setFinal(true);
		field.setInitializationString("LoggerFactory.getLogger(" + topLevelClass.getType().getShortName() + ".class)"); // set value
		field.setName("logger"); // set var name
		field.setStatic(true);
		field.setType(new FullyQualifiedJavaType("Logger")); // type
		field.setVisibility(JavaVisibility.PRIVATE);
		topLevelClass.addField(field);
	}













	/**
	 * BaseUsers to baseUsers
	 *
	 * @param tableName
	 * @return
	 */
	protected String toLowerCase(String tableName) {
		StringBuilder sb = new StringBuilder(tableName);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		return sb.toString();
	}

	/**
	 * BaseUsers to baseUsers
	 *
	 * @param tableName
	 * @return
	 */
	protected String toUpperCase(String tableName) {
		StringBuilder sb = new StringBuilder(tableName);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return sb.toString();
	}


}

