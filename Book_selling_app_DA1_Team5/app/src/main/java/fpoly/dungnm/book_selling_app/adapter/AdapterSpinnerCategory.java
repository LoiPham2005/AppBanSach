package fpoly.dungnm.book_selling_app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.dungnm.book_selling_app.R;
import fpoly.dungnm.book_selling_app.models.ModelCategory;

public class AdapterSpinnerCategory extends BaseAdapter {
    private Context mContext;
    private ArrayList<ModelCategory> list;

    public AdapterSpinnerCategory(Context mContext, ArrayList<ModelCategory> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_spiner_category, null);
        }
        TextView tvDistributor = convertView.findViewById(R.id.tvDistributor);
        ModelCategory cate = list.get(position);
        tvDistributor.setText(cate.getCategoryName());

        return convertView;
    }
}

