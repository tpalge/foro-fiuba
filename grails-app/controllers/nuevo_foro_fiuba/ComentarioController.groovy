package nuevo_foro_fiuba

import grails.gorm.transactions.Transactional
import nuevo_foro_fiuba.Comentario.EstadoComentario

@Transactional
class ComentarioController {

    def comentarioService

    def index() {}

    def verComentario (long id, long idUsuario){
      def comentarioInstance = Comentario.get(id)
      def usuarioInstance = Usuario.get(idUsuario)
      def esDueño = comentarioService.usuarioEsDueñoDelComentario(usuarioInstance, comentarioInstance)
      def esSubComentario = comentarioService.esSubComentario(comentarioInstance)
      def comentarios = comentarioInstance.comentarios.findAll {comentario -> comentario.getEstado() != EstadoComentario.ELIMINADO}
      [comentario: comentarioInstance, usuario:usuarioInstance, modificar:esDueño, subComentario:esSubComentario, comentarios:comentarios]
    }

    def modificarTextoComentario(long id, long idUsuario,String nuevoTexto){
      def usuarioLogin = Usuario.get(idUsuario)
      def comentarioInstance = Comentario.get(id)
      comentarioService.modificarTexto(usuarioLogin, comentarioInstance, nuevoTexto)
      redirect(controller:"comentario", action: "verComentario", id: comentarioInstance.id,  params: [idUsuario:idUsuario])
    }

    def eliminarComentario(long id, long idUsuario){
      def comentarioInstance = Comentario.get(id)
      comentarioService.eliminarComentario(comentarioInstance)
      //PONER ELVIS
      if (comentarioInstance.publicacionComentada){
        redirect(controller: "publicacion", action: "verPublicacion", id: comentarioInstance.publicacionComentada.id, params: [idUsuario:idUsuario])
      }
      else{
        redirect(controller:"comentario", action: "verComentario", id: comentarioInstance.comentarioComentado.id,  params: [idUsuario:idUsuario])
      }
      //PONER ELVIS
    }

    def calificarPositivo(long id, long idUsuario){
      calificarComentario(Puntaje.TipoPuntaje.ME_GUSTA, id, idUsuario)
    }

    def calificarNegativo(long id, long idUsuario){
      calificarComentario(Puntaje.TipoPuntaje.NO_ME_GUSTA, id, idUsuario)
    }

    def calificarComentario(Puntaje.TipoPuntaje tipo, long id, long idUsuario){
      def usuarioInstance = Usuario.get(idUsuario)
      def comentarioInstance = Comentario.get(id)
      try{
        comentarioService.calificarComentario(usuarioInstance, comentarioInstance, tipo)
      }
      catch (UsuarioYaCalificoException e){
        flash.message = e.MENSAJE
      }
      redirect (action: "verComentario", id: comentarioInstance.id, params: [idUsuario:idUsuario])
    }

    def comentar(long id, long idUsuario, long idComentario, String textoComentario){
      def usuarioLogin = Usuario.get(idUsuario)
      def comentarioInstance = Comentario.get(idComentario)
      try{
        comentarioService.comentarComentario(usuarioLogin, textoComentario, comentarioInstance)
      }
      catch (PublicacionCerradaException e){
        flash.message = e.MENSAJE
      }
      redirect(controller:"comentario", action: "verComentario", id: comentarioInstance.id, params: [idUsuario:idUsuario])
    }

}
