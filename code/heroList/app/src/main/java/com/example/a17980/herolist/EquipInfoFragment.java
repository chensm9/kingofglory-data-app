package com.example.a17980.herolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EquipInfoFragment extends Fragment {
    private static final String ARG_PARAM = "param";
    private int mParam;//用来表示当前需要展示的是哪一页
    public EquipInfoFragment() {
        // Required empty public constructor
    }
    public static EquipInfoFragment newInstance(int param) {
        EquipInfoFragment fragment = new EquipInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.equip_list, container, false);
        GridView gridView = view.findViewById(R.id.gridView);

        //根据mParam来判断当前展示的是哪一页，根据页数的不同展示不同的信息
        String[] type = {"攻击", "法术", "防御", "移动", "打野", "辅助"};
        gridView.setAdapter(new EquipListViewAdapter(view.getContext(), type[mParam]));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                View dialogView = View.inflate(view.getContext(), R.layout.equip_dialog, null);
                String equip_name = ((TextView)view.findViewById(R.id.equip_name)).getText().toString();

                EquipItem equip = myDB.getInstance().get_equip(equip_name);

                TextView Name = dialogView.findViewById(R.id.equip_name);
                Name.setText(equip_name);
                TextView Price = dialogView.findViewById(R.id.equip_price);
                Price.setText((equip.getPrice()));
                ImageView Icon = dialogView.findViewById(R.id.equip_icon);
                Icon.setImageBitmap(equip.getImage());
                TextView Detail = dialogView.findViewById(R.id.equip_detail);
                Detail.setText(equip.getBase_attr()+'\n'+equip.getEquip_skill());

                dialog.setView(dialogView);
                dialog.show();
            }
        });
        return view;
    }

}
