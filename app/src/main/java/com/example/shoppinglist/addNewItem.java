package com.example.shoppinglistmaster;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.shoppinglistmaster.database.databaseHandler;
import com.example.shoppinglistmaster.model.makeListModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class addNewItem extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newListText;
    private Button newItemSaveButton;
    private databaseHandler data_base;

    public static com.example.shoppinglistmaster.addNewItem newInstance() {
        return new com.example.shoppinglistmaster.addNewItem();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_list, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newListText = Objects.requireNonNull(getView()).findViewById(R.id.newListText);
        newItemSaveButton = getView().findViewById(R.id.newListButton);

        data_base = new databaseHandler(getActivity());
        data_base.openDatabase();

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String item = bundle.getString("item");
            newListText.setText(item);
           // assert item != null;
            //if (item.length() > 0)
                //newItemSaveButton.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
              //  newItemSaveButton.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark));

        }
        newListText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    newItemSaveButton.setEnabled(false);
                    newItemSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newItemSaveButton.setEnabled(true);
                    newItemSaveButton.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        final boolean finalIsUpdate = isUpdate;
        newItemSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = newListText.getText().toString();
                if(finalIsUpdate){
                    data_base.updateItem(bundle.getInt("id"), text);
                }
                else {
                    makeListModel item = new makeListModel();
                    item.setList(text);
                    item.setStatus(0);
                    data_base.insertTask(item);
                }
                dismiss();
            }
        });
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof com.example.shoppinglistmaster.DialogCloseListener)
            ((com.example.shoppinglistmaster.DialogCloseListener)activity).handleDialogClose(dialog);
    }
}
