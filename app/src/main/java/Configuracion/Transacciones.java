package Configuracion;

public class Transacciones {

    //Nombre de la base de datos
    public static final String DBName = "PMQuiz";

    //Creación de las tablas de las bases de datos.
    public static final String TablePersonas = "personas";

    //Creacion de los campos de base de datos

    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = "edad";
    public static final String correo = "correo";
    public static final String direccion = "direccion";


    // DDL Create
    public static final String CreateTablePersonas = "Create table "+ TablePersonas +"("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT, nombres TEXT, apellidos TEXT, edad INTEGER, "+
            "correo TEXT, direccion TEXT )";

    //DDL Drop
    public static final String DropTablePersonas = "DROP TABLE IF EXISTS "+ TablePersonas;

    //DML
    public static final String SelectAllPersonas = "SELECT * FROM "+ TablePersonas;
}
