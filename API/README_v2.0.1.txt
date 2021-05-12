API v2.0.1:
-----------
La siguiente actualización resuelve los siguientes problemas:
* El error 500 "Cannot read property 'id' of undefined" generado por la ruta GET /cycles/{cycleId}/exercises.
* El error 500 "Cannot read property 'count(*)' of undefined" generado por la ruta GET /routines luego de haber ingresado un valor en el parámetro page > 0.
* El error 403 "User is not the owner ot routine id X" generado por la ruta GET /routines/{routineId} al consultar una rutina pública de otro usuario.
* La ausencia de la propiedad username en el modelo retornado por la ruta GET /users/current.

Y también incorpora la siguiente funcionalidad para facilitarles la implementación de algunos requisitos opcionales:
* La ruta GET /users la cual permite recuperar la informacion pública de otros usuarios.
* Los parámetros categoryId, userId y averageRating a la ruta GET /routines. 

Instalación de la actualización del API:
----------------------------------------
1) Descomprimir el archivo "API v2.0.1 Installer.zip" en el directorio de instalación.
2) Reiniciar el API para que se aplique la actualización.
NOTA: Para corroborar la correcta instalación de la actualización verificar que en el encabezado de página de la documentación del API se muestre la leyenda "Swagger Exercise 2.0.1".