package com.newer.eshop.me;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.account.LoginActivity;
import com.newer.eshop.account.SettingActivity;
import com.newer.eshop.goods.GoodsActivity;
import com.newer.eshop.me.order.AllOrderActivity;
import com.tencent.tauth.Tencent;
import com.newer.eshop.account.PersonalActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr_LUO on 2016/4/21.
 */
public class FragmentMe extends Fragment implements View.OnClickListener {
    private static final String APP_ID = "1105291740";
    private static final int PHOTO_REQUEST_CUT = 1;
    private Tencent mTencent;
    Context context;
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String Key = "Mytoken";
    GridView  gridView;
    private List<Map<String,Object>> mylist;
    private SimpleAdapter griadadpter;
    CircleImageView imageView;
    String phone;
    String name;

    private  int[] icon ={R.drawable.me_order,R.drawable.me_comment,R.drawable.me_like,
            R.drawable.me_personal_information, R.drawable.me_address,R.drawable.me_change_psw};

    private  String [] iconname ={"我的订单","评价","喜欢","个人信息","地址","修改密码"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);

        Button goods = (Button) view.findViewById(R.id.goods);
        TextView  tv_name = (TextView) view.findViewById(R.id.login);
        gridView= (GridView) view.findViewById(R.id.me_gridview);
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("login_user_im",Context.MODE_PRIVATE);
        phone=sharedPreferences.getString("phone",null);
        name =sharedPreferences.getString("name",null);

        imageView= (CircleImageView) view.findViewById(R.id.im_head);
        if (name==null && phone==null){
            tv_name.setText("请登录/注册");
            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     startActivity(new Intent(getContext(),LoginActivity.class));
                }
            });

        }else {
//            User user =new User();
//            tv_name.setText(preferences.getString("name",user.getName()));

        }

        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GoodsActivity.class));
            }
        });
        imageView.setOnClickListener(this);
        view.findViewById(R.id.me_top_setting).setOnClickListener(this);
        view.findViewById(R.id.me_top_login).setOnClickListener(this);
        view.findViewById(R.id.btn_login_out).setOnClickListener(this);
//       context =view.getContext().getApplicationContext();
//        mTencent = Tencent.createInstance(APP_ID, context);

      mylist= new ArrayList<Map<String, Object>>();
        getdata();
        String [] from ={"image","text"};
        int [] to ={R.id.iv,R.id.tv};
        griadadpter= new SimpleAdapter(getContext(),mylist,R.layout.me_gridview_item,from,to);
        gridView.setAdapter(griadadpter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getActivity(), AllOrderActivity.class).putExtra("phone", phone));
                        break;
                    case 3:
                        if (phone==null && name==null){
                            Toast.makeText(getContext(), "请登录", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(),LoginActivity.class));

                        }else {
                            startActivity(new Intent(getContext(),PersonalActivity.class));
                        }
                        break;
                    case  4:
                        if (phone==null &&name==null){
                            Toast.makeText(getContext(), "请登录", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(),LoginActivity.class));
                        }
                       else {
                        }



                }
            }
        });

        return view;

    }






    public List<Map<String, Object>> getdata() {
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconname[i]);
           mylist.add(map);
        }
        return mylist;
    }



    public  void  removesharepreference(){
            preferences=getContext().getSharedPreferences("login_user_im", App.MODE_PRIVATE);
        editor=preferences.edit();
        editor.remove(Key);
        editor.remove("name");
        editor.remove("address");
        editor.remove("phone");
        editor.commit();
        Toast.makeText(getContext(), "注销成功", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode == 1){
            Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
        }

        Uri selectedImage = data.getData();
        crop(selectedImage);
        String[] filePathColumns={MediaStore.Images.Media.DATA};
        Cursor c = getContext().getContentResolver().query(selectedImage, filePathColumns, null,null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePathColumns[0]);
        String picturePath= c.getString(columnIndex);
        c.close();
        //获取图片并显示



    }


    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.im_head:
                startActivity(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI));





                break;

            case  R.id.me_top_setting:
                startActivity(new Intent(getContext(),SettingActivity.class));
                break;
            case R.id.me_top_login:
                startActivity(new Intent(getContext(),LoginActivity.class));
                break;
            case R.id.btn_login_out:


                removesharepreference();
//                mTencent.logout(context);
                startActivity(new Intent(getContext(),LoginActivity.class));

                break;
        }

    }
}
