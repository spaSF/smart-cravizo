package com.sfs.crfosp.soap.interceptor

import java.util.logging.Logger

import javax.servlet.http.HttpServletRequest

import org.apache.commons.io.IOUtils
import org.apache.cxf.common.logging.LogUtils
import org.apache.cxf.interceptor.AbstractLoggingInterceptor
import org.apache.cxf.interceptor.Fault
import org.apache.cxf.interceptor.LoggingMessage
import org.apache.cxf.io.CacheAndWriteOutputStream
import org.apache.cxf.io.CachedOutputStream
import org.apache.cxf.io.CachedOutputStreamCallback
import org.apache.cxf.message.Message
import org.apache.cxf.phase.Phase
import org.apache.cxf.transport.http.AbstractHTTPDestination

import com.sfs.crfosp.ws.services.LogWSFilesService

public class LogInterceptor extends AbstractLoggingInterceptor {
	
	def logWSFilesService
	
	private static final Logger toLOG = LogUtils.getLogger(LogInterceptor.class);

	public LogInterceptor() {
		super(Phase.PRE_STREAM);
	}

	public void handleMessage(Message message) throws Fault {

		
		if (message.containsKey(LoggingMessage.ID_KEY)) {
			return;
		}
				
		boolean isOutbound = false;
		isOutbound = message == message.getExchange().getOutMessage() || message == message.getExchange().getOutFaultMessage();
		if (isOutbound) {
			OutputStream os = message.getContent(OutputStream.class);
			if (os != null) {
				try {
					final CacheAndWriteOutputStream newOut = new CacheAndWriteOutputStream(os);
					message.setContent(OutputStream.class, newOut);
					newOut.registerCallback(new LoggingCallback());
				} catch (Exception e) {
					throw new Fault(e);
				}
			}
		} else {
			InputStream is = message.getContent(InputStream.class);
			if (is != null) {
				CachedOutputStream bos = new CachedOutputStream();
				try {
					IOUtils.copy(is, bos);
					bos.flush();
					is.close();
					//System.out.println(bos.currentStream.toString());
					if (bos.currentStream.count > 0 ) {
						HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
						String logMsgId = LoggingMessage.nextId();
						logWSFilesService.setRequestId(request, logMsgId);
						logWSFilesService.storeSmartFile(logWSFilesService.getRequestId(), bos.currentStream.toString(),"request");
					}					
					message.setContent(InputStream.class, bos.getInputStream());
					bos.close();
				} catch (Exception e) {
					throw new Fault(e);
				}
			}
		}
	}

	@Override
	protected Logger getLogger() {
		return toLOG;
	}

	public class LoggingCallback implements CachedOutputStreamCallback {
		public void onFlush(CachedOutputStream cos) {
		}

		public void onClose(CachedOutputStream cos) {
			try {
				if (cos.currentStream.count > 0 ) {
					logWSFilesService.storeSmartFile(logWSFilesService.getRequestId(), cos.currentStream.toString(),"response");
				}								
				//System.out.println(cos.currentStream.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
