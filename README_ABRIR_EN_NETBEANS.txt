=================================================================
  VELMORE LIBRERÍAS – VERSIÓN MEJORADA
  Sistema web de catálogo de perfumes con Spring Boot 3.2.5
=================================================================

REQUISITOS
----------
- Java 17 o superior
- Apache Maven 3.8+
- NetBeans IDE 19 (o IntelliJ IDEA)
- Conexión a internet (primera vez, para descargar dependencias)

CÓMO ABRIR EN NETBEANS
-----------------------
1. Extrae el ZIP.
2. En NetBeans: File > Open Project.
3. Selecciona la carpeta VelmoreLibreriasMejorado.
4. Marca "Trust Project Build Script" si aparece.
5. Espera a que Maven descargue las dependencias.
6. Clic derecho sobre el proyecto > Clean and Build.
7. Clic derecho > Run.
8. Abre en el navegador: http://localhost:8080

CREDENCIALES DE ADMINISTRADOR
------------------------------
Las credenciales están en application.properties (NO en el código):
  Usuario: admin
  Clave:   Velmore2024$

Para cambiarlas: edita src/main/resources/application.properties

RUTAS PRINCIPALES
-----------------
  /              → Página de inicio
  /catalogo      → Catálogo de productos (con búsqueda y filtros)
  /producto/{id} → Detalle del producto
  /contacto/{id} → Formulario de contacto WhatsApp
  /login         → Login de administrador
  /admin         → Panel de administración (requiere login)
  /admin/productos/{id}/editar → Editar producto
  /reporte/productos → Descargar reporte Excel

USO DE LIBRERÍAS
----------------
Google Guava:
  - InMemoryProductoDao: Lists.newArrayList() para inicializar colección
  - ProductoServiceImpl: CacheBuilder + ImmutableList para caché en memoria
  - ProductoValidator: Preconditions.checkNotNull(), Strings.isNullOrEmpty()

Apache POI:
  - ReporteExcelService: genera archivo .xlsx con cabecera estilizada
    (negrita, color de fondo, bordes, formato de precio)

Apache Commons:
  - Commons Lang3: StringUtils.isBlank(), equals(), trimToEmpty(), defaultIfBlank()
    en ProductoValidator, AuthServiceImpl y ContactoServiceImpl
  - Commons IO: ByteArrayOutputStream en ReporteExcelService

Logback:
  - logback-spring.xml: consola + archivo rotativo diario (7 días)
  - Logs en DAO, Services y Auth con niveles INFO, WARN y ERROR

MEJORAS INCLUIDAS EN ESTA VERSIÓN
----------------------------------
1. Credenciales externalizadas a application.properties (@Value)
2. Función de EDITAR producto (CRUD completo: crear, editar, eliminar)
3. Reporte Excel con estilos: negrita, color de fondo, bordes y formato S/
4. Validador mejorado con Guava Preconditions y Strings
5. 16+ pruebas unitarias (vs 2 originales)
6. Búsqueda ampliada: también busca en la descripción del producto
7. Caché con estadísticas (recordStats)
8. login.html sin credenciales visibles
9. Diseño CSS responsivo mejorado
10. .gitignore incluido para control de versiones

INSTRUCCIONES GIT / GITHUB
---------------------------
  git init
  git add .
  git commit -m "feat: proyecto VELMORE mejorado con todas las librerias"
  git remote add origin https://github.com/TU_USUARIO/velmore-librerias
  git push -u origin main

=================================================================
