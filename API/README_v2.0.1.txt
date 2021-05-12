API v2.0.1:
-----------
La siguiente actualizaci�n resuelve los siguientes problemas:
* El error 500 "Cannot read property 'id' of undefined" generado por la ruta GET /cycles/{cycleId}/exercises.
* El error 500 "Cannot read property 'count(*)' of undefined" generado por la ruta GET /routines luego de haber ingresado un valor en el par�metro page > 0.
* El error 403 "User is not the owner ot routine id X" generado por la ruta GET /routines/{routineId} al consultar una rutina p�blica de otro usuario.
* La ausencia de la propiedad username en el modelo retornado por la ruta GET /users/current.

Y tambi�n incorpora la siguiente funcionalidad para facilitarles la implementaci�n de algunos requisitos opcionales:
* La ruta GET /users la cual permite recuperar la informacion p�blica de otros usuarios.
* Los par�metros categoryId, userId y averageRating a la ruta GET /routines. 

Instalaci�n de la actualizaci�n del API:
----------------------------------------
1) Descomprimir el archivo "API v2.0.1 Installer.zip" en el directorio de instalaci�n.
2) Reiniciar el API para que se aplique la actualizaci�n.
NOTA: Para corroborar la correcta instalaci�n de la actualizaci�n verificar que en el encabezado de p�gina de la documentaci�n del API se muestre la leyenda "Swagger Exercise 2.0.1".