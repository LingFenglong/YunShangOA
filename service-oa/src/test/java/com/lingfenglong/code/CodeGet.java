package com.lingfenglong.code;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGet {

    public static void main(String[] args) {
        FastAutoGenerator.create(
                        "jdbc:mysql://localhost:3306/yunshang-oa?serverTimezone=GMT%2B8&useSSL=false&characterEncoding=utf-8",
                        "root", null)
                .globalConfig(builder -> {
                    builder.author("lingfenglong") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\University\\code\\java\\mycode\\YunShangOA\\YunShangOA\\service-oa\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.lingfenglong.auth") // 设置父包名
//                            .moduleName("YunShangOA") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    "D:\\University\\code\\java\\mycode\\YunShangOA\\YunShangOA\\service-oa\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
//                    builder.addInclude("sys_user") // 设置需要生成的表名
                    builder.addInclude("sys_user_role") // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_") // 设置过滤表前缀
                            .serviceBuilder().formatServiceFileName("%sService");
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
