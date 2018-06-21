package com.sun.manage.entity.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**推送记录类
 * Created by huangdan on 2018/4/27.
 */
@Getter
@Setter
@Document(collection="push_record")
public class PushRecord {

    @Id
    private String id;

    private String subject;

    private String devId;

    private int messageId;

    private String content;

    private int qosLevel;

    private boolean pushResult;

    private Long time;
}
