使用 `freemarker` 模板解析功能造的一个轮子。用于生成 `Java实体类`、`mybatis` 基础SQL。

# 配置文件

```javascript
{
	// jdbc连接信息配置
  	"jdbcConnectionConfig": {
		"url": "jdbc:mysql://localhost:3306/test",
		"user": "root",
		"password": "root",
		"driverClassName": "com.mysql.jdbc.Driver"
	},
    // 全局配置
	"globalConfig": {
		"generateModelClass": true,// 是否要生成Java实体类
		"generateMybatisMapperClass": true,// 是否生成xxxMapper.java文件
		"generateMybatisXmlFile": true,// 是否生成xxxMapper.xml文件
		"baseDir": "/path/to/dest",// 模板生成的位置
        // 数据库列名和Java实体类名的转换策略,目前只有支持驼峰和下划线的转换策略
		"namingStrategyClassName": "cn.hylexus.db.helper.converter.naming.CamelNamingStrategy"
	},
    // 待解析的数据库表配置
	"tableConfig": [
		{
			"tableName": "t_user",// 表名
			"modelPackageName": "cn.hylexus.app.model",// 实体类包名
          	/* 
          	指定 tableName 对应的实体类名,非必须.
          	不指定时采用namingStrategyClassName生成对应的实体类名.
          	若指定了该选项,将会使用指定值而忽略命名策略
          	*/
			"modelName": "", // 非必须
            // 是否生成链式调用风格的setter方法
			"generateChainStyleStterMethod": true,
            // xxxMapper.java文件的包名
			"mapperClassPackageName": "cn.hylexus.app.mapper",
            /* 
          	指定实体类对应的xxxMapper.java文件名,非必须.
          	不指定时采用namingStrategyClassName生成对应的类名.
          	若指定了该选项,将会使用指定值而忽略命名策略
          	*/
			"mapperClassName": ""
		},
		{
          	// 最少的配置,只需指定表名即可
			"tableName": "t_order"
		}
	],
    // 针对于实体类的全局配置
	"modelConfig": {
        // 该选项可能被 tableConfig.modelPackageName 覆盖
		"packageName": "cn.hylexus.app.model",
		"trimmedPrefix": "t_",// 非必须,去掉表的前缀
		"appendedPrefix": "",// 非必须,为实体类名添加的前缀
		"appendedSufix": "PO",// 非必须,为实体类名添加的后缀
        // 可能会被 tableConfig.generateChainStyleStterMethod 覆盖
		"generateChainStyleStterMethod": true,
		"overrideIfExists": true,
		"namingStrategyClassName": "cn.hylexus.db.helper.converter.naming.CamelNamingStrategy"
	},
    // 针对于 xxxMapper.java 的全局配置
	"mapperClassConfig": {
		"packageName": "com.hylexus.app.mapper",
		"appendedSufix": "Mapper",
		"overrideIfExists": true,
		"generateSelectMethodByPrimaryKey": true,
		"generateInsertMethod": true,
		"generateUpdateMethod": true,
		"generateDeleteMethodByPrimaryKey": true,
		"generateFinalAllMethod": true
	}
}
```

# 打包

```shell
mvn clean package assembly:assembly
```

# 运行

```shell
java -jar db-helper-0.0.1-SNAPSHOT.jar /path/to/generator-config.json
```

