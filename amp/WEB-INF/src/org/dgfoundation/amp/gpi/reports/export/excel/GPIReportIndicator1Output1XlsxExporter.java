package org.dgfoundation.amp.gpi.reports.export.excel;

import static java.util.stream.Collectors.groupingBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.dgfoundation.amp.ar.ColumnConstants;
import org.dgfoundation.amp.ar.view.xls.IntWrapper;
import org.dgfoundation.amp.gpi.reports.GPIDocument;
import org.dgfoundation.amp.gpi.reports.GPIDonorActivityDocument;
import org.dgfoundation.amp.gpi.reports.GPIRemark;
import org.dgfoundation.amp.gpi.reports.GPIReport;
import org.dgfoundation.amp.gpi.reports.GPIReportConstants;
import org.dgfoundation.amp.gpi.reports.GPIReportOutputColumn;
import org.dgfoundation.amp.gpi.reports.GPIReportUtils;
import org.dgfoundation.amp.newreports.FilterRule;
import org.dgfoundation.amp.newreports.ReportElement;
import org.digijava.kernel.ampapi.endpoints.gpi.GPIDataService;
import org.digijava.kernel.translator.TranslatorWorker;

/**
 * @author Viorel Chihai
 *
 */
public class GPIReportIndicator1Output1XlsxExporter extends GPIReportXlsxExporter {
	
	protected String remarkSheetName = "Donor Remarks";
	
	public final Map<String, String> HEADER_LABELS = Collections.unmodifiableMap(new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(GPIReportConstants.COLUMN_YEAR, "Year");
			put(ColumnConstants.DONOR_AGENCY, "Provider Name");
			put(ColumnConstants.PROJECT_TITLE, "Project Title");
			put(GPIReportConstants.GPI_1_Q1, String.format("%s\n%s", GPIReportConstants.GPI_1_Q1, "Project Amount"));
			put(GPIReportConstants.GPI_1_Q2, String.format("%s\n%s", 
					GPIReportConstants.GPI_1_Q2, "Approval date, (month/year)"));
			put(GPIReportConstants.GPI_1_Q3, String.format("%s\n%s", 
					GPIReportConstants.GPI_1_Q3, "Type of Intervention"));
			put(GPIReportConstants.GPI_1_Q4, String.format("%s\n%s", 
					GPIReportConstants.GPI_1_Q4, "Implementing Entity"));
			put(GPIReportConstants.GPI_1_Q5, String.format("%s\n%s", 
					GPIReportConstants.GPI_1_Q5, "What is the sector that the intervention targets?"));
			put(ColumnConstants.GPI_1_Q6, String.format("%s\n%s", ColumnConstants.GPI_1_Q6, 
					"The objective is drawn from government result frame work's or other splanning document"
					+ "\nYes=1, N0=0"));
			put(ColumnConstants.GPI_1_Q6_DESCRIPTION, String.format("%s\n%s", ColumnConstants.GPI_1_Q6, 
					"The objective is drawn from government result frame work's or other planning document"));
			put(ColumnConstants.GPI_1_Q7, String.format("%s\n%s", ColumnConstants.GPI_1_Q7, 
					"Total number of outcome indicators included in the projects result framework"));
			put(ColumnConstants.GPI_1_Q8, String.format("%s\n%s", ColumnConstants.GPI_1_Q8, 
					"Number of outcome indicators drawn from existing Gov’s result framework "
					+ "and/or other planning documents"));
			put(ColumnConstants.GPI_1_Q9, String.format("%s\n%s", ColumnConstants.GPI_1_Q9, 
					"Number of outcome indication to be tracked using Gov’t ongoing statistical data source "
					+ "or M&E system"));
			put(ColumnConstants.GPI_1_Q10, String.format("%s\n%s", ColumnConstants.GPI_1_Q10, 
					"The project plans a final evaluative\nYes=1, N0=0"));
			put(ColumnConstants.GPI_1_Q10_DESCRIPTION, String.format("%s\n%s", ColumnConstants.GPI_1_Q10, 
					"To what extent will the Gov't participate in carrying out the final evaluation?"
					+ "\n(if there is one planned)"));
			put(GPIReportConstants.COLUMN_EXTENT_OF_USE_OF_COUNTRY_RESULT, "Extent of use of country owned result "
					+ "framework or similar planning document\nCalculation = Q8/Q7");
			put(GPIReportConstants.COLUMN_EXTENT_OF_USE_OF_GOV_SOURCES, "Extent of use of Gov’t sources and M&E systems"
					+ " to track project progress\nCalculation = Q9/Q7");
			put(GPIReportConstants.GPI_1_Q11, "Supportive Documents");
			put(GPIReportConstants.GPI_1_Q11a, "Electronic link to project document");
			put(GPIReportConstants.GPI_1_Q11b, "Electronic link to gov. planning doc. or results framework "
					+ "used for project design");
			put(GPIReportConstants.GPI_1_Q11c, "Electronic link to gov. existing data source, statistical database "
					+ "or M&E system that will be used to track project progress");
			
		}
	});
	
	public final Map<String, String> SUMMARY_LABELS = Collections.unmodifiableMap(new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(GPIReportConstants.GPI_1_Q1, "Overall extent of use existing CRFs");
			put(GPIReportConstants.GPI_1_Q2, "Overall use of country owned Results Frameworks");
			put(GPIReportConstants.GPI_1_Q3, "Overall use of country lead Results Monitoring Frameworks");
			put(GPIReportConstants.GPI_1_Q4, "Overall existence of ex post (final Evaluations)");

		}
	});
	
	public GPIReportIndicator1Output1XlsxExporter() {
		reportSheetName = "Indicator 1 Output 1";
	}
	
	@Override
	protected void addAllSheetsToWorkbook(GPIReport report, SXSSFWorkbook wb) {
		addReportSheetToWorkbook(wb, report, getReportSheetName());
		addRemarkSheetToWorkbook(wb, report, getRemarkSheetName());
		addSummarySheetToWorkbook(wb, report, getSummarySheetName());
	}
	
	protected void addRemarkSheetToWorkbook(SXSSFWorkbook wb, GPIReport report, String sheetName) {
		SXSSFSheet remarkSheet = wb.createSheet(TranslatorWorker.translateText(sheetName));
		generateRemarkSheet(wb, remarkSheet, report);
	}
	
	/**
	 * Add extra info about filters applied, currency and settings.
	 * 
	 * @param wb
	 * @param remarkSheet
	 * @param reportSpec
	 * @param queryObject
	 */
	protected void generateRemarkSheet(SXSSFWorkbook workbook, SXSSFSheet remarkSheet, GPIReport report) {
		IntWrapper currLine = new IntWrapper();
		
		Row calendarRow = remarkSheet.createRow(currLine.intValue());
		Cell dateHeaderCell = calendarRow.createCell(0);
		dateHeaderCell.setCellValue(TranslatorWorker.translateText("Date"));
		dateHeaderCell.setCellStyle(template.getHeaderCellStyle());
		
		Cell donorHeaderCell = calendarRow.createCell(1);
		donorHeaderCell.setCellValue(TranslatorWorker.translateText("Donor"));
		donorHeaderCell.setCellStyle(template.getHeaderCellStyle());
		
		Cell remarkHeaderCell = calendarRow.createCell(2);
		remarkHeaderCell.setCellValue(TranslatorWorker.translateText("Remarks"));
		remarkHeaderCell.setCellStyle(template.getHeaderCellStyle());
		
		String donorType = GPIReportConstants.HIERARCHY_DONOR_AGENCY;

		FilterRule donorAgencyRule = GPIReportUtils.getFilterRule(report.getOriginalFormParams(), 
				ColumnConstants.DONOR_AGENCY);
		List<Long> ids = donorAgencyRule == null ? new ArrayList<>()
				: donorAgencyRule.values.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
		
		FilterRule aprDateRule = GPIReportUtils.getFilterRule(report.getOriginalFormParams(), 
				ColumnConstants.ACTUAL_APPROVAL_DATE);
		
		Long min = aprDateRule == null ? 0L : aprDateRule.min != null ? Long.parseLong(aprDateRule.min) : 0L;
		Long max = aprDateRule == null ? 0L : aprDateRule.max != null ? Long.parseLong(aprDateRule.max) : 0L;
		
		List<GPIRemark> remarks = GPIDataService.getGPIRemarks(GPIReportConstants.REPORT_1, ids, donorType, min, max);
		
		for (GPIRemark remark : remarks) {
			Row remarkRow = remarkSheet.createRow(currLine.inc().intValue());
			
			Cell dateCell = remarkRow.createCell(0);
			dateCell.setCellValue(remark.getDate());
			dateCell.setCellStyle(template.getDefaultStyle());
			
			Cell donorCell = remarkRow.createCell(1);
			donorCell.setCellValue(remark.getDonorAgency());
			donorCell.setCellStyle(template.getDefaultStyle());
			
			Cell remarkCell = remarkRow.createCell(2);
			remarkCell.setCellValue(remark.getRemark());
			remarkCell.setCellStyle(template.getDefaultStyle());
		}

		remarkSheet.trackAllColumnsForAutoSizing();

		for (int l = 0; l < 3; l++) {
			remarkSheet.autoSizeColumn(l, true);
		}
	}

	/**
	 * @param wb
	 * @param sheet
	 * @param report
	 */
	protected void renderReportTableHeader(Workbook wb, Sheet sheet, GPIReport report) {
		Set<CellRangeAddress> mergedCells = new HashSet<CellRangeAddress>();
		int firstCol = 0;
		int firstRow = initHeaderRowOffset;
		Row row = sheet.createRow(firstRow);

		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(GPIReportConstants.COLUMN_YEAR), 
				firstRow, firstRow + 1, firstCol, firstCol));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(ColumnConstants.DONOR_AGENCY), 
				firstRow, firstRow + 1, firstCol + 1, firstCol + 1));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(ColumnConstants.PROJECT_TITLE), 
				firstRow, firstRow + 1, firstCol + 2, firstCol + 2));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(GPIReportConstants.GPI_1_Q1), 
				firstRow, firstRow + 1, firstCol + 3, firstCol + 3));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(GPIReportConstants.GPI_1_Q2), 
				firstRow, firstRow + 1, firstCol + 4, firstCol + 4));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(GPIReportConstants.GPI_1_Q3), 
				firstRow, firstRow + 1, firstCol + 5, firstCol + 5));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(GPIReportConstants.GPI_1_Q4), 
				firstRow, firstRow + 1, firstCol + 6, firstCol + 6));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(GPIReportConstants.GPI_1_Q5), 
				firstRow, firstRow + 1, firstCol + 7, firstCol + 9));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(ColumnConstants.GPI_1_Q6), 
				firstRow, firstRow + 1, firstCol + 10, firstCol + 10));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(ColumnConstants.GPI_1_Q6_DESCRIPTION), 
				firstRow, firstRow + 1, firstCol + 11, firstCol + 11));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(ColumnConstants.GPI_1_Q7), 
				firstRow, firstRow + 1, firstCol + 12, firstCol + 12));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(ColumnConstants.GPI_1_Q8), 
				firstRow, firstRow + 1, firstCol + 13 , firstCol + 13));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(ColumnConstants.GPI_1_Q9), 
				firstRow, firstRow + 1, firstCol + 14, firstCol + 14));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(ColumnConstants.GPI_1_Q10), 
				firstRow, firstRow + 1, firstCol + 15, firstCol + 15));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(ColumnConstants.GPI_1_Q10_DESCRIPTION), 
				firstRow, firstRow + 1, firstCol + 16, firstCol + 16));
		mergedCells.add(createHeaderCell(sheet, row, 
				HEADER_LABELS.get(GPIReportConstants.COLUMN_EXTENT_OF_USE_OF_COUNTRY_RESULT), 
				firstRow, firstRow + 1, firstCol + 17, firstCol + 17));
		mergedCells.add(createHeaderCell(sheet, row, 
				HEADER_LABELS.get(GPIReportConstants.COLUMN_EXTENT_OF_USE_OF_GOV_SOURCES), 
				firstRow, firstRow + 1, firstCol + 18, firstCol + 18));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(GPIReportConstants.GPI_1_Q11), 
				firstRow, firstRow, firstCol + 19, firstCol + 21));
		
		row = sheet.createRow(firstRow + 1);
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(GPIReportConstants.GPI_1_Q11a), 
				firstRow + 1, firstRow + 1, firstCol + 19, firstCol + 19));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(GPIReportConstants.GPI_1_Q11b), 
				firstRow + 1, firstRow + 1, firstCol + 20, firstCol + 20));
		mergedCells.add(createHeaderCell(sheet, row, HEADER_LABELS.get(GPIReportConstants.GPI_1_Q11c), 
				firstRow + 1, firstRow + 1, firstCol + 21, firstCol + 21));
		
		for (CellRangeAddress ca : mergedCells) {
			GPIReportExcelTemplate.fillHeaderRegionWithBorder(wb, sheet, ca);
		}
	}

	/**
	 * @param sheet
	 * @param headerLabel
	 * @param initColPos
	 * @param initRowPos
	 * @return
	 */
	private CellRangeAddress createHeaderCell(Sheet sheet, Row row, String headerLabel, int firstRow, int lastRow,
			int firstCol, int lastCol) {
		
		Cell cell = row.createCell(firstCol);
		cell.setCellValue(headerLabel);
		cell.setCellStyle(template.getHeaderCellStyle());
		setMaxColWidth(sheet, cell, firstCol);

		CellRangeAddress mergedHeaderCell = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
		
		if (mergedHeaderCell.getNumberOfCells() > 1)
			sheet.addMergedRegion(mergedHeaderCell);
		
		return mergedHeaderCell;
	}
	
	/**
	 * @param sheet
	 * @param report
	 */
	public void renderReportData(SXSSFSheet sheet, GPIReport report) {
		
		Map<String, GPIReportOutputColumn> columns = report.getPage().getContents().stream()
				.findAny().get()
				.keySet().stream()
				.collect(Collectors.toMap(GPIReportOutputColumn::getOriginalColumnName, Function.identity()));
		
		for (int i = 0; i < report.getPage().getContents().size(); i++) {
			Map<GPIReportOutputColumn, String> rowData = report.getPage().getContents().get(i);
			Row row = sheet.createRow(initHeaderRowOffset + i + 2);
			createDataCell(sheet, row, report, 0, columns.get(GPIReportConstants.COLUMN_YEAR), rowData);
			createDataCell(sheet, row, report, 1, columns.get(ColumnConstants.DONOR_AGENCY), rowData);
			createDataCell(sheet, row, report, 2, columns.get(ColumnConstants.PROJECT_TITLE), rowData);
			createDataCell(sheet, row, report, 3, columns.get(GPIReportConstants.GPI_1_Q1), rowData);
			createDataCell(sheet, row, report, 4, columns.get(GPIReportConstants.GPI_1_Q2), rowData);
			createDataCell(sheet, row, report, 5, columns.get(GPIReportConstants.GPI_1_Q3), rowData);
			createDataCell(sheet, row, report, 6, columns.get(GPIReportConstants.GPI_1_Q4), rowData);
			createPrimarySectorsCells(sheet, row, report, 7, columns.get(GPIReportConstants.GPI_1_Q5), rowData);
			createDataCell(sheet, row, report, 10, columns.get(ColumnConstants.GPI_1_Q6), rowData);
			createDataCell(sheet, row, report, 11, columns.get(ColumnConstants.GPI_1_Q6_DESCRIPTION), rowData);
			createDataCell(sheet, row, report, 12, columns.get(ColumnConstants.GPI_1_Q7), rowData);
			createDataCell(sheet, row, report, 13, columns.get(ColumnConstants.GPI_1_Q8), rowData);
			createDataCell(sheet, row, report, 14, columns.get(ColumnConstants.GPI_1_Q9), rowData);
			createDataCell(sheet, row, report, 15, columns.get(ColumnConstants.GPI_1_Q10), rowData);
			createDataCell(sheet, row, report, 16, columns.get(ColumnConstants.GPI_1_Q10_DESCRIPTION), rowData);
			createDataCell(sheet, row, report, 17, 
					columns.get(GPIReportConstants.COLUMN_EXTENT_OF_USE_OF_COUNTRY_RESULT), rowData);
			createDataCell(sheet, row, report, 18, 
					columns.get(GPIReportConstants.COLUMN_EXTENT_OF_USE_OF_GOV_SOURCES), rowData);
			
			createSupportiveDocumentCells(sheet, row, report, 19, columns, rowData);
		}
	}
	
	@Override
	protected void renderReportTableSummary(Workbook wb, Sheet sheet, GPIReport report) {
		Set<CellRangeAddress> mergedCells = new HashSet<CellRangeAddress>();
		Row summaryRow = sheet.createRow(initSummaryRowOffset);
		
		Map<String, GPIReportOutputColumn> columns = report.getSummary().keySet().stream()
				.collect(Collectors.toMap(GPIReportOutputColumn::getOriginalColumnName, Function.identity()));
	    
		
		mergedCells.add(createSummaryCell(sheet, summaryRow, report, initSummaryRowOffset, initSummaryRowOffset, 1, 1,
				GPIReportConstants.GPI_1_Q1, columns));
		mergedCells.add(createSummaryCell(sheet, summaryRow, report, initSummaryRowOffset, initSummaryRowOffset, 2, 2, 
				GPIReportConstants.GPI_1_Q2, columns));
		mergedCells.add(createSummaryCell(sheet, summaryRow, report, initSummaryRowOffset, initSummaryRowOffset, 3, 4,
				GPIReportConstants.GPI_1_Q3, columns));
		mergedCells.add(createSummaryCell(sheet, summaryRow, report, initSummaryRowOffset, initSummaryRowOffset, 5, 6,
				GPIReportConstants.GPI_1_Q4, columns));

		for (CellRangeAddress ca : mergedCells) {
			GPIReportExcelTemplate.fillHeaderRegionWithBorder(wb, sheet, ca);
		}
	}
	
	/**
	 * @param sheet
	 * @param headerLabel
	 * @param initColPos
	 * @param initRowPos
	 * @return
	 */
	private CellRangeAddress createSummaryCell(Sheet sheet, Row row, GPIReport report, int firstRow, int lastRow, 
			int firstCol, int lastCol, String columnName, Map<String, GPIReportOutputColumn> columns) {
		
		String summaryNumber = report.getSummary().get(columns.get(columnName)) == null 
				? "" : report.getSummary().get(columns.get(columnName));
		
		String summaryLabel = String.format("%s\n%s", summaryNumber, SUMMARY_LABELS.get(columnName));
		
		Cell cell = row.createCell(firstCol);
		cell.setCellValue(summaryLabel);
		cell.setCellStyle(template.getSummaryCellStyle());
		setMaxColWidth(sheet, cell, firstCol);

		CellRangeAddress mergedHeaderCell = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
		
		if (mergedHeaderCell.getNumberOfCells() > 1)
			sheet.addMergedRegion(mergedHeaderCell);
		
		return mergedHeaderCell;
	}

	private void createSupportiveDocumentCells(SXSSFSheet sheet, Row row, GPIReport report, int startPos,
			Map<String, GPIReportOutputColumn> columns, Map<GPIReportOutputColumn, String> rowData) {
		
		String donorId = rowData.get(columns.get(ColumnConstants.DONOR_ID));
		String activityId = rowData.get(columns.get(ColumnConstants.ACTIVITY_ID));
		List<GPIDonorActivityDocument> gpiDocuments = GPIDataService.getGPIDocuments(donorId, activityId);
		
		List<GPIDocument> documents = gpiDocuments.stream()
				.map(GPIDonorActivityDocument::getDocuments)
				.flatMap(docs -> docs.stream())
				.collect(Collectors.toList());
		
		Map<String, List<GPIDocument>> documentsMap = documents.stream()
				.collect(groupingBy(gpiDoc -> "Q" + gpiDoc.getQuestion()));
		
		createDocumentCell(sheet, row, report, startPos, columns, documentsMap, GPIReportConstants.GPI_1_Q11a);
		createDocumentCell(sheet, row, report, startPos + 1, columns, documentsMap, GPIReportConstants.GPI_1_Q11b);
		createDocumentCell(sheet, row, report, startPos + 2, columns, documentsMap, GPIReportConstants.GPI_1_Q11c);
		
	}

	/**
	 * @param sheet
	 * @param row
	 * @param report
	 * @param colPos
	 * @param columns
	 * @param documentsMap
	 * @param columnName
	 */
	private void createDocumentCell(SXSSFSheet sheet, Row row, GPIReport report, int colPos,
			Map<String, GPIReportOutputColumn> columns, Map<String, List<GPIDocument>> documentsMap,
			String columnName) {
		
		if (documentsMap.containsKey(columnName)) {
			String supportiveUrls = String.join("\n", documentsMap.get(columnName)
					.stream().map(GPIDocument::getUrl)
					.collect(Collectors.toList()));
			
			createCell(report, sheet, row, colPos, columnName, supportiveUrls);
		}
	}

	private void createPrimarySectorsCells(SXSSFSheet sheet, Row row, GPIReport report, int startPos,
			GPIReportOutputColumn column, Map<GPIReportOutputColumn, String> rowData) {

		String sectors = rowData.get(column);
		if (StringUtils.isNotBlank(sectors)) {
			List<String> sectorList = Arrays.asList(sectors.split("###"));
			int max = sectorList.isEmpty() ? 0 : sectorList.size() < 3 ? sectorList.size() : 3;
			for (int i = 0; i < max; i++) {
				createCell(report, sheet, row, startPos + i, column.originalColumnName, sectorList.get(i));
			}
		}
	}

	/**
	 * @param sheet
	 * @param report
	 * @param i
	 * @param columns
	 * @param rowData
	 */
	private void createDataCell(SXSSFSheet sheet, Row row, GPIReport report, int colPos, 
			GPIReportOutputColumn column, Map<GPIReportOutputColumn, String> rowData) {
		
		String value = rowData.get(column);
		
		if (column.originalColumnName.equals(ColumnConstants.GPI_1_Q6) ||
				column.originalColumnName.equals(ColumnConstants.GPI_1_Q10)) {
			value = "Yes".equals(value) ? "1" : "0";
		}
		
		if (column.originalColumnName.equals(GPIReportConstants.GPI_1_Q2)) {
			if (!StringUtils.isBlank(value)) {
				try {
					value = new SimpleDateFormat("MM/yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(value));
				} catch (ParseException e) {
					throw new RuntimeException("Error in parsing the approval date", e);
				}
			}
		}
		
		createCell(report, sheet, row, colPos, column.originalColumnName, value);
	}
	
	@Override
	protected void calculateColumnsWidth(Sheet sheet, GPIReport report) {
		Map<Integer, Integer> sheetWidths = cachedWidths.get(sheet.getSheetName());

		for (int i = 0; i < 22; i++) {
			try {
				if (sheetWidths.containsKey(i)) {
					int width = (int) ((sheetWidths.get(i)) * template.getCharWidth());
					width = width < template.getMaxColumnWidth() ? width : template.getMaxColumnWidth() - 1;
					sheet.setColumnWidth(i, width);
				} else {
					sheet.setColumnWidth(i, (int) (template.getDefaultColumnWidth() * template.getCharWidth()));
				}
			} catch (Exception e) {
				// Alternative slow method.
				sheet.autoSizeColumn(i);
			}
		}
	}
	
	@Override
	public int getCellType(String columnName) {
		switch(columnName) {
			case GPIReportConstants.GPI_1_Q1:
			case ColumnConstants.GPI_1_Q6:
			case ColumnConstants.GPI_1_Q7:
			case ColumnConstants.GPI_1_Q8:
			case ColumnConstants.GPI_1_Q9:
			case ColumnConstants.GPI_1_Q10:
				return Cell.CELL_TYPE_NUMERIC;
			default:
				return super.getCellType(columnName);
		}
	}
	
	@Override
	protected boolean hasSpecificStyle(String columnName) {
		switch(columnName) {
			case GPIReportConstants.COLUMN_EXTENT_OF_USE_OF_COUNTRY_RESULT:
			case GPIReportConstants.COLUMN_EXTENT_OF_USE_OF_GOV_SOURCES:
			case GPIReportConstants.GPI_1_Q2:
			case ColumnConstants.GPI_1_Q6:
			case ColumnConstants.GPI_1_Q6_DESCRIPTION:
			case ColumnConstants.GPI_1_Q7:
			case ColumnConstants.GPI_1_Q8:
			case ColumnConstants.GPI_1_Q9:
			case ColumnConstants.GPI_1_Q10:
			case ColumnConstants.GPI_1_Q10_DESCRIPTION:
			case GPIReportConstants.GPI_1_Q11a:
			case GPIReportConstants.GPI_1_Q11b:
			case GPIReportConstants.GPI_1_Q11c:
				return true;
			default:
				return false;
		}
	}
	
	@Override
	protected CellStyle getSpecificStyle(String columnName) {
		switch(columnName) {
			case GPIReportConstants.COLUMN_EXTENT_OF_USE_OF_COUNTRY_RESULT:
			case GPIReportConstants.COLUMN_EXTENT_OF_USE_OF_GOV_SOURCES:
				return template.getNumberStyle();
			case GPIReportConstants.GPI_1_Q11a:
			case GPIReportConstants.GPI_1_Q11b:
			case GPIReportConstants.GPI_1_Q11c:
				return template.getWrappedStyle();
			case GPIReportConstants.GPI_1_Q2:
			case ColumnConstants.GPI_1_Q6:
			case ColumnConstants.GPI_1_Q6_DESCRIPTION:
			case ColumnConstants.GPI_1_Q7:
			case ColumnConstants.GPI_1_Q8:
			case ColumnConstants.GPI_1_Q9:
			case ColumnConstants.GPI_1_Q10:
			case ColumnConstants.GPI_1_Q10_DESCRIPTION:
				return template.getCenterStyle();
			default:
				return template.getNumberStyle();
		}
	}
	
	protected String getRemarkSheetName() {
		return remarkSheetName;
	}
}
