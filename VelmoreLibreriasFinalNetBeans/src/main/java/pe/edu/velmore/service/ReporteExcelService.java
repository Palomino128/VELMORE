package pe.edu.velmore.service;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pe.edu.velmore.model.Producto;

import java.io.IOException;
import java.util.List;

@Service
public class ReporteExcelService {
    private final ProductoService productoService;

    public ReporteExcelService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public byte[] generarReporteProductos() {
        List<Producto> productos = productoService.listarActivos();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet hoja = workbook.createSheet("Productos VELMORE");
            Row cabecera = hoja.createRow(0);
            cabecera.createCell(0).setCellValue("ID");
            cabecera.createCell(1).setCellValue("Nombre");
            cabecera.createCell(2).setCellValue("Marca");
            cabecera.createCell(3).setCellValue("Categoría");
            cabecera.createCell(4).setCellValue("Precio");
            cabecera.createCell(5).setCellValue("Stock");

            int fila = 1;
            for (Producto producto : productos) {
                Row row = hoja.createRow(fila++);
                row.createCell(0).setCellValue(producto.getId());
                row.createCell(1).setCellValue(producto.getNombre());
                row.createCell(2).setCellValue(producto.getMarca());
                row.createCell(3).setCellValue(producto.getCategoria().getEtiqueta());
                row.createCell(4).setCellValue(producto.getPrecio());
                row.createCell(5).setCellValue(producto.getStock());
            }

            for (int i = 0; i <= 5; i++) {
                hoja.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo generar el reporte Excel", e);
        }
    }
}
