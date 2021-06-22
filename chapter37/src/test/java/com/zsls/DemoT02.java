package com.zsls;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

//https://www.cnblogs.com/chx9832/p/12642800.html
public class DemoT02 {

    /**
     * 1. 创建leave01.bpmn
     * Activiti中个人任务分配
     *             1.1分配任务负责人：
     *                 1.1.1 固定分配，每一个任务都是固定的人进行执行
     *                 1.1.2 表达式分配：
     *                     UEL表达式，统一表达式语言，在activiti当中支持两种形式，一种是value，另外一种是method形式
     *                 1.1.3 创建流程图，给每一个环节制定assignee值，通过UEL表达式分配任务负责人
     */

    /**  1.1.4.流程部署
     * 流程部署
     * `act_ge_bytearray` 流程定义的资源信息，包含bpmn和png流程文件信息
     * `act_re_deployment` 流程部署信息，包含流程名称,ID,Key等
     * `act_re_procdef` 流程定义信息
     */
    @Test
    public void deployment() {
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RepositoryService对象进行流程部署
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //进行部署,将对应的流程定义文件生成到数据库当中，作为记录进行保存
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/leave01.bpmn")     //加载流程文件
//                .addClasspathResource("bpmn/demo.png")
                .name("请假流程01")       //设置流程名称
                .key("leave01")
                .deploy();              //部署
        //输出部署信息
        System.out.println("流程名称：" + deployment.getName());
        System.out.println("流程ID：" + deployment.getId());
        System.out.println("流程Key：" + deployment.getKey());

    }


    /**
     * 流程定义信息的查看
     */
    @Test
    public void getProDef(){
        //获取processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取一个RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //得到流程定义查看对象ProcessDefinitionQuery
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //指定查看的流程   processDefinitionKey()指定流程定义的key值   orderByProcessDefinitionVersion根据流程定义版本号排序
        List<ProcessDefinition> holiday = processDefinitionQuery.processDefinitionKey("leave01").orderByProcessDefinitionVersion().desc().list();
        for (ProcessDefinition proDef:holiday) {
            System.out.println("流程定义ID："+proDef.getId());
            System.out.println("流程定义的名称："+proDef.getName());
            System.out.println("流程定义的Key："+proDef.getKey());
            System.out.println("流程定义的版本号："+proDef.getVersion());
        }
    }

    /**
     * 启动一个流程实例
     */
    @Test
    public void getInstance(){
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RuntimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //定义一个Map集合，存放assignee的值
        Map<String,Object> assMap=new HashMap<>();
        //assignee01 leave01.bpmn 设置的 ${assignee01}
        assMap.put("assignee01","longe");
        assMap.put("assignee02","anxin");
        assMap.put("assignee03","jiage");
        assMap.put("assignee04","qiangge");
        //启动一个流程实例
        ProcessInstance holiday = runtimeService.startProcessInstanceByKey("leave01", assMap);
        System.out.println("流程实例Name:"+holiday.getName());
    }



}
