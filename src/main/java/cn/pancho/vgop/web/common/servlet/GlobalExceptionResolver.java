package cn.pancho.vgop.web.common.servlet;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：pancho
 * @date ：Created in 2019/9/25 17:43
 * @description :异常处理类
 */
//@ControllerAdvice
public class GlobalExceptionResolver {

    Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionResolver.class);

    /**
     * 处理所有不可知异常
     *
     * @param e 异常
     * @return json结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public void handleException(Exception e) throws Exception {
        // 打印异常堆栈信息

        //System.out.println("============="+e.getMessage());
        //throw e;

    }

    /**
     * 处理所有业务异常
     *
     * @param e 业务异常
     * @return json结果
     */
    /*@ExceptionHandler(BusinessRuntimeException.class)
    @ResponseBody
    public ApiResult handleOpdRuntimeException(BusinessRuntimeException e) {
        // 不打印异常堆栈信息
        LOG.error(e.getMsg());
        return ApiResult.of(e.getResultCode());
    }*/

}
