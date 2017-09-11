package net.cuongmai.shoeclothingsizeconverter;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SizeChartFragment extends Fragment {
    private String mTableName;

    private ArrayList<Size> sizeList;

    public static final String STRING_TABLE_NAME = "tableName";

    private final int LISTVIEW_ID = R.id.list_size;

    public SizeChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            mTableName = getArguments().getString(STRING_TABLE_NAME);
        }

        Activity context = getActivity();

        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        sizeList = databaseHelper.getAllSizes(mTableName);

        View view = inflater.inflate(R.layout.fragment_size_chart, container, false);
        ListView listView = (ListView) view.findViewById(LISTVIEW_ID);

        SizeArrayAdapter adapter = new SizeArrayAdapter(context, sizeList, databaseHelper, mTableName);

        listView.setAdapter(adapter);

        return view;
    }

}
