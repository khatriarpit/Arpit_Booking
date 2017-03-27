package com.emxcel.dms.portal.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.beans.RequestData;

/**
 * @author emxcelsolutions
 */
@Controller
public class SseController {
	
	private static final Logger logger = Logger.getLogger(SseController.class);

	@RequestMapping("/emitter")
	public SseEmitter runEmitter(@RequestParam(value = "string1") String string1,
			@RequestParam(value = "string2") String string2) {
		SseEmitter sseEmitter = new SseEmitter();
		RequestData requestData = new RequestData();
		requestData.setString1(string1);
		requestData.setString2(string2);
		new Thread(new RunProcess(requestData, sseEmitter)).start();
		return sseEmitter;
	}

	private class RunProcess implements Runnable {
		private RequestData requestData;
		private SseEmitter sseEmitter;

		RunProcess(RequestData requestData, SseEmitter sseEmitter) {
			this.requestData = requestData;
			this.sseEmitter = sseEmitter;
		}

		public void run() {
			try {
				if (requestData.getString1() != null && requestData.getString1().equals("msg")) {
					sseEmitter.send(requestData.getString2());
					sseEmitter.complete();
				}
			} catch (IOException e) {
				logger.error(Constants.EXCEPTION_THROW, e);
				sseEmitter.completeWithError(e);
			}
		}
	}

}