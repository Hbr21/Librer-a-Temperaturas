  # Librer-a-Temperaturas
# 🌤️ Librería de Clima Java - OpenWeather API

En este proyecto se emplea una librería en Java que permite consultar el clima actual y el pronóstico del clima de las próximas horas para una ciudad determinada, utilizando la API de OpenWeather, presenta los datos con imágenes de fondo y pequeños íconos de clima por hora,según el clima del lugar, casi similar a la de una app de clima real.

---


## 🚀 Propósito

Diseñar e implementar una librería y componetente visual, capaz de ser reutilizado en otros proyectos, atraves del entorno de Netbeans, con la finalidad de ser usada en diferentes proyectos, en el cual necesitemos consultar la información del clima en tiempo real de un determinado lugar.

---


## 🧱 Estructura del Código


La librería está organizada en tres clases principales que encapsulan la lógica de conexión con la API, el modelado de datos del clima y la visualización del clima desde una interfaz gráfica.

### 🔹 Clase Clima

Esta clase se encarga de la conexión del proyecto con la API de OpenWeather, en la cual se encarga de hacer las peticiones HTTP Y procesar las respuestas atravez del uso de la libreria JSON para convertir esos datos en objetos para que el resto del programa lo pueda utilizar facilmemte.

El metodo obtenerClima se encarga de obtener el clima actual, incluyendo la temperatura, la descripción y la zona horaria del lugar solicitado.

El metodo obtenerProximosPronosticos devuelve una lista de objetos con la temperatura e ícono del clima para las proximas 5 horas del lugar.

El metodo obtenerRespuestaDeUrl conecta con la API y devuelve el contenido solicitado en forma de texto.


### 🔹 Clase Temperatura

En esta clase es la principal de la librería ya que en ella se obtiene el contexto climatico y es la que recibe los datos obtenidos de la clase Clima, aqui representa los grados del clima, su descripción, la presencia de lluvia y la zona horaria del lugar.

Esta clase tiene atributos que son los siguientes:

double valorCelsius: Temperatura en grados Celsius.

String descripcion: Texto que describe el clima, provisto por la API.

boolean lluvia: Determina si la descripción indica presencia de lluvia.

int timezone: Zona horaria del lugar en segundos con respecto a UTC.

Además se tienen los siguientes métodos:

double getCelsius()
Retorna directamente el valor en °C. Se mantiene sin redondeo para mantener precisión.

double getFahrenheit()
Convierte los °C a °F usando la fórmula estándar: F=(C×(5/9)+32 Donde: 9/5 representa la diferencia de escala entre °C y °F, y el 32 ajusta el punto cero entre ambas escalas.

double getKelvin()
Convierte °C a °K con la fórmula:𝐾=𝐶+273.15 Ya que 0 °C equivale a 273.15 K.

String getDescripcion()
Devuelve la descripción textual del clima

boolean hayLluvia()
Devuelve true si la descripción contiene indicios de lluvia (analizado previamente en la clase Clima), usado para cambiar el fondo o mostrar alerta visual.

boolean haceFrio()
Retorna true si la temperatura es menor a 15 °C, lo que generalmente indica clima frío en la mayoría de contextos urbanos.

boolean haceCalor()
Retorna true si la temperatura supera los 30 °C, considerado clima caluroso.

int getTimezone()
Retorna el desfase horario con respecto a UTC, usado para ajustar horas locales.


### 🔹 Clase PronosticoHora

Modelo de datos para almacenar una hora del pronóstico:

Temperatura.
Ícono del clima (`"10d"`, `"02n"`, etc.) que se carga desde la carpeta `/recursos`.


### 🔹 Clase Tem


Esta clase hereda de JPanel y representa visualmente el clima en una interfaz, es el componente que el usuario de la librería ve directamente.

En la clase se encuentran los siguientes atributos:

JLabel lblCiudadConsultada: Muestra el nombre de la ciudad.

JLabel lblTemperatura: Muestra la temperatura actual.

JLabel lblLluvia: Indica si hay probabilidad de lluvia.

JLabel lblTipo: Muestra la descripción textual del clima.

JLabel[] lblHoras: Arreglo de etiquetas para mostrar las próximas 5 temperaturas por hora.

JLabel[] lblIconosHora: Arreglo de etiquetas para mostrar íconos por hora según la API.

Image fondo: Imagen de fondo representando el clima actual.

Y los metodos son los siguientes:

Constructor Tem()
Configura el layout manualmente y coloca todos los componentes en coordenadas absolutas.

También inicializa el fondo con una imagen por defecto (fondo.jpg) y crea las etiquetas con estilo definido (fuente, color, posición).

Metodo crearLabel: Método auxiliar para crear JLabel con fuente y color estándar, reutiliza lógica para evitar repetición.

Método paintComponent: Sobrescribe el método de JPanel para dibujar la imagen de fondo, esto nos permite cambiar el fondo dinámicamente cuando cambia el clima.

Método mostrarClimaDe(String ciudad): Es el metodo más importante del panel la cual hace lo siguiente:

Llama a la clase Clima para obtener: Clima actual, Pronóstico por hora y ajusta todos los JLabel con los datos obtenidos.

Cambia la imagen de fondo dependiendo de si el clima contiene lluvia, nubes o sol: ("lluvia" → lluvia.jpg), ("nube" → nublado.jpg) o ("sol" →sol.jpg)

Calcula la hora local usando timezone, Instant.now() y ZoneOffset.

Para cada hora: Muestra el texto (hora local + temperatura redondeada)

Carga el ícono correspondiente y lo escala a 40x40 pixeles para que se vea completo el icono.


## Importar el .jar en otro proyecto

Para importar la libreria en otro proyecto, lo que se debe de tener es el proyecto a la cual se lo vamos a implementar, en este caso sería en un JFrame, una vez ya en el frame, debemos tambien ya de tener el .jar, la cual se obtiene al darle Clean and Build al proyecto y se crear el .jar en la carpeta dist del proyecto, en el proyecto de implkemetación daremos clic derecho a la paleta, y le daremos clic donde dice palette manager.

Una vez ahí le daremos clic en New Category y le pondremos un nombre a la nueva categoría, eso creara una nueva categoria, posteriormente le daremos clic a donde dice Add from JAR y buscaremos la carpeta dist donde se guardo la libreria, la seleccionamos y se abriran las clases, selecionaremos la clase Tem que es la interfaz, y nos dira en que categoría lo guardaremos, la cual seleccionaremos la categoría que hemos creado y por ultimo le daremos clic en Finish y con eso se agrega el componente que hicimos a la paleta de Netbeans y con eso podremos arrastrar el componentes y utilizarla en nuestro proyecto.



## Funcionamiento del programa

A continuación se muestra un ejemplo del funcionamiento de la librería del clima en otro proyecto:

<img width="980" height="707" alt="image" src="https://github.com/user-attachments/assets/c67d774e-0461-484b-848f-bc765f253eb0" />

Aqui se muestra como se encuentra el proyecto ya con el componente ya arrastrado


<img width="933" height="617" alt="image" src="https://github.com/user-attachments/assets/dc1e4552-8126-43a5-8efa-3ca4d75f67c6" />


Aqui se muestra que desde el otro proyecto tenemos una caja de texto en la cual ingresamos el lugar del que queremos saber su clima y el componente lo que hace es mostrar el clima de dicho lugar, en este caso en México



<img width="928" height="646" alt="image" src="https://github.com/user-attachments/assets/fb596848-33a0-45db-9b7d-417e5ab48173" />


Aqui tenemos otro ejemplo pero ahora del país de China.



<img width="936" height="641" alt="image" src="https://github.com/user-attachments/assets/a2c19487-c89b-4412-ab4f-838d81ba683d" />


Aqui se muestra otro ejemplo pero ahora con el país de Portugal, y con esto demostramos que la librería es capaz de mostrar el clima de cualquier país del mundo así como tambien su clima en las proximas 5 horas.




## Video Explicativo 


https://youtu.be/8ao5H0BO4cs?si=0ODnH39mryLlHzYp


https://youtu.be/8gsBWapD1q0?si=fXGA8F1M1SCC82P7



## Documentación JavaDoc 

https://Hbr21.github.io/Librer-a-Temperaturas/



