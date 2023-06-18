# StackTrace_Tecnico



## Para poner en funcionamiento el proyecto:  
- Dirigirse a MYSQL Workbench, cargar la database, `Server -> Data import` y seleccionar la opcion `Import From Self-Contained File` y luego seleccionar el archivo sql `proyect_database`
- Si no tenemos los mismos datos de acceso, vas a tener que cambiarlos desde el `application.properties` dentro de `proyecto-wallet/src/main/resources/`
- Abrir la carpeta `proyecto-wallet` desde IntelliJ
- Ejecutar la applicacion
- Listo para usar!




## REQUEST utilizadas con PostMan

Se incorporo a la carpeta un Collection V2 para importar a PostMan con todas las Request pero tambien las dejo aqui, con aclaraciones:

- Para crear un usuario:
`http://localhost:8080/api/createUser` de tipo `POST` donde le tenemos que enviar como body un Json:
```
{
    "nombre": "AAAAAAAA",
    "apellido": "Del AAAA",
    "email": "aaaa@gmail.com",
    "sexo": "hombre",
    "dni": "33222333",
    "telefono": "123123123",
    "password": "123"
}
```

- Modificar Usuario:
 `http://localhost:8080/api/editUser/id` de tipo `PUT`
donde le pasamos el id del usuario a modificar asi tambien un body como el de crear usuario pero con los cambios a realizar.

- Eliminar Usuario: `http://localhost:8080/api/deleteUser/id`con id del usuario a eliminar, tipo `DELETE`.

- Listar Usuarios: `http://localhost:8080/api/getUsers` tipo `GET`
- Alta Divisa: `http://localhost:8080/api/altaDivisa` tipo `POST`
  body:
    ```
    {
    "nombre_cripto": "BTC",
    "valor_cripto": 10.50
    }
    ```

- Modificar Divisa: `http://localhost:8080/api/modificarDivisa/id` id de la divisa a modificar y le pasamos el nuevo body:
  ```
  {
    "nombre_cripto": "BTC",
    "valor_cripto": 55
  }
  ```
- Borrar Divisa: `http://localhost:8080/api/deleteDivisa/id` id de la divisa a eliminar, tipo `DELETE`.

- Listar Divisas: `http://localhost:8080/api/listarDivisas/` tipo `GET`.

- Crear Billetera: `http://localhost:8080/api/createWallet/id` id del usuario, donde indico a que usuario le quiero crear esa billetera, tipo `POST`.

- Modificar Billetera: `http://localhost:8080/api/modificarWallet/id` , id donde indico la direccion de la billetera, el unico atributo que puedo modificar es hash, siempre y cuando no se repita con otra billetera,tipo `PUT`.
  ```
  {
    "hash": 9100000000000000000
  }
  ```
- Eliminar Biletera: `http://localhost:8080/api/deleteWallet/id`, id de la billetera a eliminar, tipo `DELETE`.

- Transferencia: `http://localhost:8080/api/transferir/` tipo `PUT` donde le paso como body un json del tipo:
  ```
  {
    "walletOrigen": 4652798357273486490,
    "walletDestino": 3850703319127461953,
    "cantidad": 10,
    "criptoDivisa": "BTC"
  }
  ```
- Deposito: `http://localhost:8080/api/deposito/id` donde indico el id de la billetera, tipo `PUT` y a su vez un body:
  ```
  {
    "nombre_divisa": "BTC",
    "cantidad_divisa": 4
  }
  ```
- Consultar saldo de una billetera: `http://localhost:8080/api/consultarSaldo/id`, donde el id es el de la billeteraa, tipo `GET`.

- Consultar saldo total de usuario (Todas las billeteras): `http://localhost:8080/api/consultarSaldoDeUsuario/id`, donde el id es el del usuario, tipo `GET`.

## Orden de ejecucion, Importante!

***Desde la web, un usuario no puede crear una billetera si no existe una divisa anteriormente, por ende en los REQUEST con POSTMAN, es importante seguir esta ejecucion:***
- Crear usuario
- Crear Divisa
- Crear Billetera
  
Despues, las ejecuciones que se hagan, van a ser exitosas segun las consideraciones aplicadas.


## Funcionamiento

El funcionamiento de la pagina web es el planteado, se puede acceder a todo lo que se pidio desde el navbar!
