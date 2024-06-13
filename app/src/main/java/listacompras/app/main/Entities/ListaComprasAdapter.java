package listacompras.app.main.Entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import listacompras.app.main.R;

public class ListaComprasAdapter extends ArrayAdapter<ListaCompras> {
    public ListaComprasAdapter(Context context, List<ListaCompras> listasCompras) {
        super(context, 0, listasCompras);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListaCompras contact = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_compras_list_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewData = convertView.findViewById(R.id.textViewData);

        textViewName.setText(contact.getName());
        textViewData.setText(contact.getData());

        return convertView;
    }
}
