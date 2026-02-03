# ppss-2026-G6-morell-far
## Resumen de la práctica P00 - Uso de Maven

En esta práctica se trabajó con proyectos Maven para familiarizarse con la estructura y las fases de construcción. Los pasos realizados fueron los siguientes:

### 1. **Estructura de un proyecto Maven**
   - Se exploraron dos proyectos Maven: `P00-maven1` y `P00-maven2`.
   - Se verificó la estructura de directorios estándar de Maven utilizando el comando `tree` en la terminal.
   - Se creó un archivo de texto `estructura_directorios_maven.txt` para documentar la estructura de los directorios y los propósitos de cada uno.

### 2. **Construcción del Proyecto con Maven**
   - **Fase de compilación**: Se ejecutó el comando `mvn compile` en el proyecto `P00-maven1`, lo que compiló el código fuente y generó la carpeta `target/classes`.
   - **Limpieza del Proyecto**: Se ejecutó el comando `mvn clean`, que eliminó la carpeta `target`, limpiando el proyecto de los archivos generados previamente.
   - **Ejecución de pruebas**: Se ejecutó el comando `mvn test`, lo que ejecutó los tests definidos en el proyecto. Se verificó que si los tests fallan, el proceso termina con el mensaje "BUILD FAILURE". En el caso de errores de compilación, la fase de pruebas no se ejecutó.

### 3. **Trabajo con Maven en `P00-maven2`**
   - Se verificó el comportamiento de Maven cuando los tests fallan debido a errores en la lógica del código fuente.
   - Se introdujeron errores en el código para ver cómo Maven detecta y reporta fallos de compilación o de tests.

### 4. **Comandos utilizados**:
   - `mvn compile` para compilar el código fuente.
   - `mvn clean` para limpiar el proyecto.
   - `mvn test` para ejecutar las pruebas y validar el funcionamiento del proyecto.

### 5. **Documentación de los Comandos Maven**
   - Se creó un archivo de texto `comandos_maven.txt` para registrar la secuencia de acciones realizadas por Maven durante la ejecución de los comandos `mvn compile`, `mvn clean` y `mvn test`.

## Resumen de la práctica P01 - Uso de IntelliJ
###***Ejercicio 1***
PACKAGE
Ejecuta hasta package
Genera un .jar
Lo deja en:
target/

INSTALL
Ejecuta hasta install
Copia el .jar al:
~/.m2/repository

¿Para qué sirve?

Reutilizar tu proyecto como dependencia
Evitar copiar librerías a mano

Base del desarrollo modular con Maven

