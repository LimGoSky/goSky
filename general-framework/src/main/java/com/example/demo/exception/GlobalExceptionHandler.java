package com.example.demo.exception;

import com.example.demo.result.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResultBody bizExceptionHandler(HttpServletRequest req, BizException e) {
        logger.error("发生业务异常！原因是：{}", e.getErrorMsg());
        return ResultBody.error(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpServletRequest req, NullPointerException e) {
        logger.error("发生空指针异常！原因是:", e);
        return ResultBody.error(CommonEnum.BODY_NOT_MATCH.getResultCode(), CommonEnum.BODY_NOT_MATCH.getResultMsg());
    }


    /**
     * 处理其他异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("未知异常！原因是:", e);
        return ResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR.getResultCode(), CommonEnum.INTERNAL_SERVER_ERROR.getResultMsg());
    }


    // 捕获方法参数校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResultBody constraintViolationExceptionHandler(ConstraintViolationException e){
        Set<ConstraintViolation<?>> message = e.getConstraintViolations();
        HashMap<String, Object> map = new HashMap<>();
        message.stream().forEach(msg -> {
            String path = msg.getPropertyPath().toString();
            String field = path.substring(path.indexOf(".")+1);
            map.put(field,msg.getMessageTemplate());
        });
        return ResultBody.error(map);
    }

    // 捕获实体参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultBody resolveMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        HashMap<String, Object> map = new HashMap<>();
        allErrors.stream().forEach(error -> {
            FieldError fieldError = (FieldError) error;
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResultBody.error(map);
    }

}
