# NCM2MP3-By-JAVA
网易云ncm音乐格式转换为mp3音乐格式工具

## 项目组成
项目分为三个版本
- NCM2MP3(Maven):该版本为IDEA中的Maven项目,需要得到Maven的支持,同时使用了Lombok包,需要下载Lombok;
- NCM2MP3:该版本为普通的Java项目,只需要配置jar包(lib目录下),便可以运行.
- NCM2MP3.jar:该版本为NCM2MP3的打包项目,所有环境均已经配置完成,只需要电脑上含有JDK环境,在命令行中便可以运行

## 运行说明
- NCM2MP3.jar:命令行中在该jar包的目录下执行java -jar NCM2MP3.jar
- 其他两个若要运行,在环境配置好后,执行入口为main.java

## 原理说明
  NCM格式是网易云音乐特有的音乐格式,这种音乐格式用到AES,CR4的加密算法对普通的音乐格式(如MP3,FLAC)进行加密,若要了解该加密过程,最好的方法就是知道起格式图,以及加密的原理(可以参考笔记`密码学.md`).
  
## 现在简述一下加密的过程
|          信息          |             大小              | 备注                                                         |
| :--------------------: | :---------------------------: | :----------------------------------------------------------- |
|      Magic Header      |           10 bytes            | 0x4354454e4644414d0170,验证即可                              |
|       KEY Length       |            4 bytes            | 用AES128加密CR4密钥后的长度(小端字节排序,无符号整型)         |
| KEY From AES128 Decode | KEY Length(其实就是128 bytes) | 用AES128加密的CR4密钥(注意:1.按字节对0x64异或2.AES解密(其中PKCS5Padding填充模式会去除末尾填充部分;)3.去除前面`neteasecloudmusic`17个字节; |
|      Mata Length       |            4 bytes            | Mata的信息的长度(小端字节排序,无符号整型)                    |
|    Mata Data(JSON)     |          Mata Length          | JSON的格式的Mata的信息(注意:1.按字节对0x63异或;2.去除前面`163 key(Don't modify):`22个字节;3.Base64进行decode;4.AES解密;5.去除前面`music:`6个字节后获得JSON) |
|       CRC校验码        |            4 bytes            | 跳过                                                         |
|          Gap           |            5 bytes            | 跳过                                                         |
|       Image Size       |            4 bytes            | 图片大小                                                     |
|         Image          |          Image Size           | 图片数据                                                     |
|       Music Data       |               -               | CR4-KSA生成s盒,CR4-PRGA解密                                  |

