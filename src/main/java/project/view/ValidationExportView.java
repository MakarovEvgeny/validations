package project.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import project.model.message.MessageExportRow;
import project.model.validation.ValidationExportRow;

@Component
public class ValidationExportView extends AbstractXlsxStreamingView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        @SuppressWarnings("unchecked") List<MessageExportRow> messages = (List<MessageExportRow>) map.get("messages");
        @SuppressWarnings("unchecked") List<ValidationExportRow> validations = (List<ValidationExportRow>) map.get("validations");
        CreationHelper creationHelper = workbook.getCreationHelper();

        Map<String, String> linkByMessageCode = new HashMap<>();

        WorkbookHelper workbookHelper = new WorkbookHelper(workbook);
        CellStyle headerStyle = workbookHelper.createHeaderStyle();
        CellStyle rowTextStyle = workbookHelper.createRowTextStyle();

        // Вкладка сообщений системы.
        {
            SheetHelper sheetHelper = new SheetHelper(workbook.createSheet("Справочник сообщений об ошибке"));
            sheetHelper.
                    headerHelper(headerStyle)
                        .createCell("Код сообщения", 90)
                        .createCell("Текст сообщения", 700)
                        .build()
            ;
            sheetHelper.printRows(messages, (message, rowHelper) -> {
                rowHelper
                        .createCell(rowTextStyle, message.getCode(), (cell) -> {
                            linkByMessageCode.put(message.getCode(), "'Справочник сообщений об ошибке'!" +  cell.getAddress().formatAsString());
                        })
                        .createCell(rowTextStyle, message.getText())
                ;

            });
        }

        // Вкладка проверок.
        {
            SheetHelper sheetHelper = new SheetHelper(workbook.createSheet("Справочник проверок"));
            sheetHelper.headerHelper(headerStyle)
                .createCell("Код ошибки", 70)
                .createCell("Тип ошибки", 70)
                .createCell("Код сообщения", 90)
                .createCell("Сущности", 150)
                .createCell("Операции", 150)
                .createCell("Описание", 500)
                .build()
            ;

            sheetHelper.printRows(validations, (validation, rowHelper) ->
                rowHelper
                    .createCell(rowTextStyle, validation.getCode())
                    .createCell(rowTextStyle, validation.getSeverity())
                    .createCell(
                            rowTextStyle,
                            validation.getMessageCode(),
                            (cell) -> {
                                // Создадим ссылку на сообщение.
                                Hyperlink messageLink = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);
                                messageLink.setAddress(linkByMessageCode.get(validation.getMessageCode()));

                                cell.setHyperlink(messageLink);
                            }
                        )
                    .createCell(rowTextStyle, validation.getEntities())
                    .createCell(rowTextStyle, validation.getOperations())
                    .createCell(rowTextStyle, validation.getDescription())
            );
        }
        workbook.setActiveSheet(/* Номер вкладки с проверками. */1);
    }

    /** Методы использующие Workbook. */
    private static class WorkbookHelper {
        private final Workbook workbook;

        public WorkbookHelper(Workbook workbook) {
            this.workbook = workbook;
        }


        public CellStyle createHeaderStyle() {
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.JUSTIFY);
            configureBorder(style);


            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            style.setFont(headerFont);

            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

            return style;
        }


        public CellStyle createRowTextStyle() {
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.LEFT);
            style.setVerticalAlignment(VerticalAlignment.JUSTIFY);
            configureBorder(style);
            return style;
        }


        private void configureBorder(CellStyle style) {
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
        }
    }

    /** Методы использующие Sheet. */
    private static class SheetHelper {
        private final Sheet sheet;

        public SheetHelper(Sheet sheet) {
            this.sheet = sheet;
        }

        public HeaderRowHelper headerHelper(CellStyle headerStyle) {
            return new HeaderRowHelper(sheet.createRow(0), headerStyle, sheet);
        }
        /** Пройтись о списку моделей, для каждой создать строчку и настроить её при помощи @param operator. */
        public <T> void printRows(List<T> rowModels, ByRow<T> operator) {
            int rowIndex = 1;
            for (T rowModel: rowModels) {
                operator.byRow(rowModel, new RowHelper(sheet.createRow(rowIndex)));
                rowIndex++;
            }
        }

        /** Для конфигурации excel-строки (регумендуется делать лямба-вызов). */
        interface ByRow<T> {
            void byRow(T model, RowHelper rowHelper);
        }
    }

    /** Настройка строки-шапки. */
    private static class HeaderRowHelper {
        private final Sheet sheet;
        private final RowHelper helper;
        private final CellStyle cellStyle;

        public HeaderRowHelper(Row headerRow, CellStyle cellStyle, Sheet sheet) {
            this.helper = new RowHelper(headerRow);
            this.cellStyle = cellStyle;
            this.sheet = sheet;
        }

        public HeaderRowHelper createCell(String data, int width) {
            helper.createCell(cellStyle, data);
            sheet.setColumnWidth(helper.getLastColumnIndex(), width * 60);
            return this;
        }

        public int getLastColumnIndex() {
            return helper.getLastColumnIndex();
        }

        public Cell getLastCell() {
            return helper.getLastCell();
        }

        public void build() {
            sheet.setAutoFilter(new CellRangeAddress(0, 1, 0, getLastColumnIndex()));
            sheet.createFreezePane(0,1);
        }
    }

    /** Настройка строки excel. */
    private static class RowHelper {
        private final Row row;
        private int columnIndex = 0;
        private Cell lastCell = null;


        public RowHelper(Row row) {
            this.row = row;
        }


        public RowHelper createCell(CellStyle style, String data) {
            Cell cell = row.createCell(columnIndex);
            cell.setCellValue(data);
            cell.setCellStyle(style);
            cell.setCellType(CellType.STRING);
            columnIndex++;
            lastCell = cell;
            return this;
        }

        /**
         *
         * @param style Стиль ячейки.
         * @param data Данные ячейки (для данной задачи хватит и строки).
         * @param forCell Дополнительные конфигурации и обработка ячейки (регумендуется делать лямба-вызов).
         * @return Вернуть для самого себя для цепочных вызовов.
         */
        public RowHelper createCell(CellStyle style, String data, ForCell forCell) {
            createCell(style, data);
            forCell.forCell(getLastCell());
            return this;
        }


        public int getLastColumnIndex() {
            return columnIndex - 1;
        }

        public Cell getLastCell() {
            return lastCell;
        }

        /** Дополнительные конфигурации и обработка ячейки (регумендуется делать лямба-вызов).*/
        interface ForCell {
            void forCell(Cell cell);
        }

    }
}
