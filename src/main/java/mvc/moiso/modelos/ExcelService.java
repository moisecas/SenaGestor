package mvc.moiso.modelos;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import mvc.moiso.repository.MovimientosRepository; // Asume que tienes un repositorio para los movimientos

@Service
public class ExcelService {

    @Autowired
    private MovimientosRepository movimientosRepository; // Asume que tienes un repositorio para los movimientos

    public ByteArrayInputStream exportarMovimientosAExcel() throws IOException {
        String[] columnas = {"ID", "Descripción", "Monto", "Fecha"};
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Movimientos");

            // Crear fila de encabezado
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            // Llenar datos
            List<Movimiento> movimientos = movimientosRepository.findAll();
            int rowNum = 1;
            for (Movimiento movimiento : movimientos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(movimiento.getId());
                row.createCell(1).setCellValue(movimiento.getDescripcion());
                row.createCell(2).setCellValue(movimiento.getMonto());
                row.createCell(3).setCellValue(movimiento.getFecha().toString()); // Asegúrate de formatear la fecha como prefieras
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}

