API v2.0.2:
-----------
La siguiente actualización resuelve los siguientes problemas:
* La ausencia de resultados retornados por la ruta GET /favourites en ciertas ocasiones.

Y también incorpora la siguiente funcionalidad:
* La ruta POST /favourites/{routineId} permite marcar como favorita una rutina creada por otro usuario.
* La ruta DELETE /favourites/{routineId} permite desmarcar como favorita una rutina creada por otro usuario.
* La ruta GET /routines/{routineId} retorna la información del usuario cuando el usuario actual es el creador de la misma. 

Instalación de la actualización del API:
----------------------------------------
1) Descomprimir el archivo "API v2.0.2 Installer.zip" en el directorio de instalación.
2) Reiniciar el API para que se aplique la actualización.
NOTA: Para corroborar la correcta instalación de la actualización verificar que en el encabezado de página de la documentación del API se muestre la leyenda "Swagger Exercise 2.0.2".