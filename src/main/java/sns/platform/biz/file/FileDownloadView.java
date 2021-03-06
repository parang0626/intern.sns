/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * <pre>
 * 파일 다운로드 뷰
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
public class FileDownloadView extends AbstractView {

	/**
	 * 생성자
	 */
	public FileDownloadView() {
		setContentType("applicaiton/octet-stream;charset=utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		File file = (File) model.get("downloadFile");
		String Orifilename = file.getName();
		String extension = Orifilename.substring(Orifilename.lastIndexOf(".")+1);
		setContentType("image/"+extension);
		String type = "inline";
		
		if(request.getParameter("view") != null && request.getParameter("view").equals("down")) {
			setContentType("applicaiton/download;charset=utf-8");
			type = "attachment";
		}
		response.setContentType(getContentType());
		response.setContentLength((int) file.length());
		
		String fileName = java.net.URLEncoder.encode(file.getName(), "UTF-8");
		String realfileName = fileName.substring(fileName.lastIndexOf('_')+1);

		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", type + "; filename=\"" + realfileName + "\";");
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e2) {

				}
			}
		}
		out.flush();

	}

}
