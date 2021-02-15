package mtf.project.helpers;

import mtf.project.model.PengajuanModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PengajuanExporter {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<PengajuanModel> listPengajuan;

    public PengajuanExporter(List<PengajuanModel> listPengajuan) {
        this.listPengajuan = listPengajuan;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Pengajuan");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);

        createCell(row, 0, "Nomor", style);
        createCell(row, 1, "Nama", style);
        createCell(row, 2, "E-mail", style);
        createCell(row, 3, "Nomor Telepon", style);
        createCell(row, 4, "Nomor KTP", style);
        createCell(row, 5, "Domisili", style);
        createCell(row, 6, "Waktu Bersedia Dihubungi", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);

        for (PengajuanModel pengajuan : listPengajuan) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, rowCount-1, style);
            createCell(row, columnCount++, pengajuan.getName(), style);
            createCell(row, columnCount++, pengajuan.getEmail(), style);
            createCell(row, columnCount++, pengajuan.getPhone(), style);
            createCell(row, columnCount++, pengajuan.getNoktp(), style);
            createCell(row, columnCount++, pengajuan.getLokasi(), style);
            createCell(row, columnCount++, pengajuan.getHubungi(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}