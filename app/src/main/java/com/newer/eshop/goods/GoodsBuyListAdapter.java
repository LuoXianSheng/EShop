package com.newer.eshop.goods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Cart;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Mr_LUO on 2016/4/26.
 */
public class GoodsBuyListAdapter extends BaseAdapter {

    private Context context;
    private List<Cart> list;

    public GoodsBuyListAdapter(Context context, List<Cart> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.goods_buy_list_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.goods_buy_list_item_img);
            holder.name = (TextView) convertView.findViewById(R.id.goods_buy_list_item_name);
            holder.id = (TextView) convertView.findViewById(R.id.goods_buy_list_item_id);
            holder.price = (TextView) convertView.findViewById(R.id.goods_buy_list_item_price);
            holder.count = (TextView) convertView.findViewById(R.id.goods_buy_list_item_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Cart cart = list.get(position);
        String str = cart.getGoods().getImage_path();
        String imgPath = str.substring(0, str.indexOf(",")) + ".jpg";
        ImageLoader.getInstance().displayImage(App.SERVICE_IMAGES_URL + imgPath, holder.img, App.initOptions());
        holder.name.setText(cart.getGoods().getName());
        holder.id.setText("型号：" + cart.getGoods().getId());
        holder.price.setText("单价：" + cart.getGoods().getPrice());
        holder.count.setText("x" + cart.getCount());
        return convertView;
    }

    class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView id;
        public TextView price;
        public TextView count;
    }
}
