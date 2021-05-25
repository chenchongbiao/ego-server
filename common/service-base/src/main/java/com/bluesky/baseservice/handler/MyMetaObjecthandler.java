package com.bluesky.baseservice.handler;



import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

// MetaObjectHandler接口自动更新创建时间和更新时间
//注入实体类
@Component
public class MyMetaObjecthandler implements MetaObjectHandler{
    //插入时间
    @Override
    public void insertFill(MetaObject metaObject) {
        //第一个参数是属性名称不是字段名称
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
    //修改时间
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

}
