package listacompras.app.main.Entities;

import java.io.Serializable;

public class ListaCompras implements Serializable {
    private long ID;
    private String NOME;
    private String DATA;

    public ListaCompras(long id, String name, String data) {
        this.ID = id;
        this.NOME = name;
        this.DATA = data;
    }

    public long getId() {
        return ID;
    }

    public String getName() {
        return NOME;
    }

    public String getData() {
        return DATA;
    }
}
