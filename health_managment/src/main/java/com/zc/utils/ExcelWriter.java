package com.zc.utils;

import com.zc.entity.Record;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelWriter {

    private static List<String> CELL_HEADS; //列头

    static {
        // 类装载时就载入指定好的列头信息，如有需要，可以考虑做成动态生成的列头
        CELL_HEADS = new ArrayList<>();
        CELL_HEADS.add("id");
        CELL_HEADS.add("weight");
        CELL_HEADS.add("temperature");
        CELL_HEADS.add("bloodPressure");
        CELL_HEADS.add("textualNote");
        CELL_HEADS.add("createTime");
    }

    public static Workbook exportData(List<Record> dataList) {
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = buildDataSheet(workbook);
        int rowNum = 1;
        for (Iterator<Record> it = dataList.iterator(); it.hasNext(); ) {
            Record data = it.next();
            if (data == null) {
                continue;
            }
            Row row = sheet.createRow(rowNum++);
            convertDataToRow(data, row);
        }
        return workbook;
    }

    private static Sheet buildDataSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet();
        // 设置列头宽度
        for (int i = 0; i < CELL_HEADS.size(); i++) {
            sheet.setColumnWidth(i, 4000);
        }
        // 设置默认行高
        sheet.setDefaultRowHeight((short) 400);
        // 构建头单元格样式
        CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
        // 写入第一行各列的数据
        Row head = sheet.createRow(0);
        for (int i = 0; i < CELL_HEADS.size(); i++) {
            Cell cell = head.createCell(i);
            cell.setCellValue(CELL_HEADS.get(i));
            cell.setCellStyle(cellStyle);
        }
        // 单独设置 第1 列为文本格式
        CellStyle textCellStyle = workbook.createCellStyle();
        textCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
        sheet.setDefaultColumnStyle(0, textCellStyle);
        // 单独设置 第2 列为整数格式
        CellStyle intNumCellStyle = workbook.createCellStyle();
        intNumCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        sheet.setDefaultColumnStyle(1, intNumCellStyle);
        // 单独设置 第3 列为两位小数格式
        CellStyle floatNumCellStyle = workbook.createCellStyle();
        floatNumCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        sheet.setDefaultColumnStyle(2, floatNumCellStyle);
        return sheet;
    }

    private static CellStyle buildHeadCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        // 水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 边框颜色和宽度设置
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边框
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边框
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边框
        // 设置背景颜色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 字体设置
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        return style;
    }

    private static void convertDataToRow(Record data, Row row){
        int cellNum = 0;
        Cell cell;
        // id
        cell = row.createCell(cellNum++);
        cell.setCellValue(String.valueOf(null == data.getId() ? "" : data.getId()));
        // weight
        cell = row.createCell(cellNum++);
        if (null != data.getWeight()) {
            cell.setCellValue(data.getWeight());
        } else {
            cell.setCellValue("");
        }
        // temperature
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getTemperature() ? "" : data.getTemperature());
        // bloodPressure
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getBloodPressure() ? "" : data.getBloodPressure());
        // textualNote
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getTextualNote() ? "" : data.getTextualNote());
        // createTime
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getCreateTime() ? "" : data.getCreateTime());
    }
}
