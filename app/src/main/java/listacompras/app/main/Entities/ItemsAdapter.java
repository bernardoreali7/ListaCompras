package listacompras.app.main.Entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import listacompras.app.main.R;
import listacompras.app.main.Utils.Util;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private List<Item> itemList;
    private List<Item> selectedItems = new ArrayList<>();;
    private OnItemSelectedListener onItemSelectedListener;

    public interface OnItemSelectedListener {
        void onItemSelected(List<Item> selectedItems);
    }

    public ItemsAdapter(List<Item> itemList, OnItemSelectedListener onItemSelectedListener) {
        this.itemList = itemList;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_lista_compras_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemNome;
        TextView textViewItemPreco;
        CheckBox checkBoxItem;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemNome = itemView.findViewById(R.id.textViewName);
            textViewItemPreco = itemView.findViewById(R.id.textViewPrice);
            checkBoxItem = itemView.findViewById(R.id.checkBoxItem);
        }

        void bind(Item item){
            textViewItemNome.setText(item.getNome());
            textViewItemPreco.setText(item.getPreco().toString());
            checkBoxItem.setChecked(selectedItems.contains(item));
            checkBoxItem.setOnCheckedChangeListener(null);

            checkBoxItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedItems.add(item);
                } else {
                    selectedItems.remove(item);
                }
                onItemSelectedListener.onItemSelected(selectedItems);
            });
        }
    }
}
