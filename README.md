# NCM2MP3
网易云ncm音乐格式转换为mp3音乐格式工具

## 环境准备
- JDK8.0
- 依赖构建工具 Maven
- 集成开发环境 IDEA(插件支持:Lombok)

## 运行说明

```text

1. 使用NCM2MP3.jar运行图形界面(只需要准备jdk环境便可以):命令行中在该jar包的目录下执行`java -jar NCM2MP3.jar`
2. 用源代码运行:在环境配置好后,执行入口为`main.java`
3. 命令行相关操作`java -jar NCM2MP3.jar [command]`
Usage: java -jar NCM2MP3.jar [command]
If don't add command, there will open NCM2MP3 GUI directly
[Command List]
-v,-view                      : open NCM View GUI(default command)
-c,--convert [path] ...       : convert NCM File in path to ./output directory
-h,-help                      : Help about any command
```


## 原理说明
  NCM格式是网易云音乐特有的音乐格式,这种音乐格式用到AES,RC4的加密算法对普通的音乐格式(如MP3,FLAC)进行加密,若要了解该加密过程,最好的方法就是知道起格式图,以及加密的原理(可以参考笔记`密码学.md`).

## 现在简述一下加密的过程
|          信息          |             大小              | 备注                                                         |
| :--------------------: | :---------------------------: | :----------------------------------------------------------- |
|      Magic Header      |           10 bytes            | 跳过                              |
|       KEY Length       |            4 bytes            | 用AES128加密RC4密钥后的长度(小端字节排序,无符号整型)         |
| KEY From AES128 Decode | KEY Length(其实就是128 bytes) | 用AES128加密的RC4密钥(注意:1.按字节对0x64异或2.AES解密(其中PKCS5Padding填充模式会去除末尾填充部分;)3.去除前面`neteasecloudmusic`17个字节; |
|      Mata Length       |            4 bytes            | Mata的信息的长度(小端字节排序,无符号整型)                    |
|    Mata Data(JSON)     |          Mata Length          | JSON的格式的Mata的信息(注意:1.按字节对0x63异或;2.去除前面`163 key(Don't modify):`22个字节;3.Base64进行decode;4.AES解密;5.去除前面`music:`6个字节后获得JSON) |
|       CRC校验码        |            4 bytes            | 跳过                                                         |
|          Gap           |            5 bytes            | 跳过                                                         |
|       Image Size       |            4 bytes            | 图片大小                                                     |
|         Image          |          Image Size           | 图片数据                                                     |
|       Music Data       |               -               | RC4-KSA生成s盒,RC4-PRGA解密                                  |

## 项目构成说明
- executor:控制管理
  - ConvertTask.java 对应每一个音乐转换的任务(消费者)
  - AsyncTaskExecutor.java 线程池,双空判断懒加载模式,核心线程10个，最大线程数20个，队列长度为100
- service:音乐格式转换核心功能实现
  - Converter.java 将NCM音乐解密拆分(==如果想快速看懂这个项目:建议从这个类开始看==), 将分析的各个数据整合到一起
  - Interpreter: 命令行参数解析器(策略模式分配命令处理)
    - ConvertCommand,HelpCommand,ViewCommand: 现在支持的3种命令
- mime 封装的数据类型
  - MATA.java 音乐头部信息
  - NCM.java 音乐输入输出信息等基本信息
- utils 工具类
  - AES.java AES解密的实现(ECB加密模式,PKCS5Padding填充模式)
  - RC4.java RC4解密的实现(这算法本质就是打乱顺序,需要注意的byte是1个字节且无符号的,以及对其取整操作(&0xff))
  - Utils.java 杂七杂八的工具(有注释说明)
- View 视图
  - view.java 用的Swing做的视图(Flatlaf这个jar包中有皮肤,所以看起来还不错.以后有机会学学javaFX..)
-main.java 

## 效果
- 打开界面

![](https://github.com/charlotte-xiao/NCM2MP3/blob/master/image/picture1.png)

- 准备转换

![](https://github.com/charlotte-xiao/NCM2MP3/blob/master/image/picture2.png)

- 转换成功

![](https://github.com/charlotte-xiao/NCM2MP3/blob/master/image/picture3.png)

## 更多
- 密码学.md:关于密码学相关知识不懂的可以查看该文档

## 声明
此工具仅供学习用途，请勿使用其盈利！
