package listacompras.app.main.Entities;

public class Item {
    private long id;
    private String nome;
    private Float preco;
    private Boolean isSelected = false;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public Boolean isSelected() {
        return isSelected;
    }
}
