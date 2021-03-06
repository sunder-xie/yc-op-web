package com.ai.yc.op.web.common.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ai.opt.sdk.components.dss.DSSClientFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.dss.base.interfaces.IDSSClient;
import com.ai.yc.op.web.constant.Constants;
import com.ai.yc.op.web.controller.order.OrdOrderController;
import com.ai.yc.op.web.model.common.Attachment;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/attachment")
public class AttachmentController {

	private static final Logger log = LoggerFactory
			.getLogger(OrdOrderController.class);

	@RequestMapping(value = "/upload/one")
	@ResponseBody
	public ResponseData<Attachment> upload(
			@RequestParam(value = "file", required = false) MultipartFile file) {
		if (file == null) {
			return new ResponseData<Attachment>(
					ResponseData.AJAX_STATUS_FAILURE, "请选择上传文件", null);
		}
		IDSSClient client = DSSClientFactory
				.getDSSClient(Constants.IPAAS_ORDER_FILE_DSS);
		try {
			String fileId = client.save(file.getBytes(),
					file.getOriginalFilename());
			Attachment attachment = new Attachment(file.getOriginalFilename(),
					fileId, file.getSize());
			return new ResponseData<Attachment>(
					ResponseData.AJAX_STATUS_SUCCESS, "上传成功", attachment);
		} catch (IOException e) {
			log.debug("系统异常，请稍后重试", e);
			return new ResponseData<Attachment>(
					ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
		}

	}

	@RequestMapping(value = "/upload/batch")
	@ResponseBody
	public ResponseData<List<Attachment>> upload(
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		if (files == null || files.length == 0) {
			return new ResponseData<List<Attachment>>(
					ResponseData.AJAX_STATUS_FAILURE, "请选择上传文件", null);
		}
		IDSSClient client = DSSClientFactory
				.getDSSClient(Constants.IPAAS_ORDER_FILE_DSS);
		List<Attachment> attachments = new ArrayList<Attachment>();
		for (MultipartFile file : files) {
			try {
				String fileId = client.save(file.getBytes(),
						file.getOriginalFilename());
				Attachment attachment = new Attachment(
						file.getOriginalFilename(), fileId, file.getSize());
				attachments.add(attachment);
			} catch (IOException e) {
				log.debug("系统异常，请稍后重试", e);
				return new ResponseData<List<Attachment>>(
						ResponseData.AJAX_STATUS_FAILURE, "系统异常，请稍后重试", null);
			}
		}
		return new ResponseData<List<Attachment>>(
				ResponseData.AJAX_STATUS_SUCCESS, "上传成功", attachments);
	}

	@RequestMapping(value = "/download")
	public void download(HttpServletResponse response, HttpServletRequest request,String fileId,String fileName) {

		log.info("id=" + fileId);
		IDSSClient client = DSSClientFactory
				.getDSSClient(Constants.IPAAS_ORDER_FILE_DSS);
		byte[] bs = client.read(fileId);
		log.info("data=" + JSON.toJSONString(bs));
		OutputStream out = null;
		try {
			String agent = request.getHeader("User-Agent");
	         //不是ie
	         if (agent.indexOf("MSIE") == -1 && agent.indexOf("like Gecko")== -1) {
	             //空格、（、）、；、@、#、&
	             String newFileName = java.net.URLDecoder.decode(fileName,"utf-8");
	             fileName = new String(newFileName.getBytes("utf-8"), "ISO-8859-1");
	         }
			out = response.getOutputStream();
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName=\"" + fileName +"\"");
			response.setHeader("Content-Length", String.valueOf(bs.length));
			out.write(bs);
		} catch (IOException e) {
			log.error("下载文件失败", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				log.error("关闭流失败", e);
			}
		}
	}

}
