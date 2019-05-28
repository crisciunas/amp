package org.digijava.module.aim.action;

import static org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined.BLACK;
import static org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined.BROWN;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xerces.parsers.DOMParser;
import org.digijava.module.aim.form.ExportTableForm;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ExportAdminTable extends Action {

    private static final String BULLET = "\u2022";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "inline; filename=Export.xls");
        ExportTableForm exportForm = (ExportTableForm) form;

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("export");

        // title cells
        HSSFCellStyle titleCS = wb.createCellStyle();
        titleCS.setWrapText(true);
        titleCS.setFillForegroundColor(BROWN.getIndex());
        HSSFFont fontHeader = wb.createFont();
        fontHeader.setFontName(HSSFFont.FONT_ARIAL);
        fontHeader.setFontHeightInPoints((short) 10);
        fontHeader.setBold(true);
        titleCS.setAlignment(HorizontalAlignment.CENTER);
        titleCS.setBorderBottom(BorderStyle.THIN);
        titleCS.setBorderLeft(BorderStyle.THIN);
        titleCS.setBorderRight(BorderStyle.THIN);
        titleCS.setBorderTop(BorderStyle.THIN);
        titleCS.setFont(fontHeader);

        // ordinary cells
        HSSFCellStyle cs = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setColor(BLACK.getIndex());
        cs.setBorderLeft(BorderStyle.THIN);
        cs.setBorderRight(BorderStyle.THIN);
        HSSFDataFormat df = wb.createDataFormat();
        cs.setDataFormat(df.getFormat("General"));
        

        cs.setFont(font);
        cs.setWrapText(true);
        cs.setVerticalAlignment(VerticalAlignment.TOP);
        
        // ordinary cells
        HSSFCellStyle csLastCell = wb.createCellStyle();

        csLastCell.setBorderBottom(BorderStyle.THIN);
        csLastCell.setBorderLeft(BorderStyle.THIN);
        csLastCell.setBorderRight(BorderStyle.THIN);
        csLastCell.setFont(font);
        csLastCell.setWrapText(true);
        csLastCell.setVerticalAlignment(VerticalAlignment.TOP);

        String data = exportForm.getData();
        DOMParser parser = new DOMParser();
        parser.parse(new InputSource(new java.io.StringReader(data)));
        Document doc = parser.getDocument();
        int cellSize=0;
        NodeList rows = doc.getElementsByTagName("row");
        int rowIndex = 0;
        float rowHeight = 12.75f;
        for (int i = 0; i < rows.getLength(); i++) {
            int maxMerge = 1;
            Node nodeRow = rows.item(i);
            NodeList cells = nodeRow.getChildNodes();
            Map<Integer, String[]> columnsPerRow = new HashMap<Integer, String[]>();
            cellSize = cells.getLength();
            for (int j = 0; j < cells.getLength(); j++) {
                String textContent;
                if (cells.item(j).getNodeName().equalsIgnoreCase("cell")) {
                    textContent = cells.item(j).getTextContent().trim();
                    if (textContent.contains(BULLET)) {
                    int firstBullet=textContent.indexOf(BULLET);
                    textContent=textContent.substring(firstBullet+BULLET.length());
                      String[] splitedText = textContent.split(BULLET);
                        if (maxMerge < splitedText.length) {
                            maxMerge = splitedText.length;
                        }
                       columnsPerRow.put(j, splitedText);
                    } else {
                         columnsPerRow.put(j, new String[]{textContent});
                    }
                }
            }
            for (int j = 0; j < maxMerge; j++) {
                HSSFRow row = sheet.createRow(rowIndex++);
                row.setHeightInPoints(rowHeight);
                int cellIndex = 0;
                for (int key = 0; key < cellSize; key++) {
                    String[] splitedText = columnsPerRow.get(key);
                    HSSFCell bulletCell;
                    if (splitedText.length >j) {
                        bulletCell = row.createCell(cellIndex++);
                        String cellValue = null;
                        if (splitedText.length > 1) {
                            cellValue = BULLET + splitedText[j].trim();
                        } else {
                            cellValue = splitedText[0].trim();
                        }
                       
                        HSSFRichTextString text = new HSSFRichTextString(cellValue);
                        bulletCell.setCellValue(text);
                        
                    } else {
                        bulletCell =  row.createCell(cellIndex++);
                    }
                    if (i == 0) { //title cells
                        bulletCell.setCellStyle(titleCS);
                    } else {
                        if(j==maxMerge-1){ // last cell from merged cells
                            bulletCell.setCellStyle(csLastCell);
                        }
                        else{
                            bulletCell.setCellStyle(cs);
                        }
                       
                    }

                }
            }
        }
        for(int i=0;i<cellSize;i++){
            sheet.autoSizeColumn(i); //adjust width of the first column
        }


        wb.write(response.getOutputStream());
        return null;
    }
}
