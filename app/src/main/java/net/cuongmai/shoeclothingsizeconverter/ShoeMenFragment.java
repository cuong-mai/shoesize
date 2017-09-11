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
public class ShoeMenFragment extends Fragment {
    private ArrayList<Size> sizeList;
    private final String TABLE_NAME = DatabaseHelper.TABLE_MEN;
    private final int LISTVIEW_ID = R.id.list_men;

    public ShoeMenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity context = getActivity();

        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        sizeList = databaseHelper.getAllSizes(TABLE_NAME);

        View view = inflater.inflate(R.layout.fragment_shoe_men, container, false);
        ListView listView = (ListView) view.findViewById(LISTVIEW_ID);

        SizeArrayAdapter adapter = new SizeArrayAdapter(context, sizeList, databaseHelper, TABLE_NAME);

        listView.setAdapter(adapter);

        return view;
    }

}
