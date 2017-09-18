package net.cuongmai.myshoesize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cuong Mai on 9/09/17.
 */

public class SizeArrayAdapter extends ArrayAdapter<Size> {
    private final int BOOKMARK_IMAGE_SELECTED_ID = R.drawable.bookmark_selected_red;
    private final int BOOKMARK_IMAGE_UNSELECTED_ID = R.drawable.bookmark_unselected;

    private Activity mContext;
    private DatabaseHelper mDatabaseHelper;
    private String mTableName;
    private ArrayList<Size> mSizeList;

    public SizeArrayAdapter(Activity context, ArrayList<Size> sizeList, DatabaseHelper databaseHelper, String tableName) {
        super(context, 0, sizeList);
        mContext = context;
        mSizeList = sizeList;
        mDatabaseHelper = databaseHelper;
        mTableName = tableName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        final Size currentSize = getItem(position);

        final TextView bookmarkNameTextView= (TextView) listItemView.findViewById(R.id.bookmark_name_text);
        bookmarkNameTextView.setText(currentSize.getBookmarkName());

        final ImageView bookmarkImageView = (ImageView) listItemView.findViewById(R.id.bookmark_img);

        if (currentSize.isBookmarked()) {
            bookmarkImageView.setImageResource(BOOKMARK_IMAGE_SELECTED_ID);
        } else {
            bookmarkImageView.setImageResource(BOOKMARK_IMAGE_UNSELECTED_ID);
        }

        TextView usSizeTextView= (TextView) listItemView.findViewById(R.id.us_size_text);
        usSizeTextView.setText(currentSize.getUs());

        TextView euroSizeTextView= (TextView) listItemView.findViewById(R.id.euro_size_text);
        euroSizeTextView.setText(currentSize.getEuro());

        TextView ukSizeTextView= (TextView) listItemView.findViewById(R.id.uk_size_text);
        ukSizeTextView.setText(currentSize.getUk());

        TextView inSizeTextView= (TextView) listItemView.findViewById(R.id.in_size_text);
        inSizeTextView.setText(currentSize.getIn());

        TextView cmSizeTextView= (TextView) listItemView.findViewById(R.id.cm_size_text);
        cmSizeTextView.setText(currentSize.getCm());


        bookmarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ImageView imageView = (ImageView) view;

                final int newImageViewResourceId;
                final String newBookmarkName;

                if (currentSize.isBookmarked()) {
                    imageView.setImageResource(BOOKMARK_IMAGE_UNSELECTED_ID);
                    currentSize.setBookmarkName("");
                    bookmarkNameTextView.setText("");
                    mDatabaseHelper.updateSizeBookmarkName(mTableName, currentSize.getId(), "");
                } else {
                    final AlertDialog.Builder inputAlert = new AlertDialog.Builder(mContext);
                    inputAlert.setTitle("Bookmark the Size");
                    inputAlert.setMessage("Persons having this size");

                    final EditText userInput = new EditText(mContext);
                    inputAlert.setView(userInput);

                    inputAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String userInputValue = userInput.getText().toString();

                            imageView.setImageResource(BOOKMARK_IMAGE_SELECTED_ID);
                            currentSize.setBookmarkName(userInputValue);
                            bookmarkNameTextView.setText(userInputValue);
                            mDatabaseHelper.updateSizeBookmarkName(mTableName, currentSize.getId(), userInputValue);

                        }
                    });

                    inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = inputAlert.create();
                    alertDialog.show();

                }


            }
        });

        return listItemView;
    }
}
