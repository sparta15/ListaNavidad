package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if ( row == null || (row.getTag() == null)) {

            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, null);

            holder = new ViewHolder();

            holder.presentName = (TextView) row.findViewById(R.id.name);
            holder.presentDate = (TextView) row.findViewById(R.id.dateText);
            holder.presentPrize = (TextView) row.findViewById(R.id.price);

            row.setTag(holder);

        }else {

            holder = (ViewHolder) row.getTag();
        }


        holder.present = getItem(position);

        holder.presentName.setText(holder.present.getPresentName());
        holder.presentDate.setText(holder.present.getRecordDate());
        holder.presentPrize.setText(String.valueOf(holder.present.getPresentPrize()));

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity, PresentsItemDetailsActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("userObj", finalHolder.present);
                i.putExtras(mBundle);


                activity.startActivity(i);

            }
        });

        return row;

    }

    public class ViewHolder {
        Present present;
        TextView presentName;
        TextView presentPrize;
        TextView presentDate;

    }
}


