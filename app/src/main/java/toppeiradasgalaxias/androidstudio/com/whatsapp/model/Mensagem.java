package toppeiradasgalaxias.androidstudio.com.whatsapp.model;

/**
 * Created by toppeiradasgalaxias on 17/01/2018.
 */

public class Mensagem {
    private String idUsuario;
    private String mensagem;

    public Mensagem () {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
