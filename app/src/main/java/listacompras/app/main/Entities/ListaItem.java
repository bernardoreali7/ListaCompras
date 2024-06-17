package listacompras.app.main.Entities;

public class ListaItem {
    private long listaId;
    private long itemId;

    public ListaItem(long listaId, long itemId) {
        this.listaId = listaId;
        this.itemId = itemId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getListaId() {
        return listaId;
    }

    public void setListaId(long listaId) {
        this.listaId = listaId;
    }
}
