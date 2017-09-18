## 简介
基于s2jh4net框架创建的云充吧共享充电宝后端业务管理系统。
目前集成的功能有：
    数据操作审计 - 基于Hibernate Envers的专业数据操作审计支持
    报表功能展示 - 基于JasperReport的报表功能展示
    计划任务 - 基于Spring Schedule & Quartz的支持集群和实时动态的定时任务配置监控
    查询/分页/排序/分组汇总统计/Grid组件功能 - 功能强大丰富的表格Grid组件封装增强
    工作流引擎 - 基于Acitiviti的工作流引擎以支持业务流程处理
    
## 特殊事项
### Tomcat cache 警告问题：
在tomcat的/conf/context.xml中的Context中添加<Resources cachingAllowed="true" cacheMaxSize="102400"/>
### 无法自动创建表问题：
创建Bean尽量在原有包路径下创建，新创建包要配置很多地方，否则Hibernate的hbm2ddl功能不能正确在数据库中创建表
### 菜单目录问题：
菜单在Controller中的index方法上实现注解 @MenuData("业务模块:客户管理")@RequiresPermissions("业务模块:客户管理")即可
### 数据字典问题
将util.js 的getCacheDictDatasByType 方法url修正 /pub/data!dictDatas.json-> /pub/data/dictDatas
同时在controller中添加接口
## 打包注意事项
<packagingExcludes>WEB-INF/lib/*.jar</packagingExcludes>
在向服务器打包时加上，本地调试时 注释掉
application.properties 