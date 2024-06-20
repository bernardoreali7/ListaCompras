package listacompras.app.main.Entities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import listacompras.app.main.R;

public class ItemsEditAdapter extends RecyclerView.Adapter<ItemsEditAdapter.ItemViewHolder> {
    private List<Item> itemList;
    private OnItemSelectedListener onItemSelectedListener;
    private Set<Long> selectedItems;
    private Context context;

    public interface OnItemSelectedListener {
        void onItemSelected(List<Long> selectedItems, float total);
    }

    public ItemsEditAdapter(List<Item> itemList, List<Long> selectedItems, Context context, OnItemSelectedListener onItemSelectedListener) {
        this.itemList = itemList;
        this.selectedItems = new HashSet<>(selectedItems);
        this.onItemSelectedListener = onItemSelectedListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.edit_lista_compras_list_item, parent, false);
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

    public List<Long> getSelectedItems() {
        return new ArrayList<>(selectedItems);
    }

    public float calculateTotal() {
        float total = 0;
        for (Item item : itemList) {
            if (selectedItems.contains(item.getId())) {
                total += item.getPreco();
            }
        }
        return total;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView editTextItemName;
        TextView editTextItemPrice;
        CheckBox checkBoxSelect;

        ItemViewHolder(View itemView) {
            super(itemView);
            editTextItemName = itemView.findViewById(R.id.textViewName);
            editTextItemPrice = itemView.findViewById(R.id.textViewPrice);
            checkBoxSelect = itemView.findViewById(R.id.checkBoxItem);
        }

        void bind(Item item) {
            editTextItemName.setText(item.getNome());
            editTextItemPrice.setText(String.valueOf(item.getPreco()));
            checkBoxSelect.setChecked(selectedItems.contains(item.getId()));

            checkBoxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedItems.add(item.getId());
                } else {
                    selectedItems.remove(item.getId());
                }

                onItemSelectedListener.onItemSelected(getSelectedItems(), calculateTotal());
            });
        }
    }
}