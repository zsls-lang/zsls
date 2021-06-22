package com.zsls;

import com.zsls.model.Holiday;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//https://www.cnblogs.com/chx9832/p/12643558.html

/**
 * 注意问题 bpmn中变量：
 *                 1、如果UEL 表达式中流程变量名不存在则报错。
 *                 2、如果UEL 表达式中流程变量值为空NULL，流程不按UEL 表达式去执行，而流程结束 。
 *                 3、如果UEL 表达式都不符合条件，流程结束
 *                 4、如果连线不设置条件，会走flow 序号小的那条线
 */
public class Demo03 {
    /**
     *
     * 1. 流程变量的含义：
     * 　　我们可以在业务系统和流程系统当中，通过流程变量来制定对应的分支，如：员工请假
     * 　　请假天数<=3那么部门经理审批后交给人事，如果>3那么部门经理审批结束后交给总经理审批，总经理结束后交给任务
     *
     * 2. 流程变量的数据类型：
     * 　　string,double,boolean,short,long,Integer,binary,date日期
     * 　　serializable，如果流程变量需要用到pojo当中的属性字段，则该pojo需要实现序列化接口
     *
     * 3. 流程变量的作用域
     * 　　1.默认情况下，流程变量存在整个流程实例当中，每一个环节都可以获取到该变量，称之为global，类似于Java全局变量
     * 　　2.任务和执行实例仅仅是针对一个任务和一个执行实例范围，范围没有流程实例大，称为local 变量。类似于Java局部变量
     *
     * 4.流程变量案例：
     * 　　 请假天数<=3那么部门经理审批后交给人事，如果>3那么部门经理审批结束后交给总经理审批，总经理结束后交给任务
     *
     */

    //4.1 定义流程图，在流程线上设置流程变量的条件

//4.2 定义POJO，要去实现serializable接口

// 4.3 流程部署
    /**
     * 流程部署
     */
    @Test
    public void deployment() {
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RepositoryService对象进行流程部署
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //进行部署,将对应的流程定义文件生成到数据库当中，作为记录进行保存
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/leave02.bpmn")     //加载流程文件
                .name("请假流程-流程变量")
                .key("leave02")//设置流程名称
                .deploy();                      //部署

        //输出部署信息
        System.out.println("流程名称：" + deployment.getName());
        System.out.println("流程ID：" + deployment.getId());
        System.out.println("流程Key：" + deployment.getKey());
    }

    //  4.4 启动流程实例

    /**
     * 启动流程实例
     */
    @Test
    public void startInstance() {
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RuntimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //设置流程变量
        Holiday holiday = new Holiday();
        holiday.setNum(3);
        //定义一个Map集合，存放流程变量的值
        Map<String, Object> assMap = new HashMap<>();
        assMap.put("holiday", holiday);
        //启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave02",assMap);
        //输出实例信息
        System.out.println("流程部署ID：" + processInstance.getDeploymentId());
        System.out.println("流程实例ID：" + processInstance.getId());
        System.out.println("活动ID：" + processInstance.getActivityId());
        System.out.println("流程实例启动成功~");
    }

    /**
     * 获取到流程实例ID，跟据流程实例设置流程变量的值
     * 执行分支任务之前,执行此方法设置值
     */
//    @Test
//    public void getInstanceVar(){
//        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        //获取RuntimeService
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        //设置流程变量
//        Holiday holiday = new Holiday();
//        holiday.setNum(3);
//        //设置流程变量
//        runtimeService.setVariable("52501","holiday",holiday);
//    }


    /**
     * 任务执行时，设置流程变量
     */
    @Test
    public void runTask() {
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取一个TaskService对象
        TaskService taskService = processEngine.getTaskService();
        //查询代办业务 createTaskQuery查询任务   taskCandidateOrAssigned查询任务执行者   processDefinitionKey：查询流程
        /**
         * taskCandidateOrAssigned匹配规则:1.Assigned   2.配置bpmn文件中定义的值
         * taskAssignee匹配规则:1.Assigned
         */
        /*List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned("lisi").processDefinitionKey("leave02").list();*/
        List<Task> list = taskService.createTaskQuery().taskAssignee("wangwu").processDefinitionKey("leave02").list();

        //分页：List<Task> list = taskService.createTaskQuery().taskAssignee("zhangsan").processDefinitionKey("leave02").listPage(i,j);
        for (Task task : list) {
            System.out.println("任务名称：" + task.getName());
            System.out.println("任务执行人：" + task.getAssignee());
            System.out.println("任务ID：" + task.getId());
//            //设置流程变量
//            Holiday holiday = new Holiday();
//            holiday.setNum(5);
//            //定义一个Map集合，存放流程变量的值
//            Map<String, Object> assMap = new HashMap<>();
//            assMap.put("holiday", holiday);
//            //处理任务
//            taskService.complete(task.getId(),assMap);

            //设置流程变量
            Holiday holiday = new Holiday();
            holiday.setNum(5);
            taskService.setVariable(task.getId(),"holiday",holiday);
            //处理任务
            taskService.complete(task.getId());
            System.out.println("流程实例ID：" + task.getProcessInstanceId());
        }
    }

    /**
     * 当业务流程结束后通过历史可以查看到已经走完的流程
     * 查看历史任务
     */
    @Test
    public void getHistory() {
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取HistoryService接口
        HistoryService historyService = processEngine.getHistoryService();
        //获取历史任务
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        //获取指定流程实例的任务
        historicActivityInstanceQuery.processInstanceId("52501");
        //获取任务列表
        List<HistoricActivityInstance> list = historicActivityInstanceQuery.list();
        for (HistoricActivityInstance ai : list) {
            System.out.println("任务节点ID："+ai.getActivityId());
            System.out.println("任务节点名称："+ai.getActivityName());
            System.out.println("流程实例ID信息："+ai.getProcessDefinitionId());
            System.out.println("流程实例ID信息："+ai.getProcessInstanceId());
            System.out.println("==============================");
        }
    }

}
