package Helper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelHelper {
    public static void writeToExcelFile(ArrayList<String> fields, String fileName) throws IOException {
        /*
         * By James
         * Writes an ArrayList of Strings to an Excel File
         */

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Throughput");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        String[] columns = {"Test Case Name", "Total Test Case Execution Time (Seconds)",
                "Overall Pacing (Seconds)", "Minimum Pacing", "Maximum Pacing",
                "TPS Format", "Transactions Per Second", "Number of Concurrent Users"};

        // Create a row
        Row headerRow = sheet.createRow(0);
        //Create cells and populate
        for(int i = 0; i < columns.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        //Create content row
        Row contentRow = sheet.createRow(1);
        for(int j = 0; j < fields.size(); j++){
            Cell cell = contentRow.createCell(j);
            cell.setCellValue(fields.get(j));
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();

    }
}
