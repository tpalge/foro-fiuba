package nuevo_foro_fiuba

class BootStrap {

    def init = { servletContext ->
      Usuario usuario = new Usuario ("Mariano", "Martin", "mmartin")
      usuario.save(failOnError:true)
      Usuario usuario2 = new Usuario ("Rodrigo", "Aparicio", "raparicio")
      usuario2.save(failOnError:true)
      Usuario usuario3 = new Usuario ("German", "Rotili", "grotili")
      usuario3.save(failOnError:true)
      Materia materia = new Materia ('Matematica discreta', 'Esto es mate discreta')
      materia.save(failOnError:true)
      Materia materia2 = new Materia ('Analisis II', 'Esto es analisis II')
      materia2.save(failOnError:true)
      Materia materia3 = new Materia ('Fisica II', 'Esto es algo III')
      materia3.save(failOnError:true)
      Profesor profesor = new Profesor ('Acero')
      profesor.save(failOnError:true)
      Profesor profesor2 = new Profesor ('Sirne')
      profesor2.save(failOnError:true)
      Catedra catedra = new Catedra (materia, profesor, 'DescripcionCatedra_')
      catedra.save(failOnError:true)
      Catedra catedra2 = new Catedra (materia2, profesor2, 'DescripcionCatedra2_')
      catedra2.save(failOnError:true)
      Catedra catedra3 = new Catedra (materia3, profesor, 'DescripcionCatedra3_')
      catedra3.save(failOnError:true)
      Cursada cursada = new Cursada (usuario, catedra)
      cursada.save(failOnError:true)
      usuario.setCursadas(usuario.cursadas+=[cursada])
      Publicacion publicacion = new Publicacion ('TextoPublicacion_', usuario, materia, catedra)
      publicacion.save(failOnError:true)
      Publicacion publicacion2 = new Publicacion ('TextoPublicacion_', usuario2, materia, catedra)
      publicacion2.save(failOnError:true)
      Comentario comentario = new Comentario ("Soy el usuario2", usuario2, publicacion, null)
      comentario.save(failOnError:true)
      Comentario comentario2 = new Comentario ("Soy el usuario2 otra vez", usuario2, null, comentario)
      comentario2.save(failOnError:true)
      publicacion.comentarios += [comentario]
      comentario.comentarios += [comentario2]
      usuario.setPromedioCalificaciones(4)
      Cursada cursada2 = new Cursada (usuario, catedra2)
      cursada2.save(failOnError:true)
      usuario.setCursadas(usuario.cursadas+=[cursada2])
      Cursada cursada3 = new Cursada (usuario2, catedra3)
      cursada3.save(failOnError:true)
      usuario2.setCursadas(usuario2.cursadas+=[cursada3])
      Puntaje puntaje = new Puntaje (Puntaje.TipoPuntaje.ME_GUSTA, 3)
      Calificacion calificacion = new Calificacion (usuario2, puntaje, publicacion, null)
      calificacion.save(failOnError:true)
      publicacion.agregarCalificacion(calificacion)
    }
    def destroy = {
    }
}
