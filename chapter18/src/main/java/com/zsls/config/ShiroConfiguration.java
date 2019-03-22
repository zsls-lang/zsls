package com.zsls.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *@ClassName ShiroConfiguration
 *@Description Shiro 配置
 * Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 * 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限
 *@Author zsls
 *@Date 2019/3/21 16:25
 *@Version 1.0
 */
@Configuration
public class ShiroConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShiroConfiguration.class);

	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		return ehCacheManager;
	}

	/**
	 * @Description //TODO  shiro 管理的生命周期
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}


	/**
	 * 加密器：这样一来数据库就可以是密文存储，为了演示我就不开启了
	 *
	 * @return HashedCredentialsMatcher
	 */
//	    @Bean
//	    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//	        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//	        //散列算法:这里使用MD5算法;
//	        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//	        //散列的次数，比如散列两次，相当于 md5(md5(""));
//	        hashedCredentialsMatcher.setHashIterations(2);
//	        return hashedCredentialsMatcher;
//	    }

	/**
	 * @Description //TODO
	 * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		autoProxyCreator.setProxyTargetClass(true);
		return autoProxyCreator;
	}

	@Bean(name = "customerRealm")
	public CustomerRealm customerRealm(EhCacheManager cacheManager) {
		CustomerRealm customerRealm = new CustomerRealm();
		customerRealm.setCacheManager(cacheManager);
//		customerRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return customerRealm;
	}

	@Bean(name = "securityManager")
	public SecurityManager securityManager(CustomerRealm customerRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(customerRealm);
		// <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
		securityManager.setCacheManager(ehCacheManager());
		return securityManager;
	}

	/**
	 * @Author zsls
	 * @Description
	 * /开启shiro aop注解支持.使用代理方式;所以需要开启代码支持;
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	/**
	 *  * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 *      * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
	 *      * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 *      * <p>
	 *      * Filter Chain定义说明
	 *      * 1、一个URL可以配置多个Filter，使用逗号分隔
	 *      * 2、当设置多个过滤器时，全部验证通过，才视为通过
	 *      * 3、部分过滤器可指定参数，如perms，roles
	 * ShiroFilter<br/>
	 * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
	 * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
	 *
	 * @param securityManager 安全管理器
	 * @return ShiroFilterFactoryBean
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
		//此处应是url并非静态资源位置
		shiroFilterFactoryBean.setLoginUrl("/login");

		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");

		//未授权页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/denied");
		loadShiroFilterChain(shiroFilterFactoryBean);

		return shiroFilterFactoryBean;
	}

	/**
	 * 加载shiroFilter权限控制规则（从数据库读取然后配置）
	 */
	private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
		/////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
		// TODO 重中之重啊，过滤顺序一定要根据自己需要排序
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

		//配置退出过滤器，其中的具体的退出代码shiro已经实现
		filterChainDefinitionMap.put("/logout", "logout");

		//过滤连定义，从上向下顺序执行，一般将/**放在最为下边 ！！！！！
		//authc：所有的url都必须认证通过才可以访问；anon:所有的url都可以匿名访问
		// 需要验证的写 authc 不需要的写 anon
		filterChainDefinitionMap.put("/resource/**", "anon");
		filterChainDefinitionMap.put("/install", "anon");
		filterChainDefinitionMap.put("/hello", "anon");

		// anon：它对应的过滤器里面是空的,什么都没做
		LOGGER.info("##################从数据库读取权限规则，加载到shiroFilter中##################");

		// 不用注解也可以通过 API 方式加载权限规则
		Map<String, String> permissions = new LinkedHashMap<>();
		permissions.put("/users/find", "perms[user:find]");
		filterChainDefinitionMap.putAll(permissions);
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	}


}
