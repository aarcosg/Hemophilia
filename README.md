# Hemophilia
### Instrucciones para compilar la aplicación:

1. Descargar todo el código haciendo click en el botón `Download ZIP` de la derecha.

2. Descomprimir los archivos y abrir el proyecto desde Android Studio.

3. Crear un nuevo proyecto en https://console.developers.google.com.

4. Copiar el identificador del proyecto y pegarlo en el archivo `backend/src/main/webapp/WEB-INF/appengine-web.xml` en la siguiente etiqueta:
  ```xml
  <application>ID_PROYECTO</application>
  ```
  
5. Iniciar sesión en Android Studio con tu cuenta de Google haciendo click en el botón de la esquina superior derecha de Android Studio.

6. Desplegar el proyecto en Google App Engine mediante la opción `Build > Deploy Module to App Engine...`

7. Editar el archivo `app/src/main/java/us/idinfor/hemophilia/Constants.java` para introducir el identificador de tu proyecto de Google App Engine:
  ```java
  public static final String GOOGLE_APPENGINE_URL = "https://ID_PROYECTO.appspot.com/_ah/api/";
  public static final String GOOGLE_APPENGINE_APP_NAME = "ID_PROYECTO";
  ```
  
8. Ya puedes ejecutar la app en tu dispositivo móvil o en el emulador.
