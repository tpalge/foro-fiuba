package nuevo_foro_fiuba

class CreadorEncuestaNoPuedeVotarException extends Exception {

    static final String MENSAJE = "El creador de la encuesta no puede votar"

    static constraints = {
    }

    CreadorEncuestaNoPuedeVotarException(){}
}
