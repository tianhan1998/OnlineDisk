# OnlineDisk
一个小网盘
# 记录

## 第一天
重新配置ssm都花了半天，各种莫名其妙的问题，什么mybatis的mapper映射不上去，静态资源拦截等等，重新弄得我头大，我按照原来的demo一点点改，最后代码基本上都改成老的了，还是有错。最后直接新建新项目然后拉老的demo复制粘贴。所以代码里叫 **NewOnlineDisk**。idea新建的maven项目结构不能乱动，每次修改之后再reimport就会变回去。所以直接用默认的配置就好,静态资源拦截记得在是spring-mvc.xml里添加一个<mvc:default-servlet-handler/>就好。我的导入不进去实际是因为jsp里面引入js文件使用了相对路径，改成动态获取绝对路径就行了。

***
### 第一天实现
- 项目的登录和注册（包括cookie保持登陆状态）
- 文件的上传和下载
- 前端页面基本完成
***
另外第一天最后push的时候还出了一点小插曲，因为前端框架文件太多，我idea一开始忘记取消代码检查了，速度极慢，最后还漏文件没有push上来。重新push的时候总是push不上去，上网查新建了新的分支再push，最后merge入主分支就解决了。因为之前我写 **ojtools** 这个项目的时候就不小心给我写完的代码全部搞丢了，最后用了 **Revert** 这个命令恢复的，所以现在关于git的东西都比较小心，害怕一不小心把代码全部报销= =
## 第二天
第二天主要任务是增加了删除文件功能，另外对上传和下载进行了优化，之前会被人用url换参数偷东西，现在会验证好名字并用id而不是 ~~filename，username~~ 来下载指定文件，比较科学。
***
### 第二天实现
- 文件的删除功能
- 上传下载的重写
- 前端页面修正
***
今天加的功能不多，但是代码量比第一天要多。首先给删除使用了事务，删除数据库上你存的文件记录，并删除硬盘上实际对应的文件，如果文件删除失败，那么要回滚数据库，把删除的记录撤销。事务我是用的在类上的@Transactional注解来实现。一开始在controller层的事务一直失效，最后上网查才知道其实是在xml中配置的扫描事务注解 <tx:annotation-driven>的问题，一开始这个配置是在applicationContext.xml里的，但controller的注解扫描是在spring-mvc.xml里，所以不仅ApplicationContext.xml里面要配置，spring-mvc.xml也需要配置，因为这个是根据上下文中的注解扫描实现的。网上原文是这样的
> 一般而言，事务都是加在Service层的，也可以加在Controller层

> 在spring-framework-reference.pdf文档中有这样一段话：
>> <tx:annotation-driven/>only looks for @Transactional on beans in the same application context it is defined in. This means that, if you put
 **<tx:annotation-driven/>**
in a WebApplicationContext for a DispatcherServlet, it only checks for @Transactional beans in your controllers, and not your services. 

> 这句话的意思是<tx:annoation-driven/>只会查找和它在相同的应用上下文件中定义的bean上面的@Transactional注解，如果你把它放在Dispatcher的应用上下文中,它只检查控制器上的@Transactional注解，而不是你services上的@Transactional注解。于是，我将事务配置定义在Spring MVC的应用上下文(spring-mvc.xml)中。

除此之外，还有不少新发现，学习了不少知识，比如
### 输入输出流关闭
在删除文件时，发现返回false删除失败，上网查阅资料有几种可能性
> 1. 此文件被使用的时候无法删除（比如网络输出没关闭流）
> 2. 判断此文件是否存在再做删除（exists）
> 3. 删除文件夹之前先删除文件夹下的所有文件（递归解决）
> 4. 判断是否删除成功会有返回值，文件名错了的话，删除文件不会报错。（new File("x://123.txt"),但是123.txt不存在，不报错

我的情况是第一种，在使用下载功能后，再进行删除的话就会失败。所以我去查看下载里是否关流。我实际上添加了流的关闭代码，但是不严谨，没有写到try-catch块里，我规范了一下代码，在try-catch块之前声明流，在块内赋值，finally内部关流。最后发现输出流没有关闭，报错

**java.io.IOException: 你的主机中的软件中止了一个已建立的连接。**

网上查这个Exception很广，查到了一个贴跟我相关，大概原因应该是我点击了下载按钮但没有确认下载，取消了下载框后导致流没有关闭。网上关于这个问题只有一个原因，而没有解决方案，最后我是靠try-with-resources的语法糖解决的，在try（）括号内声明你的流，在代码结束后会自动关闭，

``` 
try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(res.getOutputStream()))
```

### Spring-mvc中@PathVariable的参数丢失问题
如果使用@PathVariable来获取url内的参数，那么参数中'.'后面的东西会消失

比如我的参数是文件名xxxx.zip

那么获取到的参数filename=xxxx

'.'后面的东西没了，要解决这个，最简单的就是直接用@RequestParam，但是get的url略不美观

解决方法是这样，在@RequestMapping注解内修改，@RequestMapping("/DownLoad/{filename:.*}")

参数后面加上:.*就可，记住要重新部署项目才行，不能直接更新classes和resource，因为懒得等在这里卡了一会233

#### 总结下两天学到的东西
1. 静态资源导入要动态获取绝对路径
2. @Pathvariable会丢参数
3. @Transactional要放到不仅有扫描注解而且还要有扫描事务注解的xml对应的层上
4. try-with-resources语法糖不会关不上流
## 第三天
***
### 第三天实现
- ajax判断注册用户名是否重复
***
现学现造的，虽然功能很简单，但由于我用js和jquery的时候非常少，所以这个简单功能写了好几个小时，主要就是前端js老是写错，然后还没有什么提示，就得猜。其次就是对jquery的不熟悉，要操纵label的话，不能用val修改，要用html。而且选择器的#老是忘。有点头疼
