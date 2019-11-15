package com.miracle.cloud.config;

import com.miracle.cloud.collection.BeanUtil;
import com.miracle.cloud.exception.ErrorCode;
import com.miracle.cloud.exception.ServiceException;
import com.miracle.cloud.result.RestResult;
import com.miracle.cloud.result.RestResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

/**
 * @author miracle_
 * Created at 2019-09-05 20:47
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public RestResult handleServiceException(ServiceException e) {
        log.error("Exception {}", e.getMessage(), e);
        return RestResultBuilder.builder().code(e.getCode()).msg(e.getMsg()).build();
    }


    @ExceptionHandler(Exception.class)
    public RestResult handleException(Exception e) {
        log.error("Exception {}", e.getMessage(), e);
        return RestResultBuilder.builder().code(ErrorCode.FAILED.getCode()).msg(ErrorCode.FAILED.getMsg()).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException {}", e.getMessage(), e);
        return RestResultBuilder.builder().code(ErrorCode.INVALID_PARAM.getCode()).msg(ErrorCode.INVALID_PARAM.getMsg()).build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RestResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException {},{}", e.getMessage(), e.getParameterName(), e);
        return RestResultBuilder.builder().code(ErrorCode.INVALID_PARAM.getCode()).msg(ErrorCode.INVALID_PARAM.getMsg()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        //解析原错误信息，封装后返回字段名称，原始值和错误信息
        List<FieldError> errorList = e.getBindingResult().getFieldErrors();
        if (BeanUtil.isNotEmpty(errorList)) {
            FieldError error = errorList.get(0);
            StringBuilder builder = new StringBuilder();
            builder.append("[ ").append(error.getField()).append(" ]").append(error.getDefaultMessage());
            builder.append(": ").append(error.getRejectedValue());
            //return ServerResponse.createByErrorMessage(builder.toString());
            log.error("MethodArgumentNotValidException {}", builder.toString(), e);
        }
        return RestResultBuilder.builder().code(ErrorCode.INVALID_PARAM.getCode()).msg(ErrorCode.INVALID_PARAM.getMsg()).build();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public RestResult handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("MaxUploadSizeExceededException {}", e.getMessage(), e);
        return RestResultBuilder.builder().code(ErrorCode.INVALID_UPLOAD_SIZE.getCode()).msg(ErrorCode.INVALID_UPLOAD_SIZE.getMsg()).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestResult handlerMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("Exception ==> {}", e.getMessage(), e);
        return RestResultBuilder.builder().code(ErrorCode.INVALID_REQUEST_METHOD.getCode()).msg(ErrorCode.INVALID_REQUEST_METHOD.getMsg()).build();
    }
}
