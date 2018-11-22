package com.example.a17980.herolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HeroInfoFragment extends Fragment {
    private static final String ARG_PARAM = "param";
    private static List<Fragment> fragmentList;
    private HeroListViewAdapter myAdapter = null;
    private int mParam;//用来表示当前需要展示的是哪一页

    public HeroInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_PARAM);
        }
    }

    public HeroListViewAdapter getAdapter() {
        return myAdapter;
    }

    public static List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public static HeroInfoFragment newInstance(int param, List<Fragment> list) {
        fragmentList = list;
        HeroInfoFragment fragment = new HeroInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.hero_list, container, false);
        final GridView gridView = view.findViewById(R.id.gridView);

        //根据mParam来判断当前展示的是哪一页，根据页数的不同展示不同的信息
        final String[] type = {"全部", "坦克", "战士", "刺客", "法师", "射手", "辅助"};

        final  HeroListViewAdapter heroListViewAdapter = new HeroListViewAdapter(view.getContext(), type[mParam]);
        myAdapter = heroListViewAdapter;

        gridView.setAdapter(myAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    String hero_name = ((TextView)view.findViewById(R.id.heroName)).getText().toString();
                    if (hero_name.equals("添加英雄")) {
                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
                        final View dialogView = View.inflate(view.getContext(), R.layout.dialog, null);
                        GridView gridView1 = dialogView.findViewById(R.id.gridView);
                        final HeroDialogListViewAdapter heroDialogListViewAdapter =
                                new HeroDialogListViewAdapter(dialogView.getContext(), type[mParam]);
                        gridView1.setAdapter(heroDialogListViewAdapter);
                        if (heroDialogListViewAdapter.getCount() == 0) {
                            TextView title = dialogView.findViewById(R.id.title);
                            title.setText("无可添加的英雄");
                            dialogView.findViewById(R.id.gridView).setVisibility(View.GONE);
                            dialogBuilder.setView(dialogView).create().show();
                            return;
                        }
                        dialogBuilder.setView(dialogView);
                        final AlertDialog dialog = dialogBuilder.create();
                        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                TextView heroName = view.findViewById(R.id.hero_name);
                                String hero_name = heroName.getText().toString();
                                Hero hero = (Hero)heroDialogListViewAdapter.getItem(position);
                                heroListViewAdapter.addItem(hero);
                                if (type[mParam].equals("全部")) {
                                    for (int i = 0;i < type.length; i++) {
                                        if (hero.getHeroRole().equals(type[i])) {
                                            ((HeroInfoFragment)HeroInfoFragment.getFragmentList().get(i))
                                                    .getAdapter().addItem(hero);
                                            break;
                                        }
                                    }
                                } else {
                                    ((HeroInfoFragment)HeroInfoFragment.getFragmentList().get(0))
                                            .getAdapter().addItem(hero);
                                }
                                myDB.getInstance().update_like_hero(hero_name, 1);
                                heroDialogListViewAdapter.removeItem(position);
                                Toast.makeText(view.getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    } else {
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);;
                        intent.putExtra("detail", hero_name);
                        startActivityForResult(intent, 0);
                    }
            }
        });

        //设置事件监听(长按删除)
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view,
                                           final int position, long id) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                TextView heroName = view.findViewById(R.id.heroName);
                final String hero_name = heroName.getText().toString();
                if (heroName.getText().equals("添加英雄")) {
                    dialog.setMessage("请别瞎几把长按，谢谢配合");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                    return true;
                }
                dialog.setMessage("是否将英雄\"" + hero_name +"\"从喜欢列表移除");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDB.getInstance().update_like_hero(hero_name, 0);
                        Hero hero = (Hero) heroListViewAdapter.getItem(position);
                        if (type[mParam].equals("全部")) {
                            for (int i = 0;i < type.length; i++) {
                                if (hero.getHeroRole().equals(type[i])) {
                                    ((HeroInfoFragment)HeroInfoFragment.getFragmentList().get(i))
                                            .getAdapter().removeItemByName(hero_name);
                                    break;
                                }
                            }
                        } else {
                            ((HeroInfoFragment)HeroInfoFragment.getFragmentList().get(0))
                                    .getAdapter().removeItemByName(hero_name);
                        }

                        heroListViewAdapter.removeItem(position);
                        Toast.makeText(view.getContext(), "移除成功.", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消", null);
                dialog.create().show();
                return true;
            }
        });

//        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//        final String[] type = {"全部", "坦克", "战士", "刺客", "法师", "射手", "辅助"};
//        final HeroRecyclerViewAdapter myAdapter = new HeroRecyclerViewAdapter<Hero>(view.getContext(), R.layout.item, myDB.getInstance().get_hero_list(type[mParam])) {
//            @Override
//            public void convert(MyViewHolder holder, Hero h) {
//                TextView heroName = holder.getView(R.id.heroName);
//                heroName.setText(h.getHeroName());
//                ImageView heroIcon = holder.getView(R.id.heroIcon);
//                heroIcon.setImageBitmap(h.getHeroIcon());
//            }
//        };
//        recyclerView.setAdapter(myAdapter);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),3);//3列
//        recyclerView.setLayoutManager(gridLayoutManager);
        return view;
    }
}
