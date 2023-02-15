package com.sfs.crfosp.soap.interceptor

import org.apache.cxf.feature.LoggingFeature

public class PrettyLoggingFeature extends LoggingFeature{

    public PrettyLoggingFeature(){
        super.setPrettyLogging(true);
    }
		
}