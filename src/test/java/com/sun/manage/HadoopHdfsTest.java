package com.sun.manage;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

/**
 * @Description:hdfs api
 * @Author: sunzhao
 * @Create on: 2019-03-21 14:25
 */
@Slf4j
public class HadoopHdfsTest {

    FileSystem fs=null;

    @Before
    public void init() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://hadoop100:9000");
        conf.set("dfs.replication", "2");
        conf.set("dfs.block.size", "128m");
        /**
         * 参数优先级： 1、客户端代码中设置的值 2、classpath下的用户自定义配置文件 3、然后是jar中默认配置
         */
        // 获取一个hdfs的访问客户端
        fs = FileSystem.get(new URI("hdfs://hadoop100:9000"), conf, "root");
        log.debug("================获取资源=============");
    }

    @Before
    public void close() throws IOException {
        if(fs!=null){
            fs.close();
        }
        log.debug("================关闭资源=============");
    }

    @Test
    public void testMkdirAndDeleteAndRename() throws IOException {
        fs.mkdirs(new Path("/java-api/testDir/aaa"));
        FSDataOutputStream fsDataOutputStream = fs.create(new Path("/java-api/testDir/aaa/1.txt"));
        fsDataOutputStream.write(new String("hello 你好 dfs").getBytes());
        fsDataOutputStream.close();
    }

}
