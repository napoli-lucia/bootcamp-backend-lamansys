# Informe Proyecto Ecommerce

## Integrantes
- Emiliana Girardi
- Lucía Napoli

## Introducción
El objetivo principal de este Bootcamp es la construcción de una API REST empleando Spring Boot como framework de desarrollo. El proyecto se centra en la creación de un sistema de comercio electrónico, comúnmente conocido como E-commerce. Este sistema permitirá a los usuarios gestionar un carrito de compras a través de una API que soporte operaciones de alta, baja y modificación (ABM).

La API debe proporcionar servicios esenciales para crear un carrito de compras que contenga al menos un producto, añadir productos al carrito, eliminar productos, calcular el precio total de los productos en el carrito y finalizar la compra. Para ello, se utilizarán tecnologías como Spring Boot, PostgreSQL y Docker.

Además, se ha adoptado la arquitectura hexagonal como convención para dirigir todo el desarrollo, asegurando una estructura modular y mantenible que permita una fácil adaptación a futuros cambios y necesidades.

Para la gestión de los requerimientos, utilizamos Jira, donde se presentan en forma de User Stories los requerimientos y se desglosan en tareas concretas de desarrollo. Este enfoque permite brindar visibilidad tanto del trabajo en progreso como del trabajo pendiente, facilitando el seguimiento y avance del proyecto. Cada commit está vinculado a una tarea en Jira mediante un identificador utilizando el siguiente formato `EC-[nro_tarea_jira]: [Descripción de la funcionalidad que incorpora]`. Esto asegura la trazabilidad entre los requerimientos y los desarrollos incluidos en el repositorio.

🔗[Proyecto en Jira](https://lamansys-grupo2.atlassian.net/jira/software/projects/EC/boards/2?atlOrigin=eyJpIjoiNzZkN2ZmMTEwMjU4NGYwMWIxZDBkZjNmZGFmYTg5MTYiLCJwIjoiaiJ9)

# User Stories

## Como usuario, quiero obtener un listado de productos con sus cantidades disponibles, para saber qué productos están actualmente en el stock de otro usuario.

[EC-33](https://lamansys-grupo2.atlassian.net/browse/EC-33) El usuario del sistema debe contar con la posibilidad de acceder al listado de productos con sus cantidades disponibles y su precio unitario.

### Criterios de aceptación:

* La API debe devolver una lista de productos, con su precio unitario y cantidad.
* Si no hay productos disponibles, se debe devolver una lista vacía.

> GET /products/{ownerId}
- Obtener listado de productos

200 Lista con productos
```json
[
  {
    "productId": "1",
    "ownerId": "1",
    "productName": "Cafe",
    "stock": 5,
    "unityPrice": 20.0
  },
  {
    "productId": "2",
    "ownerId": "1",
    "productName": "Ojotas",
    "stock": 10,
    "unityPrice": 1000.0
  }
]
```
200 Lista vacía
> [ ]

404 Usuario no existe
> 404 "User 4 don't exists"

## Como usuario, quiero crear un carrito de compra con productos de otro usuario, para poder preparar una compra.

[EC-5](https://lamansys-grupo2.atlassian.net/browse/EC-5) El usuario del sistema debe poder iniciar un nuevo carrito de compra y seleccionar los productos que desea adquirir del inventario de otro usuario. El sistema debe permitir agregar múltiples productos a un mismo carrito.

### Criterios de aceptación:

* La API debe permitir crear un carrito de compra especificando el usuario al que se desea realizar misma.
* El carrito contiene como minimo, una lista de códigos de productos con sus cantidades.
* El sistema debe validar que el carrito contenga al menos un producto.
* El sistema debe validar que cada código de producto pertenezca a un producto existente en ese stock; si no, debe devolver un error indicando el problema.
* El sistema debe validar que la cantidad solicitada no exceda el stock disponible; si lo hace, debe devolver un error.
* Las respuestas de error deben ser claras y específicas al problema.
* En caso de éxito en la creación del carrito, se debe devolver el código del mismo.

> POST /carts/{ownerId}/{sellerId}
- Crear un carrito de compra dado un id de dueño de carrito y de usuario vendedor

Body
```json
[
  {
    "productId": "3",
    "quantity": 2
  },
  {
    "productId": "4",
    "quantity": 1
  }
]
```

200 Carrito creado
```json
{
  "cartId": "9672a354-4630-4e52-98c2-9441ecd6dcee",
  "addedProducts": [
    {
      "productId": "3",
      "quantity": 2
    },
    {
      "productId": "4",
      "quantity": 1
    }
  ],
  "exceptions": []
}
```

200 Carrito creado, pero un producto no se agrego por falta de stock
```json
{
  "cartId": "27dca674-1050-47fa-ab5b-2b8e850f3026",
  "addedProducts": [
    {
      "productId": "3",
      "quantity": 2
    }
  ],
  "exceptions": [
    "Product 4 cannot meet the stock"
  ]
}
```

404 Carrito no creado
```json
[
  "Product 3 cannot meet the stock",
  "Product 4 cannot meet the stock"
]
```

## Como usuario, quiero agregar un tipo de producto a un carrito de compra existente.

[EC-6](https://lamansys-grupo2.atlassian.net/browse/EC-6) El usuario del sistema debe poder agregar un tipo de producto específico a un carrito de compra existente. El sistema debe permitir seleccionar la cantidad de unidades del producto a agregar.

### Criterios de aceptación:

* La API debe permitir agregar un tipo de producto a un carrito de compra específico.
* El sistema debe validar que el carrito de compra exista y pertenezca al usuario que realiza la operación, también que el tipo de producto que se agrega, pertenezca al stock del usuario al que se esta realizando la compra; en caso contrario, debe devolver el error especifico.
* El sistema debe validar que la cantidad de unidades del tipo de producto a agregar al carrito no exceda el stock disponible; si lo hace, debe devolver un error.

> POST /carts/addProduct/{ownerId}/{cartId}
- Agregar un tipo de producto al carrito de compra

200 Producto agregado a carrito
> Product added to cart successfully

404 Carrito no existe
> Cart 27dca674-1050-47fa-ab5b-2b8e850f302 not exists

404 Usuario no existe
> User 7 don't exists

404 Producto no existe
> Product 30 not exists

401 Usuario no autorizado
> Owner 2 does not own cart 27dca674-1050-47fa-ab5b-2b8e850f3026

400 Stock insuficiente
> Product 3 cannot meet the stock

## Como usuario, quiero modificar la cantidad de unidades de un tipo de producto al carrito de compra, para ajustar mi pedido.

[EC-7](https://lamansys-grupo2.atlassian.net/browse/EC-7) El usuario del sistema debe poder modificar la cantidad de unidades de un producto ya agregado a un carrito de compra.

### Criterios de aceptación:

* La API debe permitir modificar la cantidad unidades de un tipo de producto de un carrito de compra específico.
* El sistema debe validar que el carrito de compra exista, que pertenezca al usuario asociado y el tipo de producto ya esté precargado en el carrito; si no, debe devolver un error.

> PUT /carts/editProduct/{owner}/{cartId}
- Editar un tipo de producto al carrito de compra

Body
```json
{
  "productId": "3",
  "quantity": 7
}
```

200 Producto editado en carrito
> Product edited quantity in cart successfully

404 Carrito no existe
> Cart 27dca674-1050-47fa-ab5b-2b8e850f302 not exists

404 Usuario no existe
> User 7 don't exists

404 Producto no existe
> Product 30 not exists

401 Usuario no autorizado
> Owner 2 does not own cart 27dca674-1050-47fa-ab5b-2b8e850f3026

400 Stock insuficiente
> Product 3 cannot meet the stock


## Como usuario, quiero eliminar un tipo de producto en un carrito de compra, para ajustar mi pedido.

[EC-8](https://lamansys-grupo2.atlassian.net/browse/EC-8) El usuario del sistema debe poder eliminar un tipo de producto ya agregado a un carrito de compra.

#### Criterios de aceptación:

* La API debe permitir eliminar un tipo de producto en un carrito de compra específico.
* El sistema debe validar que el carrito de compra exista y pertenezca al usuario asociado; también que el tipo de producto a eliminar, pertenezca actualmente al carrito, si no, debe devolver un error.

> DELETE /carts/deleteProduct/{ownerId}/{cartId}/{productId}
- Eliminar productos del carrito de compra

200 Producto eliminado en carrito
> Product in cart deleted successfully

404 Carrito no existe
> Cart 27dca674-1050-47fa-ab5b-2b8e850f302 not exists

404 Usuario no existe
> User 7 don't exists

401 Usuario no autorizado
> Owner 2 does not own cart 27dca674-1050-47fa-ab5b-2b8e850f3026

400 Producto no existe en el carrito
> Product 1 not exists in cart

## Como usuario, quiero solicitar el estado de un carrito para hacer la compra.

[EC-9](https://lamansys-grupo2.atlassian.net/browse/EC-9) El usuario del sistema debe poder solicitar el estado de un carrito de compra específico, para obtener información sobre los productos que contiene y el costo total de la compra.

#### Criterios de aceptación:

* El sistema debe validar que el carrito de compra exista y pertenezca al usuario asociado; si no, debe devolver un error.
* La API debe permitir validar si la cantidad solicitada de cada tipo de producto puede ser satisfacida por el stock actual. si no, debe devolver la información del faltante.
* La API debe permitir devolver el precio por unidad de cada producto solicitado.
* La API debe permitir calcular el costo total de los productos en un carrito de compra específico.

> GET carts/state/{ownerId}/{cartId}
- Calcular el estado del carrito de compra

200 Estado del carrito
```json
{
  "totalPrice": 31000.0,
  "leftOut": [],
  "products": [
    {
      "productId": "3",
      "unityPrice": 3000.0
    },
    {
      "productId": "4",
      "unityPrice": 25000.0
    }
  ]
}
```
404 Carrito no existe
> Cart 27dca674-1050-47fa-ab5b-2b8e850f302 not exists

404 Usuario no existe
> User 7 don't exists

401 Usuario no autorizado
> Owner 2 does not own cart 27dca674-1050-47fa-ab5b-2b8e850f3026

## Como usuario, quiero finalizar mi compra, para completar el proceso de compra.

[EC-10](https://lamansys-grupo2.atlassian.net/browse/EC-10) El usuario del sistema debe poder finalizar una compra, para completar el proceso de compra y actualizar el stock de los productos.

#### Criterios de aceptación:

* La API debe permitir finalizar una compra para un carrito de compra específico.
* El sistema debe validar que el carrito de compra exista y pertenezca al usuario asociado; si no, debe devolver un error.
* El sistema debe validar que el carrito de compra pueda ser satisfacido en términos de stock; si no, debe devolver un error.
* El sistema debe validar que el estado del carrito que el usuario obtuvo coincida con el estado de la compra que va a realizar (precio unirario / precio total); si no, debe devolver un error.
* Al finalizar la compra, el sistema debe actualizar el stock de los productos.
* El carrito debe ser procesado, impidiendo operaciones sobre él.

> POST /carts/purchase/{ownerId}/{cartId}
- Finalizar compra

200 Compra finalizada
> Cart purchase successfully. Total price: 31000.0

404 Carrito no existe
> Cart 27dca674-1050-47fa-ab5b-2b8e850f302 not exists

404 Usuario no existe
> User 7 don't exists

401 Usuario no autorizado
> Owner 2 does not own cart 27dca674-1050-47fa-ab5b-2b8e850f3026

400 Stock insuficiente
```json
[
  "Product 4 cannot meet the stock"
]
```