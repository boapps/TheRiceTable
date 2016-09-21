package org.ricetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static org.ricetable.edita.adapterArray;
import static org.ricetable.edita.editor;
import static org.ricetable.edita.from;
import static org.ricetable.edita.teremArray;

public class customadapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public customadapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list, null);
        }
        TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
        final TextView teremView = (TextView) view.findViewById(R.id.teremView);
        listItemText.setText(position+1 + ". " + list.get(position));
        teremView.setText(teremArray.get(position));
        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapterArray.remove(0);
                list.remove(position);
                teremArray.remove(position);
                notifyDataSetChanged();
                editor.putString(from, adapterArray.toString());
                editor.putString(from + "terem", teremArray.toString());
                editor.commit();
                System.out.println(position);
                System.out.println(adapterArray);
                System.out.println(teremArray);
            }
        });

        return view;
    }
}
