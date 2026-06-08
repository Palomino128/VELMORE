VELMORE LIBRERÍAS FINAL NETBEANS

Este proyecto cumple el criterio de librerías:
- Google Guava
- Apache POI
- Apache Commons
- Logback
- Seguridad para el tratamiento de información

Cómo abrir:
1. Extrae el ZIP.
2. En NetBeans IDE 19: File > Open Project.
3. Selecciona la carpeta VelmoreLibreriasFinalNetBeans.
4. Marca Trust Project Build Script.
5. Espera a que Maven descargue dependencias.
6. Click derecho > Clean and Build.
7. Click derecho > Run.
8. Abre http://localhost:8080

Login:
admin / 123456

Dónde se usa cada librería:
- Google Guava: InMemoryProductoDao y ProductoServiceImpl.
- Apache POI: ReporteExcelService.
- Apache Commons Lang: ProductoValidator, AuthServiceImpl y ContactoServiceImpl.
- Apache Commons IO: ReporteExcelService.
- Logback: logback-spring.xml y logs en DAO/Service/Auth.
