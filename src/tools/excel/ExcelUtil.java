package tools.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public static void main(String[] args) {
		try {
			convertScrodCard();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void convertScrodCard() throws Exception {
		String modelPath = "C:\\A.xlsx";
		String targetPath = "C:\\B.xlsx";
		InputStream ins = new FileInputStream(modelPath);
		XSSFWorkbook wb = new XSSFWorkbook(ins);

		for (int i = 0; i < 6; i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
			XSSFRow row0 = sheet.getRow(0);
			XSSFRow row3 = sheet.getRow(3);
			int rowNum = sheet.getLastRowNum();
			int columnNum = row0.getLastCellNum();
			if (rowNum < 5 || columnNum < 8) {
				continue;
			}
			for (int j = 4; j < rowNum +1; j++) {
				int totalScore = 0;
				XSSFRow row = sheet.getRow(j);
				XSSFCell cell4 = row.getCell(4);
				if(cell4 == null){
					continue;
				}
				String scoreCards = cell4.toString();
				
				List<String> list = Arrays.asList(scoreCards.split(","));
				for (int k = 7; k < columnNum; k++) {
//					System.out.println("(" +i+")"+"(" + j + "," + k + ")");
					XSSFCell cell = row.getCell(k);
					if (cell == null) {
						cell = row.createCell(k);
					}
					if (list.contains(row0.getCell(k).getRawValue())) {
						cell.setCellValue("1");
						totalScore = totalScore
								+ Integer.valueOf(row3.getCell(k).getRawValue());
					} else {
						cell.setCellValue("0");
					}
				}
				XSSFCell totalCell = row.createCell(columnNum);
				totalCell.setCellValue(totalScore);
			}
		}
		OutputStream os = new FileOutputStream(targetPath);
		wb.write(os);
		wb.close();
		System.out.println("job's done!");
	}

}
