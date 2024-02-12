package pgl.practicafinalpgl.Rutas

sealed class Rutas(var ruta: String) {
    object PantallaLogin: Rutas(ruta = "pantallalogin")
    object PantallaReproductor: Rutas(ruta = "pantallareproductor")
    object PantallaPlaylists: Rutas(ruta = "pantallaplaylists")
    object PantallaAlbum: Rutas(ruta = "pantallaalbum")
    object PantallaAllCanciones: Rutas(ruta = "pantallaallcanciones")

    object PantallaUser: Rutas(ruta = "pantallauser")
    object PantallaTesting: Rutas(ruta = "testing")
}