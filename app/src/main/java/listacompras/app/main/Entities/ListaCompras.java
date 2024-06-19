package listacompras.app.main.Entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class ListaCompras implements Serializable {
    private long id;
    private String nome;
    private Date data;

    public long getId() {
        return id;
    }

    public String getName() {
        return nome;
    }

    public Date getData() {
        return data;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
