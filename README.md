# **高速公路ETC入深圳数据**大数据可视化
给个动态演示图 前一段调一调可以变成实时的
!(gif)[https://ahuiblog.oss-cn-beijing.aliyuncs.com/github/%E5%A4%A7%E6%95%B0%E6%8D%AE%E6%BC%94%E7%A4%BA.gif]

# 后端 

后端使用SpringBoot+MybatisPlus+MySql

只有一些简单的CRUD

还用了一点Redis

数据库文件放到项目里了express.sql

自己导一下 然后配置一下文件就行

数据来源:https://opendata.sz.gov.cn/data/dataSet/toDataDetails/29200_00403621



# 前端

![LNIXYNQN8MPVRZVO17XMP3K](https://user-images.githubusercontent.com/60649120/148231284-6d335cf1-263f-42cd-a962-a916cc09ad17.png)


前端VUE套用Echarts

同学写的和后端配套 开箱即用

# python
从官方的API爬取数据以及简单的处理 数据已经转成sql文件了(自己爬的话需要先去申请appkey)
