package dev.elliman.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import dev.elliman.app.TRMSJavalin;
import dev.elliman.beans.Claim;
import dev.elliman.beans.Person;
import dev.elliman.beans.RFC;
import dev.elliman.services.ClaimService;
import dev.elliman.services.ClaimServiceImpl;
import dev.elliman.services.RFCService;
import dev.elliman.services.RFCServiceImpl;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

public class CommentController {
	
	private static  ClaimService cs = new ClaimServiceImpl();
	private static RFCService rfcs = new RFCServiceImpl();
	
	public static void makeRFC(Context ctx) {
		String rfcString = ctx.body().substring(1, ctx.body().length()-1);
		String[] rfcParts = rfcString.split(",");
		
		RFC rfc = new RFC();
		
		Person user = ctx.sessionAttribute("user");
		rfc.setCommenter(user);
		
		for(String part : rfcParts) {
			String field = part.split(":")[0];
			String value = part.split(":")[1];
			//remove "
			field = field.substring(1, field.length()-1);
			value = value.substring(1, value.length()-1);
			
			if("claim".equals(field)) {
				Integer id = Integer.valueOf(value);
				Claim c = cs.getClaimByID(id);
				rfc.setClaim(c);
			} else if("description".equals(field)) {
				rfc.setDescription(value);
			}
		}
		
		rfcs.makeRFC(rfc);
	}
	
	public static void getCommentsForClaim(Context ctx) {
		Integer claimID = Integer.valueOf(ctx.pathParam("id"));
		List<RFC> rfcList = rfcs.getRFCByClaim(claimID);
		
		ctx.json(rfcList);
		ctx.status(200);

	}
	
	public static void answerComment(Context ctx) {
		String body = ctx.body();
		body = body.substring(1, body.length()-1);
		String[] rfcAnswerParts = body.split(",");
		Integer id = null;
		String answer = null;
		for(String part : rfcAnswerParts) {
			String field = part.split(":")[0];
			field = field.substring(1,field.length()-1);
			String value = part.split(":",2)[1];
			value = value.substring(1, value.length()-1);
			if("id".equals(field)) {
				id = Integer.valueOf(value);
			} else if("answer".equals(field)) {
				answer = value;
			}
		}
		
		Boolean success = rfcs.answerRFC(id,answer);
		if(success) {
			ctx.status(200);
		} else {
			ctx.status(500);
		}
	}
	
	public static void uploadFile(Context ctx) {
		System.out.println();
		Integer id = Integer.valueOf(ctx.pathParam("id"));
		final WorkaroundBoolean success = new WorkaroundBoolean();
		success.setValue(Boolean.TRUE);
		ctx.uploadedFiles("files").forEach(file -> {
			File claimFolder = new File(TRMSJavalin.STORAGE_FOLDER_LOCATION + "\\f" + id);
			if(!claimFolder.exists()) {
				claimFolder.mkdir();
			}
			File dataFile = new File(claimFolder, file.getFilename());
			try {
				if(!dataFile.exists()) {
					dataFile.createNewFile();
				}
				Files.copy(file.getContent(), Paths.get(dataFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
				success.setValue(Boolean.TRUE);
			}
		});
		
		if(success.getValue()) {
			ctx.status(200);
		} else {
			ctx.status(500);
		}
	}
	
	public static void getClaimFiles(Context ctx) {
		Integer id = Integer.valueOf(ctx.pathParam("id"));
		
		File[] relatedFiles = new File(TRMSJavalin.STORAGE_FOLDER_LOCATION + "\\f" + id).listFiles();
		String[] fileNames = null;
		if(relatedFiles != null) {
			fileNames = new String[relatedFiles.length];
			for(int i = 0; i < relatedFiles.length; i++) {
				fileNames[i] = relatedFiles[i].getName();
			}
		}
		
		if(fileNames != null) {
			ctx.json(fileNames);
			ctx.status(200);
		} else {
			ctx.status(204);
		}
		
	}
	
	public static void downloadFile(Context ctx) {
		String id = ctx.pathParam("id");
		String fileName = ctx.pathParam("fileName");
		File f = new File(TRMSJavalin.STORAGE_FOLDER_LOCATION + "\\f" + id + "\\" + fileName);
		String mimeType = "application/octet-stream";
		
		ctx.contentType(mimeType);
		
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", f.getName());
		ctx.header(headerKey, headerValue);
		
		try {
			ctx.result(new FileInputStream(f));
			ctx.status(200);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ctx.status(500);
		}
	}
	
	private static class WorkaroundBoolean{
		private Boolean value;
		
		public void setValue(Boolean b) {
			value = b;
		}
		
		public Boolean getValue() {
			return value;
		}
	}
}
