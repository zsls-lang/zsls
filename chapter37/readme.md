参考: https://www.cnblogs.com/chx9832/p/12599492.html
###Activiti数据库构成：
        ACT_RE_*: 'RE'表示 repository。 这个前缀的表包含了流程定义和流程静态资源 （图片， 规则，等等）。
        ACT_RU_*: 'RU'表示 runtime。 这些运行时的表，包含流程实例，任务，变量，异步任务， 等运行中的数据。 Activiti 只在流程实例执行过程中保存这些数据， 在流程结束时就会删除这些记录。 这样运行时表可以一直很小速度很快。
        ACT_HI_*: 'HI'表示 history。 这些表包含历史数据，比如历史流程实例， 变量，任务等等。
        ACT_GE_*:    GE 表示general。通用数据， 用于不同场景下

 通过加载activiti.cfg.xml文件得到ProcessEngineConfiguration对象，通过ProcessEngineConfiguration对象可以得到ProcessEngine对象
        得到该对象后，可以通过流程引擎对象ProcessEngine来得到各种Service
###每一种Service接口有每个用途
            RepositoryService    activiti 的资源管理类
            RuntimeService        activiti 的流程运行管理类
            TaskService            activiti 的任务管理类
            HistoryService        activiti 的历史管理类
            ManagerService        activiti 的引擎管理类