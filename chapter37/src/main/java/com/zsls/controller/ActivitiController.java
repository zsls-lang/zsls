//package com.zsls.controller;
//
//import com.zsls.config.SecurityUtil;
//import org.activiti.api.process.model.ProcessDefinition;
//import org.activiti.api.process.model.ProcessInstance;
//import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
//import org.activiti.api.process.runtime.ProcessRuntime;
//import org.activiti.api.runtime.shared.query.Page;
//import org.activiti.api.runtime.shared.query.Pageable;
//import org.activiti.api.task.model.Task;
//import org.activiti.api.task.model.builders.TaskPayloadBuilder;
//import org.activiti.api.task.runtime.TaskRuntime;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RestController
//@RequestMapping("/activiti")
//public class ActivitiController {
//    @Autowired
//    private ProcessRuntime processRuntime;
//    @Autowired
//    private TaskRuntime taskRuntime;
//    @Autowired
//    private SecurityUtil securityUtil;
//    /**
//     * 查询流程定义
//     */
//    @RequestMapping("/getProcess")
//    public void getProcess(){
//        //查询所有流程定义信息
//        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
//        System.out.println("当前流程定义的数量："+processDefinitionPage.getTotalItems());
//        //获取流程信息
//        for (ProcessDefinition processDefinition:processDefinitionPage.getContent()) {
//            System.out.println("流程定义信息"+processDefinition);
//        }
//    }
//
//    /**
//     * 启动流程示例
//     */
//    @RequestMapping("/startInstance")
//    public void startInstance(){
//        ProcessInstance instance = processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey("demo").build());
//        System.out.println(instance.getId());
//    }
//
//    /**
//     * 获取任务，拾取任务，并且执行
//     */
//    @RequestMapping("/getTask")
//    public void getTask(){
//        securityUtil.logInAs("salaboy");        //指定组内任务人
//        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
//        if(tasks.getTotalItems()>0){
//            for (Task task:tasks.getContent()) {
//                System.out.println("任务名称："+task.getName());
//                //拾取任务
//                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
//                //执行任务
//                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
//            }
//        }
//    }
//}