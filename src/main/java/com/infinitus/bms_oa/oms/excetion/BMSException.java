package com.infinitus.bms_oa.oms.excetion;

/**
 * 业务异常,所有得业务异常都应该抛出本异常
 * @author szj 
 * @date 2018年4月28日 下午3:04:17
 */
public class BMSException extends RuntimeException {
    
    public BMSException() {
        super();
        
    }
    
    public BMSException(String message, Throwable cause) {
        super(message, cause);
        
    }
    
    public BMSException(String message) {
        super(message);
        
    }
    
    public BMSException(Throwable cause) {
        super(cause);
        
    }
    
}
