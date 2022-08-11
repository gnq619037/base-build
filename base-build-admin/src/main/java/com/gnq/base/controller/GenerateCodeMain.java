package com.gnq.base.controller;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc:
 * @Author: guonanqing
 * @Date: 2022/3/8 9:26
 * @Version: 1.0
 */
public class GenerateCodeMain {

    public static String url = "jdbc:mysql://127.0.0.1:3306/high_concurrent?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8";

    public static String parentPackageUrl = "com.baomidou.mybatisplus.samples.generator";

    public static String author = "guonanqing";

    public static String outputDir = "D://";

    public static Map<OutputFile, String> map = new HashMap<>();

    static {
        map.put(OutputFile.serviceImpl, "D://code//");
        map.put(OutputFile.service, "D://code//");
        map.put(OutputFile.entity, "D://code//");
        map.put(OutputFile.controller, "D://code//");
        map.put(OutputFile.xml, "D://code//");
        map.put(OutputFile.mapper, "D://code//");
    }

    public static void main(String[] args) {
        FastAutoGenerator.create(url, "root", "")
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(outputDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(parentPackageUrl) // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .pathInfo(map); // 设置mapperXml生成路径
                })
//        Collections.singletonMap(OutputFile.serviceImpl, "D://")
                .strategyConfig(builder -> {
                    builder.addInclude("order_detail").addFieldPrefix(); // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀

                }).templateConfig(builder -> {
                    builder.disable(TemplateType.XML).xml("/templates/generatorConfig.xml");
                })
                .injectionConfig(builder -> {
                    builder.customFile(Collections.singletonMap("/templates/generatorConfig.xml.ftl", "/templates/mapper.xml.ftl"));
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

    public static void main2(String[] args) {

//        boolean fileOverride = false;
//        GlobalConfig config = new GlobalConfig();
//        String path = System.getProperty("user.dir");
//        config.setActiveRecord(true)
//                .setAuthor("qiDing")
//                .setOutputDir(path+"\\src\\main\\java\\")
//                .setBaseResultMap(true)
//                .setBaseColumnList(true)
//                .setFileOverride(fileOverride);
//        //****************************** resource ***************************************
//        DataSourceConfig dataSourceConfig = new DataSourceConfig();
//        dataSourceConfig.setDbType(DbType.MYSQL)
//                .setUrl(DB_URL)
//                .setUsername(USERNAME)
//                .setPassword(PASSWORD)
//                .setDriverName(DRIVER_NAME)
//                .setTypeConvert(new MySqlTypeConvert() {
//                    @Override
//                    public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
//                        System.out.println("转换类型：" + fieldType);
//                        //tinyint转换成Boolean
//                        if (fieldType.toLowerCase().contains("tinyint")) {
//                            return DbColumnType.BOOLEAN;
//                        }
//                        if (fieldType.toLowerCase().contains("datetime")) {
//                            return DbColumnType.DATE;
//                        }
//                        return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
//                    }
//                });
//
//        //****************************** Policy configuration ******************************************************
//        List<TableFill> tableFillList = new ArrayList<>();
//        tableFillList.add(new TableFill("gmt_modified", FieldFill.INSERT_UPDATE));
//        tableFillList.add(new TableFill("modifier_id", FieldFill.INSERT_UPDATE));
//        tableFillList.add(new TableFill("creator_id", FieldFill.INSERT));
//        tableFillList.add(new TableFill("gmt_create", FieldFill.INSERT));
//        tableFillList.add(new TableFill("available_flag", FieldFill.INSERT));
//        tableFillList.add(new TableFill("deleted_flag", FieldFill.INSERT));
//        tableFillList.add(new TableFill("sync_flag", FieldFill.INSERT));
//        StrategyConfig strategyConfig = new StrategyConfig();
//        strategyConfig.setCapitalMode(true)
//                .setEntityLombokModel(true)
//                .setNaming(NamingStrategy.underline_to_camel)
//                .setTableFillList(tableFillList)
//                .setInclude(TABLE_NAME);
//        new AutoGenerator().setGlobalConfig(config)
//                .setDataSource(dataSourceConfig)
//                .setStrategy(strategyConfig)
//                .setPackageInfo(
//                        new PackageConfig()
//                                .setParent(PACKAGE)
//                                .setController("controller")
//                                .setEntity("domain")
//                                .setMapper("dao")
//                                .setXml("dao")
//                )
//                .setTemplate(
//                        new TemplateConfig()
//                                .setServiceImpl("templates/serviceImpl.java")
//                )
//                .execute();
    }
}
