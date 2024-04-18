package com.infinitus.bms_oa.oms.excetion;

/**
 * 业务异常,所有得业务异常都应该抛出本异常
 * @author szj 
 * @date 2018年4月28日 下午3:04:17
 */
public class BusinessException extends RuntimeException {
    
    public BusinessException() {
        super();
        
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        
    }
    
    public BusinessException(String message) {
        super(message);
        
    }
    
    public BusinessException(Throwable cause) {
        super(cause);
        
    }
    
}
