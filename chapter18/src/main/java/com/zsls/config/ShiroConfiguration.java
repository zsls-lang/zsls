package com.zsls.config;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *@ClassName ShiroConfiguration
 *@Description Shiro 配置
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
	 * @Description //TODO 自动代理所有的advisor
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		autoProxyCreator.setProxyTargetClass(true);
		return autoProxyCreator;
	}

	@Bean(name = "customerRealm")
	public CustomerRealm customerRealm(EhCacheManager cacheManager) {
		CustomerRealm customerRealm = new CustomerRealm();
		customerRealm.setCacheManager(cacheManager);
		return customerRealm;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager(CustomerRealm customerRealm) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(customerRealm);
		// <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
		defaultWebSecurityManager.setCacheManager(ehCacheManager());
		return defaultWebSecurityManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	/**
	 * ShiroFilter<br/>
	 * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
	 * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
	 *
	 * @param securityManager 安全管理器
	 * @return ShiroFilterFactoryBean
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的连接
		shiroFilterFactoryBean.setSuccessUrl("/index");
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
