API v2.0.2:
-----------
La siguiente actualizaci�n resuelve los siguientes problemas:
* La ausencia de resultados retornados por la ruta GET /favourites en ciertas ocasiones.

Y tambi�n incorpora la siguiente funcionalidad:
* La ruta POST /favourites/{routineId} permite marcar como favorita una rutina creada por otro usuario.
* La ruta DELETE /favourites/{routineId} permite desmarcar como favorita una rutina creada por otro usuario.
* La ruta GET /routines/{routineId} retorna la informaci�n del usuario cuando el usuario actual es el creador de la misma. 

Instalaci�n de la actualizaci�n del API:
----------------------------------------
1) Descomprimir el archivo "API v2.0.2 Installer.zip" en el directorio de instalaci�n.
2) Reiniciar el API para que se aplique la actualizaci�n.
NOTA: Para corroborar la correcta instalaci�n de la actualizaci�n verificar que en el encabezado de p�gina de la documentaci�n del API se muestre la leyenda "Swagger Exercise 2.0.2".