import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@RestControllerAdvice(basePackages = "com.jvz.ylxh.controller")
public class GlobalExceptionHandler {

    /**
     * @description 捕获异常，统一返回值
     */
    @ResponseStatus
    @ExceptionHandler(Exception.class)
    public Result globalException(Exception e) {
        return Result.error("服务异常，请联系管理员。");
    }

    /**
     * @description 捕获运行异常
     */
    @ResponseStatus
    @ExceptionHandler(RuntimeException.class)
    public Result exceptionHandler(RuntimeException e) {
        return Result.error(e.getMessage());
    }

    /**
     * @description 处理请求参数格式错误
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class, TypeMismatchException.class})
    public Result paramsExceptionHandler(Exception e) {
        return Result.error("参数格式异常，请联系开发人员。");
    }

    /**
     * @description 处理@Validated参数校验失败异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, ValidationException.class, ConstraintViolationException.class})
    public Result paramsValidExceptionHandler(Exception e) {
        List<String> msgs = Lists.newArrayList();
        if (e instanceof BindException) {
            BindException be = (BindException) e;
            BindingResult result = be.getBindingResult();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(x -> {
                    FieldError fieldError = (FieldError) x;
                    log.error("Bad Request Parameters: dto entity [{}],field [{}],message [{}]",
                            fieldError.getObjectName(),
                            fieldError.getField(),
                            fieldError.getDefaultMessage());
                    msgs.add(fieldError.getDefaultMessage());
                });
            }
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) e;
            cve.getConstraintViolations().forEach(x -> msgs.add(x.getMessage()));
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manve1 = (MethodArgumentNotValidException) e;
            manve1.getBindingResult().getAllErrors().forEach(x -> msgs.add(x.getDefaultMessage()));
        }
        return Result.error(String.join(", ", msgs));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handleNoHandlerFoundException(NoHandlerFoundException e) {
        return Result.error("未找到匹配的请求处理器，请检查请求路径。");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return Result.error("缺少必要的请求参数，请检查后重试。");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.error("请求方法不支持，请检查请求方法。");
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return Result.error("请求媒体类型不支持，请检查 Content-Type 头。");
    }

    /**
     * @description 捕获自定义异常，自定义返回值。
     */
    @ResponseStatus
    @ExceptionHandler(BizException.class)
    public Result exceptionHandler(BizException e) {
        return Result.error(e.getCode(), e.getMessage());
    }
}
