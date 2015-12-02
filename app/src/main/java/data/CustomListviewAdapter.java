package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.listaNavidad.DisplayPresentsActivity;
import com.listaNavidad.Inicio;
import com.listaNavidad.PresentsItemDetailsActivity;
import com.listaNavidad.R;

import java.util.ArrayList;

import model.Present;

/**
 * Created by Sparta on 1/12/15.
 */
public class CustomListviewAdapter extends ArrayAdapter<Present> {

    private int layoutResource;
    private Activity activity;
    private ArrayList<Present> listaNavidad = new ArrayList<>();

    public CustomListviewAdapter(Activity act, int resource, ArrayList<Present> data) {
        super(act, resource, data);
        layoutResource = resource;
        activity = act;
        listaNavidad = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listaNavidad.size();
    }

    @Override
    public Present getItem(int position) {
        return listaNavidad.get(position);
    }

    @Override
    public int getPosition(Present item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if ( row == null || (row.getTag() == null)) {

            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, null);

            holder = new ViewHolder();

            holder.presentName = (TextView) row.findViewById(R.id.name);
            holder.presentDate = (TextView) row.findViewById(R.id.dateText);
            holder.presentPrize = (TextView) row.findViewById(R.id.price);
            holder.next = (TextView) row.findViewById(R.id.hasChild);
            holder.delete = (TextView) row.findViewById(R.id.deleteItem);

            row.setTag(holder);

        }else {

            holder = (ViewHolder) row.getTag();
        }


        holder.present = getItem(position);

        holder.presentName.setText(holder.present.getPresentName());
        holder.presentDate.setText(holder.present.getRecordDate());
        holder.presentPrize.setText(String.valueOf(holder.present.getPresentPrice()));

        final ViewHolder finalHolder = holder;
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity, PresentsItemDetailsActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("userObj", finalHolder.present);
                i.putExtras(mBundle);


                activity.startActivity(i);

            }
        });
        final int id = holder.present.getPresentId();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(id);
            }
        });

        return row;

    }

    private void deleteItem(int id) {
        DatabaseHandler dba = new DatabaseHandler(this.getContext());
        dba.deletePresent(id);
        Intent intent = new Intent(this.getContext(), DisplayPresentsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.getContext().startActivity(intent);
    }

    public class ViewHolder {
        Present present;
        TextView presentName;
        TextView presentPrize;
        TextView presentDate;
        TextView next;
        TextView delete;

    }
}


