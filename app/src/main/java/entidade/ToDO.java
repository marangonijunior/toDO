package entidade;

/**
 * Created by hedneijr on 11/02/2016.
 */
public class ToDO {

    private long id;
    private String titulo;
    private String detalhe;
    private String urgencia;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalhe() {
        return this.detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public String getUrgencia() {
        return this.urgencia;
    }

    public void setUrgencia(String urgencia) {
        this.urgencia = urgencia;
    }

}
