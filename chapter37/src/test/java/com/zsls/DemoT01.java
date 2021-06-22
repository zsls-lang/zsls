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
import java.util.List;
import java.util.zip.ZipInputStream;

//https://www.cnblogs.com/chx9832/p/12620855.html
public class DemoT01 {

    /**
     * 压缩包的方式部署流程
     */
    @Test
    public void deploymentByZip(){
        //获取processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取一个RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //定义一个输入流，加载Zip文件
        InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("bpmn/holiday.zip");
        //定义一个ZIPInputStream对象
        ZipInputStream zipInputStream=new ZipInputStream(inputStream);
        //流程部署
        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name("请求审批流程")
                .key("holidayKey")
                .deploy();
        System.out.println("流程名称："+deploy.getName());

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
        List<ProcessDefinition> holiday = processDefinitionQuery.processDefinitionKey("myProcess_1").orderByProcessDefinitionVersion().desc().list();
        for (ProcessDefinition proDef:holiday) {
            System.out.println("流程定义ID："+proDef.getId());
            System.out.println("流程定义的名称："+proDef.getName());
            System.out.println("流程定义的Key："+proDef.getKey());
            System.out.println("流程定义的版本号："+proDef.getVersion());
        }
    }


    /**
     * 删除流程：
     * 假设在删除时，当前正在有该流程实例执行，那么会导致删除失败
     * 如果强制要求，则可以使用repositoryService.deleteDeployment("1",true); true代表级联删除
     */
    @Test
    public void deleteDeployment(){
        //获取processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取一个RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //删除流程
        repositoryService.deleteDeployment("35001",true);
    }


    /**
     * 从数据中读取资源文件到本地
     */
    @Test
    public void getResource() throws IOException {
        //获取processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取一个RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //获取到流程查询对象
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //获取到流程资源
        processDefinitionQuery.processDefinitionKey("myProcess_1");
        //获取单一结果
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
        //流程部署的ID
        String deploymentId = processDefinition.getDeploymentId();
        //获取到bpmnResource
        InputStream bpmnStream = repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());
        //获取到png
//        InputStream pngStream = repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());
        //构建输出流
        OutputStream bpmnOut = new FileOutputStream("D:\\test\\" + processDefinition.getResourceName());
//        OutputStream pngOut = new FileOutputStream("D:\\test\\" + processDefinition.getDiagramResourceName());
        //将数据输出到磁盘当中
        IOUtils.copy(bpmnStream,bpmnOut);
//        IOUtils.copy(pngStream,pngOut);
        //关流
//        pngOut.close();
        bpmnOut.close();
//        pngStream.close();
        bpmnStream.close();
    }


    /**
     * 启动流程实例和业务系统关联
     * 正常应该先添加数据到业务表当中，拿到当前添加数据的主键ID，通过启动流程实例，将主键ID交给BuessniessKkey
     * 代表和流程系统进行关联
     */
    @Test
    public void startInstance(){
        /**
         * 让张三在页面提交，然后拿到ID
         */


        /**
         * 创建张三请假的实例流程
         */
        //获取processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //生成流程
        ProcessInstance holiday = runtimeService.startProcessInstanceByKey("myProcess_1","2");

        /**
         * 张三提交请假审批
         */
        //获取一个TaskService对象
        TaskService taskService = processEngine.getTaskService();
        //获取张三的任务
        Task task = taskService.createTaskQuery().taskAssignee("zhangsan").processDefinitionKey("myProcess_1").singleResult();
        //任务审批
        taskService.complete(task.getId());
    }

    /**
     * 所有流程定义的挂起
     */
    @Test
    public void allSuspend(){
        //获取processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取repositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //获取流程定义
        ProcessDefinition holiday = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myProcess_1").singleResult();
        //获取到当前流程定义是否为暂停状态   suspended方法为true代表为暂停   false就是运行的
        boolean suspended = holiday.isSuspended();
        if(suspended){
            repositoryService.activateProcessDefinitionById(holiday.getId(),true,null);
            System.out.println("该流程定义激活");
        }else{
            repositoryService.suspendProcessDefinitionById(holiday.getId(),true,null);
            System.out.println("该流程定义暂停");
        }
    }

    /**
     * 单个流程实例的挂起
     */
    @Test
    public void singleSuspend(){
        //获取processEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RuntimeService对象
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId("2501").singleResult();
        //获取到当前流程定义是否为暂停状态   suspended方法为true代表为暂停   false就是运行的
        boolean suspended = processInstance.isSuspended();
        if(suspended){
            runtimeService.activateProcessInstanceById("2501");
            System.out.println("xx的请假流程激活");
        }else{
            runtimeService.suspendProcessInstanceById("2501");
            System.out.println("xx的请假流程挂起");
        }
    }


}
