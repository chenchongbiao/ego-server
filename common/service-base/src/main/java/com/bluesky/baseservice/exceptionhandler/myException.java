package com.bluesky.baseservice.exceptionhandler;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//生成get set方法
@AllArgsConstructor//生成有参数构造方法
@NoArgsConstructor//生成无参构造方法
public class myException extends RuntimeException {
    //@ApiModelProperty 表示对model属性的说明或者数据操作更改 。
    @ApiModelProperty(value = "状态码")
    private Integer code;//状态码
    private String msg;//异常信息

    @Override
    public String toString() {
        return "myException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }
}
