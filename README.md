# HCI-REPS
HCI 1er Cuatrimestre 2021 - Grupo 8

# WEB

Instructivo de instalación

Archivos necesarios para el correcto funcionamiento del sitio Web

Los archivos y datos necesarios para el correcto funcionamiento del sitio web son:
poner q archivo
archivo
Estos archivos se encuentran dentro del .zip entregado

Para la creación de este sitio Web se utilizaron:
Vue CLI
Vuetify
node.js
Vuex

○ Archivo con extensión ZIP o RAR conteniendo todos los archivos que sean indispensables para el correcto funcionamiento del sitio Web, descartando del mismo los archivos generados para pruebas, Frameworks que finalmente no se utilizaron, etc. 

Instructivo de instalación: 

Pasos a realizar para instalar y configurar el sitio Web  
Como se mencionó en clase, en el .zip  se encuentra la página web implementada  Para poder correr la web deberá seguir los siguientes pasos:

En el directorio de la API y correr las siguientes instrucciones:

>npm install


>npm start

Esto le devolverá la dirección http://localhost:8080/ la cual indica en qué puerto se está corriendo la API. 
En el caso de querer correr en otra dirección seguir las instrucciones de cambio de dirección que se encuentran al término de estos pasos.

Desde otra terminal ubicarse dentro del proyecto web y correr:
>npm install
>npm run serve

Esto le devolverá la dirección http://localhost:8081/ la cual indica en qué puerto se está corriendo la web. Debe abrir el enlace en su navegador. 

En el caso de querer correr en otra dirección seguir las instrucciones de cambio de dirección que se encuentran al término de estos pasos.


INSTRUCCIONES DE CAMBIO DE DIRECCIÓN:
Para poder correr la API y la web con las direcciones modificadas deberá modificar algunos archivos.

Archivo de la API:
Ir a la carpeta “config” y abrir el archivo “mailing.js”. 
Dirigirse donde dice:
confirmationLink: 'http://localhost:8080/#/ConfirmacionMail’


y reemplazar http://localhost:8081 por el enlace deseado donde se correrá la web.

Archivo del proyecto Web:
Dentro de : src/reps-web/reps-web/components/shareRoutine
Dentro de la función “copyAndShowSnackBar” reemplazar 
‘await
navigator.clipboard.writeText(‘urlDeseada/#/routines/${this.rutina.id}’)
	Dentro de : src/API_EJS/js/api  cambiar el valor de “baseUrl” por el enlace en el que se encuentra su API corriendo.


Navegadores Soportados:
-Google Chrome 86
-Microsoft Edge 86
-Firefox 82.0


- Nombres y versiones de los navegadores soportados. 
- Secuencia de pasos que deben realizarse para instalar y configurar el sitio Web en un servidor Web.

