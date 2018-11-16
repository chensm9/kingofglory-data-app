package com.example.a17980.herolist;

//public class EpigraphSelectAdapter extends BaseAdapter {
//    private List<attr_collection> list;
//    LayoutInflater inflater;
//    public EpigraphSelectAdapter(Context context, List<attr_collection> list) {
//        this.list = list;
//        inflater = LayoutInflater.from(context);
//    }
//    @Override
//    public int getCount() {
//        if (list == null) {
//            return 0;
//        }
//        return list.size();
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        if (list == null) {
//            return null;
//        }
//        return list.get(i);
//    }
//
//    @Override
//    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        ViewHolder viewHolder;
//        // 当view为空时才加载布局，否则，直接修改内容
//        if (convertView == null) {
//            // 通过inflate的方法加载布局，context需要在使用这个Adapter的Activity中传入。
//            convertView = inflater.inflate(R.layout.epigraph_select, null);
//            viewHolder = new ViewHolder();
//            viewHolder.attr_name = convertView.findViewById(R.id.attr_name);
//            viewHolder.attr_count = convertView.findViewById(R.id.attr_count);
//            convertView.setTag(viewHolder); // 用setTag方法将处理好的viewHolder放入view中
//        } else { // 否则，让convertView等于view，然后从中取出ViewHolder即可
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        // 从viewHolder中取出对应的对象，然后赋值给他们
//        viewHolder.attr_name.setText(list.get(i).getM_attr());
//        viewHolder.attr_count.setText("+"+list.get(i).getCount()+"");
//        final attr_collection c = list.get(i);
//        final int t = i;
//        // 将这个处理好的view返回
//        return convertView;
//    }
//    private class ViewHolder {
//        public DefineView sample;
//        public TextView name;
//    }
//
//
//}
